package objs.pickups;

import game.DrawEngine;
import objs.pickups.effects.impl.SpeedEffect;

public class HpPickup extends Pickup{

	public HpPickup(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
		this.effect = new SpeedEffect(120);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(150,150,0), position.x, position.y, size, size);
		
	}

	@Override
	public void integrate() {
		// TODO Auto-generated method stub
		
	}

}