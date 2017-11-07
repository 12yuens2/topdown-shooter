package game;

import objs.characters.PlayerCharacter;
import processing.core.PApplet;
import processing.core.PConstants;

public class GameUI {

	public GameContext context;
	public DrawEngine drawEngine;
	public PApplet parent;
	
	public GameUI(GameContext context, DrawEngine drawEngine) {
		this.context = context;
		this.drawEngine = drawEngine;
		this.parent = drawEngine.parent;
	}
	
	
	public void display(PlayerCharacter player) {
//		int bullets = player.currentWeapon.clipAmmo;
//		int ammo = player.currentWeapon.ammo;
//		
//		drawEngine.drawText(16, bullets+"/"+ammo, 50, 50, 255);
//		drawEngine.drawRectangle(drawEngine.parent.color(200), 100, 50, player.currentWeapon.reloading, 20);
		
		drawEngine.drawText(16, "Score: " + context.score, 50, 75, 255);
//		drawEngine.drawText(16, "HP: " + player.health, 50, 100, 255);
//		
//		int health = Math.min(100, player.health);
//		drawEngine.drawRectangle(drawEngine.parent.color(255,0,0), 100, 90, health, 20);
//		drawEngine.drawText(16, player.health+"/100", 145, 100, 255);
//		
		drawHealthDisplay(1200, 700, player);
		drawReloadDisplay(100, 700, player);
	}
	
	private void drawHealthDisplay(int xPos, int yPos, PlayerCharacter player) {
		float health = Math.min(100, player.health);
		float healthDisplay = scaleDisplay(health, 100, PConstants.HALF_PI, PConstants.TWO_PI);
		
		drawEngine.drawArc(parent.color(255,0,0), xPos, yPos, 100, 100, PConstants.HALF_PI, healthDisplay, 75);
		drawEngine.drawText(16, player.health+"/100", xPos+5, yPos, parent.color(255,0,0));
	}
	
	private void drawReloadDisplay(int xPos, int yPos, PlayerCharacter player) {
		float ammoDisplay = scaleDisplay(player.currentWeapon.clipAmmo, player.currentWeapon.clipSize, PConstants.HALF_PI, PConstants.TWO_PI); 

		if (player.currentWeapon.reloading > 0) {
			ammoDisplay = scaleDisplayInverse(player.currentWeapon.reloading, player.currentWeapon.reloadTime, PConstants.HALF_PI, PConstants.TWO_PI);
		}
		
		drawEngine.drawArc(parent.color(0,191,255), xPos, yPos, 100, 100, PConstants.HALF_PI, ammoDisplay, 75);
		drawEngine.drawText(16, player.currentWeapon.clipAmmo+"/"+player.currentWeapon.ammo, xPos+5, yPos, parent.color(0,191,255));
	}
	
	private float scaleDisplayInverse(float value, float maxValue, float startValue, float endValue) {
		return startValue + (1 - (value/maxValue)) * (endValue - startValue);
	}
	
	private float scaleDisplay(float value, float maxValue, float startValue, float endValue) {
		return startValue + (value/maxValue * (endValue - startValue));
	}

}
