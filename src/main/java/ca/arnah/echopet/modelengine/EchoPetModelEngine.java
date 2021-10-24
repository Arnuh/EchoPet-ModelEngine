package ca.arnah.echopet.modelengine;

import ca.arnah.echopet.modelengine.entity.ModelEnginePet;
import ca.arnah.echopet.modelengine.entity.ModelEnginePetRegistrationEntry;
import ca.arnah.echopet.modelengine.entity.ModelPetTypes;
import ca.arnah.echopet.modelengine.provider.EchoPetProvider;
import ca.arnah.echopet.modelengine.provider.ModelEngineProvider;
import com.dsh105.echopet.compat.api.config.YAMLConfig;
import com.dsh105.echopet.compat.api.config.YAMLConfigManager;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.dsh105.echopet.compat.api.entity.PetData;
import com.dsh105.echopet.compat.api.entity.PetType;
import com.dsh105.echopet.compat.api.event.PetSpawnEvent;
import com.dsh105.echopet.compat.api.util.Logger;
import com.ticxo.modelengine.api.manager.ModelManager;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Arnah
 * @since Sep 14, 2021
 **/
public class EchoPetModelEngine extends JavaPlugin implements Listener{
	
	private static EchoPetModelEngine plugin;
	private EchoPetProvider echoPetProvider;
	private ModelEngineProvider modelEngineProvider;
	private YAMLConfigManager configManager;
	private YAMLConfig config;
	private ConfigOptions options;
	
	@Override
	public void onEnable(){
		plugin = this;
		configManager = new YAMLConfigManager(this);
		loadConfiguration();
		echoPetProvider = new EchoPetProvider(this);
		modelEngineProvider = new ModelEngineProvider(this);
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void load(){
		if(getEchoPetProvider() == null || !getEchoPetProvider().isHooked()){
			return;
		}
		if(!ModelPetTypes.pets.isEmpty()){
			return;
		}
		for(String model : getOptions().getPets()){
			ModelPetTypes type = new ModelPetTypes(this, model);
			ModelPetTypes.pets.add(type);
			var registry = getEchoPetProvider().getDependency().getPetRegistry();
			registry.register(type, new ModelEnginePetRegistrationEntry(type.name(), type.getPetClass(), type.getEntityClass(), type));
		}
		Logger.log(Logger.LogLevel.NORMAL, "Loaded %d Model Pets".formatted(ModelPetTypes.pets.size()), true);
		PetType.pets.addAll(ModelPetTypes.pets);
	}
	
	public void cleanup(){
		ModelPetTypes.pets.clear();
		PetType.pets.removeIf(pet->pet instanceof ModelPetTypes);
	}
	
	private void loadConfiguration(){
		try{
			config = configManager.getNewConfig("config.yml");
		}catch(Exception e){
			Logger.log(Logger.LogLevel.WARNING, "Configuration File [config.yml] generation failed.", e, true);
		}
		options = new ConfigOptions(config);
		config.reloadConfig();
	}
	
	@EventHandler
	public void petSpawn(PetSpawnEvent event){
		IPet pet = event.getPet();
		if(pet == null){
			return;
		}
		if(!(pet instanceof ModelEnginePet modelPet)){
			return;
		}
		
		ModelManager manager = modelEngineProvider.getDependency().getModelManager();
		
		ActiveModel model = manager.createActiveModel(pet.getPetType().name());
		
		if(model == null){
			pet.removePet(false, false);
			return;
		}
		pet.getCraftPet().setSilent(true);
		ModeledEntity entity = manager.createModeledEntity(pet.getCraftPet());
		entity.addActiveModel(model);
		entity.detectPlayers();
		entity.setWalking(true);
		entity.setInvisible(true);
		modelPet.setModeledEntity(entity);
		// Since we don't know what models can be mounted until the async load is done
		// We check once we know the model exists.
		if(pet.getPetType() instanceof ModelPetTypes petType){
			// Is mount entity the best way to check for mounting? canRide in IMountHandler doesn't work as i'd expect.
			if(model.getMountEntity() != null && !petType.getAllowedDataTypes().contains(PetData.RIDE)){
				petType.getAllowedDataTypes().add(PetData.RIDE);
			}
		}
	}
	
	public static EchoPetModelEngine getPlugin(){
		return plugin;
	}
	
	public EchoPetProvider getEchoPetProvider(){
		return echoPetProvider;
	}
	
	public YAMLConfig getMainConfig(){
		return config;
	}
	
	public ConfigOptions getOptions(){
		return options;
	}
}
