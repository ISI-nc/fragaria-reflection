package nc.isi.fragaria_reflection.services;

import org.apache.tapestry5.ioc.ServiceBinder;

public class FragariaReflectionModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(ReflectionProvider.class, ReflectionProviderImpl.class);
		binder.bind(ResourceFinder.class, ResourceFinderImpl.class);
	}

}
