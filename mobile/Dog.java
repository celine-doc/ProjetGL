package mobile;

import config.GameConfiguration;
import engine.Action;
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
        boolean succes = true;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void dormirNiche() {
        Action action = GameConfiguration.listActionChien.get("dormirNiche");
        boolean succes = true;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void dormirLit() {
        Action action = GameConfiguration.listActionChien.get("dormirLit");
        boolean succes = false;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void dormirCanape() {
        Action action = GameConfiguration.listActionChien.get("dormirCanape");
        boolean succes = false;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void mangerGamelle() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle");
        boolean succes = true;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void mangerGamelle2() {
        Action action = GameConfiguration.listActionChien.get("mangerGamelle2");
        boolean succes = false;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
    }

    public void monterTable() {
        Action action = GameConfiguration.listActionChien.get("monterTable");
        boolean succes = false;
        journal.writeTxt(action.getEcritJournal());
        realiserAction(action, succes);
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
