package guiT;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import config.GameConfiguration;
import config.RoomPosition;
import process.MobileElementManager;

public class Dashboard extends JPanel {

	private static final long serialVersionUID = 1L;

	private MobileElementManager manager; 

	// Positions des angles des pièces
	private int upLeftAngleBathroomX = 0;
	private int upLeftAngleBathroomY = 0;
	private int lowRightAngleBathroomX = upLeftAngleBathroomX + RoomPosition.LONGUEUR_BATHROOM; // Salle de bain rectangulaire
	private int lowRightAngleBathroomY = upLeftAngleBathroomY + RoomPosition.LARGEUR_BATHROOM; 

	private int upLeftAngleBedroomX = lowRightAngleBathroomX;  // A droite de la salle de bain
	private int upLeftAngleBedroomY = 0;
	private int lowRightAngleBedroomX = upLeftAngleBedroomX + RoomPosition.LONGUEUR_BEDROOM;
	private int lowRightAngleBedroomY = upLeftAngleBedroomY + RoomPosition.LARGEUR_BATHROOM; 

	private int upLeftAngleLaundryX = upLeftAngleBathroomX; // Sous la salle de bain
	private int upLeftAngleLaundryY = lowRightAngleBathroomY;
	private int lowRightAngleLaundryY = upLeftAngleLaundryY + RoomPosition.LARGEUR_LAUNDRY;

	private int upLeftAngleKitchenX = upLeftAngleLaundryX; // Sous la buanderie
	private int upLeftAngleKitchenY = lowRightAngleLaundryY;

	private int upLeftAngleLivingX = upLeftAngleBedroomX; // Sous la chambre et à droite de toutes les autres pièce
	private int upLeftAngleLivingY = lowRightAngleBedroomY; 

	private int upLeftAngleGardenX = lowRightAngleBedroomX; // A droite du salon et de la chambre
	private int upLeftAngleGardenY = upLeftAngleBedroomY; 

	public void setManager(MobileElementManager manager) { 
		this.manager = manager;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Contours de la maison
		g.setColor(Color.BLACK);
		g.drawRect(0,0,1600, 720);


		// Salle de bain 
		//couleur: Bleu clair
		g.setColor(new Color(173, 216, 230)); 
		g.fillRect(upLeftAngleBathroomX, upLeftAngleBathroomY, 300, 220);

		// Chambre
		//couleur: Rose
		g.setColor(new Color(239, 187, 204)); 
		g.fillRect(upLeftAngleBedroomX, upLeftAngleBedroomY, 600, 220);

		// Buanderie
		//couleur gris clair
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(upLeftAngleLaundryX, upLeftAngleLaundryY, 300, 200);

		// Cuisine
		//couleur: beige sable
		g.setColor(new Color(237, 201, 175));
		g.fillRect(upLeftAngleKitchenX, upLeftAngleKitchenY, 300, 300);

		// Salon
		//couleur: beige
		g.setColor(new Color(245, 245, 220)); 
		g.fillRect(upLeftAngleLivingX, upLeftAngleLivingY, 600, 500);

		// Jardin		
		// couleur: vert
		g.setColor(new Color(144, 238, 144)); 
		g.fillRect(upLeftAngleGardenX, upLeftAngleGardenY, 500, 720);

		// Contours des pièces 
		g.setColor(Color.BLACK);
		// Salle de bain 
		g.drawRect(upLeftAngleBathroomX, upLeftAngleBathroomY, 300, 220);
		// Chambre
		g.drawRect(upLeftAngleBedroomX, upLeftAngleBedroomY, 600, 220);
		// Buanderie
		g.drawRect(upLeftAngleLaundryX, upLeftAngleLaundryY, 300, 200);
		// Cuisine 
		g.drawRect(upLeftAngleKitchenX, upLeftAngleKitchenY, 300, 300);
		// Salon
		g.drawRect(upLeftAngleLivingX, upLeftAngleLivingY, 600, 500);
		//Jardin 
		g.drawRect(upLeftAngleGardenX, upLeftAngleGardenY,500,720);

		// Ouverture pour les portes
		g.setColor(Color.WHITE);
		// Salle de bain -> chambre
		g.drawLine(RoomPosition.UP_OF_DOOR_BATHROOM_TO_BEDROOM_X,RoomPosition.UP_OF_DOOR_BATHROOM_TO_BEDROOM_Y,RoomPosition.LOW_OF_DOOR_BATHROOM_TO_BEDROOM_X,RoomPosition.LOW_OF_DOOR_BATHROOM_TO_BEDROOM_Y);
		// Salle de bain -> buanderie
		g.drawLine(RoomPosition.LEFT_OF_DOOR_BATHROOM_TO_LAUNDRY_X,RoomPosition.LEFT_OF_DOOR_BATHROOM_TO_LAUNDRY_Y,RoomPosition.RIGHT_OF_DOOR_BATHROOM_TO_LAUNDRY_X,RoomPosition.RIGHT_OF_DOOR_BATHROOM_TO_LAUNDRY_Y);
		// Buanderie -> cuisine
		g.drawLine(RoomPosition.LEFT_OF_DOOR_LAUNDRY_TO_KITCHEN_X,RoomPosition.LEFT_OF_DOOR_LAUNDRY_TO_KITCHEN_Y,RoomPosition.RIGHT_OF_DOOR_LAUNDRY_TO_KITCHEN_X,RoomPosition.RIGHT_OF_DOOR_LAUNDRY_TO_KITCHEN_Y);
		// Buanderie -> salon
		g.drawLine(RoomPosition.UP_OF_DOOR_LAUNDRY_TO_LIVING_X,RoomPosition.UP_OF_DOOR_LAUNDRY_TO_LIVING_Y,RoomPosition.LOW_OF_DOOR_LAUNDRY_TO_LIVING_X,RoomPosition.LOW_OF_DOOR_LAUNDRY_TO_LIVING_Y);
		// Cuisine -> salon
		g.drawLine(RoomPosition.UP_OF_DOOR_KITCHEN_TO_LIVING_X,RoomPosition.UP_OF_DOOR_KITCHEN_TO_LIVING_Y,RoomPosition.LOW_OF_DOOR_KITCHEN_TO_LIVING_X,RoomPosition.LOW_OF_DOOR_KITCHEN_TO_LIVING_Y);
		// Chambre -> salon
		g.drawLine(RoomPosition.LEFT_OF_DOOR_BEDROOM_TO_LIVING_X,RoomPosition.LEFT_OF_DOOR_BEDROOM_TO_LIVING_Y,RoomPosition.RIGHT_OF_DOOR_BEDROOM_TO_LIVING_X,RoomPosition.RIGHT_OF_DOOR_BEDROOM_TO_LIVING_Y);
		// Salon -> jardin
		g.drawLine(RoomPosition.UP_OF_DOOR_LIVING_TO_GARDEN_X,RoomPosition.UP_OF_DOOR_LIVING_TO_GARDEN_Y,RoomPosition.LOW_OF_DOOR_LIVING_TO_GARDEN_X,RoomPosition.LOW_OF_DOOR_LIVING_TO_GARDEN_Y);

		// Image objet avec intéraction animal 
		g.drawImage(GameConfiguration.IMAGE_BUISSON,RoomPosition.BUISSON_X,RoomPosition.BUISSON_Y,GameConfiguration.SIZE_BUISSON_X,GameConfiguration.SIZE_BUISSON_Y,null);
		g.drawImage(GameConfiguration.IMAGE_NICHE,RoomPosition.NICHE_X,RoomPosition.NICHE_Y,GameConfiguration.SIZE_NICHE_X,GameConfiguration.SIZE_NICHE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_PANIER_CHIEN,RoomPosition.PANIER_CHIEN_X,RoomPosition.PANIER_CHIEN_Y,GameConfiguration.SIZE_PANIER_CHIEN_X,GameConfiguration.SIZE_PANIER_CHIEN_Y,null);
		g.drawImage(GameConfiguration.IMAGE_ARBRE_CHAT,RoomPosition.ARBRE_CHAT_X,RoomPosition.ARBRE_CHAT_Y,GameConfiguration.SIZE_ARBRE_CHAT_X,GameConfiguration.SIZE_ARBRE_CHAT_Y,null);
		g.drawImage(GameConfiguration.IMAGE_CANAPE,RoomPosition.CANAPE_X,RoomPosition.CANAPE_Y,GameConfiguration.SIZE_CANAPE_X,GameConfiguration.SIZE_CANAPE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_TABLE_A_MANGER,RoomPosition.TABLE_A_MANGER_X,RoomPosition.TABLE_A_MANGER_Y,GameConfiguration.SIZE_TABLE_A_MANGER_X,GameConfiguration.SIZE_TABLE_A_MANGER_Y,null);
		g.drawImage(GameConfiguration.IMAGE_GAMELLE,RoomPosition.GAMELLE_X,RoomPosition.GAMELLE_Y,GameConfiguration.SIZE_GAMELLE_X,GameConfiguration.SIZE_GAMELLE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_GAMELLE2,RoomPosition.GAMELLE2_X,RoomPosition.GAMELLE2_Y,GameConfiguration.SIZE_GAMELLE2_X,GameConfiguration.SIZE_GAMELLE2_Y,null);
		g.drawImage(GameConfiguration.IMAGE_LIT,RoomPosition.LIT_X,RoomPosition.LIT_Y,GameConfiguration.SIZE_LIT_X,GameConfiguration.SIZE_LIT_Y,null);
		g.drawImage(GameConfiguration.IMAGE_TONDEUSE,RoomPosition.TONDEUSE_X,RoomPosition.TONDEUSE_Y,GameConfiguration.SIZE_TONDEUSE_X,GameConfiguration.SIZE_TONDEUSE_Y,null);
		
		// Image objet sans intéraction
		g.drawImage(GameConfiguration.IMAGE_CLOTURE,RoomPosition.CLOTURE1_X,RoomPosition.CLOTURE_Y,GameConfiguration.SIZE_CLOTURE_X,GameConfiguration.SIZE_CLOTURE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_CLOTURE,RoomPosition.CLOTURE2_X,RoomPosition.CLOTURE_Y,GameConfiguration.SIZE_CLOTURE_X,GameConfiguration.SIZE_CLOTURE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_CLOTURE,RoomPosition.CLOTURE3_X,RoomPosition.CLOTURE_Y,GameConfiguration.SIZE_CLOTURE_X,GameConfiguration.SIZE_CLOTURE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_CLOTURE,RoomPosition.CLOTURE4_X,RoomPosition.CLOTURE_Y,GameConfiguration.SIZE_CLOTURE_X,GameConfiguration.SIZE_CLOTURE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_EVIER_CUISINE,RoomPosition.EVIER_CUISINE_X,RoomPosition.EVIER_CUISINE_Y,GameConfiguration.SIZE_EVIER_CUISINE_X,GameConfiguration.SIZE_EVIER_CUISINE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_FOUR,RoomPosition.FOUR_X,RoomPosition.FOUR_Y,GameConfiguration.SIZE_FOUR_X,GameConfiguration.SIZE_FOUR_Y,null);
		g.drawImage(GameConfiguration.IMAGE_FRIGO,RoomPosition.FRIGO_X,RoomPosition.FRIGO_Y,GameConfiguration.SIZE_FRIGO_X,GameConfiguration.SIZE_FRIGO_Y,null);
		g.drawImage(GameConfiguration.IMAGE_MEUBLE_CUISINE,RoomPosition.MEUBLE_CUISINE_X,RoomPosition.MEUBLE_CUISINE_Y,GameConfiguration.SIZE_MEUBLE_CUISINE_X,GameConfiguration.SIZE_MEUBLE_CUISINE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_MACHINE_A_LAVER,RoomPosition.MACHINE_A_LAVER_X,RoomPosition.MACHINE_A_LAVER_Y,GameConfiguration.SIZE_MACHINE_A_LAVER_X,GameConfiguration.SIZE_MACHINE_A_LAVER_Y,null);
		g.drawImage(GameConfiguration.IMAGE_SECHOIR,RoomPosition.SECHOIR_X,RoomPosition.SECHOIR_Y,GameConfiguration.SIZE_SECHOIR_X,GameConfiguration.SIZE_SECHOIR_Y,null);
		g.drawImage(GameConfiguration.IMAGE_PANIER_LINGE,RoomPosition.PANIER_LINGE_X,RoomPosition.PANIER_LINGE_Y,GameConfiguration.SIZE_PANIER_LINGE_X,GameConfiguration.SIZE_PANIER_LINGE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_TOILET,RoomPosition.TOILET_X,RoomPosition.TOILET_Y,GameConfiguration.SIZE_TOILET_X,GameConfiguration.SIZE_TOILET_Y,null);
		g.drawImage(GameConfiguration.IMAGE_ARMOIRE,RoomPosition.ARMOIRE_X,RoomPosition.ARMOIRE_Y,GameConfiguration.SIZE_ARMOIRE_X,GameConfiguration.SIZE_ARMOIRE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_BAIGNOIRE,RoomPosition.BAIGNOIRE_X,RoomPosition.BAIGNOIRE_Y,GameConfiguration.SIZE_BAIGNOIRE_X,GameConfiguration.SIZE_BAIGNOIRE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_BIBLIOTHEQUE,RoomPosition.BIBLIOTHEQUE_X,RoomPosition.BIBLIOTHEQUE_Y,GameConfiguration.SIZE_BIBLIOTHEQUE_X,GameConfiguration.SIZE_BIBLIOTHEQUE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_EVIER_SALLE_DE_BAIN,RoomPosition.EVIER_SALLE_DE_BAIN_X,RoomPosition.EVIER_SALLE_DE_BAIN_Y,GameConfiguration.SIZE_EVIER_SALLE_DE_BAIN_X,GameConfiguration.SIZE_EVIER_SALLE_DE_BAIN_Y,null);
		g.drawImage(GameConfiguration.IMAGE_MEUBLE_BUANDERIE,RoomPosition.MEUBLE_BUANDERIE_X,RoomPosition.MEUBLE_BUANDERIE_Y,GameConfiguration.SIZE_MEUBLE_BUANDERIE_X,GameConfiguration.SIZE_MEUBLE_BUANDERIE_Y,null);
		g.drawImage(GameConfiguration.IMAGE_MEUBLE_SALLE_DE_BAIN,RoomPosition.MEUBLE_SALLE_DE_BAIN_X,RoomPosition.MEUBLE_SALLE_DE_BAIN_Y,GameConfiguration.SIZE_MEUBLE_SALLE_DE_BAIN_X,GameConfiguration.SIZE_MEUBLE_SALLE_DE_BAIN_Y,null);
		g.drawImage(GameConfiguration.IMAGE_NAIN,RoomPosition.NAIN_X,RoomPosition.NAIN_Y,GameConfiguration.SIZE_NAIN_X,GameConfiguration.SIZE_NAIN_Y,null);
		g.drawImage(GameConfiguration.IMAGE_PLANTE1,RoomPosition.PLANTE1_X,RoomPosition.PLANTE1_Y,GameConfiguration.SIZE_PLANTE1_X,GameConfiguration.SIZE_PLANTE1_Y,null);
		g.drawImage(GameConfiguration.IMAGE_PLANTE2,RoomPosition.PLANTE2_X,RoomPosition.PLANTE2_Y,GameConfiguration.SIZE_PLANTE2_X,GameConfiguration.SIZE_PLANTE2_Y,null);
		
		// affichage de la position du chien : 
		g.setColor(Color.BLACK);
		g.drawImage(GameConfiguration.IMAGE_DOG,manager.getDog().getPosition().getLine() * GameConfiguration.BLOCK_SIZE, 
				manager.getDog().getPosition().getColumn() * GameConfiguration.BLOCK_SIZE, GameConfiguration.DogSize, GameConfiguration.DogSize,
				null);
		// Father : 
		g.drawImage(GameConfiguration.IMAGE_FATHER,manager.getFather().getPosition().getLine()*GameConfiguration.BLOCK_SIZE,
			    manager.getFather().getPosition().getColumn()*GameConfiguration.BLOCK_SIZE, GameConfiguration.FatherSize,GameConfiguration.FatherSize,
			    null);
	}
} 
