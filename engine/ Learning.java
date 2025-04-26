package engine;

import java.util.HashMap;
import java.util.Map;

public class Learning {
    private Map<Action, Integer> probabilitesActions;
    private Map<Action, Long> dernieresExecutions;
    private static final int PROBA_MAX = 100;
    private static final int PROBA_MIN = 0;
    private static final long INTERVALLE_DEGRADATION = 10000;

    public Learning() {
        this.probabilitesActions = new HashMap<>();
        this.dernieresExecutions = new HashMap<>();
    }

    private void initialiserAction(Action action) {
        if (!probabilitesActions.containsKey(action)) {
            probabilitesActions.put(action, action.getProba());
            dernieresExecutions.put(action, System.currentTimeMillis());
        }
    }

    public void ajouterRecompense(Action action) {
        initialiserAction(action);
        int recompense = 1; // Réduit de 2 à 1
        recompense = (int) (recompense * Math.min(1.0, action.getTimeAction() / 10.0));
        int nouvelleProba = Math.min(PROBA_MAX, probabilitesActions.get(action) + recompense);
        probabilitesActions.put(action, nouvelleProba);
        dernieresExecutions.put(action, System.currentTimeMillis());
        System.out.println("Action récompensée : " + action.getName() + ", Probabilité : " + nouvelleProba + "%");
    }

    public void ajouterPunition(Action action) {
        initialiserAction(action);
        int punition = 1; // Réduit de 2 à 1
        punition = (int) (punition * Math.min(1.0, action.getTimeAction() / 10.0));
        int nouvelleProba = Math.max(PROBA_MIN, probabilitesActions.get(action) - punition);
        probabilitesActions.put(action, nouvelleProba);
        dernieresExecutions.put(action, System.currentTimeMillis());
        System.out.println("Action punie : " + action.getName() + ", Probabilité : " + nouvelleProba + "%");
    }

    public void degradationNaturelle() {
        long heureActuelle = System.currentTimeMillis();
        for (Action action : probabilitesActions.keySet()) {
            long derniereExecution = dernieresExecutions.getOrDefault(action, heureActuelle);
            int probaActuelle = probabilitesActions.get(action);
            int probaInitiale = action.getProba();

            // Dégradation constante (augmentée à 2%)
            if (probaActuelle > probaInitiale) {
                int nouvelleProba = Math.max(probaInitiale, probaActuelle - 2); // Augmenté de 1 à 2
                probabilitesActions.put(action, nouvelleProba);
                System.out.println("Dégradation constante pour " + action.getName() + " : " + nouvelleProba + "%");
            }

            // Dégradation périodique toutes les 10 secondes
            if (heureActuelle - derniereExecution >= INTERVALLE_DEGRADATION) {
                if (probaActuelle > probaInitiale) {
                    int nouvelleProba = Math.max(probaInitiale, probaActuelle - 3);
                    probabilitesActions.put(action, nouvelleProba);
                    System.out.println("Dégradation périodique pour " + action.getName() + " : " + nouvelleProba + "%");
                }
                dernieresExecutions.put(action, heureActuelle);
            }
        }
    }

    public void afficherApprentissage() {
        System.out.println("\nÉtat de l'apprentissage :");
        for (Action action : probabilitesActions.keySet()) {
            System.out.println("  Action : " + action.getName() + ", Probabilité : " + probabilitesActions.get(action) +
                               "%, Dernière exécution : " + dernieresExecutions.get(action));
        }
    }

    public int getProba(Action action) {
        initialiserAction(action);
        return probabilitesActions.get(action);
    }
}
