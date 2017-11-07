package objs.pickups.effects.impl;

import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;

public class AddAmmoEffect extends Effect {

	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		for (Weapon weapon : character.weapons) {
			weapon.ammo += weapon.clipSize;
			if (weapon.ammo > weapon.maxAmmo) weapon.ammo = weapon.maxAmmo;
		}
	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		/*
		 * No implementation needed.
		 */
	}

}
