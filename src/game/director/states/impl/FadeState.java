package game.director.states.impl;

import game.GameContext;
import game.director.states.DirectorState;

public class FadeState extends DirectorState {
	
	public FadeState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
	}

	@Override
	public DirectorState update() {
		minThreatSpawn();
		
		if (intensity < threshold) {
			return new RestState(difficulty, intensity, context);
		}
		return this;
	}

}
