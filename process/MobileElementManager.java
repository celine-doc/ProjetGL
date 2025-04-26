package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import config.GameConfiguration;
import config.RoomPosition;
import engine.Action;
import map.Block;
import map.Map;
import mobile.Animal;
import mobile.Cat;
import mobile.Dog;
import mobile.MobileElement;
import guiT.ChartManager;

/**
 * 
 **/

public class MobileElementManager{
	private Map map;
	private Dog dog;
	private Cat cat;
        private ChartManager chartManager;

	public MobileElementManager(Map map) {
		this.map = map;
		//positions initiales 
		dog = new Dog(new Block(GameConfiguration.DEFAULT_POSITION_DOG_X,GameConfiguration.DEFAULT_POSITION_DOG_Y), "bathroom");
		GameConfiguration.initActionChien();
                chartManager = new ChartManager();
	}

	// gestion des actions des animaux et des pnjs
	public void action(int random) {
		if(dog.dontHaveAction()) {
			Action act = selectedAction(dog,random);
			System.out.println(act.getName());
			goToNewAction(dog,act);

		}else {
			mooveElement(dog);
		}
                mettreAJourMetriquesApprentissage(dog); // Enregistrer les données après chaque action
	}
	public Action selectedAction(Animal animal, int random) {
		ArrayList<String> listNomAction = animal.getListNomAction();
		HashMap<String,Action> listAction = animal.getListActionPossible();
		for(Iterator<String> it = listNomAction.iterator();it.hasNext();) {
			String currentAction = it.next();
			if (random < animal.getProba(listAction.get(currentAction)) || random == animal.getProba(listAction.get(currentAction))) { //récupère la derniere proba de l'action chez l'animal 
				return listAction.get(currentAction);
			}
		}
		return null;
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

	// déplacement des éléments
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
	// déplacement vers une destination précise en commençant par les déplacements verticale ou horizontale
	private void horizontalDeplacement(Block destination, MobileElement element) {
		Block actualPos = element.getPosition();
		if(actualPos.getLine() < destination.getLine()) {
			moveRightElement(element);
		}else {
			moveLeftElement(element);
		}
	}

	private void verticalDeplacement(Block destination, MobileElement element) {
		Block actualPos = element.getPosition();
		if(actualPos.getColumn() < destination.getColumn()) {
			moveDownElement(element);
		}else {
			moveUpElement(element);
		}
	}
	private void goTo_hF(Block destination, MobileElement element) {
		if(destination.getLine() != element.getPosition().getLine()) {
			horizontalDeplacement(destination,element);
		}else {
			if(destination.getColumn() != element.getPosition().getColumn()) {
				verticalDeplacement(destination, element);
			}
		}
	}
	private void goTo_vF(Block destination, MobileElement element) {
		if(destination.getColumn() != element.getPosition().getColumn()) {
			verticalDeplacement(destination,element);
		}else {
			if(destination.getLine() != element.getPosition().getLine()) {
				horizontalDeplacement(destination, element);
			}
		}
	}
	// méthodes pour la pièce de la destination, voir si toujours utile ! 
	private String pieceDestination(Block destination) {
		if(isInBathrom(destination)) {
			return "bathroom";
		}else {
			if(isInLaundry(destination)) {
				return "laundry";
			}else {
				if(isInKitchen(destination)) {
					return "kitchen";
				}else {
					if(isInBedroom(destination)) {
						return "bedroom";
					}else {
						if(isInLiving(destination)) {
							return "living";
						}else {
							return "garden";
						}
					}
				}
			}
		}
	}
	private boolean isInBathrom(Block block) {
		if(block.getLine()<RoomPosition.LONGUEUR_BATHROOM && block.getColumn()<RoomPosition.LARGEUR_BATHROOM) {
			return true;
		}else {
			return false;
		}
	}
	private boolean isInLaundry(Block block) {
		if(block.getLine()<RoomPosition.LONGUEUR_BATHROOM && block.getColumn()<RoomPosition.LARGEUR_BATHROOM+RoomPosition.LARGEUR_LAUNDRY) {
			return true;
		}else {
			return false;
		}
	}
	private boolean isInKitchen(Block block) {
		if(block.getLine()<RoomPosition.LONGUEUR_BATHROOM && block.getColumn()<RoomPosition.LARGEUR_BATHROOM+RoomPosition.LARGEUR_LAUNDRY+RoomPosition.SIZE_KITCHEN) {
			return true;
		}else {
			return false;
		}
	}
	private boolean isInBedroom(Block block) {
		if(block.getLine()<RoomPosition.LONGUEUR_BATHROOM+RoomPosition.LONGUEUR_BEDROOM && block.getColumn()<RoomPosition.LARGEUR_BATHROOM){
			return true;
		}else {
			return false;
		}
	}
	private boolean isInLiving(Block block) {
		if(block.getLine()<RoomPosition.LONGUEUR_BATHROOM+RoomPosition.LONGUEUR_BEDROOM && block.getColumn()<RoomPosition.LARGEUR_BATHROOM + RoomPosition.LARGEUR_LAUNDRY){
			return true;
		}else {
			return false;
		}
	}
	// Définition d'un nouvel itinéraire
	private void goToNewAction(MobileElement element, Action action) {
		switch(element.getLocation()) {
		case "bathroom" :
			switch(action.getRoom()) {
			case "bathroom":
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction("goLaundry");
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction("goLaundry");
				element.addAction("goKitchen");
				element.addAction(action.getName());
				break;
			case "bedroom" :
				element.addAction("goBedroom");
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction("goLaundry");
				element.addAction("goLiving");
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction("goLaundry");
				element.addAction("goLiving");
				element.addAction("goGarden");
				element.addAction(action.getName());
				break;
			}
			break;
		case "laundry" :
			switch(action.getRoom()) {
			case "bathroom" :
				element.addAction("goBathroom");
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction("goKitchen");
				element.addAction(action.getName());
			case "bedroom" :
				element.addAction("goLiving");
				element.addAction("goBedroom");
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction("goLiving");
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction("goLiving");
				element.addAction("goGarden");
				element.addAction(action.getName());
				break;
			}
			break;
		case "kitchen" :
			switch(action.getRoom()) {
			case "bathroom" :
				element.addAction("goLaundry");
				element.addAction("goBathroom");
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction("goLaundry");
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction(action.getName());
				break;
			case "bedroom" :
				element.addAction("goLiving");
				element.addAction("goBedroom");
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction("goLiving");
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction("goLiving");
				element.addAction("goGarden");
				element.addAction(action.getName());
				break;
			}
			break;
		case "bedroom" :
			switch(action.getRoom()) {
			case "bathroom" :
				element.addAction("goBathroom");
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction("goLiving");
				element.addAction("goLaundry");
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction("goLiving");
				element.addAction("goKitchen");
				element.addAction(action.getName());
				break;
			case "bedroom" :
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction("goLiving");
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction("goLiving");
				element.addAction("goGarden");
				element.addAction(action.getName());
				break;
			}
			break;
		case "living" :
			switch(action.getRoom()) {
			case "bathroom" :
				element.addAction("goLaundry");
				element.addAction("goBathroom");
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction("goLaundry");
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction("goKitchen");
				element.addAction(action.getName());
				break;
			case "bedroom" :
				element.addAction("goBedroom");
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction("goGarden");
				element.addAction(action.getName());
				break;
			}
			break;
		case "garden" :
			switch(action.getRoom()) {
			case "bathroom" :
				element.addAction("goLiving");
				element.addAction("goLaundry");
				element.addAction("goBathroom");
				element.addAction(action.getName());
				break;
			case "laundry" :
				element.addAction("goLiving");
				element.addAction("goLaundry");
				element.addAction(action.getName());
				break;
			case "kitchen" :
				element.addAction("goLiving");
				element.addAction("goKitchen");
				element.addAction(action.getName());
				break;
			case "bedroom" :
				element.addAction("goLiving");
				element.addAction("goBedroom");
				element.addAction(action.getName());
				break;
			case "living" :
				element.addAction("goLiving");
				element.addAction(action.getName());
				break;
			case "garden" :
				element.addAction(action.getName());
				break;
			}
			break;
		}
	}
	// méthode pour le déplacement à appeler, element à déplacer dans lequel est stocké toutes les informations. 
	public void mooveElement(MobileElement element) {
		switch(element.getAction()) {
		// Déplacement d'une pièce à une autre 
		case "goBathroom": // Bathroom
			switch(element.getLocation()) {
			case "bedroom": 
				goTo_vF(RoomPosition.DOOR_BEDROOM_TO_BATHROOM,element);
				if(element.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_BATHROOM)) {
					element.supFirstAction();
					element.setLocation("bathroom");
				}
				break;
			case "laundry":
				if(element.getLocation() == "laundry") {
					goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM,element);
					if(element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_BATHROOM)) {
						element.supFirstAction();
						element.setLocation("bathroom");
					}
				}
				break;
			default:
				System.err.println("err, goBathroom");
				break;
			}
			break;
		case "goLaundry": // Laundry
			switch(element.getLocation()) {
			case "bathroom":
				goTo_hF(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY,element);
				if(element.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_LAUNDRY)) {
					element.supFirstAction();
					element.setLocation("laundry");
				}
				break;
			case "living":
				goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING, element);
				if(element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_LAUNDRY)) {
					element.supFirstAction();
					element.setLocation("laundry");
				}
				break;
			case "kitchen":
				goTo_hF(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY, element);
				if(element.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LAUNDRY)) {
					element.supFirstAction();
					element.setLocation("laundry");
				}
				break;
			default:
				System.err.println("err, goLaundry");
				break;
			}
			break;
		case "goKitchen": // Kitchen
			switch(element.getLocation()) {
			case "laundry":
				goTo_hF(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN, element);
				if(element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_KITCHEN)) {
					element.supFirstAction();
					element.setLocation("kitchen");
				}
				break;
			case "living":
				goTo_vF(RoomPosition.DOOR_LIVING_TO_KITCHEN,element);
				if(element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_KITCHEN)) {
					element.supFirstAction();
					element.setLocation("kitchen");
				}
				break;
			default :
				System.err.println("err, goKitchen");
				break;
			}
			break;
		case "goBedroom": // Bedroom
			switch(element.getLocation()) {
			case "living":
				goTo_hF(RoomPosition.DOOR_LIVING_TO_BEDROOM,element);
				if(element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_BEDROOM)) {
					element.supFirstAction();
					element.setLocation("bedroom");
				}
				break;
			case "bathroom":
				goTo_vF(RoomPosition.DOOR_BATHROOM_TO_BEDROOM,element);
				if(element.getPosition().equals(RoomPosition.DOOR_BATHROOM_TO_BEDROOM)) {
					element.supFirstAction();
					element.setLocation("bedroom");
				}
				break;
			default:
				System.err.println("err, goBedroom");
				break;
			}
			break;
		case "goLiving": // Living
			switch(element.getLocation()) {
			case "bedroom":
				goTo_hF(RoomPosition.DOOR_BEDROOM_TO_LIVING,element);
				if(element.getPosition().equals(RoomPosition.DOOR_BEDROOM_TO_LIVING)) {
					element.supFirstAction();
					element.setLocation("living");
				}
				break;
			case "laundry":
				goTo_vF(RoomPosition.DOOR_LAUNDRY_TO_LIVING,element);
				if(element.getPosition().equals(RoomPosition.DOOR_LAUNDRY_TO_LIVING)) {
					element.supFirstAction();
					element.setLocation("living");
				}
				break;
			case "kitchen":
				goTo_vF(RoomPosition.DOOR_KITCHEN_TO_LIVING, element);
				if(element.getPosition().equals(RoomPosition.DOOR_KITCHEN_TO_LIVING)) {
					element.supFirstAction();
					element.setLocation("living");
				}
				break;
			case "garden":
				goTo_vF(RoomPosition.DOOR_LIVING_TO_GARDEN,element);
				if(element.getPosition().equals(RoomPosition.DOOR_LIVING_TO_GARDEN)) {
					element.supFirstAction();
					element.setLocation("living");
				}
				break;
			default:
				System.err.println("err, goLiving");
				break;
			}
			break;
		case "goGarden": // Garden
			if(element.getLocation() == "living") {
				goTo_vF(RoomPosition.DOOR_GARDEN_TO_LIVING,element);
				if(element.getPosition().equals(RoomPosition.DOOR_GARDEN_TO_LIVING)) {
					element.supFirstAction();
					element.setLocation("garden");
				}
			}else {
				System.err.println("err, goGarden");
			}
			break;
			// Déplacement au sein d'une pièce pour les actions 
		case "dormirPanier":// Action dormirPanier
			goTo_hF(RoomPosition.PANIER_CHIEN,element);
			if(element.getPosition().equals(RoomPosition.PANIER_CHIEN)) {
				dog.interagir("dormirPanier");
				element.supFirstAction();
			}
			break;
		case "dormirNiche":
			goTo_hF(RoomPosition.NICHE,element);
			if(element.getPosition().equals(RoomPosition.NICHE)) {
				dog.interagir("dormirNiche"); // déclenche Dog.dormirPanier(), puis realiserAction(action, true) donc augmentation de la proba (recompense)
				element.supFirstAction();
			}
		}
	}




       private void mettreAJourMetriquesApprentissage(Dog dog) {
          chartManager.registerActionScores(dog);
          chartManager.registerTrustByStep(dog.getConfiance());
          chartManager.registerMentalStateByStep(dog.getMentalState());
          chartManager.registerPhysicalStateByStep(dog.getPhysicalState());
      }
}
