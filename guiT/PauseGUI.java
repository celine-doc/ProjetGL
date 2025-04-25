package guiT;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PauseGUI extends JFrame {
	MaisonGUI maison;
    public PauseGUI(MaisonGUI maison) {
        super("Pause");
        this.maison = maison;
        init();
    }

    private void init() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 20));

        JButton reprendre = new JButton("Reprendre");
        JButton sauvegarder = new JButton("Sauvegarder");
        JButton parametres = new JButton("Paramètres");
        JButton sauvegarderQuitter = new JButton("Sauvegarder et Quitter");
        JButton quitter = new JButton("Quitter");

        buttonPanel.add(reprendre);
        buttonPanel.add(sauvegarder);
        buttonPanel.add(parametres);
        buttonPanel.add(sauvegarderQuitter);
        buttonPanel.add(quitter);

        contentPane.add(buttonPanel, BorderLayout.CENTER);
        
        setSize(400, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Actions des boutons
        reprendre.addActionListener(new ActionReprendre()); // Ferme la fenêtre et reprend le jeu
        quitter.addActionListener(e -> System.exit(0)); // Quitte complètement l'application
    }
    private void close() {
		this.dispose();
	}
    public class ActionReprendre implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			maison.reStartGame();
			close();
		}
    	
    }
}
