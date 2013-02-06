package nc.isi.fragaria_reflection;

import nc.isi.fragaria_reflection.services.FragariaReflectionModule;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.annotations.SubModule;

@SubModule(FragariaReflectionModule.class)
public class QaModule {
	public void contributeReflectionProvider(Configuration<String> configuration) {
		configuration.add("nc.isi.fragaria_reflection");
	}

}
