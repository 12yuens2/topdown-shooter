package game.states.impl;


import com.sun.glass.events.KeyEvent;

import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.states.GameState;
import objs.characters.PlayerCharacter;
import objs.particles.Particle;
import processing.core.PConstants;

public class PlayingState extends GameState {
	
	public PlayingState(GameContext context) {
		super(context);
	}

	@Override
	public void display(DrawEngine drawEngine, PlayerCharacter player) {
		displayGame(drawEngine);
		ui.display(drawEngine, player);
	}

	@Override
	public GameState update(int mouseX, int mouseY, PlayerCharacter player) {
		updateStep(mouseX, mouseY, player);
		aiDirector.step();
		
		/* Game over if no players alive */
		return context.players.size() > 0 ? this : new GameOverState(context);
	}

	@Override
	public GameState handleInput(GameInput input, PlayerCharacter player) {
		if (player != null) {
			player.facingDirection((int)input.mouseX, (int)input.mouseY);
			
			if (input.keyDown) {
				player.directionPress(input.keyCode);
				
				if (input.keyCode == KeyEvent.VK_R) {
					player.currentWeapon.reload();
				}
				
				if (input.keyCode == KeyEvent.VK_1) {
					player.currentWeapon = player.weapons.get(0);
				}
				if (input.keyCode == KeyEvent.VK_2) {
					player.currentWeapon = player.weapons.get(1);
				}
				if (input.keyCode == KeyEvent.VK_3) {
					player.currentWeapon = player.weapons.get(2);
				}
			}
			else {
				player.directionRelease(input.keyCode);
			}
			
			if (input.mouseButton == PConstants.LEFT) {
				player.attacking = input.mouseDown;
				
				/* Continue attacking if the player is holding down left click */
				if (player.attacking) {
					Particle bullet = player.attack(input.mouseX, input.mouseY);
					if (bullet != null) {
						context.particles.add(bullet);
					}
				}
				
			}
		}
		
		return this;
	}
}
