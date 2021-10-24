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
	private final List<PetData> allowedDataTypes;
	
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
	public List<PetData> getAllowedDataTypes(){
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
	public boolean isEnabled(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".enable", true);
	}
	
	@Override
	public boolean isTagVisible(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".tagVisible", true);
	}
	
	@Override
	public boolean isInteractMenuEnabled(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".interactMenu", true);
	}
	
	@Override
	public boolean allowRidersFor(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".allow.riders", true);
	}
	
	@Override
	public boolean isDataAllowed(PetData data){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".allow." + data.getConfigKeyName(), true);
	}
	
	@Override
	public boolean isDataForced(PetData data){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".force." + data.getConfigKeyName(), false);
	}
	
	@Override
	public boolean canFly(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".canFly", false);
	}
	
	@Override
	public boolean canIgnoreFallDamage(){
		return getConfig().getBoolean("pets." + getConfigKeyName() + ".ignoreFallDamage", true);
	}
	
	@Override
	public double getWalkSpeed(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".walkSpeed", 0.37D);
	}
	
	@Override
	public float getRideSpeed(){
		return (float) getConfig().getDouble("pets." + getConfigKeyName() + ".rideSpeed", 0.2D);
	}
	
	@Override
	public float getFlySpeed(){
		return (float) getConfig().getDouble("pets." + getConfigKeyName() + ".flySpeed", 0.5D);
	}
	
	@Override
	public double getRideJumpHeight(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".rideJump", 0.6D);
	}
	
	@Override
	public double getStartFollowDistance(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".startFollowDistance", 6);
	}
	
	@Override
	public double getStopFollowDistance(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".stopFollowDistance", 2);
	}
	
	@Override
	public double getTeleportDistance(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".teleportDistance", 10);
	}
	
	@Override
	public double getFollowSpeedModifier(){
		return getConfig().getDouble("pets." + getConfigKeyName() + ".followSpeedModifier", 1);
	}
	
	private YAMLConfig getConfig(){
		return plugin.getMainConfig();
	}
}
