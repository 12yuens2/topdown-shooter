package objs.weapons;

import game.DrawEngine;
import objs.particles.Particle;

public class Gun extends Weapon {

	public static final int BASE_FIRERATE = 20;
	public static final int CLIP_SIZE = 12;
	public static final int RELOAD_TIME = 120;
	public static final int BULLET_RADIUS = 5;
	
	public Gun(float xPos, float yPos, float radius, int ammo, int damage) {
		super(xPos, yPos, radius, ammo, damage, CLIP_SIZE, RELOAD_TIME, BASE_FIRERATE, BULLET_RADIUS);
	}

	public Gun(float xPos, float yPos, float radius, int ammo, int damage, boolean friendly) {
		super(xPos, yPos, radius, ammo, damage, CLIP_SIZE, RELOAD_TIME, BASE_FIRERATE, BULLET_RADIUS, friendly);
	}
	
	public Gun(float xPos, float yPos, float radius, int ammo, int damage, int clipSize, int reloadTime, int fireRate, int bulletRadius) {
		super(xPos, yPos, radius, ammo, damage, clipSize, reloadTime, fireRate, bulletRadius);
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size);
	}


	@Override
	public Particle shoot(float targetX, float targetY) {
		if (clipAmmo <= 0) {
			return null;
		}
		else {
			if (friendly) clipAmmo--;
			return new Particle(position.copy(), targetX, targetY, bulletRadius, damage, friendly, pierce);
		}
	}


}
