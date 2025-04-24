package engine;

import java.util.HashMap;
import java.util.Map;

public class Learning {
    private Map<Action, Integer> scoresActions; // Stocke un score des actions

    public Learning() {
        this.scoresActions = new HashMap<>();
    }

    // Vérifie si l'action existe, sinon l'initialise avec un score par défaut.
    private void initialiserAction(Action action) {
        if (!scoresActions.containsKey(action)) {
            scoresActions.put(action, 0); // Score initial à 0
        }
    }

    // Ajoute une récompense à une action.
    public void ajouterRecompense(Action action) {
        initialiserAction(action);
        scoresActions.put(action, scoresActions.get(action) + 10); // Augmente le score de 10
        System.out.println("Action récompensée : " + action);
    }

    // Ajoute une punition à une action.
    public void ajouterPunition(Action action) {
        initialiserAction(action);
        scoresActions.put(action, scoresActions.get(action) - 10); // Diminue le score de 10
        System.out.println("Action punie : " + action);
    }

    // Dégrade progressivement l’apprentissage des actions non pratiquées.
    public void degradationNaturelle() {
        for (Action action : scoresActions.keySet()) {
            if (scoresActions.get(action) > 0) {
                scoresActions.put(action, scoresActions.get(action) - 1); // Dégrade le score de 1
            }
        }
        System.out.println("Dégradation appliquée.");
    }

    // Affiche l'évolution du score des actions.
    public void afficherApprentissage() {
        System.out.println("\nÉtat de l'apprentissage :");
        for (Action action : scoresActions.keySet()) {
            System.out.println("  Action : " + action + " Score : " + scoresActions.get(action));
        }
    }

    // Retourne le score d'une action donnée.
    public int getScore(Action action) {
        return scoresActions.getOrDefault(action, 0);
    }
} 
