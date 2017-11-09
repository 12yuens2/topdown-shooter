package objs.pickups;

import game.GameObject;
import objs.pickups.effects.Effect;

/**
 * A Pickup object which applies an effect to the character when it is picked up.
 * @author sy35
 *
 */
public abstract class Pickup extends GameObject{
	
	public static final int SPAWN_RATE = 500;
	public static final int LIFESPAN = 300;
	public static final int PATROL_SPAWN_RATE = 10;
	public static final int MIN_LIFESPAN = 100;
	
	public static final int MIN_OPACITY = 20;
	
	public Effect effect;
	
	public int lifespan;
	
	/**
	 * The effect attribute should be filled for any implementation of Pickup.
	 * @param xPos - x position of the pickup.
	 * @param yPos - y position of the pickup.
	 * @param radius - Radius size of the pickup.
	 * @param lifespan - How long the pickup lasts for on the ground. 
	 */
	public Pickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius);
		this.lifespan = lifespan;
	}
	
	@Override
	public void integrate() {
		lifespan--;
	}
}
