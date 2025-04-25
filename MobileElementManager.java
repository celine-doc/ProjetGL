package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import config.GameConfiguration;
import config.RoomPosition;
import engine.Action;
import guiT.ChartManager;
import map.Block;
import map.Map;
import mobile.Animal;
import mobile.Cat;
import mobile.Dog;
import mobile.MobileElement;

public class MobileElementManager {
    private Map map;
    private Dog dog;
    private Cat cat;
    private Random random;
    private ChartManager chartManager;

    public MobileElementManager(Map map) {
        this.map = map;
        dog = new Dog(new Block(GameConfiguration.DEFAULT_POSITION_DOG_X, GameConfiguration.DEFAULT_POSITION_DOG_Y), "bathroom");
        cat = new Cat(new Block(GameConfiguration.DEFAULT_POSITION_CAT_X, GameConfiguration.DEFAULT_POSITION_CAT_Y), "bathroom");
        GameConfiguration.initActionChien();
        random = new Random();
        chartManager = new ChartManager();
    }

    public void action(int randomValue) {
        if (dog.dontHaveAction()) {
            Action act = selectedAction(dog, randomValue);
            if (act == null) {
                dog.addAction("moveRandom");
            } else {
                System.out.println("Action sélectionnée: " + act.getName());
                goToNewAction(dog, act);
            }
        } else {
            mooveElement(dog);
        }
        mettreAJourMetriquesApprentissage(dog); // Enregistrer les données après chaque action
    }

    public Action selectedAction(Animal animal, int random) {
        ArrayList<String> listNomAction = animal.getListNomAction();
        HashMap<String, Action> listAction = animal.getListActionPossible();
        int totalScore = 0;

        for (String actionName : listNomAction) {
            Action action = listAction.get(actionName);
            int score = animal.getScore(action);
            totalScore += Math.max(1, score + 100);
        }

        int randomThreshold = random % totalScore;
        int currentThreshold = 0;
        for (String actionName : listNomAction) {
            Action action = listAction.get(actionName);
            int score = animal.getScore(action);
            currentThreshold += Math.max(1, score + 100);
            if (randomThreshold < currentThreshold) {
                return action;
            }
        }
        return listAction.get(listNomAction.get(0));
    }

    public Dog getDog() {
        return dog;
    }

    public Cat getCat() {
        return cat;
    }

    public ChartManager getChartManager() {
        return chartManager;
    }

    private void moveLeftElement(MobileElement element) {
        Block position = element.getPosition();
        if (position.getLine() > 0) {
            Block newPosition = map.getBlock(position.getLine() - 1, position.getColumn());
            element.setPosition(newPosition);
        }
    }

    private void moveRightElement(MobileElement element) {
        Block position = element.getPosition();
        if (position.getLine() < GameConfiguration.LINE_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine() + 1, position.getColumn());
            element.setPosition(newPosition);
        }
    }

    private void moveUpElement(MobileElement element) {
        Block position = element.getPosition();
        if (position.getColumn() > 0) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() - 1);
            element.setPosition(newPosition);
        }
    }

    private void moveDownElement(MobileElement element) {
        Block position = element.getPosition();
        if (position.getColumn() < GameConfiguration.COLUMN_COUNT - 1) {
            Block newPosition = map.getBlock(position.getLine(), position.getColumn() + 1);
            element.setPosition(newPosition);
        }
    }

    private void horizontalDeplacement(Block destination, MobileElement element) {
        Block actualPos = element.getPosition();
        if (actualPos.getLine() < destination.getLine()) {
            moveRightElement(element);
        } else {
            moveLeftElement(element);
        }
    }

    private void verticalDeplacement(Block destination, MobileElement element) {
        Block actualPos = element.getPosition();
        if (actualPos.getColumn() < destination.getColumn()) {
            moveDownElement(element);
        } else {
            moveUpElement(element);
        }
    }

    private void goTo_hF(Block destination, MobileElement element) {
        if (destination.getLine() != element.getPosition().getLine()) {
            horizontalDeplacement(destination, element);
        } else {
            if (destination.getColumn() != element.getPosition().getColumn()) {
                verticalDeplacement(destination, element);
            }
        }
    }

    private String pieceDestination(Block destination) {
        if (isInBathroom(destination)) {
            return "bathroom";
        } else if (isInLaundry(destination)) {
            return "laundry";
        } else if (isInKitchen(destination)) {
            return "kitchen";
        } else if (isInBedroom(destination)) {
            return "bedroom";
        } else if (isInLiving(destination)) {
            return "living";
        } else {
            return "garden";
        }
    }

    private boolean isInBathroom(Block block) {
        return block.getLine() < RoomPosition.NUMBER_BLOCK_LONGUEUR_BATHROOM &&
               block.getColumn() < RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM;
    }

    private boolean isInLaundry(Block block) {
        return block.getLine() < RoomPosition.NUMBER_BLOCK_LONGUEUR_LAUNDRY &&
               block.getColumn() >= RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM &&
               block.getColumn() < RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LARGEUR_LAUNDRY;
    }

    private boolean isInKitchen(Block block) {
        return block.getLine() < RoomPosition.NUMBER_BLOCK_SIZE_KITCHEN &&
               block.getColumn() >= RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LARGEUR_LAUNDRY &&
               block.getColumn() < RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LARGEUR_LAUNDRY + RoomPosition.NUMBER_BLOCK_SIZE_KITCHEN;
    }

    private boolean isInBedroom(Block block) {
        return block.getLine() >= RoomPosition.NUMBER_BLOCK_LONGUEUR_BATHROOM &&
               block.getLine() < RoomPosition.NUMBER_BLOCK_LONGUEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LONGUEUR_BEDROOM &&
               block.getColumn() < RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM;
    }

    private boolean isInLiving(Block block) {
        return block.getLine() >= RoomPosition.NUMBER_BLOCK_LONGUEUR_BATHROOM &&
               block.getLine() < RoomPosition.NUMBER_BLOCK_LONGUEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LONGUEUR_LIVING &&
               block.getColumn() >= RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM &&
               block.getColumn() < RoomPosition.NUMBER_BLOCK_LARGEUR_BATHROOM + RoomPosition.NUMBER_BLOCK_LARGEUR_LIVING;
    }

    private void goToNewAction(MobileElement element, Action action) { // Déplacement pièce par pièce. 
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
            case "laundry" :
            	switch(action.getRoom()) {
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
            case "kitchen" :
            	switch(action.getRoom()) {
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
            case "bedroom" :
            	switch(action.getRoom()) {
            	case "bathroom":
            		element.addAction("goBathroom");
                    element.addAction(action.getName());
                    break;
                case "laundry":
                	element.addAction("goBathroom");
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
            case "living" :
            	switch(action.getRoom()) {
            	case "bathroom":
            		element.addAction("goBedroom");
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
            case "garden" :
            	switch(action.getRoom()) {
            	case "bathroom":
            		element.addAction("goLiving");
            		element.addAction("goBedroom");
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

    private void moveRandomly(MobileElement element) {
        int direction = random.nextInt(4);
        switch (direction) {
            case 0:
                moveLeftElement(element);
                break;
            case 1:
                moveRightElement(element);
                break;
            case 2:
                moveUpElement(element);
                break;
            case 3:
                moveDownElement(element);
                break;
        }
        Block newPosition = element.getPosition();
        String newRoom = pieceDestination(newPosition);
        if (!newRoom.equals(element.getLocation())) {
            element.setLocation(newRoom);
        }
    }

    public void mooveElement(MobileElement element) {
        switch (element.getAction()) {
            case "moveRandom":
                moveRandomly(element);
                element.supFirstAction();
                break;
            case "goBathroom":
                switch (element.getLocation()) {
                    case "bedroom":
                        goTo_hF(RoomPosition.DOOR_BEDROOM_TO_BATHROOM, element);
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
                        goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, element);
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
                        goTo_hF(RoomPosition.DOOR_LIVING_TO_KITCHEN, element);
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
                        goTo_hF(RoomPosition.DOOR_BATHROOM_TO_BEDROOM, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_BEDROOM)) {
                            element.supFirstAction();
                            element.setLocation("bedroom");
                        }
                        break;
                    default:
                        System.err.println("err, goBedroom");
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
                        goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    case "kitchen":
                        goTo_hF(RoomPosition.DOOR_KITCHEN_TO_LIVING, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
                            element.supFirstAction();
                            element.setLocation("living");
                        }
                        break;
                    case "garden":
                        goTo_hF(RoomPosition.DOOR_LIVING_TO_GARDEN, element);
                        if (element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
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
                    goTo_hF(RoomPosition.DOOR_GARDEN_TO_LIVING, element);
                    if (element.getPosition().equals(RoomPosition.DOOR_GARDEN_TO_LIVING)) {
                        element.supFirstAction();
                        element.setLocation("garden");
                    }
                } else {
                    System.err.println("err, goGarden");
                }
                break;
            case "dormirPanier": 
                goTo_hF(RoomPosition.PANIER_CHIEN, element); // Déplacement vers le lieu
                if (element.getPosition().equals(RoomPosition.PANIER_CHIEN)) {
                	if(element.getActionTime()==0) {
                		((Dog) element).interagir("dormirPanier"); // Interaction pour journal + apprentissage
                	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
                		element.supFirstAction(); // Fin de l'action -> reset. 
                		element.setActionTime(0); 
                	}else {
                		element.setActionTime(element.getActionTime()+1); // Ecoulement du temps à faire l'action
                	}
                    
                }
                break;
            case "dormirNiche":
                goTo_hF(RoomPosition.NICHE, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("dormirNiche");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
            case "dormirLit":
                goTo_hF(RoomPosition.LIT, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("dormirLit");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
            case "dormirCanape":
                goTo_hF(RoomPosition.CANAPE, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("dormirCanape");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
            case "mangerGamelle":
                goTo_hF(RoomPosition.GAMELLE, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("mangerGamelle");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
            case "mangerGamelle2":
                goTo_hF(RoomPosition.GAMELLE2, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("mangerGamelle2");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
            case "monterTable":
                goTo_hF(RoomPosition.TABLE_A_MANGER, element);
                if(element.getActionTime()==0) {
                	((Dog) element).interagir("monterTable");
            	}else if(element.getActionTime() == GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
            		element.supFirstAction();
            		element.setActionTime(0);
            	}else {
            		element.setActionTime(element.getActionTime()+1);
            	}
                break;
        }
    }

    private void mettreAJourMetriquesApprentissage(Dog dog) {
        chartManager.registerActionScores(dog);
        chartManager.registerTrustByStep(dog.getConfiance());
        chartManager.registerMentalStateByStep(dog.getMentalState());
        chartManager.registerPhysicalStateByStep(dog.getPhysicalState());
    }
}
