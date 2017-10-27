package objs.weapons;

import game.DrawEngine;
import objs.particles.Particle;

public class Gun extends Weapon{

	public Gun(float xPos, float yPos, float radius, int clipSize, int clipAmmo, int ammo, int maxAmmo) {
		super(xPos, yPos, radius, clipSize, clipAmmo, ammo, maxAmmo);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,0,255), position.x, position.y, size, size);
	}

	@Override
	public void integrate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Particle shoot(float targetX, float targetY) {
		if (clipAmmo <= 0) {
			return null;
		}
		else {
			clipAmmo--;
			return new Particle(position.copy(), targetX, targetY, 5, 5);
		}
	}

	@Override
	public void reload() {
		int missingAmmo = clipSize - clipAmmo;
		
		if (ammo < missingAmmo) {
			clipAmmo += ammo;
		}
		else {
			clipAmmo += missingAmmo;
			ammo -= missingAmmo;
		}
		
	}



}
