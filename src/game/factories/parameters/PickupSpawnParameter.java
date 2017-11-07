package game.factories.parameters;

public class PickupSpawnParameter extends Parameter {

	public int lifespan;
	public float radius;
	
	public PickupSpawnParameter(float radius, int lifespan) {
		this.radius = radius;
		this.lifespan = lifespan;
	}
}
