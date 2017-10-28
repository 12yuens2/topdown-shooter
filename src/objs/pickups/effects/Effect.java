package objs.pickups.effects;

import objs.characters.Character;

public abstract class Effect{

	public int lifespan;
	
	public abstract <T extends Character> void apply(T character);
	
	public abstract <T extends Character> void cease(T character);

	
}
