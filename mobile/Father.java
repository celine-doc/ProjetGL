package mobile;

import map.Block;

public class Father extends MobileElement {
	private boolean actionAnimal; // A mettre dans une classe individu intermédaire
	private Animal target; // A mettre dans une classe individu intermédaire
	private static final long serialVersionUID = 1L;
	private boolean punishment = false;
	
	public Father(Block position) {
		this.position = position;
		this.actionAnimal = false; // A mettre dans une classe individu intermédaire
		this.target = null; // A mettre dans une classe individu intermédaire
		addAction("randomMoove"); // Initialisation à un mouvement aléatoire
	}

	//// A mettre dans une classe individu intermédaire : 
	public boolean getActionAnimal(){
		return actionAnimal;
	}
	public void setActionAnimal(boolean actionAnimal){
		this.actionAnimal = actionAnimal;
	}
	public Animal getTarget(){
		return target;
	}
	public void setTarget(Animal target){
		this.target = target;
	}
	public boolean isPunishment() {
		return punishment;
	}
	public void setPunishment(boolean punishment) {
		this.punishment = punishment;
	}
	// ------------------
}

