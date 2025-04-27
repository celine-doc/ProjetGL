package engine;

import java.util.HashMap;
import java.util.Map;

public class Learning {
    private Map<Action, Integer> probasActions;  // Stocke les probabilités des actions (0 à 100)
    private Map<Action, Long> lastPerformed;    // Timestamp de la dernière exécution
    private static final int MAX_PROBA = 100;   // Borne supérieure
    private static final int MIN_PROBA = 0;     // Borne inférieure
    private static final long DEGRADATION_INTERVAL = 15000;  // 15 secondes

    public Learning() {
        this.probasActions = new HashMap<>();
        this.lastPerformed = new HashMap<>();
    }

    private void initialiserAction(Action action) {
        if (!probasActions.containsKey(action)) {
            probasActions.put(action, action.getProba());
            lastPerformed.put(action, System.currentTimeMillis());
        }
    }

    public void ajouterRecompense(Action action) {
        initialiserAction(action);
        int reward = 2;  // Valeur fixe
        int newProba = Math.min(MAX_PROBA, probasActions.get(action) + reward);
        probasActions.put(action, newProba);
        lastPerformed.put(action, System.currentTimeMillis());
        System.out.println("Action récompensée : " + action.getName() + ", Probabilité: " + newProba + "%");
    }

    public void ajouterPunition(Action action) {
        initialiserAction(action);
        int punishment = 2;  // Valeur fixe
        int newProba = Math.max(MIN_PROBA, probasActions.get(action) - punishment);
        probasActions.put(action, newProba);
        lastPerformed.put(action, System.currentTimeMillis());
        System.out.println("Action punie : " + action.getName() + ", Probabilité: " + newProba + "%");
    }

    public void degradationNaturelle() {
        long currentTime = System.currentTimeMillis();
        for (Action action : probasActions.keySet()) {
            long lastTime = lastPerformed.getOrDefault(action, currentTime);
            if (currentTime - lastTime > DEGRADATION_INTERVAL) {
                int currentProba = probasActions.get(action);
                if (currentProba > action.getProba()) {
                    probasActions.put(action, Math.max(action.getProba(), currentProba - 1));
                    System.out.println("Dégradation pour " + action.getName() + ": " + probasActions.get(action) + "%");
                }
            }
        }
    }

    public void afficherApprentissage() {
        System.out.println("\nÉtat de l'apprentissage :");
        for (Action action : probasActions.keySet()) {
            System.out.println("  Action: " + action.getName() + ", Probabilité: " + probasActions.get(action) +
                               "%, Dernière exécution: " + lastPerformed.get(action));
        }
    }

    public int getProba(Action action) {
        initialiserAction(action);
        return probasActions.get(action);
    }
}
