package nc.isi.fragaria_reflection.utils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;

import com.google.common.collect.ImmutableSet;

public interface ObjectMetadata {

	ImmutableSet<String> propertyNames();

	<T extends Annotation> T getPropertyAnnotation(String propertyName,
			Class<T> annotation);

	Class<?> getTypeClass();

	Class<?> propertyType(String propertyName);

	PropertyDescriptor getPropertyDescriptor(String propertyName);

	/**
	 * 
	 * Renvoie les types des paramètres de la propriété
	 * 
	 * @param propertyName
	 * @return
	 * @return
	 */
	Class<?>[] propertyParameterClasses(String propertyName);

	Object read(Object object, String propertyName);

	Boolean canWrite(String propertyName);

	void write(Object object, String propertyName, Object value);

}