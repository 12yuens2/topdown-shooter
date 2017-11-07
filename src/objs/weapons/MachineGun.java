package objs.weapons;

import game.DrawEngine;

public class MachineGun extends Gun {
	
	public static final int BASE_FIRERATE = 5;
	public static final int CLIP_SIZE = 30;
	public static final int RELOAD_TIME = 180;
	
	
	public MachineGun(float xPos, float yPos, float radius, int ammo, int damage) {
		super(xPos, yPos, radius, ammo, damage, CLIP_SIZE, RELOAD_TIME, BASE_FIRERATE, Gun.BULLET_RADIUS);
	}
	
//	public MachineGun(float xPos, float yPos, float radius, int clipSize, int ammo, int maxAmmo, int reloadTime, boolean friendly) {
//		this(xPos, yPos, radius, clipSize, ammo, maxAmmo, reloadTime);
//	}
	
	@Override
	public void display(DrawEngine drawEngine) {
		float size = radius * 2;
		drawEngine.drawEllipse(drawEngine.parent.color(0,255,0), position.x, position.y, size, size);
	}
}
