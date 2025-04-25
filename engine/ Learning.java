package engine;

import java.util.HashMap;
import java.util.Map;

public class Learning {
    private Map<Action, Integer> scoresActions;  // Stocke un score des actions
    private Map<Action, Long> lastPerformed;  // Timestamp de la dernière exécution
    private static final int MAX_SCORE = 50;  // Borne supérieure
    private static final int MIN_SCORE = -50;  // Borne inférieure
    private static final long DEGRADATION_INTERVAL = 5000;  // 5 secondes

    public Learning() {
        this.scoresActions = new HashMap<>();
        this.lastPerformed = new HashMap<>();
    }

    private void initialiserAction(Action action) {
        if (!scoresActions.containsKey(action)) {
            scoresActions.put(action, 0);
            lastPerformed.put(action, System.currentTimeMillis());
        }
    }

    public void ajouterRecompense(Action action) {
        initialiserAction(action);
        int reward = 5;  // Récompense 
        // Moduler en fonction de timeAction
        reward = (int) (reward * Math.min(1.0, action.getTimeAction() / 10.0));
        int newScore = Math.min(MAX_SCORE, scoresActions.get(action) + reward);
        scoresActions.put(action, newScore);
        lastPerformed.put(action, System.currentTimeMillis());
        System.out.println("Action récompensée : " + action + ", Score: " + newScore);
    }

    public void ajouterPunition(Action action) {
        initialiserAction(action);
        int punishment = 5;  // Punition 
        // Moduler en fonction de timeAction
        punishment = (int) (punishment * Math.min(1.0, action.getTimeAction() / 10.0));
        int newScore = Math.max(MIN_SCORE, scoresActions.get(action) - punishment);
        scoresActions.put(action, newScore);
        lastPerformed.put(action, System.currentTimeMillis());
        System.out.println("Action punie : " + action + ", Score: " + newScore);
    }

    public void degradationNaturelle() {
        long currentTime = System.currentTimeMillis();
        for (Action action : scoresActions.keySet()) {
            long lastTime = lastPerformed.getOrDefault(action, currentTime);
            if (currentTime - lastTime > DEGRADATION_INTERVAL) {
                int currentScore = scoresActions.get(action);
                if (currentScore > 0) {
                    scoresActions.put(action, Math.max(0, currentScore - 2));  // Dégradation plus forte
                } else if (currentScore < 0) {
                    scoresActions.put(action, Math.min(0, currentScore + 2));  // Ramener vers 0
                }
                System.out.println("Dégradation pour " + action + ": " + scoresActions.get(action));
            }
        }
    }

    public void afficherApprentissage() {
        System.out.println("\nÉtat de l'apprentissage :");
        for (Action action : scoresActions.keySet()) {
            System.out.println("  Action: " + action + ", Score: " + scoresActions.get(action) +
                               ", Dernière exécution: " + lastPerformed.get(action));
        }
    }

    public int getScore(Action action) {
        initialiserAction(action);
        return scoresActions.get(action);
    }
}
