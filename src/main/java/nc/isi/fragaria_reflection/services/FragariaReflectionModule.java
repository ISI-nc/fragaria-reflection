package nc.isi.fragaria_reflection.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.Scanner;

public class FragariaReflectionModule {
	public static void bind(ServiceBinder binder) {
		binder.bind(ScannerProvider.class, ScannerProviderImpl.class);
		binder.bind(ReflectionProvider.class, ReflectionProviderImpl.class);
		binder.bind(ResourceFinder.class, ResourceFinderImpl.class);
		binder.bind(ObjectMetadataProvider.class,
				ObjectMetadataProviderImpl.class);
	}

	public void contributeScannerProvider(Configuration<Scanner> scanners) {
		scanners.add(new ResourcesScanner());
	}

}
