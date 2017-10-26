package objs.pickups;

import game.GameObject;
public abstract class Pickup extends GameObject{
	
	public Effect effect;
	
	public Pickup(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
	}
	
	public void pickup() {
		//effect.apply();
	}
}
