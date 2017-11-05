package objs.pickups.effects.impl;

import java.util.ArrayList;

import game.GameContext;
import objs.characters.Character;
import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;

public class BombEffect extends Effect {

	GameContext context;
	
	public BombEffect(GameContext context) {
		this.context = context;
	}

	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		context.enemies.clear();
		context.flockEnemies.clear();
	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		/*
		 * No implemented required.
		 */
	}

}
