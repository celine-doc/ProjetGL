package engine;

public class Stimulus {
	private int type;
	private int intensity;

	public Stimulus(int type, int intensity) {
		this.intensity = intensity; 
		this.type = type;
	}
	public int getType() {
		return type;
	}

	public int getIntensity() {
		return intensity;
	}
}
