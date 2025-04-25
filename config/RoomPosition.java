package config;

import map.Block;

public abstract class RoomPosition {

	// Constante pour les dimensions des pièces en pixels

	// size sale de bain : 
	public static final int LONGUEUR_BATHROOM = 300;
	public static final int LARGEUR_BATHROOM = 220;

	// size chambre
	public static final int LONGUEUR_BEDROOM = 600;

	// size buandery
	public static final int LARGEUR_LAUNDRY = 200;

	// size cuisine
	public static final int SIZE_KITCHEN = 300;

	// size salon
	public static final int LARGEUR_LIVING = SIZE_KITCHEN + LARGEUR_LAUNDRY;

	// size jardin
	public static final int LONGUEUR_GARDEN = 500;
	public static final int LARGEUR_GARDEN = LARGEUR_BATHROOM + LARGEUR_LIVING;

	// Nombre de block sur les longueurs et largeur de chaque pièce 

	public static final int NUMBER_BLOCK_LONGUEUR_BATHROOM = LONGUEUR_BATHROOM / GameConfiguration.BLOCK_SIZE;
	public static final int NUMBER_BLOCK_LARGEUR_BATHROOM = LARGEUR_BATHROOM / GameConfiguration.BLOCK_SIZE;

	public static final int NUMBER_BLOCK_LONGUEUR_BEDROOM = LONGUEUR_BEDROOM / GameConfiguration.BLOCK_SIZE;
	public static final int NUMBER_BLOCK_LARGEUR_BEDROOM = LARGEUR_BATHROOM / GameConfiguration.BLOCK_SIZE;

	public static final int NUMBER_BLOCK_LONGUEUR_LAUNDRY = LONGUEUR_BATHROOM / GameConfiguration.BLOCK_SIZE;
	public static final int NUMBER_BLOCK_LARGEUR_LAUNDRY = LARGEUR_LAUNDRY / GameConfiguration.BLOCK_SIZE;

	public static final int NUMBER_BLOCK_SIZE_KITCHEN = SIZE_KITCHEN / GameConfiguration.BLOCK_SIZE;

	public static final int NUMBER_BLOCK_LONGUEUR_LIVING = LONGUEUR_BEDROOM / GameConfiguration.BLOCK_SIZE;
	public static final int NUMBER_BLOCK_LARGEUR_LIVING = LARGEUR_LIVING / GameConfiguration.BLOCK_SIZE;

	public static final int NUMBER_BLOCK_LONGUEUR_GARDEN = LONGUEUR_GARDEN / GameConfiguration.BLOCK_SIZE;
	public static final int NUMBER_BLOCK_LARGEUR_GARDEN = LARGEUR_GARDEN / GameConfiguration.BLOCK_SIZE;

	// Constante pour la position des portes des pièces en Block 

	// depuis la salle de bain 
		public static final Block DOOR_BATHROOM_TO_BEDROOM = new Block( NUMBER_BLOCK_LONGUEUR_BATHROOM , NUMBER_BLOCK_LARGEUR_BATHROOM / 2 );
		public static final Block DOOR_BATHROOM_TO_LAUNDRY = new Block( NUMBER_BLOCK_LONGUEUR_BATHROOM / 2 , NUMBER_BLOCK_LARGEUR_BATHROOM );
		
		
		// depuis la chambre 
		public static final Block DOOR_BEDROOM_TO_LIVING = new Block( NUMBER_BLOCK_LONGUEUR_BATHROOM + NUMBER_BLOCK_LONGUEUR_BEDROOM - ( NUMBER_BLOCK_LONGUEUR_BEDROOM / 3 ) , NUMBER_BLOCK_LARGEUR_BEDROOM );
		public static final Block DOOR_BEDROOM_TO_BATHROOM = new Block(NUMBER_BLOCK_LONGUEUR_BATHROOM - (GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE), NUMBER_BLOCK_LARGEUR_BATHROOM / 2);

		// depuis la buanderie 
		public static final Block DOOR_LAUNDRY_TO_LIVING = new Block( NUMBER_BLOCK_LONGUEUR_LAUNDRY , ( NUMBER_BLOCK_LARGEUR_LAUNDRY / 2 ) + NUMBER_BLOCK_LARGEUR_BATHROOM );
		public static final Block DOOR_LAUNDRY_TO_KITCHEN = new Block( NUMBER_BLOCK_LONGUEUR_LAUNDRY / 2 , NUMBER_BLOCK_LARGEUR_LAUNDRY + NUMBER_BLOCK_LARGEUR_BATHROOM );
		public static final Block DOOR_LAUNDRY_TO_BATHROOM = new Block(NUMBER_BLOCK_LONGUEUR_BATHROOM / 2 , NUMBER_BLOCK_LARGEUR_BATHROOM-(GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE));

		// depuis la cuisine
		public static final Block DOOR_KITCHEN_TO_LIVING = new Block( NUMBER_BLOCK_SIZE_KITCHEN , (NUMBER_BLOCK_SIZE_KITCHEN / 2 ) + NUMBER_BLOCK_LARGEUR_BATHROOM + NUMBER_BLOCK_LARGEUR_LAUNDRY );
		public static final Block DOOR_KITCHEN_TO_LAUNDRY = new Block(NUMBER_BLOCK_LONGUEUR_LAUNDRY / 2 , NUMBER_BLOCK_LARGEUR_LAUNDRY + NUMBER_BLOCK_LARGEUR_BATHROOM - (GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE));

		// depuis le salon 
		public static final Block DOOR_LIVING_TO_GARDEN = new Block(NUMBER_BLOCK_LONGUEUR_BATHROOM + NUMBER_BLOCK_LONGUEUR_LIVING, NUMBER_BLOCK_LARGEUR_BEDROOM + ( NUMBER_BLOCK_LARGEUR_LIVING / 3 ));
		public static final Block DOOR_LIVING_TO_LAUNDRY = new Block(NUMBER_BLOCK_LONGUEUR_LAUNDRY - (GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE), ( NUMBER_BLOCK_LARGEUR_LAUNDRY / 2 ) + NUMBER_BLOCK_LARGEUR_BATHROOM);
		public static final Block DOOR_LIVING_TO_KITCHEN = new Block(NUMBER_BLOCK_SIZE_KITCHEN - (GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE),(NUMBER_BLOCK_SIZE_KITCHEN / 2 ) + NUMBER_BLOCK_LARGEUR_BATHROOM + NUMBER_BLOCK_LARGEUR_LAUNDRY);
		public static final Block DOOR_LIVING_TO_BEDROOM = new Block(NUMBER_BLOCK_LONGUEUR_BATHROOM + NUMBER_BLOCK_LONGUEUR_BEDROOM - ( NUMBER_BLOCK_LONGUEUR_BEDROOM / 3 ) , NUMBER_BLOCK_LARGEUR_BEDROOM-(GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE));
		
		// depuis le jardin
		public static final Block DOOR_GARDEN_TO_LIVING = new Block(NUMBER_BLOCK_LONGUEUR_BATHROOM + NUMBER_BLOCK_LONGUEUR_LIVING - (GameConfiguration.SIZE_DOOR/GameConfiguration.BLOCK_SIZE), NUMBER_BLOCK_LARGEUR_BEDROOM + ( NUMBER_BLOCK_LARGEUR_LIVING / 3 ));
		
		
	// Position des portes (en px) : 
	// salle de bain -> chambre
	public static final int UP_OF_DOOR_BATHROOM_TO_BEDROOM_X = LONGUEUR_BATHROOM;
	public static final int UP_OF_DOOR_BATHROOM_TO_BEDROOM_Y = (LARGEUR_BATHROOM/2);
	public static final int LOW_OF_DOOR_BATHROOM_TO_BEDROOM_X = LONGUEUR_BATHROOM;
	public static final int LOW_OF_DOOR_BATHROOM_TO_BEDROOM_Y = UP_OF_DOOR_BATHROOM_TO_BEDROOM_Y + GameConfiguration.SIZE_DOOR;

	//Salle de bain -> buanderie
	public static final int LEFT_OF_DOOR_BATHROOM_TO_LAUNDRY_X = (LONGUEUR_BATHROOM/2);
	public static final int LEFT_OF_DOOR_BATHROOM_TO_LAUNDRY_Y = LARGEUR_BATHROOM;
	public static final int RIGHT_OF_DOOR_BATHROOM_TO_LAUNDRY_X = LEFT_OF_DOOR_BATHROOM_TO_LAUNDRY_X + GameConfiguration.SIZE_DOOR;
	public static final int RIGHT_OF_DOOR_BATHROOM_TO_LAUNDRY_Y = LARGEUR_BATHROOM;

	// Buanderie -> cuisine
	public static final int LEFT_OF_DOOR_LAUNDRY_TO_KITCHEN_X = LONGUEUR_BATHROOM/2;
	public static final int LEFT_OF_DOOR_LAUNDRY_TO_KITCHEN_Y = LARGEUR_BATHROOM + LARGEUR_LAUNDRY;
	public static final int RIGHT_OF_DOOR_LAUNDRY_TO_KITCHEN_X = LEFT_OF_DOOR_LAUNDRY_TO_KITCHEN_X + GameConfiguration.SIZE_DOOR;
	public static final int RIGHT_OF_DOOR_LAUNDRY_TO_KITCHEN_Y = LARGEUR_BATHROOM + LARGEUR_LAUNDRY;

	// Buanderie -> salon
	public static final int UP_OF_DOOR_LAUNDRY_TO_LIVING_X = LONGUEUR_BATHROOM;
	public static final int UP_OF_DOOR_LAUNDRY_TO_LIVING_Y = LARGEUR_BATHROOM + (LARGEUR_LAUNDRY/2);
	public static final int LOW_OF_DOOR_LAUNDRY_TO_LIVING_X = LONGUEUR_BATHROOM;
	public static final int LOW_OF_DOOR_LAUNDRY_TO_LIVING_Y = UP_OF_DOOR_LAUNDRY_TO_LIVING_Y + GameConfiguration.SIZE_DOOR;

	// Cuisine -> salon
	public static final int UP_OF_DOOR_KITCHEN_TO_LIVING_X = LONGUEUR_BATHROOM;
	public static final int UP_OF_DOOR_KITCHEN_TO_LIVING_Y = LARGEUR_BATHROOM + LARGEUR_LAUNDRY + (SIZE_KITCHEN/2);
	public static final int LOW_OF_DOOR_KITCHEN_TO_LIVING_X = LONGUEUR_BATHROOM;
	public static final int LOW_OF_DOOR_KITCHEN_TO_LIVING_Y = UP_OF_DOOR_KITCHEN_TO_LIVING_Y + GameConfiguration.SIZE_DOOR;

	// Chambre -> salon 
	public static final int LEFT_OF_DOOR_BEDROOM_TO_LIVING_X = LONGUEUR_BATHROOM + (LONGUEUR_BEDROOM * 2/3);
	public static final int LEFT_OF_DOOR_BEDROOM_TO_LIVING_Y = LARGEUR_BATHROOM;
	public static final int RIGHT_OF_DOOR_BEDROOM_TO_LIVING_X = LEFT_OF_DOOR_BEDROOM_TO_LIVING_X + GameConfiguration.SIZE_DOOR;
	public static final int RIGHT_OF_DOOR_BEDROOM_TO_LIVING_Y = LARGEUR_BATHROOM;
	
	// Salon -> jardin
	public static final int UP_OF_DOOR_LIVING_TO_GARDEN_X = LONGUEUR_BATHROOM + LONGUEUR_BEDROOM;
	public static final int UP_OF_DOOR_LIVING_TO_GARDEN_Y = LARGEUR_BATHROOM + LARGEUR_LIVING/3;
	public static final int LOW_OF_DOOR_LIVING_TO_GARDEN_X = LONGUEUR_BATHROOM + LONGUEUR_BEDROOM;
	public static final int LOW_OF_DOOR_LIVING_TO_GARDEN_Y = UP_OF_DOOR_LIVING_TO_GARDEN_Y + GameConfiguration.SIZE_DOOR;
	
	// Position des objets en pixel
	   public static final int BUISSON_X = (LONGUEUR_BATHROOM+LONGUEUR_BEDROOM+150);
		public static final int BUISSON_Y = (LARGEUR_BATHROOM-20);
		
		public static final int CLOTURE_Y = 0;
		public static final int CLOTURE1_X= LONGUEUR_BATHROOM+LONGUEUR_BEDROOM;
		public static final int CLOTURE2_X= CLOTURE1_X+GameConfiguration.SIZE_CLOTURE_X;
		public static final int CLOTURE3_X= CLOTURE2_X+GameConfiguration.SIZE_CLOTURE_X;
		public static final int CLOTURE4_X= CLOTURE3_X+GameConfiguration.SIZE_CLOTURE_X;
		
		public static final int NICHE_X = LONGUEUR_BATHROOM+LONGUEUR_BEDROOM;
		public static final int NICHE_Y = GameConfiguration.SIZE_CLOTURE_Y;
		
		public static final int EVIER_CUISINE_X=0;
		public static final int EVIER_CUISINE_Y=LARGEUR_BATHROOM+LARGEUR_LAUNDRY;

		public static final int FOUR_X=GameConfiguration.SIZE_EVIER_CUISINE_X;
		public static final int FOUR_Y=LARGEUR_BATHROOM+LARGEUR_LAUNDRY;

		public static final int FRIGO_X=FOUR_X + GameConfiguration.SIZE_FOUR_X;
		public static final int FRIGO_Y=LARGEUR_BATHROOM+LARGEUR_LAUNDRY;
		
		public static final int MEUBLE_CUISINE_X = 0;
		public static final int MEUBLE_CUISINE_Y = LARGEUR_BATHROOM+LARGEUR_LAUNDRY+SIZE_KITCHEN-GameConfiguration.SIZE_MEUBLE_CUISINE_Y;
		
		public static final int MACHINE_A_LAVER_X = 0;
		public static final int MACHINE_A_LAVER_Y = LARGEUR_BATHROOM+LARGEUR_LAUNDRY-GameConfiguration.SIZE_MACHINE_A_LAVER_Y;
		
		public static final int SECHOIR_X = 0;
		public static final int SECHOIR_Y = LARGEUR_BATHROOM;
		
		public static final int PANIER_LINGE_X = LONGUEUR_BATHROOM-GameConfiguration.SIZE_PANIER_LINGE_X;
		public static final int PANIER_LINGE_Y = LARGEUR_BATHROOM+LARGEUR_LAUNDRY-GameConfiguration.SIZE_PANIER_LINGE_Y;
		
		public static final int TOILET_X = 0;
		public static final int TOILET_Y = 0;

		public static final int PANIER_CHIEN_X = LONGUEUR_BATHROOM;
		public static final int PANIER_CHIEN_Y = LARGEUR_BATHROOM-GameConfiguration.SIZE_PANIER_CHIEN_Y;
		
		public static final int ARBRE_CHAT_X = LONGUEUR_BATHROOM;
		public static final int ARBRE_CHAT_Y = PANIER_CHIEN_Y-(GameConfiguration.SIZE_ARBRE_CHAT_Y+120);
		
		public static final int ARMOIRE_X = NICHE_X-100;
		public static final int ARMOIRE_Y = ARBRE_CHAT_Y;
		
		public static final int LIT_X =ARMOIRE_X-100 ;
		public static final int LIT_Y = ARBRE_CHAT_Y;
		
		public static final int BAIGNOIRE_X = 0;
		public static final int BAIGNOIRE_Y = 100;
		
		public static final int BIBLIOTHEQUE_X =ARMOIRE_X  ;
		public static final int BIBLIOTHEQUE_Y = 220;
		
		public static final int CANAPE_X =ARMOIRE_X-200 ;
		public static final int CANAPE_Y =BIBLIOTHEQUE_Y;
		
		public static final int EVIER_SALLE_DE_BAIN_X =100 ;
		public static final int EVIER_SALLE_DE_BAIN_Y =0;
		
		public static final int MEUBLE_BUANDERIE_X =PANIER_LINGE_X-20 ;
		public static final int MEUBLE_BUANDERIE_Y =SECHOIR_Y;
		
		public static final int MEUBLE_SALLE_DE_BAIN_X =LONGUEUR_BATHROOM-GameConfiguration.SIZE_MEUBLE_SALLE_DE_BAIN_X ;
		public static final int MEUBLE_SALLE_DE_BAIN_Y =0;
		
		public static final int TABLE_A_MANGER_X =LONGUEUR_BATHROOM-GameConfiguration.SIZE_TABLE_A_MANGER_X ;
		public static final int TABLE_A_MANGER_Y =PANIER_LINGE_Y+60;
		
		public static final int GAMELLE_X =0 ;
		public static final int GAMELLE_Y =EVIER_CUISINE_Y+70;
		
		public static final int GAMELLE2_X =0 ;
		public static final int GAMELLE2_Y =GAMELLE_Y+30;
		
		public static final int TONDEUSE_X = NICHE_X+200 ;
		public static final int TONDEUSE_Y =NICHE_Y;
		
		public static final int NAIN_X = NICHE_X+50 ;
		public static final int NAIN_Y =NICHE_Y+450;
		
		public static final int PLANTE1_X = PANIER_CHIEN_X ;
		public static final int PLANTE1_Y =220;
		
		public static final int PLANTE2_X = PANIER_CHIEN_X+535 ;
		public static final int PLANTE2_Y =PANIER_CHIEN_Y;
		
		
		// Block des objets
		// Panier chien
		public static final Block PANIER_CHIEN = new Block(PANIER_CHIEN_X/GameConfiguration.BLOCK_SIZE,PANIER_CHIEN_Y/GameConfiguration.BLOCK_SIZE);
		// Niche Chien
		public static final Block NICHE = new Block(NICHE_X/GameConfiguration.BLOCK_SIZE,NICHE_Y/GameConfiguration.BLOCK_SIZE);
		//Gamelle Chien
		public static final Block GAMELLE = new Block(GAMELLE_X/GameConfiguration.BLOCK_SIZE,GAMELLE_Y/GameConfiguration.BLOCK_SIZE);
		//Gamelle Chat
		public static final Block GAMELLE2 = new Block(GAMELLE2_X/GameConfiguration.BLOCK_SIZE,GAMELLE2_Y/GameConfiguration.BLOCK_SIZE);
		//Lit 
		public static final Block LIT = new Block(LIT_X/GameConfiguration.BLOCK_SIZE,LIT_Y/GameConfiguration.BLOCK_SIZE);
		//Canapé
		public static final Block CANAPE = new Block(CANAPE_X/GameConfiguration.BLOCK_SIZE,CANAPE_Y/GameConfiguration.BLOCK_SIZE);
		//Arbre à chat 
		public static final Block ARBRE_CHAT = new Block(ARBRE_CHAT_X/GameConfiguration.BLOCK_SIZE,ARBRE_CHAT_Y/GameConfiguration.BLOCK_SIZE);
		//Table cuisine
		public static final Block TABLE_A_MANGER = new Block(TABLE_A_MANGER_X/GameConfiguration.BLOCK_SIZE,TABLE_A_MANGER_Y/GameConfiguration.BLOCK_SIZE);
		//Buisson
		public static final Block BUISSON = new Block(BUISSON_X/GameConfiguration.BLOCK_SIZE,BUISSON_Y/GameConfiguration.BLOCK_SIZE);
		//Tondeuse
		public static final Block TONDEUSE = new Block(TONDEUSE_X/GameConfiguration.BLOCK_SIZE,TONDEUSE_Y/GameConfiguration.BLOCK_SIZE);
		//Nain de jardin 
		public static final Block NAIN = new Block(NAIN_X/GameConfiguration.BLOCK_SIZE,NAIN_Y/GameConfiguration.BLOCK_SIZE);
		//Plante 1 
		public static final Block PLANTE1 = new Block(PLANTE1_X/GameConfiguration.BLOCK_SIZE,PLANTE1_Y/GameConfiguration.BLOCK_SIZE);
		//Plante 2
		public static final Block PLANTE2 = new Block(PLANTE2_X/GameConfiguration.BLOCK_SIZE,PLANTE2_Y/GameConfiguration.BLOCK_SIZE);
	}

