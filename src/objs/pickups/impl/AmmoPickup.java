package objs.pickups.impl;

import game.DrawEngine;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.AddAmmoEffect;

public class AmmoPickup extends Pickup {

	public AmmoPickup(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
		this.effect = new AddAmmoEffect();
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(75), position.x, position.y, size, size);
	}

	@Override
	public void integrate() {
		/*
		 * No implementation required.
		 */
	}

}
