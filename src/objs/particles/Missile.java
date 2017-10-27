package objs.particles;

import processing.core.PVector;

public class Missile extends Particle {

	public Missile(float xPos, float yPos, float radius, int damage) {
		super(xPos, yPos, radius, damage);
	}
	
	public Missile(PVector position, float mouseX, float mouseY, float radius, int damage) {
		super(position, mouseX, mouseY, radius, damage);
	}
	
	public Explosion explode() {
		return new Explosion(position, position.x, position.y, radius, damage);
	}

}
