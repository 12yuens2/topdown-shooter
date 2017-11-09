package game.director.states.impl;

import game.GameContext;
import game.director.states.DirectorState;

/**
 * The BuildupState is where enemies spawns are built up until the player intensity goes past 
 * the threshold.
 * @author sy35
 *
 */
public class BuildupState extends DirectorState {

	public BuildupState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
	}

	@Override
	public DirectorState update() {
		maxThreatSpawn();
		
		if (intensity > threshold) {
			return new PeakState(difficulty, intensity, context);
		}
		
		return this;
	}

}
