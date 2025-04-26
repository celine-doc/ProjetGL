package mobile;

import java.util.ArrayList;
import java.util.HashMap;
import config.GameConfiguration;
import engine.Action;
import engine.Learning;
import engine.StateAnimal;

public abstract class Animal extends MobileElement {
    private static final long serialVersionUID = 1L;
    private StateAnimal stateAnimal = new StateAnimal(GameConfiguration.initialHealthState / 100.0,
                                                      GameConfiguration.initialMentalState / 100.0);
    private HashMap<String, Action> listActionPossible;
    private ArrayList<String> listNomAction;
    private double confiance;
    private Learning apprentissage = new Learning();

    public void realiserAction(Action action, boolean reussi) {
        if (reussi) {
            apprentissage.ajouterRecompense(action);
        } else {
            apprentissage.ajouterPunition(action);
        }
        ajusterConfiance(reussi, action);
        ajusterEtatDeSante(reussi, action);
        ajusterEtatMental(reussi, action);
    }

    private void ajusterConfiance(boolean reussi, Action action) {
        double adjustment = reussi ? 0.05 : -0.05;  // Ajustements plus petits
        confiance = Math.min(1.0, Math.max(0.0, confiance + adjustment));
        System.out.println("Confiance actuelle: " + (int)(confiance * 100) + "%");
    }

    private void ajusterEtatDeSante(boolean reussi, Action action) {
        double adjustment = reussi ? 0.05 : -0.05;
        // Moduler par timeAction et état actuel
        adjustment *= Math.min(1.0, action.getTimeAction() / 10.0);
        if (stateAnimal.getphysicalState() < 0.3) {
            adjustment *= 1.5;  // Effet amplifié si santé faible
        }
        stateAnimal.setphysicalState(Math.min(1.0, Math.max(0.0, stateAnimal.getphysicalState() + adjustment)));
        System.out.println("État de santé actuel: " + (int)(stateAnimal.getphysicalState() * 100) + "%");
    }

    private void ajusterEtatMental(boolean reussi, Action action) {
        double adjustment = reussi ? 0.03 : -0.03;
        adjustment *= Math.min(1.0, action.getTimeAction() / 10.0);
        if (stateAnimal.getMentalState() < 0.3) {
            adjustment *= 1.5;  // Effet amplifié si état mental faible
        }
        stateAnimal.setMentalState(Math.min(1.0, Math.max(0.0, stateAnimal.getMentalState() + adjustment)));
        System.out.println("État mental actuel: " + (int)(stateAnimal.getMentalState() * 100) + "%");
    }

    public void setListActionPossible(HashMap<String, Action> listActionPossible) {
        this.listActionPossible = listActionPossible;
    }

    public HashMap<String, Action> getListActionPossible() {
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
        System.out.println("\nÉtat actuel de l'apprentissage de l'animal:");
        apprentissage.afficherApprentissage();
        System.out.println("Confiance générale: " + (int)(confiance * 100) + "%");
        System.out.println("État de santé: " + (int)(stateAnimal.getphysicalState() * 100) + "%");
        System.out.println("État mental: " + (int)(stateAnimal.getMentalState() * 100) + "%");
    }

    public int getProba(Action action) {
        return apprentissage.getProba(action);
    }

    public double getConfiance() {
        return confiance;
    }

    public double getPhysicalState() {
        return stateAnimal.getphysicalState();
    }

    public double getMentalState() {
        return stateAnimal.getMentalState();
    }

    public Learning getLearning() {
        return apprentissage;
    }
}
