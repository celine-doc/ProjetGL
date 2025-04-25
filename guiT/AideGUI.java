package guiT;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AideGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MaisonGUI maison;
	public AideGUI(String title, MaisonGUI maison) {
		super(title);
		this.maison = maison;
		init();
	}

	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setLocationRelativeTo(null); 

		// Panel principal 
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// Panel pour les fonctionnalités 
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(0, 1)); 

		// Liste des fonctionnalités
		String[] fonctionnalités = {
				" a : Récompenser le chat avec une friandise ",
				" b : Récompenser le chien avec une friandise ",
				" c : Réprimander verbalement le chat : dire Non! ",
				" d : Réprimander verbalement le chien : dire Non!",
				" e : Prendre le chat dans ses bras",
				" f : Récompenser le chien par des caresses",
				" g : utiliser un pointeur laser",
				" h : Demander au chien de s'assoir ",
				" i : Placer un objet attirant pour le chat pour l'inciter à jouer à un endroit précis ",
				" j : Demander au chien de donner la patte",
		};

		// Ajouter chaque fonctionnalité sous forme de JLabel
		for (String fonctionnalité : fonctionnalités) {
			textPanel.add(new JLabel(fonctionnalité));
		}

		panel.add(textPanel, BorderLayout.CENTER); 


		// Bouton Retour
		JButton Retour = new JButton("Retour");
		Retour.addActionListener(new actionRestartGame());

		JPanel buttonPanel = new JPanel(); 
		buttonPanel.add(Retour);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		Container container = getContentPane();
		container.add(panel);
	}

	public static void goToMenuAide(MaisonGUI maison) {
		AideGUI aideWindow = new AideGUI("Aide - Fonctionnalités",maison);
		aideWindow.setVisible(true);
	}
	private void close() {
		this.dispose();
	}
	class actionRestartGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			maison.reStartGame();
			close();
		}

	}
}



