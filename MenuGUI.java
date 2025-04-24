package guiT;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MaisonGUI menuJeu;
	
	public MenuGUI(String title) {
		super(title);
		init();
	}
	
	private void init() {

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 30, 30));
		
		JButton nouvellePartie = new JButton("Nouvelle Partie");
        JButton chargerPartie = new JButton("Charger Partie ");
        JButton paramètres = new JButton("Paramètres");
        JButton quitter = new JButton("Quitter");
        
        buttonPanel.add(nouvellePartie);
        buttonPanel.add(chargerPartie);
        buttonPanel.add(paramètres);
        buttonPanel.add(quitter);
        
        contentPane.add(buttonPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	
	
		nouvellePartie.addActionListener(new actionStartGame());

	}
	
	public static void main(String[] args ){
		MenuGUI menuGUI = new MenuGUI("Menu");
	}
	private void close() {
		this.dispose();
	}
	class actionStartGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			menuJeu = new MaisonGUI("maison");
			menuJeu.startGame();
			close();
		}
		
	}
}

  