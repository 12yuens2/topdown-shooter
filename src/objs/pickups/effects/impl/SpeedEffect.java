package objs.pickups.effects.impl;

import objs.characters.Character;
import objs.pickups.effects.Effect;

public class SpeedEffect extends Effect {

	public SpeedEffect(int lifespan) {
		this.lifespan = lifespan;
	}
	
	@Override
	public <T extends Character> void apply(T character) {
		character.speedMultiplier *= 2f;
	}

	@Override
	public <T extends Character> void cease(T character) {
		character.speedMultiplier = character.speedMultiplier/2f;
	}

}
