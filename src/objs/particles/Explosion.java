package objs.particles;

import game.DrawEngine;
import processing.core.PVector;

public class Explosion extends Particle{
	
	public float initialRadius;
	public int lifespan;
	
	public Explosion(PVector position, float mouseX, float mouseY, float radius, int damage) {
		super(position, mouseX, mouseY, radius, damage);
		this.initialRadius = radius;
		this.lifespan = 30;
	}
	
	@Override
	public void display(DrawEngine drawEngine) {
		radius += initialRadius/2.5f;
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(255,127,80), position.x, position.y, size, size);
	}
	
	@Override
	public void integrate() {
		lifespan--;
	}
	
	

}
