package objs.pickups.impl;

import game.DrawEngine;
import game.GameContext;
import objs.pickups.Pickup;
import objs.pickups.effects.impl.BombEffect;

public class BombPickup extends Pickup {

	public BombPickup(float xPos, float yPos, float radius, int lifespan, GameContext context) {
		super(xPos, yPos, radius, lifespan);
		this.effect = new BombEffect(context);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(75), position.x, position.y, size, size, lifespan);
		drawEngine.drawText(15, "B", (int) position.x, (int) position.y, drawEngine.parent.color(255));
	}

}
