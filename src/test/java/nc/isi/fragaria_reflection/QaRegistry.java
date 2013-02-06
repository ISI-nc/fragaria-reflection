package nc.isi.fragaria_reflection;

import org.apache.tapestry5.ioc.Registry;
import org.apache.tapestry5.ioc.RegistryBuilder;

public enum QaRegistry {
	INSTANCE;

	private final Registry registry = RegistryBuilder
			.buildAndStartupRegistry(QaModule.class);

	public Registry getRegistry() {
		return registry;
	}

}
