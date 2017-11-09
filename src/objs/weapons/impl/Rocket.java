package objs.weapons.impl;

import game.DrawEngine;
import objs.particles.Missile;
import objs.particles.Particle;

public class Rocket extends Gun {

	public static final int BASE_FIRERATE = 60;
	public static final int CLIP_SIZE = 3;
	public static final int RELOAD_TIME = 240;
	public static final int BULLET_RADIUS = 10;
	
	public Rocket(float xPos, float yPos, float radius, int ammo, int damage) {
		super(xPos, yPos, radius, ammo, damage, CLIP_SIZE, RELOAD_TIME, BASE_FIRERATE, BULLET_RADIUS);
	}

	@Override
	public Particle shoot(float targetX, float targetY) {
		if (clipAmmo <= 0) {
			return null;
		}
		else {
			clipAmmo--;
			return new Missile(position.copy(), targetX, targetY, bulletRadius, damage);
		}
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,255), position.x, position.y, size, size);
		
	}

}
