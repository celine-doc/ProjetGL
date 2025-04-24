package engine;

public class StateAnimal {
	private int healthState;
	private int mentalState;
	
	public StateAnimal(int healthState,int mentalState) {
		this.healthState = healthState;
		this.mentalState = mentalState;
	}

	public int getHealthState() {
		return healthState;
	}

	public void setHealthState(int healthState) {
		this.healthState = healthState;
	}

	public int getMentalState() {
		return mentalState;
	}

	public void setMentalState(int mentalState) {
		this.mentalState = mentalState;
	}
	
}
