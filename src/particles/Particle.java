package particles;

import game.DrawEngine;
import game.IDrawable;
import processing.core.PVector;

public class Particle implements IDrawable {

	public PVector position, velocity;
	
	public Particle(PVector position, PVector velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	public Particle(PVector position, int destX, int destY) {
		this.position = position;
		this.velocity = new PVector((destX - position.x), (destY - position.y)).normalize().mult(10f);
	}
	
	
	public void display(DrawEngine drawEngine) {
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, 10, 10);
	}

	public void integrate() {
		position.x += velocity.x;
		position.y += velocity.y;
	}
}
