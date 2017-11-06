package objs.pickups;

import game.GameObject;
import objs.pickups.effects.Effect;
public abstract class Pickup extends GameObject{
	
	public static final int SPAWN_RATE = 200;
	
	public Effect effect;
	
	public int lifespan;
	
	public Pickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius);
		this.lifespan = lifespan;
	}
	
	@Override
	public void integrate() {
		lifespan--;
	}
	
	public void pickup() {
		//effect.apply();
	}
}
