package objs.pickups.effects.impl;

import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;

public class PermanentBulletRadiusEffect extends Effect {

	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		for (Weapon w : character.weapons) {
			w.bulletRadius += 0.5f;
			System.out.println(w.bulletRadius);
		}
	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		/*
		 * No implementation required.
		 */
	}

}
