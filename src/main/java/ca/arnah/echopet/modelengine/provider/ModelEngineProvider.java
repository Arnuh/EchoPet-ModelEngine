package ca.arnah.echopet.modelengine.provider;

import ca.arnah.echopet.modelengine.EchoPetModelEngine;
import com.dsh105.echopet.compat.api.plugin.hook.PluginDependencyProvider;
import com.ticxo.modelengine.api.ModelEngineAPI;

/**
 * @author Arnah
 * @since Sep 14, 2021
 **/
public class ModelEngineProvider extends PluginDependencyProvider<EchoPetModelEngine, ModelEngineAPI>{
	
	
	public ModelEngineProvider(EchoPetModelEngine myPluginInstance){
		super(myPluginInstance, "ModelEngine");
	}
	
	@Override
	public void onHook(){
		getHandlingPlugin().load();
	}
	
	@Override
	public void onUnhook(){
		getHandlingPlugin().cleanup();
	}
}
