package nc.isi.fragaria_reflection.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class DefaultObjectMetadata implements ObjectMetadata {
	private static final Logger LOGGER = Logger.getLogger(DefaultObjectMetadata.class);
	private final Class<?> tClass;
	private ImmutableSet<String> propertyNames;
	private LoadingCache<String, PropertyDescriptor> cache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, PropertyDescriptor>() {

				@Override
				public PropertyDescriptor load(String key) {
					return checkNotNull(
							BeanUtils.getPropertyDescriptor(tClass, key),
							"property %s should exist for class %s", key,
							tClass);
				}
			});

	private final Map<Entry<String, Class<? extends Annotation>>, Annotation> annotationCache = Maps
			.newHashMap();

	private LoadingCache<String, Class<?>[]> parameterClassesCache = CacheBuilder
			.newBuilder().build(new CacheLoader<String, Class<?>[]>() {

				@Override
				public Class<?>[] load(String key) throws Exception {
					Type type = getPropertyDescriptor(key).getReadMethod()
							.getGenericReturnType();
					if (type instanceof ParameterizedType) {
						ParameterizedType realType = ParameterizedType.class
								.cast(type);
						int length = realType.getActualTypeArguments().length;
						Class<?>[] classes = new Class[length];
						for (int i = 0; i < length; i++) {
							classes[i] = ReflectionUtils.getClass(realType
									.getActualTypeArguments()[i]);
						}
						return classes;
					}
					return new Class[0];
				}

			});

	public DefaultObjectMetadata(Class<?> tClass) {
		this.tClass = tClass;
	}

	@Override
	public ImmutableSet<String> propertyNames() {
		if (propertyNames == null) {
			Set<String> temp = Sets.newHashSet();
			for (PropertyDescriptor propertyDescriptor : BeanUtils
					.getPropertyDescriptors(tClass)) {
				String propertyName = propertyDescriptor.getName();
				temp.add(propertyName);
			}
			propertyNames = ImmutableSet.copyOf(temp);
		}
		return propertyNames;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getPropertyAnnotation(String propertyName,
			Class<T> annotation) {

		Entry<String, Class<? extends Annotation>> entry = new MyEntry<String, Class<? extends Annotation>>(
				propertyName, annotation);
		if (!annotationCache.keySet().contains(entry)) {
			annotationCache.put(entry, ReflectionUtils
					.getRecursivePropertyAnnotation(tClass, annotation,
							propertyName));
		}
		return (T) annotationCache.get(entry);
	}

	@Override
	public Class<?> getTypeClass() {
		return tClass;
	}

	@Override
	public Class<?> propertyType(String propertyName) {
		return getPropertyDescriptor(propertyName).getPropertyType();
	}

	@Override
	public PropertyDescriptor getPropertyDescriptor(String propertyName) {
		try {
			return cache.get(propertyName);
		} catch (ExecutionException e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * 
	 * Renvoie les types des paramètres de la propriété
	 * 
	 * @param propertyName
	 * @return
	 * @return
	 */
	@Override
	public Class<?>[] propertyParameterClasses(String propertyName) {
		try {
			return parameterClassesCache.get(propertyName);
		} catch (ExecutionException e) {
			throw Throwables.propagate(e);
		}
	}

	@Override
	public Object read(Object object, String propertyName) {
		LOGGER.debug(String.format("read %s in %s", propertyName, object));
		try {
			return getPropertyDescriptor(propertyName).getReadMethod().invoke(
					object, (Object[]) null);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw Throwables.propagate(e);
		}

	}

	@Override
	public Boolean canWrite(String propertyName) {
		return getPropertyDescriptor(propertyName).getWriteMethod() != null;
	}

	@Override
	public void write(Object object, String propertyName, Object value) {
		try {
			checkNotNull(getPropertyDescriptor(propertyName).getWriteMethod())
					.invoke(object, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
