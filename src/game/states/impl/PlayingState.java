package game.states.impl;

import java.util.ArrayList;

import characters.BasicChaseCharacter;
import characters.PlayerCharacter;
import game.DrawEngine;
import game.GameContext;
import game.GameInput;
import game.states.GameState;
import particles.Particle;
import processing.core.PConstants;

public class PlayingState extends GameState{

	PlayerCharacter player;
	BasicChaseCharacter enemy;
	
	ArrayList<Particle> particles;
	
	public PlayingState(GameContext context, DrawEngine drawEngine) {
		super(context, drawEngine);

		particles = new ArrayList<>();
		
		this.player = new PlayerCharacter(100, 100);
		this.enemy = new BasicChaseCharacter(500, 500, player);
	}

	@Override
	public void display() {
		
		parent.background(0);

		player.display(drawEngine);
		enemy.display(drawEngine);

		
		for (Particle p : particles) {
			p.display(drawEngine);
			p.integrate();
		}
		
	}

	@Override
	public GameState update(int mouseX, int mouseY) {
		player.move();
		enemy.move();
		player.facingDirection(mouseX, mouseY);
		
		return this;
	}

	@Override
	public GameState handleInput(GameInput input) {
		if (input.keyDown) player.directionPress(input.keyCode);
		else player.directionRelease(input.keyCode);
		
		if (input.mouseButton == PConstants.LEFT) {
			particles.add(new Particle(player.facing.copy(), input.mouseX, input.mouseY));
		}
		
		return this;
	}

}
