package ca.arnah.echopet.modelengine;

import java.util.Set;
import ca.arnah.echopet.modelengine.entity.ModelPetTypes;
import com.dsh105.echopet.compat.api.config.Options;
import com.dsh105.echopet.compat.api.config.YAMLConfig;
import com.dsh105.echopet.compat.api.entity.PetData;

/**
 * @author Arnah
 * @since Sep 27, 2021
 **/
public class ConfigOptions extends Options{
	
	public ConfigOptions(YAMLConfig config){
		super(config);
	}
	
	public Set<String> getPets(){
		var cs = config.getConfigurationSection("pets");
		return cs.getKeys(false);
	}
	
	@Override
	public void setDefaults(){
		if(config.getConfigurationSection("pets") != null){
			return;
		}
		ModelPetTypes petType = new ModelPetTypes(EchoPetModelEngine.getPlugin(), "kindletronsr");
		String defaultPet = "kindletronsr";
		set("pets." + defaultPet + ".enable", true);
		set("pets." + defaultPet + ".tagVisible", true);
		set("pets." + defaultPet + ".defaultName", "Kindletron Senior");
		set("pets." + defaultPet + ".interactMenu", true);
		set("pets." + defaultPet + ".startFollowDistance", 6);
		set("pets." + defaultPet + ".stopFollowDistance", 2);
		set("pets." + defaultPet + ".teleportDistance", 10);
		set("pets." + defaultPet + ".followSpeedModifier", 1);
		
		set("pets." + defaultPet + ".walkSpeed", 0.37D);
		set("pets." + defaultPet + ".rideSpeed", 0.2D);
		set("pets." + defaultPet + ".flySpeed", 0.5D);
		set("pets." + defaultPet + ".jumpHeight", 0.6D);
		
		set("pets." + defaultPet + ".ignoreFallDamage", true);
		
		set("pets." + defaultPet + ".canFly", false);
		set("pets." + defaultPet + ".allow.riders", true);
		
		for(PetData pd : PetData.values){
			// We don't know what models can be mounted at this time.
			// Show the config option exists as to prevent confusion.
			boolean skip = pd.equals(PetData.RIDE);
			if(petType.isValidData(pd) || skip){
				set("pets." + defaultPet + ".allow." + pd.getConfigKeyName(), !skip);
				set("pets." + defaultPet + ".force." + pd.getConfigKeyName(), false);
			}
		}
		config.saveConfig();
	}
}
