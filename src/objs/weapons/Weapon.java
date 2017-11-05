package objs.weapons;

import game.GameObject;
import objs.particles.Particle;

public abstract class Weapon extends GameObject {

	public int clipAmmo, ammo, reloading;
	public final int clipSize, maxAmmo, reloadTime;
	
	public boolean friendly;
	
	public Weapon(float xPos, float yPos, float radius, int clipSize, int clipAmmo, int ammo, int maxAmmo, int reloadTime) {
		super(xPos, yPos, radius);
		this.clipAmmo = clipAmmo;
		this.ammo = ammo;
		this.clipSize = clipSize;
		this.maxAmmo = maxAmmo;
		this.reloadTime = reloadTime;
		
		this.friendly = true;
		this.reloading = 0;
	}

	public Weapon(float xPos, float yPos, float radius, int clipSize, int clipAmmo, int ammo, int maxAmmo, int reloadTime, boolean friendly) {
		super(xPos, yPos, radius);
		this.clipAmmo = clipAmmo;
		this.ammo = ammo;
		this.clipSize = clipSize;
		this.maxAmmo = maxAmmo;
		this.reloadTime = reloadTime;
		
		this.friendly = friendly;
		this.reloading = 0;
	}
	

	@Override
	public void integrate() {
		if (reloading > 0) {
			reloading--;
			if (reloading <= 0) addAmmo();
		}
	}

	public abstract Particle shoot(float targetX, float targetY);
	
	public void addAmmo() {
		int missingAmmo = clipSize - clipAmmo;
		
		if (ammo < missingAmmo) {
			ammo = 0;
			clipAmmo += ammo;
		}
		else {
			clipAmmo += missingAmmo;
			if (friendly) ammo -= missingAmmo;
		}
	}
	
	
	public void reload() {
		if (reloading <= 0 && clipAmmo < clipSize) {
			clipAmmo = 0;
			reloading = reloadTime;
		}
	}
	
	
	
		
}
