package guiT;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import config.GameConfiguration;
import map.Map;

public class MenuGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    MaisonGUI menuJeu;

    public MenuGUI(String title) {
        super(title);
        init();
    }

    private void init() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 30, 30));

        JButton nouvellePartie = new JButton("Nouvelle Partie");
        JButton quitter = new JButton("Quitter");

        buttonPanel.add(nouvellePartie);
        buttonPanel.add(quitter);

        contentPane.add(buttonPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        nouvellePartie.addActionListener(new actionStartGame());
        quitter.addActionListener(e -> System.exit(0)); // pour quitter
    }

    public static void main(String[] args) {
        new MenuGUI("Menu");
    }

    private void close() {
        this.dispose();
    }

    class actionStartGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Initialiser les dépendances
            Map map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
            ChartManager chartManager = new ChartManager();
            GraphiquesApprentissageGUI learningGUI = new GraphiquesApprentissageGUI(
                "Graphiques d'Apprentissage",
                chartManager,
                GameConfiguration.listNomActionChien
            );
            menuJeu = new MaisonGUI("Maison", learningGUI);
            close();
        }
    }
		
}
