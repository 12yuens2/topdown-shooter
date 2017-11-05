package objs.pickups.impl;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.SpeedEffect;

public class SpeedPickup extends Pickup{

	public SpeedPickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius, lifespan);
		this.effect = new SpeedEffect(120);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(150,150,0), position.x, position.y, size, size, lifespan);
		
	}


}