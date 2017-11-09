package objs.pickups.impl;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.AddAmmoEffect;

public class AmmoPickup extends Pickup {

	public AmmoPickup(float xPos, float yPos, float radius, int lifespan) {
		super(xPos, yPos, radius, lifespan);
		this.effect = new AddAmmoEffect();
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(75), position.x, position.y, size, size, lifespan);
		drawEngine.drawText(15, "A", (int) position.x, (int) position.y, drawEngine.parent.color(255));
	}


}
