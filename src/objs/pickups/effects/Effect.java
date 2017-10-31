package objs.pickups.effects;

import java.io.Serializable;

import objs.characters.Character;

public abstract class Effect implements Serializable {

	public int lifespan;
	
	public abstract <T extends Character> void apply(T character);
	
	public abstract <T extends Character> void cease(T character);

	
}
