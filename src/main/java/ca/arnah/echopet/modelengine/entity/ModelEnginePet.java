package ca.arnah.echopet.modelengine.entity;

import com.dsh105.echopet.api.pet.Pet;
import com.dsh105.echopet.compat.api.entity.IPetType;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.entity.Player;

/**
 * @author Arnah
 * @since Sep 25, 2021
 **/
public class ModelEnginePet extends Pet{
	
	private ModeledEntity modeledEntity;
	
	public ModelEnginePet(Player owner, IPetType petType){
		super(owner);
		setPetType(petType);
	}
	
	@Override
	protected void setPetType(){
		//
	}
	
	public void setModeledEntity(ModeledEntity entity){
		this.modeledEntity = entity;
	}
	
	@Override
	public void removePet(boolean makeSound, boolean makeParticles){
		super.removePet(makeSound, makeParticles);
		modeledEntity.clearModels();
		modeledEntity = null;
	}
}
