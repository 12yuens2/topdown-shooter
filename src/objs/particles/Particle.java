package objs.particles;

import game.DrawEngine;
import game.GameObject;
import processing.core.PVector;

public class Particle extends GameObject {

	public PVector velocity;
	public int damage;
	
	public boolean friendly, pierce;

	public Particle(float xPos, float yPos, float radius, int damage) {
		super(xPos, yPos, radius);
		this.velocity = new PVector(0,0);
		this.damage = damage;
		
		this.friendly = true;
		this.pierce = false;
	}
	
	public Particle(PVector position, float mouseX, float mouseY, float radius, int damage) {
		this(position.x, position.y, radius, damage);
		this.velocity = new PVector((mouseX - position.x), (mouseY - position.y)).normalize().mult(10f);
	}

	public Particle(PVector position, float mouseX, float mouseY, float radius, int damage, boolean friendly, boolean pierce) {
		this(position.x, position.y, radius, damage);
		this.velocity = new PVector((mouseX - position.x), (mouseY - position.y)).normalize().mult(10f);
		this.friendly = friendly;
		this.pierce = pierce;
	}
	
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		int color = friendly ? drawEngine.parent.color(0,0,255) : drawEngine.parent.color(255,0,255);
		
		drawEngine.drawEllipse(color, position.x, position.y, size, size);
	}

	public void integrate() {
		position.x += velocity.x;
		position.y += velocity.y;
	}
}
