package objs.pickups;

import game.DrawEngine;

public class HpPickup extends Pickup{

	public HpPickup(float xPos, float yPos, float radius) {
		super(xPos, yPos, radius);
		// TODO Auto-generated constructor stub
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