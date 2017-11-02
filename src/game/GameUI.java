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
	}
}
