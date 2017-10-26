package objs.particles;

import game.DrawEngine;
import game.GameObject;
import processing.core.PVector;

public class Particle extends GameObject {

	public PVector velocity;
	public int damage;
	
	public Particle(float xPos, float yPos, float radius, int damage) {
		super(xPos, yPos, radius);
		this.velocity = new PVector(0,0);
		this.damage = damage;
	}
	
	public Particle(PVector position, float mouseX, float mouseY, float radius, int damage) {
		this(position.x, position.y, radius, damage);
		this.velocity = new PVector((mouseX - position.x), (mouseY - position.y)).normalize().mult(10f);
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
