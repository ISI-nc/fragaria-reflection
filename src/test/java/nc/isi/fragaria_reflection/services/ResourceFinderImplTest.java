package nc.isi.fragaria_reflection.services;

import java.util.Set;

import junit.framework.TestCase;
import nc.isi.fragaria_reflection.QaRegistry;

import org.junit.Test;

public class ResourceFinderImplTest extends TestCase {
	private ReflectionProvider reflectionProvider;
	private static final String TEST_REGEXP = ".*\\.yaml";

	@Override
	protected void setUp() throws Exception {
		reflectionProvider = QaRegistry.INSTANCE.getRegistry().getService(
				ReflectionProvider.class);
	}

	@Test
	public void testGetRessourcesMatching() {
		ResourceFinder resourceFinder = new ResourceFinderImpl(
				reflectionProvider);
		Set<String> files = resourceFinder.getResourcesMatching(TEST_REGEXP);
		assertNotNull("ne doit jamais retourner une liste null", files);
		assertTrue("aucun fichier trouvé", files.size() > 0);
		for (String file : files) {
			assertTrue("erreur de matching", file.matches(TEST_REGEXP));
		}
	}

}
