package mobile;

import java.util.ArrayList;
import java.util.HashMap;
import config.GameConfiguration;
import engine.Action;
import engine.Confiance;
import engine.Learning;
import engine.StateAnimal;

public abstract class Animal extends MobileElement {
    private static final long serialVersionUID = 1L;
    private StateAnimal stateAnimal = new StateAnimal(GameConfiguration.initialHealthState / 100.0,
                                                      GameConfiguration.initialMentalState / 100.0);
    private Confiance confiance = new Confiance();
    private HashMap<String, Action> listActionPossible;
    private ArrayList<String> listNomAction;
    private Learning apprentissage = new Learning();

    public void realiserAction(Action action, boolean reussi) {
        if (reussi) {
            apprentissage.ajouterRecompense(action);
        } else {
            apprentissage.ajouterPunition(action);
        }
        ajusterConfiance(reussi);
        ajusterEtatDeSante(reussi);
        ajusterEtatMental(reussi);
        confiance.degradationNaturelle();
        stateAnimal.degradationNaturelle();
    }

    private void ajusterConfiance(boolean reussi) {
        double adjustment = reussi ? 0.2 : -0.1;  // Ajustement de + 20% ou -10%
        confiance.mettreAJourConfiance(adjustment);
    }

    private void ajusterEtatDeSante(boolean reussi) {
        double adjustment = reussi ? 0.2 : -0.1;  // Ajustement de + 20% ou -10%
        stateAnimal.setPhysicalState(adjustment);
    }

    private void ajusterEtatMental(boolean reussi) {
        double adjustment = reussi ? 0.2 : -0.1;  // Ajustement de + 20% ou -10%
        stateAnimal.setMentalState(adjustment);
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
        System.out.println("Confiance générale: " + (int)(confiance.getConfianceActuelle() * 100) + "%");
        System.out.println("État de santé: " + (int)(stateAnimal.getPhysicalState() * 100) + "%");
        System.out.println("État mental: " + (int)(stateAnimal.getMentalState() * 100) + "%");
    }

    public int getProba(Action action) {
        return apprentissage.getProba(action);
    }

    public double getConfiance() {
        return confiance.getConfianceActuelle();
    }

    public double getPhysicalState() {
        return stateAnimal.getPhysicalState();
    }

    public double getMentalState() {
        return stateAnimal.getMentalState();
    }

    public Learning getLearning() {
        return apprentissage;
    }
}
