package objs.pickups.effects.impl;

import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;

public class SpeedEffect extends Effect {

	public SpeedEffect(int lifespan) {
		this.lifespan = lifespan;
	}
	
	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		character.speedMultiplier *= 2f;
	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		character.speedMultiplier = character.speedMultiplier/2f;
	}

}
