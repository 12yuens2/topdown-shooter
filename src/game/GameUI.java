package game;

import java.io.Serializable;

import objs.characters.PlayerCharacter;
import processing.core.PApplet;
import processing.core.PConstants;

public class GameUI implements Serializable {

	public GameContext context;
	
	public GameUI(GameContext context) {
		this.context = context;
	}
	
	
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		drawEngine.drawText(16, "Score: " + context.score, 50, 75, 255);
		
		int xPos = 80;
		int yPos = 750;
		drawReloadDisplay(drawEngine, xPos, yPos, player);
		drawHealthDisplay(drawEngine, xPos+65, yPos+70, player);
		drawDamagedDisplay(drawEngine, player);
	}
	
	
	private void drawDamagedDisplay(DrawEngine drawEngine, PlayerCharacter player) {
		drawEngine.drawRectangle(drawEngine.parent.color(150,0,0), 0, 0, ShooterGame.SCREEN_X, ShooterGame.SCREEN_Y, player.damaged);
	}


	/**
	 * Display the player health part of the UI.
	 * @param drawEngine - The draw engine.
	 * @param xPos - x position of this UI element.
	 * @param yPos - y position of this UI element.
	 * @param player - Player whose health is being displayed.
	 */
	private void drawHealthDisplay(DrawEngine drawEngine, int xPos, int yPos, PlayerCharacter player) {
		float health = Math.min(100, player.health);
		float healthDisplay = scaleDisplay(health, 100, PConstants.HALF_PI, PConstants.TWO_PI);
		
		drawArcDisplay(drawEngine, drawEngine.parent.color(255,0,0), xPos, yPos, 
				healthDisplay, Math.max(0, player.health)+"/100");
	}
	
	/**
	 * Display the ammunition and reloading part of the UI.
	 * @param drawEngine - The draw engine.
	 * @param xPos - x position of this UI element.
	 * @param yPos - y position of this UI element.
	 * @param player - Player whose ammunition is being displayed.
	 */
	private void drawReloadDisplay(DrawEngine drawEngine, int xPos, int yPos, PlayerCharacter player) {
		float ammoDisplay = scaleDisplay(player.currentWeapon.clipAmmo, 
										 player.currentWeapon.clipSize, 
										 PConstants.HALF_PI, PConstants.TWO_PI); 

		if (player.currentWeapon.reloading > 0) {
			ammoDisplay = scaleDisplayInverse(player.currentWeapon.reloading, 
											  player.currentWeapon.reloadTime, 
											  PConstants.HALF_PI, PConstants.TWO_PI);
		}
		
		drawArcDisplay(drawEngine, drawEngine.parent.color(0,191,255), xPos, yPos, 
				ammoDisplay, player.currentWeapon.clipAmmo+"/"+player.currentWeapon.ammo);
	}
	
	/**
	 * Generic display function for an arc UI element.
	 * @param drawEngine - The draw engine.
	 * @param col - Colour of the arc.
	 * @param xPos - x position of the arc.
	 * @param yPos - y position of the arc.
	 * @param displayValue - Value to be displayed.
	 * @param displayString - String to be displayed next to the arc.
	 */
	private void drawArcDisplay(DrawEngine drawEngine, int col, float xPos, float yPos, float displayValue, String displayString) {
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
