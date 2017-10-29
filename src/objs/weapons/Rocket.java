package objs.weapons;

import game.DrawEngine;
import objs.particles.Missile;
import objs.particles.Particle;

public class Rocket extends Gun {

	public Rocket(float xPos, float yPos, float radius, int clipSize, int clipAmmo, int ammo, int maxAmmo, int reloadTime) {
		super(xPos, yPos, radius, clipSize, clipAmmo, ammo, maxAmmo, reloadTime);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Particle shoot(float targetX, float targetY) {
		if (clipAmmo <= 0) {
			return null;
		}
		else {
			clipAmmo--;
			return new Missile(position.copy(), targetX, targetY, 5, 50);
		}
	}

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,255), position.x, position.y, size, size);
		
	}

}
