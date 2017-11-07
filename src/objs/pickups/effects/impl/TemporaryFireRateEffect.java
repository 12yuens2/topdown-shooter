package objs.pickups.effects.impl;

import java.util.HashMap;

import objs.characters.PlayerCharacter;
import objs.pickups.effects.Effect;
import objs.weapons.Weapon;

public class TemporaryFireRateEffect extends Effect {

	public HashMap<Weapon, Integer> weaponMap;
	
	public TemporaryFireRateEffect(int lifespan) {
		this.weaponMap = new HashMap<>();
		this.lifespan = lifespan;
	} 
	
	@Override
	public <T extends PlayerCharacter> void apply(T character) {
		for (Weapon w : character.weapons) {
			weaponMap.put(w, w.fireRate);
			w.fireRate = Math.max(1, w.fireRate-3);
		}

	}

	@Override
	public <T extends PlayerCharacter> void cease(T character) {
		for (Weapon w : character.weapons) {
			int fireRate = weaponMap.get(w);
			w.fireRate = fireRate;
		}
	}

}
