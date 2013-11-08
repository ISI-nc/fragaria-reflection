package nc.isi.fragaria_reflection.services;

import nc.isi.fragaria_reflection.utils.ObjectMetadata;

public interface ObjectMetadataProvider {

	ObjectMetadata provide(Class<?> typeClass);

}
