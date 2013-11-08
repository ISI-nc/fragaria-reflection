package nc.isi.fragaria_reflection.services;

import java.util.concurrent.ExecutionException;

import nc.isi.fragaria_reflection.utils.ObjectMetadata;

import com.google.common.base.Throwables;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ObjectMetadataProviderImpl implements ObjectMetadataProvider {

	private final LoadingCache<Class<?>, ObjectMetadata> cache = CacheBuilder
			.newBuilder().build(new CacheLoader<Class<?>, ObjectMetadata>() {

				@Override
				public ObjectMetadata load(Class<?> key) {
					return new ObjectMetadata(key);
				}

			});

	public ObjectMetadata provide(Class<?> typeClass) {
		try {
			return cache.get(typeClass);
		} catch (ExecutionException e) {
			throw Throwables.propagate(e);
		}
	}
}
