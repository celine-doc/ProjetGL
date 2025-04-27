package mobile;

import config.GameConfiguration;
import engine.Action;
import engine.Learning;
import map.Block;
import process.Journal;

public class Dog extends Animal {
    private static final long serialVersionUID = 1L;
    private Journal journal;

    public Dog(Block position, String location) {
        this.setPosition(position);
        this.setLocation(location);
        this.journal = new Journal("journal_dog.txt");
        this.setListActionPossible(GameConfiguration.listActionChien);
        this.setListNomAction(GameConfiguration.listNomActionChien);
    }

    public void dormirPanier() {
        Action action = GameConfiguration.listActionChien.get("dormirPanier");
        journal.writeTxt(action.getEcritJournal());
        // Introduire un taux d'échec aléatoire de 40% (augmenté de 20%)
        boolean reussi = Math.random() > 0.4;
        realiserAction(action, reussi);
        if (!reussi) {
            journal.writeTxt("J'ai essayé de dormir dans mon panier, mais je n'étais pas à l'aise.");
        }
    }

    public void dormirNiche() {
        Action action = GameConfiguration.listActionChien.get("dormirNiche");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, true);
    }

    public void dormirLit() {
        Action action = GameConfiguration.listActionChien.get("dormirLit");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, false);
    }

    public void dormirCanape() {
        Action action = GameConfiguration.listActionChien.get("dormirCanape");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, false);
    }

    public void mangerGamelle() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, true);
    }

    public void mangerGamelle2() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle2");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, false);
    }

    public void monterTable() {
        Action action = GameConfiguration.listActionChien.get("monterTable");
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, false);
    }

    public void interagir(String action) {
        switch (action) {
            case "dormirPanier":
                dormirPanier();
                break;
            case "dormirNiche":
                dormirNiche();
                break;
            case "dormirLit":
                dormirLit();
                break;
            case "dormirCanape":
                dormirCanape();
                break;
            case "mangerGamelle":
                mangerGamelle();
                break;
            case "mangerGamelle2":
                mangerGamelle2();
                break;
            case "monterTable":
                monterTable();
                break;
            case "punir":
                // Punition : affecter l'action actuelle (si présente) et les métriques
                if (getAction() != null && !getAction().isEmpty()) {
                    Action currentAction = GameConfiguration.listActionChien.get(getAction());
                    if (currentAction != null) {
                        realiserAction(currentAction, false); // Réduire la probabilité
                    }
                } else {
                    // Appliquer un ajustement négatif sans action spécifique
                    ajusterConfiance(false, null);
                    ajusterEtatDeSante(false, null);
                    ajusterEtatMental(false, null);
                }
                journal.writeTxt("J'ai été puni ! Je me sens moins confiant.");
                System.out.println("Chien puni : confiance = " + (getConfiance() * 100) + "%, état mental = " + (getMentalState() * 100) + "%");
                break;
            case "recompenser":
                // Récompense : affecter l'action actuelle (si présente) et les métriques
                if (getAction() != null && !getAction().isEmpty()) {
                    Action currentAction = GameConfiguration.listActionChien.get(getAction());
                    if (currentAction != null) {
                        realiserAction(currentAction, true); // Augmenter la probabilité
                    }
                } else {
                    // Appliquer un ajustement positif sans action spécifique
                    ajusterConfiance(true, null);
                    ajusterEtatDeSante(true, null);
                    ajusterEtatMental(true, null);
                }
                journal.writeTxt("J'ai été récompensé ! Je me sens plus confiant.");
                System.out.println("Chien récompensé : confiance = " + (getConfiance() * 100) + "%, état mental = " + (getMentalState() * 100) + "%");
                break;
            default:
                journal.writeTxt("Action inconnue: " + action);
                break;
        }
    }

    public void afficherApprentissage() {
        super.afficherApprentissage();
    }

    public double afficherConfiance() {
        return getConfiance();
    }

    public void afficherJournal() {
        journal.readTxt();
    }
}
