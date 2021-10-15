package ca.arnah.echopet.modelengine.entity;

import java.lang.reflect.Constructor;
import com.dsh105.echopet.compat.api.entity.IEntityPet;
import com.dsh105.echopet.compat.api.entity.IPet;
import com.dsh105.echopet.compat.api.entity.IPetType;
import com.dsh105.echopet.compat.api.registration.PetRegistrationEntry;
import org.bukkit.entity.Player;

/**
 * @author Arnah
 * @since Sep 25, 2021
 **/
public class ModelEnginePetRegistrationEntry extends PetRegistrationEntry{
	
	private final IPetType petType;
	
	public ModelEnginePetRegistrationEntry(String name, Class<? extends IPet> petClass, Class<? extends IEntityPet> entityClass, IPetType petType){
		super(name, petClass, entityClass);
		this.petType = petType;
	}
	
	@Override
	protected Constructor<? extends IPet> lookupPetConstructor() throws NoSuchMethodException{
		return getPetClass().getConstructor(Player.class, IPetType.class);
	}
	
	public IPet createFor(Player owner){
		try{
			return getPetConstructor().newInstance(owner, petType);
		}catch(Exception e){
			throw new IllegalStateException("Failed to create pet object for " + owner.getName(), e);
		}
	}
}
