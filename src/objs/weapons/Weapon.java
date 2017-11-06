package objs.weapons;

import game.GameObject;
import objs.particles.Particle;

public abstract class Weapon extends GameObject {

	public static final int BASE_DMG = 5;
	
	public int clipAmmo, ammo, reloading, firing, fireRate, damage, bulletRadius;
	public final int clipSize, reloadTime;
	
	public boolean friendly;
	
	public Weapon(float xPos, float yPos, float radius, int ammo, int damage, 
			int clipSize, int reloadTime, int fireRate, int bulletRadius) {
		
		super(xPos, yPos, radius);
		this.ammo = ammo;
		this.damage = damage;
		
		this.clipSize = clipSize;
		this.clipAmmo = clipSize;
		this.reloadTime = reloadTime;
		this.fireRate = fireRate;
		this.bulletRadius = bulletRadius;
		
		this.friendly = true;
		this.reloading = 0;
		this.firing = 0;
	}

	public Weapon(float xPos, float yPos, float radius, int ammo, int damage, 
			int clipSize, int reloadTime, int fireRate, int bulletRadius, boolean friendly) {
		
		this(xPos, yPos, radius, ammo, damage, clipSize, reloadTime, fireRate, bulletRadius);
		this.friendly = friendly;
	}
	

	@Override
	public void integrate() {
		if (reloading > 0) {
			reloading--;
			if (reloading <= 0) addAmmo();
		}
	}

	/**
	 * 
	 * @param targetX
	 * @param targetY
	 * @return
	 */
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
