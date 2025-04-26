package config;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import engine.Action;
import map.Block;

/**
 * 
 *
 */
public class GameConfiguration {
	
	
	public static HashMap<String, Action> listActionChien = new HashMap<String, Action>();
	public static ArrayList<String> listNomActionChien = new ArrayList<String>();
	
	public static void initActionChien() {
		listActionChien.put("dormirPanier", new Action("bedroom","dormirPanier",RoomPosition.PANIER_CHIEN,20,"je suis allé dormir dans mon panier, au chaud.",200));
		listNomActionChien.add("dormirPanier");
		listActionChien.put("dormirNiche", new Action("garden","dormirNiche",RoomPosition.NICHE,15,"je suis allé dormir dans ma niche, au frais, pour protéger mon téritoire.",200));
		listNomActionChien.add("dormirNiche");
		listActionChien.put("dormirLit", new Action("bedroom","dormirLit",RoomPosition.LIT,10,"Je pars dormir sur le lit… oui je sais que je n’ai pas le droit, mais tant pis !",200));
		listNomActionChien.add("dormirLit");
		listActionChien.put("dormirCanape", new Action("living","dormirCanape",RoomPosition.CANAPE,15,"Je monte sur le canapé pour dormir… mon humain va râler, mais tant pis",160));
		listNomActionChien.add("dormirCanape");
		listActionChien.put("mangerGamelle", new Action("kitchen","manger",RoomPosition.GAMELLE,20,"Bon… c’est l’heure du festin ! À moi la gamelle !",100));
		listNomActionChien.add("mangerGamelle");
		listActionChien.put("mangerGamelle2", new Action("kitchen","manger",RoomPosition.GAMELLE2,15,"J’me suis discrètement servi dans la gamelle du chat…",100));
		listNomActionChien.add("mangerGamelle2");
		listActionChien.put("monterTable", new Action("kitchen","monterTable",RoomPosition.TABLE_A_MANGER,5,"Je monte juste jeter un œil… promis je touche à rien (enfin presque)",0));
		listNomActionChien.add("monterTable");
		
		
	}
	
	
	public static HashMap<String, Action> listActionChat = new HashMap<String, Action>();
	public static ArrayList<String> listNomActionChat = new ArrayList<String>();
	
	public static void initActionChat() {
		listActionChat.put("dormirPanier", new Action("bedroom","dormirPanier",RoomPosition.PANIER_CHIEN,0,"je suis allé dormir dans le panier du chien .",200));
		listNomActionChat.add("dormirPanier");
		listActionChat.put("dormirLit", new Action("bedroom","dormirLit",RoomPosition.LIT,1,"je suis allé dormir sur le lit , bien au chaud ",200));
		listNomActionChat.add("dormirLit");
		listActionChat.put("jouerArbreAChat", new Action("bedroom","jouerArbreAChat",RoomPosition.ARBRE_CHAT,0,"Wouhouuu ! C’est mon parc d’attractions à moi tout seul !",200));
		listNomActionChat.add("jouerArbreAChat");
		listActionChat.put("dormirCanape", new Action("living","dormirCanape",RoomPosition.CANAPE,0,"Je monte sur le canapé pour dormir… mon humain va râler, mais tant pis",160));
		listNomActionChat.add("dormirCanape");
		listActionChat.put("mangerGamelle", new Action("kitchen","manger",RoomPosition.GAMELLE,0,"J’me suis discrètement servi dans la gamelle du chien…",100));
		listNomActionChat.add("mangerGamelle");
		listActionChat.put("mangerGamelle2", new Action("kitchen","manger",RoomPosition.GAMELLE2,0,"Bon… c’est l’heure du festin ! À moi la gamelle !",100));
		listNomActionChat.add("mangerGamelle2");
		listActionChat.put("monterTable", new Action("kitchen","monterTable",RoomPosition.TABLE_A_MANGER,0,"Je monte juste jeter un œil… promis je touche à rien (enfin presque)",0));
		listNomActionChat.add("monterTable");
		
		
	}
	
	
	
	
	public static final int WINDOW_WIDTH = 1700;
	public static final int WINDOW_HEIGHT = 750;
	
	public static final int BLOCK_SIZE = 5;
	
	public static final int LINE_COUNT = WINDOW_WIDTH / BLOCK_SIZE;
	public static final int COLUMN_COUNT = WINDOW_HEIGHT / BLOCK_SIZE;
	
	public static int initialHealthState = 50;
	public static int initialMentalState = 50;
	
	
	// Size objet avec intéraction
		public static final int SIZE_BUISSON_X = 100;
		public static final int SIZE_BUISSON_Y = 70;
		
		public static final int SIZE_NICHE_X = 100;
		public static final int SIZE_NICHE_Y = 80;
		
		public static final int SIZE_PANIER_CHIEN_X = 60;
		public static final int SIZE_PANIER_CHIEN_Y = 50;
		
		public static final int SIZE_ARBRE_CHAT_X = 60;
		public static final int SIZE_ARBRE_CHAT_Y = 50;
		
		public static final int SIZE_CANAPE_X = 100;
		public static final int SIZE_CANAPE_Y = 100;
		
		public static final int SIZE_TABLE_A_MANGER_X = 80;
		public static final int SIZE_TABLE_A_MANGER_Y = 80;
		
		public static final int SIZE_GAMELLE_X = 30;
		public static final int SIZE_GAMELLE_Y = 30;
		
		public static final int SIZE_GAMELLE2_X = 30;
		public static final int SIZE_GAMELLE2_Y = 30;
		
		public static final int SIZE_LIT_X = 50;
		public static final int SIZE_LIT_Y = 100;
		
		public static final int SIZE_TONDEUSE_X = 60;
		public static final int SIZE_TONDEUSE_Y = 60;
		
		
		
		
		// Size objet sans intéraction
		public static final int SIZE_CLOTURE_X = 100;
		public static final int SIZE_CLOTURE_Y = 50;
		
		public static final int SIZE_EVIER_CUISINE_X = 50;
		public static final int SIZE_EVIER_CUISINE_Y = 75;
		
		public static final int SIZE_FOUR_X = 50;
		public static final int SIZE_FOUR_Y = 75;
		
		public static final int SIZE_FRIGO_X = 50;
		public static final int SIZE_FRIGO_Y = 75;
		
		public static final int SIZE_MEBUBLE_CUISINE_X = 200;
		public static final int SIZE_MEUBLE_CUISINE_Y = 150;
		
		public static final int SIZE_MACHINE_A_LAVER_X = 75;
		public static final int SIZE_MACHINE_A_LAVER_Y = 75;
		
		public static final int SIZE_SECHOIR_X = 75;
		public static final int SIZE_SECHOIR_Y = 75;
		
		public static final int SIZE_PANIER_LINGE_X = 80;
		public static final int SIZE_PANIER_LINGE_Y = 40;
		
		public static final int SIZE_TOILET_X = 40;
		public static final int SIZE_TOILET_Y = 50;
		
		public static final int SIZE_ARMOIRE_X = 100;
		public static final int SIZE_ARMOIRE_Y = 100;
		
		public static final int SIZE_BAIGNOIRE_X = 100;
		public static final int SIZE_BAIGNOIRE_Y = 100;
		
		public static final int SIZE_BIBLIOTHEQUE_X = 100;
		public static final int SIZE_BIBLIOTHEQUE_Y = 100;
		
		public static final int SIZE_EVIER_SALLE_DE_BAIN_X = 50;
		public static final int SIZE_EVIER_SALLE_DE_BAIN_Y = 50;
		
		public static final int SIZE_MEUBLE_BUANDERIE_X = 90;
		public static final int SIZE_MEUBLE_BUANDERIE_Y = 90;
		
		public static final int SIZE_MEUBLE_SALLE_DE_BAIN_X = 60;
		public static final int SIZE_MEUBLE_SALLE_DE_BAIN_Y = 60;
		
		public static final int SIZE_NAIN_X = 60;
		public static final int SIZE_NAIN_Y = 60;
		
		public static final int SIZE_PLANTE1_X = 60;
		public static final int SIZE_PLANTE1_Y = 60;
		
		public static final int SIZE_PLANTE2_X = 50;
		public static final int SIZE_PLANTE2_Y = 50;
		
		
		
	
	public static final int DogSize = 40;
	public static final int CatSize= 40;
	public static final int ThiefSize= 45;
	public static final int FatherSize= 80;
	public static final int MotherSize= 80;
	public static final int GuestSize= 80;
	public static final int KidSize= 70;
	
	public static final int SIZE_DOOR = DogSize;
	

    public static final int DEFAULT_POSITION_DOG_X = 0;
    public static final int DEFAULT_POSITION_DOG_Y = 0;
    
	//public static final int DEFAULT_POSITION_DOG_X = BLOCK_SIZE;
	//public static final int DEFAULT_POSITION_DOG_Y = BLOCK_SIZE;
	
	public static final int DEFAULT_POSITION_CAT_X = BLOCK_SIZE * 20;
	public static final int DEFAULT_POSITION_CAT_Y = BLOCK_SIZE * 2;
	
	
	public static final int DEFAULT_POSITION_THIEF_X = BLOCK_SIZE * 45;
	public static final int DEFAULT_POSITION_THIEF_Y = BLOCK_SIZE * 10;
	
	public static final int DEFAULT_POSITION_FATHER_X = BLOCK_SIZE * 7;
	public static final int DEFAULT_POSITION_FATHER_Y = BLOCK_SIZE * 20;
	
	public static final int DEFAULT_POSITION_MOTHER_X = BLOCK_SIZE * 25;
	public static final int DEFAULT_POSITION_MOTHER_Y = BLOCK_SIZE * 20;
	
	public static final int DEFAULT_POSITION_GUEST_X = BLOCK_SIZE * 22;
	public static final int DEFAULT_POSITION_GUEST_Y = BLOCK_SIZE * 20;
	
	public static final int DEFAULT_POSITION_KID_X = BLOCK_SIZE * 25;
	public static final int DEFAULT_POSITION_KID_Y = BLOCK_SIZE * 5;
	
	public static final Block DESTINATION_TEST = RoomPosition.DOOR_LAUNDRY_TO_KITCHEN;
	
	
	// Image 
	
	public static final Image IMAGE_DOG= readImage("src/images/dog.png");
	public static final Image IMAGE_CAT= readImage("src/images/cat.png");
	public static final Image IMAGE_FATHER= readImage("src/images/father.png");
	public static final Image IMAGE_MOTHER= readImage("src/images/mother.png");
	public static final Image IMAGE_KID= readImage("src/images/kid.png");
	public static final Image IMAGE_GUEST= readImage("src/images/guest.png");
	public static final Image IMAGE_THIEF= readImage("src/images/thief.png");
	
	// Image objet avec intéraction animal
		public static final Image IMAGE_BUISSON = readImage("src/images/buisson.png");
		public static final Image IMAGE_NICHE = readImage("src/images/niche.png");
		public static final Image IMAGE_GAMELLE = readImage("src/images/gamelle.png");
		public static final Image IMAGE_GAMELLE2 = readImage("src/images/gamelle2.png");
		public static final Image IMAGE_LIT = readImage("src/images/lit.png");
		public static final Image IMAGE_PANIER_CHIEN = readImage("src/images/panier.png");
		public static final Image IMAGE_CANAPE = readImage("src/images/canape.png");
		public static final Image IMAGE_ARBRE_CHAT = readImage("src/images/arbreAchat.png");
		public static final Image IMAGE_TABLE_A_MANGER = readImage("src/images/table_a_manger.png");
		public static final Image IMAGE_TONDEUSE = readImage("src/images/tondeuse.png");
		
		// Image objet sans intéraction animal
		public static final Image IMAGE_CLOTURE = readImage("src/images/cloture.png");
		public static final Image IMAGE_EVIER_CUISINE = readImage("src/images/evierCuisine.png");
		public static final Image IMAGE_FOUR = readImage("src/images/four.png");
		public static final Image IMAGE_FRIGO = readImage("src/images/frigo.png");
		public static final Image IMAGE_MEUBLE_CUISINE = readImage("src/images/meubleCuisine.png");
		public static final Image IMAGE_MEUBLE_BUANDERIE = readImage("src/images/meubleBuanderie.png");
		public static final Image IMAGE_MACHINE_A_LAVER = readImage("src/images/machineAlaver.png");
		public static final Image IMAGE_SECHOIR= readImage("src/images/sechoir.png");
		public static final Image IMAGE_PANIER_LINGE = readImage("src/images/panierLinge.png");
		public static final Image IMAGE_TOILET = readImage("src/images/toilet.png");
		public static final Image IMAGE_ARMOIRE = readImage("src/images/armoire.png");
		public static final Image IMAGE_BIBLIOTHEQUE = readImage("src/images/bibliotheque.png");
		public static final Image IMAGE_EVIER_SALLE_DE_BAIN = readImage("src/images/evierSalleDeBain.png");
		public static final Image IMAGE_BAIGNOIRE = readImage("src/images/baignoire.png");
		public static final Image IMAGE_MEUBLE_SALLE_DE_BAIN = readImage("src/images/meubleSalleDeBain.png");
		public static final Image IMAGE_NAIN = readImage("src/images/nainDeJardin.png");
		public static final Image IMAGE_PLANTE1 = readImage("src/images/plante1.png");
		public static final Image IMAGE_PLANTE2 = readImage("src/images/plante2.png");
		// A implementer 
		public static final Image IMAGE_TELEVISION = readImage("src/images/television.png");
		public static final Image IMAGE_TAPIS1 = readImage("src/images/tapis1.png");
		public static final Image IMAGE_TAPIS2 = readImage("src/images/tapis2.png");
		
		
		
		public static final Image IMAGE_ = readImage("src/images/.png");
	
	
	 public static Image readImage(String filePath) {
			try {
				return ImageIO.read(new File(filePath));
			} catch (IOException e) {
				System.err.println("-- Can not read the image file ! --");
				return null;
			}
		}
}

