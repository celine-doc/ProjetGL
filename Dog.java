package mobile;

import config.GameConfiguration;
import engine.Action;
import engine.Learning;
import map.Block;
import process.Journal;

public class Dog extends Animal {

	private static final long serialVersionUID = 1L;
	private Journal journal;
	// -------
	private double mentalState;
	private double physicalState;
	private Learning learning = new Learning();;
	// -------
	public Dog(Block position, String location) {
		this.setPosition(position);
		this.setLocation(location);
		this.journal = new Journal("journal_dog.txt");
		this.setListActionPossible(GameConfiguration.listActionChien);
		this.setListNomAction(GameConfiguration.listNomActionChien);
		// -------
		this.mentalState = 0.5;
		this.physicalState = 0.5;
		// -------
	}

	public void dormirPanier() {
        Action action = GameConfiguration.listActionChien.get("dormirPanier");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
    }

    // Action pour dormir dans la niche
    public void dormirNiche() {
        Action action = GameConfiguration.listActionChien.get("dormirNiche");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
        mentalState+=0.05;
        learning.ajouterRecompense(action);
    }

    // Action pour dormir sur le lit
    public void dormirLit() {
        Action action = GameConfiguration.listActionChien.get("dormirLit");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
        mentalState-=0.1; //apres une punition
        learning.ajouterPunition(action);
    }

    // Action pour dormir sur le canapé
    public void dormirCanape() {
        Action action = GameConfiguration.listActionChien.get("dormirCanape");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
        mentalState-=0.1; //apres une punition
        learning.ajouterPunition(action);
    }

    // Action pour manger dans la gamelle
    public void mangerGamelle() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
        mentalState-=0.1; //apres une punition
        learning.ajouterRecompense(action);
    }

    // Action pour manger discrètement dans la gamelle du chat
    public void mangerGamelle2() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle2");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState += 0.2;  // Augmentation de l'état physique
        mentalState -= 0.5;  // Diminution de l'état mental (puisqu'il a fait quelque chose de "mal")s
        learning.ajouterPunition(action);
    }

    // Action pour monter sur la table
    public void monterTable() {
        Action action = GameConfiguration.listActionChien.get("monterTable");
        journal.writeTxt(action.getEcritJournal());  // Écriture de la phrase dans le journal
        realiserAction(action, true);
        physicalState -= 0.1;  // Diminution de l'état physique (action moins bénéfique)
        mentalState -= 0.1;    // Diminution de l'état mental (peut-être une réprimande de l'humain)
        learning.ajouterPunition(action);
    }
    

    // Méthode pour interagir avec l'action
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
            default:
                journal.writeTxt("Action inconnue : " + action);
                break;
        }
    }
    public void afficherApprentissage() {
        super.afficherApprentissage();
    }
    public double afficherConfiance() {
        return getConfiance();
    }

    public double getMentalState() {
        return mentalState;
    }

    public double getPhysicalState() {
        return physicalState;
    }

    public Learning getLearning() {
        return learning;
    }

	//ajouter d'autres methodes comme "interagir"... 

	public void afficherJournal() {
		journal.readTxt();
	}

}