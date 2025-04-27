package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.GameConfiguration;
import config.RoomPosition;
import engine.Action;
import guiT.ChartManager;
import guiT.GraphiquesApprentissageGUI;
import map.Block;
import map.Map;
import mobile.Animal;
import mobile.Cat;
import mobile.Dog;
import mobile.Father;
import mobile.MobileElement;

public class MobileElementManager {
    private Map map;
    private Dog dog;
    private Cat cat;
    private Father father;
    private ChartManager chartManager;
    private GraphiquesApprentissageGUI gui;

    public MobileElementManager(Map map, GraphiquesApprentissageGUI gui) {
        this.map = map;
        dog = new Dog(new Block(GameConfiguration.DEFAULT_POSITION_DOG_X, GameConfiguration.DEFAULT_POSITION_DOG_Y), "bathroom");
	cat = new Cat(new Block(GameConfiguration.DEFAULT_POSITION_CAT_X, GameConfiguration.DEFAULT_POSITION_CAT_Y), "bedroom");
        father = new Father(new Block(5, 5));
        
        GameConfiguration.initialisation();
        chartManager = new ChartManager();
        this.gui = gui;
    }
    public Cat getCat() {
		return cat;
	}
    public Father getFather() {
        return father;
    }

    public void action() {
		int randomDog = (int)((Math.random())*100);// Une valeur aléatoire chacun pour pas qu'il fasse la même chose tt le temps
		int randomCat = (int)((Math.random())*100);
		
		if (dog.dontHaveAction()) { // Le chien bouge
			Action act = selectedAction(dog, randomDog);
			if (act != null) {
				System.err.println(act.getName());
				goToNewAction(dog, act);
			} else {
				dog.addAction("randomMoove");
			}
		} else {
			mooveElement(dog);
		}
		
		if (cat.dontHaveAction()) { // Le chat bouge
			Action act = selectedAction(cat, randomCat);
			if (act != null) {
				System.err.println(act.getName());
				goToNewAction(cat, act);
			} else {
				cat.addAction("randomMoove");
			}
		} else {
			mooveElement(cat);
		}
		
		
		
		if (father.getActionAnimal()) { // Le père bouge
			targeting(father);
		} else {
			mooveElement(father);
		}


		// Les stats sont mis à jour
		mettreAJourMetriquesApprentissage(dog); //  enregistrer les métriques
		gui.mettreAJourGrapheAction(); // Rafraîchir le graphique
	}

    public Action selectedAction(Animal animal, int random) {
        ArrayList<String> listNomAction = animal.getListNomAction();
        HashMap<String, Action> listAction = animal.getListActionPossible();
        int probaTmp = 0;
        for (Iterator<String> it = listNomAction.iterator(); it.hasNext();) {
            String currentAction = it.next();
            probaTmp += animal.getProba(listAction.get(currentAction));
            if (random < probaTmp || random == probaTmp) {
                return listAction.get(currentAction);
            }
        }
        return null;
    }

    public Dog getDog() {
        return dog;
    }

   // public Cat getCat() {
     //   return cat;
   // }

    public ChartManager getChartManager() {
        return chartManager;
    }

    private void moveLeftElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5)); // Position par défaut
            return;
        }
        if (position.getLine() > 0) {
            Block newPosition = map.getBlock(position.getLine() - 1, position.getColumn());
            if (newPosition != null) {
                element.setPosition(newPosition);
            } else {
                System.err.println("Erreur: newPosition nulle pour moveLeftElement");
            }
        }
    }

    private void moveRightElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getLine() < GameConfiguration.LINE_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine() + 1, position.getColumn());
            if (newPosition != null) {
                element.setPosition(newPosition);
            } else {
                System.err.println("Erreur: newPosition nulle pour moveRightElement");
            }
        }
    }

    private void moveUpElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getColumn() > 0) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() - 1);
            if (newPosition != null) {
                element.setPosition(newPosition);
            } else {
                System.err.println("Erreur: newPosition nulle pour moveUpElement");
            }
        }
    }

    private void moveDownElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getColumn() < GameConfiguration.COLUMN_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() + 1);
            if (newPosition != null) {
                element.setPosition(newPosition);
            } else {
                System.err.println("Erreur: newPosition nulle pour moveDownElement");
            }
        }
    }

    private void randomMoveLeftElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans randomMoveLeftElement");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getLine() > 0) {
            Block newPosition = map.getBlock(position.getLine() - 1, position.getColumn());
            if (newPosition != null && GameConfiguration.isAccessible(newPosition)) {
                element.setPosition(newPosition);
                return;
            }
        }
        // Ne pas appeler une autre méthode randomMove* pour éviter la récursion
    }

    private void randomMoveRightElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans randomMoveRightElement");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getLine() < GameConfiguration.LINE_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine() + 1, position.getColumn());
            if (newPosition != null && GameConfiguration.isAccessible(newPosition)) {
                element.setPosition(newPosition);
                return;
            }
        }
        // Ne pas appeler randomMoveDownElement
    }

    private void randomMoveUpElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans randomMoveUpElement");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getColumn() > 0) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() - 1);
            if (newPosition != null && GameConfiguration.isAccessible(newPosition)) {
                element.setPosition(newPosition);
                return;
            }
        }
        // Ne pas appeler randomMoveLeftElement
    }

    private void randomMoveDownElement(MobileElement element) {
        Block position = element.getPosition();
        if (position == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans randomMoveDownElement");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (position.getColumn() < GameConfiguration.COLUMN_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() + 1);
            if (newPosition != null && GameConfiguration.isAccessible(newPosition)) {
                element.setPosition(newPosition);
                return;
            }
        }
        // Ne pas appeler randomMoveRightElement
    }

    private void randomMoveElement(MobileElement element) {
        if (element.getPosition() == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans randomMoveElement");
            element.setPosition(new Block(5, 5));
            return;
        }
        // Essayer toutes les directions dans un ordre aléatoire
        int[] directions = {0, 1, 2, 3};
        // Mélanger les directions
        for (int i = directions.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
        MobileElement tmp = element;
	int direction = directions[0];
            switch (direction) {
                case 0:
                    moveLeftElement(element);
                    moveLeftElement(element);
                    if (!element.getPosition().equals(tmp.getPosition())) return; // Vérifier si le déplacement a réussi
                    break;
                case 1:
                	moveRightElement(element);
                	moveRightElement(element);
                    if (!element.getPosition().equals(tmp.getPosition())) return;
                    break;
                case 2:
                    moveUpElement(element);
                    moveUpElement(element);
                    if (!element.getPosition().equals(tmp.getPosition())) return;
                    break;
                case 3:
                    moveDownElement(element);
                    moveDownElement(element);
                    if (!element.getPosition().equals(tmp.getPosition())) return;
                    break;
            }
        System.out.println("Aucun déplacement possible pour " + element.getClass().getSimpleName() + " à la position " + element.getPosition());
    }

    private void horizontalDeplacement(Block destination, MobileElement element) {
        Block actualPos = element.getPosition();
        if (actualPos == null || destination == null) {
            System.err.println("Erreur: Position ou destination nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (actualPos.getLine() < destination.getLine()) {
            moveRightElement(element);
        } else {
            moveLeftElement(element);
        }
    }

    private void verticalDeplacement(Block destination, MobileElement element) {
        Block actualPos = element.getPosition();
        if (actualPos == null || destination == null) {
            System.err.println("Erreur: Position ou destination nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (actualPos.getColumn() < destination.getColumn()) {
            moveDownElement(element);
        } else {
            moveUpElement(element);
        }
    }

    private void goTo_hF(Block destination, MobileElement element) {
        if (destination == null) {
            System.err.println("Erreur: Destination nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (element.getPosition() == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans goTo_hF");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (destination.getLine() != element.getPosition().getLine()) {
            horizontalDeplacement(destination, element);
        } else {
            if (destination.getColumn() != element.getPosition().getColumn()) {
                verticalDeplacement(destination, element);
            }
        }
    }

    private void goTo_vF(Block destination, MobileElement element) {
        if (destination == null) {
            System.err.println("Erreur: Destination nulle pour " + element.getClass().getSimpleName());
            element.setPosition(new Block(5, 5));
            return;
        }
        if (element.getPosition() == null) {
            System.err.println("Erreur: Position nulle pour " + element.getClass().getSimpleName() + " dans goTo_vF");
            element.setPosition(new Block(5, 5));
            return;
        }
        if (destination.getColumn() != element.getPosition().getColumn()) {
            verticalDeplacement(destination, element);
        } else {
            if (destination.getLine() != element.getPosition().getLine()) {
                horizontalDeplacement(destination, element);
            }
        }
    }

    private void goToNewAction(MobileElement element, Action action) {
        switch (element.getLocation()) {
            case "bathroom":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction("goLaundry");
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction("goLaundry");
                        element.addAction("goKitchen");
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction("goBedroom");
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction("goLaundry");
                        element.addAction("goLiving");
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction("goLaundry");
                        element.addAction("goLiving");
                        element.addAction("goGarden");
                        element.addAction(action.getName());
                        break;
                }
                break;
            case "laundry":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction("goBathroom");
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction("goKitchen");
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction("goLiving");
                        element.addAction("goBedroom");
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction("goLiving");
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction("goLiving");
                        element.addAction("goGarden");
                        element.addAction(action.getName());
                        break;
                }
                break;
            case "kitchen":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction("goLaundry");
                        element.addAction("goBathroom");
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction("goLaundry");
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction("goLiving");
                        element.addAction("goBedroom");
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction("goLiving");
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction("goLiving");
                        element.addAction("goGarden");
                        element.addAction(action.getName());
                        break;
                }
                break;
            case "bedroom":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction("goBathroom");
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction("goLiving");
                        element.addAction("goLaundry");
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction("goLiving");
                        element.addAction("goKitchen");
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction("goLiving");
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction("goLiving");
                        element.addAction("goGarden");
                        element.addAction(action.getName());
                        break;
                }
                break;
            case "living":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction("goLaundry");
                        element.addAction("goBathroom");
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction("goLaundry");
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction("goKitchen");
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction("goBedroom");
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction("goGarden");
                        element.addAction(action.getName());
                        break;
                }
                break;
            case "garden":
                switch (action.getRoom()) {
                    case "bathroom":
                        element.addAction("goLiving");
                        element.addAction("goLaundry");
                        element.addAction("goBathroom");
                        element.addAction(action.getName());
                        break;
                    case "laundry":
                        element.addAction("goLiving");
                        element.addAction("goLaundry");
                        element.addAction(action.getName());
                        break;
                    case "kitchen":
                        element.addAction("goLiving");
                        element.addAction("goKitchen");
                        element.addAction(action.getName());
                        break;
                    case "bedroom":
                        element.addAction("goLiving");
                        element.addAction("goBedroom");
                        element.addAction(action.getName());
                        break;
                    case "living":
                        element.addAction("goLiving");
                        element.addAction(action.getName());
                        break;
                    case "garden":
                        element.addAction(action.getName());
                        break;
                }
                break;
        }
    }

    public void mooveElement(MobileElement element) {
        String action = element.getAction();
        if (action == null) {
            randomMoveElement(element);
            return;
        }

        switch (action) {
            case "goBathroom":
                switch (element.getLocation()) {
                    case "bedroom":
                        goTo_vF(RoomPosition.DOOR_BEDROOM_TO_BATHROOM, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_BATHROOM)) {
                            element.supFirstAction();
                            element.setLocation("bathroom");
                        }
                        break;
                    case "laundry":
                        goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM)) {
                            element.supFirstAction();
                            element.setLocation("bathroom");
                        }
                        break;
                    default:
                        System.err.println("err, goBathroom");
                        break;
                }
                break;
            case "goLaundry":
                switch (element.getLocation()) {
                    case "bathroom":
                        goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
                            element.supFirstAction();
                            element.setLocation("laundry");
                        }
                        break;
                    case "living":
                        goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_LAUNDRY)) {
                            element.supFirstAction();
                            element.setLocation("laundry");
                        }
                        break;
                    case "kitchen":
                        goTo_hF(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY)) {
                            element.supFirstAction();
                            element.setLocation("laundry");
                        }
                        break;
                    default:
                        System.err.println("err, goLaundry");
                        break;
                }
                break;
            case "goKitchen":
                switch (element.getLocation()) {
                    case "laundry":
                        goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN)) {
                            element.supFirstAction();
                            element.setLocation("kitchen");
                        }
                        break;
                    case "living":
                        goTo_vF(RoomPosition.DOOR_LIVING_TO_KITCHEN, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_KITCHEN)) {
                            element.supFirstAction();
                            element.setLocation("kitchen");
                        }
                        break;
                    default:
                        System.err.println("err, goKitchen");
                        break;
                }
                break;
            case "goBedroom":
                switch (element.getLocation()) {
                    case "living":
                        goTo_hF(RoomPosition.DOOR_LIVING_TO_BEDROOM, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_BEDROOM)) {
                            element.supFirstAction();
                            element.setLocation("bedroom");
                        }
                        break;
                    case "bathroom":
                        goTo_vF(RoomPosition.DOOR_BATHROOM_TO_BEDROOM, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_BEDROOM)) {
                            element.supFirstAction();
                            element.setLocation("bedroom");
                        }
                        break;
                    default:
                        System.err.println("err, goBedroom : " + action);
                        break;
                }
                break;
            case "goLiving":
                switch (element.getLocation()) {
                    case "bedroom":
                        goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    case "laundry":
                        goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    case "kitchen":
                        goTo_vF(RoomPosition.DOOR_KITCHEN_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    case "garden":
                        goTo_vF(RoomPosition.DOOR_GARDEN_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_GARDEN_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    default:
                        System.err.println("err, goLiving");
                        break;
                }
                break;
            case "goGarden":
                if (element.getLocation().equals("living")) {
                    goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, element);
                    if (element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                        element.supFirstAction();
                        element.setLocation("garden");
                    }
                } else {
                    System.err.println("err, goGarden");
                }
                break;
            case "dormirPanier":
                goTo_hF(RoomPosition.PANIER_CHIEN, element);
                if (element.getPosition().equals(RoomPosition.PANIER_CHIEN)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("dormirPanier");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        System.out.println(element.getActionTime() + " :: 1 / else");
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            System.out.println(element.getActionTime() + ":: 1 / else-else");
                        }
                    }
                }
                break;
            case "dormirNiche":
                goTo_hF(RoomPosition.NICHE, element);
                if (element.getPosition().equals(RoomPosition.NICHE)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("dormirNiche");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "dormirLit":
                goTo_hF(RoomPosition.LIT, element);
                if (element.getPosition().equals(RoomPosition.LIT)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("dormirLit");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "dormirCanape":
                goTo_hF(RoomPosition.CANAPE, element);
                if (element.getPosition().equals(RoomPosition.CANAPE)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("dormirCanape");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "mangerGamelle":
                goTo_hF(RoomPosition.GAMELLE, element);
                if (element.getPosition().equals(RoomPosition.GAMELLE)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("mangerGamelle");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "mangerGamelle2":
                goTo_hF(RoomPosition.GAMELLE2, element);
                if (element.getPosition().equals(RoomPosition.GAMELLE2)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("mangerGamelle2");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "monterTable":
                goTo_hF(RoomPosition.TABLE_A_MANGER, element);
                if (element.getPosition().equals(RoomPosition.TABLE_A_MANGER)) {
                    if (element.getActionTime() == 0) {
                        dog.interagir("monterTable");
                        element.setActionTime(element.getActionTime() + 1);
                    } else {
                        if (element.getActionTime() >= GameConfiguration.listActionChien.get(action).getTimeAction()) {
                            element.supFirstAction();
                            element.setActionTime(0);
                        } else {
                            element.setActionTime(element.getActionTime() + 1);
                        }
                    }
                }
                break;
            case "randomMoove":
                randomMoveElement(element);
                break;
            default:
                System.err.println("Action inconnue: " + action);
                element.supFirstAction();
                break;
        }
    }

    private void targeting(Father humain) { // Mettre class abstraite individue pour régler
        if (humain.getTarget() == null || humain.getPosition() == null || humain.getTarget().getPosition() == null) {
            System.err.println("Erreur: Cible ou position nulle pour " + humain.getClass().getSimpleName());
            humain.setActionAnimal(false);
            humain.setTarget(null);
            return;
        }
        if (!humain.getLocation().equals(humain.getTarget().getLocation())) {
            switch (humain.getLocation()) {
                case "bathroom":
                    switch (humain.getTarget().getLocation()) {
                        case "laundry":
                            goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "kitchen":
                            goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "bedroom":
                            goTo_vF(RoomPosition.DOOR_BATHROOM_TO_BEDROOM, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_BEDROOM)) {
                                humain.setLocation("bedroom");
                            }
                            break;
                        case "living":
                            goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "garden":
                            goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                    }
                    break;
                case "laundry":
                    switch (humain.getTarget().getLocation()) {
                        case "bathroom":
                            goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM)) {
                                humain.setLocation("bathroom");
                            }
                            break;
                        case "kitchen":
                            goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN)) {
                                humain.setLocation("kitchen");
                            }
                            break;
                        case "bedroom":
                            goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "living":
                            goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "garden":
                            goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                    }
                    break;
                case "kitchen":
                    switch (humain.getTarget().getLocation()) {
                        case "bathroom":
                            goTo_hF(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "laundry":
                            goTo_hF(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "bedroom":
                            goTo_vF(RoomPosition.DOOR_KITCHEN_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "living":
                            goTo_vF(RoomPosition.DOOR_KITCHEN_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "garden":
                            goTo_vF(RoomPosition.DOOR_KITCHEN_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                    }
                    break;
                case "bedroom":
                    switch (humain.getTarget().getLocation()) {
                        case "bathroom":
                            goTo_vF(RoomPosition.DOOR_BEDROOM_TO_BATHROOM, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_BATHROOM)) {
                                humain.setLocation("bathroom");
                            }
                            break;
                        case "laundry":
                            goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "kitchen":
                            goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "living":
                            goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "garden":
                            goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
                                humain.setLocation("living");
                            }
                            break;
                    }
                    break;
                case "living":
                    switch (humain.getTarget().getLocation()) {
                        case "bathroom":
                            goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "laundry":
                            goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                                humain.setLocation("laundry");
                            }
                            break;
                        case "kitchen":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_KITCHEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_KITCHEN)) {
                                humain.setLocation("kitchen");
                            }
                            break;
                        case "bedroom":
                            goTo_hF(RoomPosition.DOOR_LIVING_TO_BEDROOM, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_BEDROOM)) {
                                humain.setLocation("bedroom");
                            }
                            break;
                        case "garden":
                            goTo_vF(RoomPosition.DOOR_GARDEN_TO_LIVING, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_GARDEN_TO_LIVING)) {
                                humain.setLocation("garden");
                            }
                            break;
                    }
                    break;
                case "garden":
                    switch (humain.getTarget().getLocation()) {
                        case "bathroom":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "laundry":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "kitchen":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "bedroom":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                                humain.setLocation("living");
                            }
                            break;
                        case "living":
                            goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN, humain);
                            if (humain.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
                                humain.setLocation("living");
                            }
                            break;
                    }
                    break;
            }
        } else {
            Dog dog = (Dog) humain.getTarget();
            goTo_hF(dog.getPosition(), humain);
            if (humain.getPosition().equals(dog.getPosition())) {
                if (humain.isPunishment()) {
                    dog.interagir("punir");
                    System.out.println("Le Father punit le chien.");
                } else {
                    dog.interagir("recompenser");
                    System.out.println("Le Father récompense le chien.");
                }
                humain.setActionAnimal(false);
                humain.setTarget(null);
            }
        }
    }

    private void mettreAJourMetriquesApprentissage(Dog dog) {
        dog.getLearning().degradationNaturelle();
        chartManager.registerActionScores(dog);
        chartManager.registerTrustByStep(dog.getConfiance());
        chartManager.registerMentalStateByStep(dog.getMentalState());
        chartManager.registerPhysicalStateByStep(dog.getPhysicalState());
    }
}

