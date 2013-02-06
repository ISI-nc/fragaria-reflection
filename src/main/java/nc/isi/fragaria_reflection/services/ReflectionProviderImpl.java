package nc.isi.fragaria_reflection.services;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ReflectionProviderImpl implements ReflectionProvider {
	private static final Logger LOGGER = Logger
			.getLogger(ReflectionProviderImpl.class);
	private final Reflections reflections;

	public ReflectionProviderImpl(Collection<String> packageNames,
			ScannerProvider scannerProvider) {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		for (String packageName : packageNames) {
			LOGGER.info(String.format("adding package : %s", packageName));
			configurationBuilder.addUrls(ClasspathHelper
					.forPackage(packageName));
		}
		configurationBuilder.addScanners(scannerProvider.provide());
		reflections = new Reflections(configurationBuilder);
	}

	@Override
	public Reflections provide() {
		return reflections;
	}

}
