package objs.particles;

import game.DrawEngine;
import game.IDrawable;
import processing.core.PVector;

public class Particle implements IDrawable {

	public PVector position, velocity;
	public float radius;
	
	public Particle(PVector position, PVector velocity, float radius) {
		this.position = position;
		this.velocity = velocity;
		this.radius = radius;
	}
	
	public Particle(PVector position, float mouseX, float mouseY, float radius) {
		this.position = position;
		this.velocity = new PVector((mouseX - position.x), (mouseY - position.y)).normalize().mult(10f);
		this.radius = radius;
	}
	
	
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size);
	}

	public void integrate() {
		position.x += velocity.x;
		position.y += velocity.y;
	}
}
