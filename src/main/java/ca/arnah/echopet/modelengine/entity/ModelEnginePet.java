package ca.arnah.echopet.modelengine.entity;

import com.dsh105.echopet.api.pet.Pet;
import com.dsh105.echopet.compat.api.entity.IPetType;
import com.dsh105.echopet.compat.api.entity.PetData;
import com.dsh105.echopet.compat.api.plugin.EchoPet;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Particle;
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
	public void ownerRidePet(boolean flag){
		if(ownerRiding == flag) return;
		ownerIsMounting = true;
		if(isHat){
			setAsHat(false);
		}
		if(!isSpawned()){
			ownerIsMounting = false;
			return;
		}
		var handler = modeledEntity.getMountHandler();
		/*if(handler.hasDriver() == flag){
			ownerIsMounting = false;
			return;
		}*/
		// Without this set, setDriver does nothing.
		handler.setSteerable(true);
		if(flag){
			handler.setDriver(getOwner());
		}else{
			handler.dismountAll();
		}
		//ownerIsMounting = false;
		//teleportToOwner();
		ownerRiding = flag;
		getLocation().getWorld().spawnParticle(Particle.PORTAL, getLocation(), 1);
		EchoPet.getManager().setData(this, PetData.RIDE, ownerRiding);
	}
	
	@Override
	public void removePet(boolean makeSound, boolean makeParticles){
		super.removePet(makeSound, makeParticles);
		modeledEntity.clearModels();
		modeledEntity = null;
	}
}
