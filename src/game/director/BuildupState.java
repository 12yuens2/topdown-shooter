package game.director;

import game.GameContext;

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
