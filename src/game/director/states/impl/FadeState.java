package game.director.states.impl;

import game.GameContext;
import game.director.states.DirectorState;

/**
 * The FadeState is the beginning of the relaxation period with minimum threat spawning
 * until the intensity is below the threshold.
 * @author sy35
 *
 */
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
