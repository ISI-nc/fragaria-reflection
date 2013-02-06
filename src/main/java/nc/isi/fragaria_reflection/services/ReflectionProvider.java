package nc.isi.fragaria_reflection.services;

import org.reflections.Configuration;
import org.reflections.Reflections;

/**
 * Une factory pour générer des {@link Reflections} associé à un nom de package
 * ou à une {@link Configuration}
 * 
 * @author justin
 * 
 */
public interface ReflectionProvider {

	Reflections provide();

}