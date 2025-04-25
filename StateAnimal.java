package engine;

public class StateAnimal {
	private double physicalState;
	private double mentalState;
	
	public StateAnimal(double d,double e) {
		this.physicalState = d;
		this.mentalState = e;
	}

	public double getphysicalState() {
		return physicalState;
	}

	public void setphysicalState(double d) {
		this.physicalState = d;
	}

	public double getMentalState() {
		return mentalState;
	}

	public void setMentalState(double d) {
		this.mentalState = d;
	}
	
}

