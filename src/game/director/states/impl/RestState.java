package game.director.states.impl;

import game.GameContext;
import game.director.states.DirectorState;

/**
 * The RestState gives players time to rest and recover with minimum threat spawning.
 * Return the BuildupState after the rest time is over.
 * @author sy35
 *
 */
public class RestState extends DirectorState {

	public static final int LIFESPAN = 800;
	
	public int lifespan;
	
	public RestState(int difficulty, float intensity, GameContext context) {
		super(difficulty, intensity, context);
		this.lifespan = LIFESPAN - (3 * difficulty);
	}

	@Override
	public DirectorState update() {
		minThreatSpawn();
		
		lifespan--;
		if (lifespan <= 0) {
			return new BuildupState(difficulty, intensity, context);
		}
		return this;
	}

}
