package objs.weapons;

import game.GameObject;
import objs.particles.Particle;

/**
 * Weapon abstract class that contains all attributes such as clip size, ammo and fire rate.
 * @author sy35
 *
 */
public abstract class Weapon extends GameObject {

	public static final int BASE_DMG = 5;
	
	public int clipAmmo, ammo, reloading, firing, fireRate, damage;
	public final int clipSize, maxAmmo, reloadTime;
	public float bulletRadius;
	
	public boolean friendly, pierce;
	
	public Weapon(float xPos, float yPos, float radius, int ammo, int damage, 
			int clipSize, int reloadTime, int fireRate, int bulletRadius) {
		
		super(xPos, yPos, radius);
		this.ammo = ammo;
		this.damage = damage;
		
		this.clipSize = clipSize;
		this.clipAmmo = clipSize;
		this.maxAmmo = clipSize * 10;
		this.reloadTime = reloadTime;
		this.fireRate = fireRate;
		this.bulletRadius = bulletRadius;
		
		this.friendly = true;
		this.pierce = false;
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
		
		if (firing > 0) firing--;
	}

	/**
	 * Fire the weapon
	 * @param targetX
	 * @param targetY
	 * @return - The bullet if the weapon successfully fired, null otherwise.
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
	
	/**
	 * Reload the weapon.
	 */
	public void reload() {
		if (reloading <= 0 && clipAmmo < clipSize && ammo > 0) {
			clipAmmo = 0;
			reloading = reloadTime;
		}
	}
	
	
	
		
}
