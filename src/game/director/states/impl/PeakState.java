package game.director.states.impl;

import game.GameContext;
import game.director.states.DirectorState;

/**
 * PeakState ensures there is a minimum build up time where there is maximum threat spawning.
 * @author sy35
 *
 */
public class PeakState extends DirectorState {

	public static final int LIFESPAN = 200;
	
	public int lifespan;
	
	public PeakState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
		this.lifespan = LIFESPAN + (difficulty * 3);
	}

	@Override
	public DirectorState update() {
		maxThreatSpawn();
		
		lifespan--;
		if (lifespan <= 0) {
			return new FadeState(difficulty, intensity, context);
		}
		return this;
	}

}
