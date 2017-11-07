package objs.pickups.impl;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.PermanentDamageEffect;

public class PermanentDamagePickup extends Pickup {

	public PermanentDamagePickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius, lifespan);
		this.effect = new PermanentDamageEffect(); 
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size, lifespan);
	}

}
