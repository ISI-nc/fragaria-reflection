package nc.isi.fragaria_reflection.services;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ResourceFinderImpl implements ResourceFinder {
	private static final long MAX_FILES_TIME = 10L;
	private static final Logger LOGGER = Logger
			.getLogger(ResourceFinderImpl.class);
	private final Reflections reflections;
	private final LoadingCache<String, Set<String>> cache = CacheBuilder
			.newBuilder().expireAfterAccess(MAX_FILES_TIME, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Set<String>>() {

				@Override
				public Set<String> load(String key) {
					return reflections.getResources(Pattern.compile(key));
				}
			});

	public ResourceFinderImpl(ReflectionProvider reflectionProvider) {
		reflections = reflectionProvider.provide();
	}

	@Override
	public Set<String> getResourcesMatching(String regExp) {
		LOGGER.info(String.format("looking for : " + regExp));
		try {
			return cache.get(regExp);
		} catch (ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

}
