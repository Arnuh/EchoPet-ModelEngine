package ca.arnah.echopet.modelengine.provider;

import ca.arnah.echopet.modelengine.EchoPetModelEngine;
import com.dsh105.echopet.compat.api.plugin.IEchoPetPlugin;
import com.dsh105.echopet.compat.api.plugin.hook.PluginDependencyProvider;

/**
 * @author Arnah
 * @since Sep 14, 2021
 **/
public class EchoPetProvider extends PluginDependencyProvider<EchoPetModelEngine, IEchoPetPlugin>{
	
	public EchoPetProvider(EchoPetModelEngine myPluginInstance){
		super(myPluginInstance, "EchoPet");
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
