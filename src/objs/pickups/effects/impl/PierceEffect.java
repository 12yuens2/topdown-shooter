package objs.pickups.effects.impl;

import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;

public class PierceEffect extends Effect {

	public PierceEffect(int lifespan) {
		this.lifespan = lifespan;
	}
	
	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		for (Weapon w : character.weapons) {
			w.pierce = true;
		}

	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		for (Weapon w : character.weapons) {
			w.pierce = false;
		}

	}

}
