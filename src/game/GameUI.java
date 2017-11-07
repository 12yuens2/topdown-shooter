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
		player.health = 100;
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
		int xPos = 80;
		int yPos = 750;
		drawReloadDisplay(xPos, yPos, player);
		drawHealthDisplay(xPos+65, yPos+70, player);

	}
	
	/**
	 * Display the player health part of the UI.
	 * @param xPos - x position of this UI element.
	 * @param yPos - y position of this UI element.
	 * @param player - Player whose health is being displayed.
	 */
	private void drawHealthDisplay(int xPos, int yPos, PlayerCharacter player) {
		float health = Math.min(100, player.health);
		float healthDisplay = scaleDisplay(health, 100, PConstants.HALF_PI, PConstants.TWO_PI);
		
		drawArcDisplay(parent.color(255,0,0), xPos, yPos, healthDisplay, player.health+"/100");
	}
	
	/**
	 * Display the ammunition and reloading part of the UI.
	 * @param xPos - x position of this UI element.
	 * @param yPos - y position of this UI element.
	 * @param player - Player whose ammunition is being displayed.
	 */
	private void drawReloadDisplay(int xPos, int yPos, PlayerCharacter player) {
		float ammoDisplay = scaleDisplay(player.currentWeapon.clipAmmo, 
										 player.currentWeapon.clipSize, 
										 PConstants.HALF_PI, PConstants.TWO_PI); 

		if (player.currentWeapon.reloading > 0) {
			ammoDisplay = scaleDisplayInverse(player.currentWeapon.reloading, 
											  player.currentWeapon.reloadTime, 
											  PConstants.HALF_PI, PConstants.TWO_PI);
		}
		
		drawArcDisplay(parent.color(0,191,255), xPos, yPos, ammoDisplay, player.currentWeapon.clipAmmo+"/"+player.currentWeapon.ammo);
	}
	
	/**
	 * Generic display function for an arc UI element.
	 * @param col - Colour of the arc.
	 * @param xPos - x position of the arc.
	 * @param yPos - y position of the arc.
	 * @param displayValue - Value to be displayed.
	 * @param displayString - String to be displayed next to the arc.
	 */
	private void drawArcDisplay(int col, float xPos, float yPos, float displayValue, String displayString) {
		float arcSize = 100;
		float opacity = 100;
		
		drawEngine.drawArc(col, xPos, yPos, arcSize, arcSize, PConstants.HALF_PI, displayValue, opacity);
		drawEngine.drawText(16, displayString, xPos+5, yPos, col);
	}
	
	
	private float scaleDisplayInverse(float value, float maxValue, float startValue, float endValue) {
		return startValue + (1 - (value/maxValue)) * (endValue - startValue);
	}
	
	private float scaleDisplay(float value, float maxValue, float startValue, float endValue) {
		return startValue + (value/maxValue * (endValue - startValue));
	}

}
