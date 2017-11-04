package game;

import objs.characters.PlayerCharacter;

public class GameUI {

	public GameContext context;
	public DrawEngine drawEngine;
	
	public GameUI(GameContext context, DrawEngine drawEngine) {
		this.context = context;
		this.drawEngine = drawEngine;
	}
	
	
	public void display(PlayerCharacter player) {
		int bullets = player.currentWeapon.clipAmmo;
		int ammo = player.currentWeapon.ammo;
		
		drawEngine.drawText(16, bullets+"/"+ammo, 50, 50, 255);
		drawEngine.drawText(16, "Score: " + context.score, 50, 75, 255);
		drawEngine.drawText(16, "HP: " + player.health, 50, 100, 255);
	}
}
