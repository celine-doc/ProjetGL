package process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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

/**
 * 
 **/

public class MobileElementManager{
	private Map map;
	private Dog dog;
	private Cat cat;
	private Father father;
	private ChartManager chartManager;

	public MobileElementManager(Map map) {
		this.map = map;
		//positions initiales 
		dog = new Dog(new Block(GameConfiguration.DEFAULT_POSITION_DOG_X,GameConfiguration.DEFAULT_POSITION_DOG_Y), "bathroom");
		father = new Father(new Block(5,5)); // Position arbitraire à modifier
		GameConfiguration.initialisation();
		chartManager = new ChartManager();
	}

	// gestion des actions des animaux et des pnjs
	public void action(int random) {
		System.out.println(random);
		if(dog.dontHaveAction()) {
			Action act = selectedAction(dog,random);
			// System.out.println(act.getName());
			goToNewAction(dog,act);

		}else {
			mooveElement(dog);
		}
		mettreAJourMetriquesApprentissage(dog); // Enregistrer les données après chaque action
		if(father.getActionAnimal()){
			// Si true -> va vers l'animal en target
			goToNewAction(father,new Action(dog.getLocation(),"targetDog",dog.getPosition(),0,"bagarre",1);
			mooveElement(father);
		}else{
			// Sinon continue de bouger aléatoirement ( possibilité de mettre en place changement de pièce avec goToNewAction
			// father.action = "randomMoove";
			mooveElement(father);
		}
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

	// déplacement des éléments temps normal
	// --------------
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
	// --------------
	// Déplacement aléatoire 
	private void randomMoveLeftElement(MobileElement element) {
		Block position = element.getPosition();
		Block newPosition = map.getBlock(position.getLine() - 1, position.getColumn());
		if (position.getLine() > 0 && GameConfiguration.isAccessible(newPosition)) {
			element.setPosition(newPosition);
		}else {
			randomMoveUpElement(element);
		}
	}
	private void randomMoveRightElement(MobileElement element) {
		Block position = element.getPosition();
		Block newPosition = map.getBlock(position.getLine() + 1, position.getColumn());
		if (position.getLine() < GameConfiguration.LINE_COUNT - 1 && GameConfiguration.isAccessible(newPosition)) {
			element.setPosition(newPosition);
		}else {
			randomMoveDownElement(element);
		}
	}
	private void randomMoveUpElement(MobileElement element) {
		Block position = element.getPosition();
		Block newPosition = map.getBlock(position.getLine(), position.getColumn() - 1);
		if (position.getColumn() > 0 && GameConfiguration.isAccessible(newPosition)) {
			element.setPosition(newPosition);
		}else {
			randomMoveLeftElement(element);
		}
	}
	private void randomMoveDownElement(MobileElement element) {
		Block position = element.getPosition();
		Block newPosition = map.getBlock(position.getLine(), position.getColumn() + 1);
		if (position.getColumn() < GameConfiguration.COLUMN_COUNT - 1 && GameConfiguration.isAccessible(newPosition)) {
			element.setPosition(newPosition);
		}else {
			randomMoveRightElement(element);
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
				System.err.println("err, goBedroom : " + element.getAction());

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
				if(element.getActionTime()==0) {
					dog.interagir("dormirPanier");
				}else {
					if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
						element.supFirstAction();
						element.setActionTime(0);
					}else {
						element.setActionTime(element.getActionTime()+1);
						
					}
				}
			}
			break;
		case "dormirNiche":
			goTo_hF(RoomPosition.NICHE,element);
			if(element.getPosition().equals(RoomPosition.NICHE)) {
				if(element.getActionTime()==0) {
					dog.interagir("dormirNiche");// déclenche Dog.dormirNiche(), puis realiserAction(action, true) donc augmentation de la proba (recompense)
				}else {
					if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
						element.supFirstAction();
						element.setActionTime(0);
					}else {
						element.setActionTime(element.getActionTime()+1);
					}
				}
			}
			break;
		case "dormirLit":
			goTo_hF(RoomPosition.LIT, element);
			if(element.getPosition().equals(RoomPosition.PANIER_CHIEN)) {
				if(element.getActionTime()==0) {
					dog.interagir("dormirLit");
				}else {
					if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
						element.supFirstAction();
						element.setActionTime(0);
					}else {
						element.setActionTime(element.getActionTime()+1);
					}
				}
			}
			break;
		case "dormirCanape":
			goTo_hF(RoomPosition.CANAPE, element);
			if(element.getActionTime()==0) {
				dog.interagir("dormirCanape");
			}else {
				if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
					element.supFirstAction();
					element.setActionTime(0);
				}else {
					element.setActionTime(element.getActionTime()+1);
				}
			}
			break;
		case "mangerGamelle":
			goTo_hF(RoomPosition.GAMELLE, element);
			if(element.getActionTime()==0) {
				dog.interagir("mangerGamelle");
			}else {
				if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
					element.supFirstAction();
					element.setActionTime(0);
				}else {
					element.setActionTime(element.getActionTime()+1);
				}
			}
			break;
		case "mangerGamelle2":
			goTo_hF(RoomPosition.GAMELLE2, element);
			if(element.getActionTime()==0) {
				dog.interagir("mangerGamelle2");
			}else {
				if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
					element.supFirstAction();
					element.setActionTime(0);
				}else {
					element.setActionTime(element.getActionTime()+1);
				}
			}
			break;
		case "monterTable":
			goTo_hF(RoomPosition.TABLE_A_MANGER, element);
			if(element.getActionTime()==0) {
				dog.interagir("monterTable");
			}else {
				if(element.getActionTime() >= GameConfiguration.listActionChien.get(element.getAction()).getTimeAction()) {
					element.supFirstAction();
					element.setActionTime(0);
				}else {
					element.setActionTime(element.getActionTime()+1);
				}
			}
			break;
		default :
			System.err.println("err default"+element.getAction());
			element.supFirstAction();
		}
	}




	private void mettreAJourMetriquesApprentissage(Dog dog) {
		dog.getLearning().degradationNaturelle(); // Dégrader les probabilités
        dog.degradationNaturelleMetriques(); // Dégrader les métriques
		chartManager.registerActionScores(dog);
		chartManager.registerTrustByStep(dog.getConfiance());
		chartManager.registerMentalStateByStep(dog.getMentalState());
		chartManager.registerPhysicalStateByStep(dog.getPhysicalState());
	}
}
