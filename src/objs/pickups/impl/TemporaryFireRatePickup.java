package objs.pickups.impl;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.TemporaryFireRateEffect;

public class TemporaryFireRatePickup extends Pickup {

	public TemporaryFireRatePickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius, lifespan);
		this.effect = new TemporaryFireRateEffect(150);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size, lifespan);
		drawEngine.drawText(16, "F", (int) position.x, (int) position.y, 255, lifespan);
	}

	
}
