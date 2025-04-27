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

    protected void ajusterConfiance(boolean reussi, Action action) {
        double adjustment = reussi ? 0.01 : -0.01; // Réduit de 0.02 à 0.01
        confiance = Math.min(1.0, Math.max(0.0, confiance + adjustment));
        System.out.printf("Confiance actuelle: %.1f%%\n", confiance * 100);
    }

    protected void ajusterEtatDeSante(boolean reussi, Action action) {
        double adjustment = reussi ? 0.01 : -0.01; // Réduit de 0.02 à 0.01
        adjustment *= Math.min(1.0, action.getTimeAction() / 10.0);
        if (stateAnimal.getphysicalState() < 0.3) {
            adjustment *= 1.5;
        }
        stateAnimal.setphysicalState(Math.min(1.0, Math.max(0.0, stateAnimal.getphysicalState() + adjustment)));
        System.out.printf("État de santé actuel: %.1f%%\n", stateAnimal.getphysicalState() * 100);
    }

    protected void ajusterEtatMental(boolean reussi, Action action) {
        double adjustment = reussi ? 0.008 : -0.008; // Réduit de 0.015 à 0.008
        adjustment *= Math.min(1.0, action.getTimeAction() / 10.0);
        if (stateAnimal.getMentalState() < 0.3) {
            adjustment *= 1.5;
        }
        stateAnimal.setMentalState(Math.min(1.0, Math.max (0.0, stateAnimal.getMentalState() + adjustment)));
        System.out.printf("État mental actuel: %.1f%%\n", stateAnimal.getMentalState() * 100);
    }

    public void degradationNaturelleMetriques() {
        confiance = Math.max(0.0, confiance - 0.005);
        stateAnimal.setphysicalState(Math.max(0.0, stateAnimal.getphysicalState() - 0.005));
        stateAnimal.setMentalState(Math.max(0.0, stateAnimal.getMentalState() - 0.005));
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
        System.out.printf("Confiance générale: %.1f%%\n", confiance * 100);
        System.out.printf("État de santé: %.1f%%\n", stateAnimal.getphysicalState() * 100);
        System.out.printf("État mental: %.1f%%\n", stateAnimal.getMentalState() * 100);
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
