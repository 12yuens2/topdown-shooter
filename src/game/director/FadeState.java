package game.director;

import game.GameContext;

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
