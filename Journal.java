package process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

 public class Journal{
	 private String fileName;

	 public Journal(String fileName) {
	     this.fileName = fileName;
	 }

	 // Lire et afficher le contenu du fichier
	  public void readTxt() {
	      System.out.println("\nContenu du journal : " + fileName);
	       try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
	            String ligne;
	            while ((ligne = reader.readLine()) != null) {
	                System.out.println(ligne);
	             }
	       } catch (IOException e) {
	            System.out.println("Erreur de lecture : " + e.getMessage());
	        }
	    }

	    // Écrire une nouvelle ligne dans le fichier (sans effacer le contenu précédent)
	    public void writeTxt(String newLigne) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) { 
	            writer.write(newLigne);
	            writer.newLine();
	            System.out.println("Ligne ajoutée au journal : " + newLigne);
	        } catch (IOException e) {
	            System.out.println("Erreur d'écriture : " + e.getMessage());
	        }
	    }
}

