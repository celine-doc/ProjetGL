package guiT;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JournalGUI extends JFrame {
    private JTextArea zoneTexte;

    public JournalGUI(String fichier, String titre) {
        setTitle(titre);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        zoneTexte = new JTextArea();
        zoneTexte.setFont(new Font("Serif", Font.ITALIC, 14));
        zoneTexte.setEditable(false);
        zoneTexte.setLineWrap(true);  // Active le retour automatique à la ligne
        zoneTexte.setWrapStyleWord(true);  // Coupe uniquement aux espaces pour éviter de couper les mots

        add(zoneTexte, BorderLayout.CENTER);

        chargerJournal(fichier);
    }

    private void chargerJournal(String fichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            StringBuilder contenu = new StringBuilder();
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
            zoneTexte.setText(contenu.toString());
        } catch (IOException e) {
            zoneTexte.setText("Aucune entrée dans le journal.");
        }
    }
    
    
}
