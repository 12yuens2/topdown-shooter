package objs.pickups.effects;

import java.io.Serializable;

import objs.characters.Character;
import objs.characters.PlayerCharacter;

public abstract class Effect implements Serializable {

	public int lifespan;
	
	public abstract <T extends PlayerCharacter> void apply(T character);
	
	public abstract <T extends PlayerCharacter> void cease(T character);

	
}
