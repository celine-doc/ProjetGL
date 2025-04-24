package mobile;

import java.util.ArrayList;
import java.util.HashMap;

import config.GameConfiguration;
import engine.Action;
import engine.Learning;
import engine.StateAnimal;

public abstract class Animal extends MobileElement {

	private static final long serialVersionUID = 1L;
	private StateAnimal stateAnimal = new StateAnimal(GameConfiguration.initialHealthState, GameConfiguration.initialMentalState);
	private HashMap<String, Action> listActionPossible;
	private ArrayList<String> listNomAction; 
	private double confiance; // Confiance générale de l'animal
	private Learning apprentissage = new Learning();


	public void realiserAction(Action action, boolean reussi) {
		if (reussi) {
			apprentissage.ajouterRecompense(action);  // Récompense l'action
		} else {
			apprentissage.ajouterPunition(action);  // Punit l'action
		}

		// Ajuste la confiance, la santé et l'état mental en fonction du succès ou échec de l'action
		ajusterConfiance(reussi);
		ajusterEtatDeSante(reussi);
		ajusterEtatMental(reussi);
	}
	
	private void ajusterConfiance(boolean reussi) {
		if (reussi) {
			confiance = Math.min(1.0, confiance + 0.1);  // Augmenter la confiance de 10% (max 100%)
		} else {
			confiance = Math.max(0.0, confiance - 0.1);  // Diminuer la confiance de 10% (min 0%)
		}
		System.out.println("Confiance actuelle de l'animal : " + (int)(confiance * 100) + "%");
	}

	// Méthode pour ajuster la santé de l'animal
	private void ajusterEtatDeSante(boolean reussi) {
		if (reussi) {
			stateAnimal.setHealthState(Math.min(100, stateAnimal.getHealthState() + 10));  // Augmenter la santé de 10 points
		} else {
			stateAnimal.setHealthState(Math.max(0, stateAnimal.getHealthState() - 10));  // Diminuer la santé de 10 points
		}
		System.out.println("État de santé actuel : " + stateAnimal.getHealthState() + "%");
	}

	// Méthode pour ajuster l'état mental de l'animal
	private void ajusterEtatMental(boolean reussi) {
		if (reussi) {
			stateAnimal.setMentalState(Math.min(100, stateAnimal.getMentalState() + 10));  // Augmenter l'état mental de 10 points
		} else {
			stateAnimal.setMentalState(Math.max(0, stateAnimal.getMentalState() - 10));  // Diminuer l'état mental de 10 points
		}
		System.out.println("État mental actuel : " + stateAnimal.getMentalState() + "%");
	}
	public void setListActionPossible(HashMap<String, Action> listActionPossible) {
		this.listActionPossible = listActionPossible;
	}
	public HashMap<String, Action> getListActionPossible(){
		return listActionPossible;
	}
	public ArrayList<String> getListNomAction() {
		return listNomAction;
	}
	public void setListNomAction(ArrayList<String> listNomAction) {
		this.listNomAction = listNomAction;
	}
	public StateAnimal getStateAnimal() {
		return stateAnimal;
	}

	public void setStateAnimal(StateAnimal stateAnimal) {
		this.stateAnimal = stateAnimal;
	}
	public void afficherApprentissage() {
		System.out.println("\n État actuel de l'apprentissage de l'animal :");
		apprentissage.afficherApprentissage();
		System.out.println("Confiance générale : " + (int)(confiance * 100) + "%");
		System.out.println("État de santé : " + stateAnimal.getHealthState() + "%");
		System.out.println("État mental : " + stateAnimal.getMentalState() + "%");
	}
	// Retourne le score pour une action donnée
    public int getScore(Action action) {
        return apprentissage.getScore(action);
    }

    // Retourne la confiance actuelle
	public double getConfiance() {
        return confiance;
    }

    // Retourne l'état de santé actuel
    public double getHealthState() {
        return stateAnimal.getHealthState();
    }

    // Retourne l'état mental actuel
    public double getMentalState() {
        return stateAnimal.getMentalState();
    }
}
