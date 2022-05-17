package ca.arnah.echopet.modelengine.entity;

import java.util.ArrayList;
import java.util.List;
import ca.arnah.echopet.modelengine.EchoPetModelEngine;
import com.dsh105.echopet.compat.api.config.YAMLConfig;
import com.dsh105.echopet.compat.api.entity.IEntityPet;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.dsh105.echopet.compat.api.entity.IPetType;
import com.dsh105.echopet.compat.api.entity.PetData;
import com.dsh105.echopet.compat.api.entity.PetDataCategory;
import com.dsh105.echopet.compat.api.entity.PetType;
import com.dsh105.echopet.compat.api.plugin.EchoPet;
import com.dsh105.echopet.compat.api.util.Version;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Arnah
 * @since Sep 25, 2021
 **/
public class ModelPetTypes implements IPetType{
	
	public static final List<IPetType> pets = new ArrayList<>();
	
	private final EchoPetModelEngine plugin;
	private final String name, configName;
	private final String defaultName;
	private final List<PetDataCategory> allowedCategories;
	private final List<PetData<?>> allowedDataTypes;
	
	public ModelPetTypes(EchoPetModelEngine plugin, String name){
		this.plugin = plugin;
		this.name = name;
		this.defaultName = name();
		this.configName = name().toLowerCase().replace("_", "");
		this.allowedCategories = new ArrayList<>();
		this.allowedDataTypes = new ArrayList<>();
		allowedDataTypes.add(PetData.HAT);
	}
	
	@Override
	public String name(){
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public String getDefaultName(){
		return defaultName;
	}
	
	@Override
	public String getDefaultName(String s){
		return defaultName;
	}
	
	@Override
	public String getMinecraftName(){
		return PetType.COW.getMinecraftName();
	}
	
	@Override
	public List<PetDataCategory> getAllowedCategories(){
		return allowedCategories;
	}
	
	@Override
	public List<PetData<?>> getAllowedDataTypes(){
		return allowedDataTypes;
	}
	
	@Override
	public IEntityPet getNewEntityPetInstance(Object world, IPet pet){
		return EchoPet.getPetRegistry().getRegistrationEntry(PetType.COW).createEntityPet(world, pet);
	}
	
	@Override
	public IPet getNewPetInstance(Player owner){
		if(owner != null){
			return EchoPet.getPetRegistry().spawn(this, owner);
		}
		return null;
	}
	
	@Override
	public Class<? extends IEntityPet> getEntityClass(){
		return PetType.COW.getEntityClass();
	}
	
	@Override
	public Class<? extends IPet> getPetClass(){
		return ModelEnginePet.class;
	}
	
	@Override
	public Version getVersion(){
		return PetType.COW.getVersion();
	}
	
	@Override
	public boolean isCompatible(){
		return PetType.COW.isCompatible();
	}
	
	@Override
	public Material getUIMaterial(){
		return PetType.COW.getUIMaterial();
	}
	
	@Override
	public String getConfigKeyName(){
		return configName;
	}
	
	@Override
	public YAMLConfig getConfig(){
		return plugin.getMainConfig();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getConfigValue(String variable, T defaultValue){
		return (T) getConfig().get("pets." + getConfigKeyName() + "." + variable, defaultValue);
	}
	
	@Override
	public ConfigurationSection getPetDataSection(PetData<?> data){
		return getConfig().getConfigurationSection("pets." + getConfigKeyName() + ".data." + data.getConfigKeyName());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getPetDataProperty(PetData<?> data, String variable, T defaultValue){
		ConfigurationSection section = getPetDataSection(data);
		if(section == null) return defaultValue;
		return (T) section.get("." + variable, defaultValue);
	}
	
	@Override
	public boolean isEnabled(){
		return getConfigValue("enable", true);
	}
	
	@Override
	public boolean isTagVisible(){
		return getConfigValue("tagVisible", true);
	}
	
	@Override
	public boolean isInteractMenuEnabled(){
		return getConfigValue("interactMenu", true);
	}
	
	@Override
	public boolean allowRidersFor(){
		return getConfigValue("riders", true);
	}
	
	@Override
	public boolean canFly(){
		return getConfigValue("canFly", false);
	}
	
	@Override
	public boolean canIgnoreFallDamage(){
		return getConfigValue("ignoreFallDamage", true);
	}
	
	@Override
	public double getWalkSpeed(){
		return getConfigValue("walkSpeed", 0.37D);
	}
	
	@Override
	public float getRideSpeed(){
		return getConfigValue("rideSpeed", 0.2D).floatValue();
	}
	
	@Override
	public float getFlySpeed(){
		return getConfigValue("flySpeed", 0.5D).floatValue();
	}
	
	@Override
	public double getRideJumpHeight(){
		return getConfigValue("rideJump", 0.6D);
	}
	
	@Override
	public double getStartFollowDistance(){
		return getConfigValue("startFollowDistance", 6);
	}
	
	@Override
	public double getStopFollowDistance(){
		return getConfigValue("stopFollowDistance", 2);
	}
	
	@Override
	public double getTeleportDistance(){
		return getConfigValue("teleportDistance", 10);
	}
	
	@Override
	public double getFollowSpeedModifier(){
		return getConfigValue("followSpeedModifier", 1);
	}
	
	@Override
	public boolean isDataAllowed(PetData<?> data){
		return getPetDataProperty(data, "allow", true);
	}
	
	@Override
	public boolean isDataForced(PetData<?> data){
		return getPetDataProperty(data, "force", false);
	}
	
	@Override
	public <T> T getDataDefaultValue(PetData<?> data, T defaultValue){
		return getPetDataProperty(data, "default", defaultValue);
	}
}
