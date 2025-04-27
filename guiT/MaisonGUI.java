package guiT;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYSeries;

import config.GameConfiguration;
import map.Map;
import process.MobileElementManager;
import mobile.Dog;

public class MaisonGUI extends JFrame implements Runnable {
    MaisonGUI thisM;
    Thread gameThread;
    private static final long serialVersionUID = 1L;
    private Map map;
    private MobileElementManager manager;
    private Dashboard dashboard;
    private boolean runningGame;
    private boolean running;
    private JPanel rightPanel;
    private ChartPanel confidenceChartPanel;
    private ChartPanel mentalStateChartPanel;
    private ChartPanel physicalStateChartPanel;
    private XYSeries confidenceSeries;
    private XYSeries mentalStateSeries;
    private XYSeries physicalStateSeries;

    public MaisonGUI(String title) {
        super(title);
        thisM = this;
        init();
    }

    private void init() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
        manager = new MobileElementManager(map);
        dashboard = new Dashboard();
        dashboard.setManager(manager);

        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setPreferredSize(new Dimension(300, 620));
        rightPanel.setLayout(new GridLayout(4, 1));

        // Bouton "Voir apprentissage"
        JButton viewLearningButton = new JButton("Voir apprentissage");
        viewLearningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GraphiquesApprentissageGUI("Graphiques d'Apprentissage", manager.getChartManager(), GameConfiguration.listNomActionChien);
            }
        });
        rightPanel.add(viewLearningButton);

        JPanel dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.add(dashboard, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 100));
        bottomPanel.setLayout(new GridLayout(1, 4));

        JButton buttonPause = new JButton("Pause");
        buttonPause.setForeground(Color.WHITE);
        buttonPause.setBackground(Color.BLACK);
        buttonPause.addActionListener(new ActionPause());
        bottomPanel.add(buttonPause);

        JButton buttonHelp = new JButton("Aide");
        buttonHelp.setForeground(Color.WHITE);
        buttonHelp.setBackground(Color.BLACK);
        buttonHelp.addActionListener(new ActionHelp());
        bottomPanel.add(buttonHelp);

        JButton buttonDog = new JButton("Journal Chien");
        buttonDog.setForeground(Color.WHITE);
        buttonDog.setBackground(Color.BLACK);
        buttonDog.addActionListener(new ActionDog());
        bottomPanel.add(buttonDog);

        JButton buttonCat = new JButton("Journal Chat");
        buttonCat.setForeground(Color.WHITE);
        buttonCat.setBackground(Color.BLACK);
        buttonCat.addActionListener(new ActionCat());
        bottomPanel.add(buttonCat);

        afficherGraphe(); // Initialiser les graphiques

        contentPane.add(dashboardPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Ajout du KeyListener pour gérer les touches
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = Character.toUpperCase(e.getKeyChar());
                if (key == 'P' || key == 'R') {
                    manager.getFather().setActionAnimal(true);
                    manager.getFather().setTarget(manager.getDog());
                    manager.getFather().setPunishment(key == 'P'); // 'P' pour punition, 'R' pour récompense
                    System.out.println("Father cible le chien avec " + (key == 'P' ? "punition" : "récompense"));
                }
            }
        });
        setFocusable(true); // Permet au JFrame de recevoir les événements clavier
        requestFocusInWindow(); // Assure que le focus est sur le JFrame

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        startGame();
    }

    private void afficherGraphe() {
        ChartManager chartManager = manager.getChartManager();

        confidenceChartPanel = new ChartPanel(chartManager.getTrustEvolutionChart());
        rightPanel.add(confidenceChartPanel);

        mentalStateChartPanel = new ChartPanel(chartManager.getMentalStateEvolutionChart());
        rightPanel.add(mentalStateChartPanel);

        physicalStateChartPanel = new ChartPanel(chartManager.getPhysicalStateEvolutionChart());
        rightPanel.add(physicalStateChartPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void mettreAJourGraphes() {
        confidenceChartPanel.repaint();
        mentalStateChartPanel.repaint();
        physicalStateChartPanel.repaint();
        rightPanel.revalidate();
        rightPanel.repaint();
        System.out.println("Graphiques mis à jour");
    }

    class ActionPause implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pauseGame(true);
        }
    }

    public void pauseGame(boolean bouttonPause) {
        if (bouttonPause) {
            if (runningGame) {
                runningGame = false;
                new PauseGUI(thisM);
            } else {
                reStartGame();
            }
        } else {
            if (!runningGame) {
                reStartGame();
            }
        }
    }

    class ActionHelp implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runningGame = false;
            AideGUI.goToMenuAide(thisM);
        }
    }

    class ActionDog implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            testerJournal();
            new JournalGUI("journal_dog.txt", "Journal du Chien").setVisible(true);
        }
    }

    class ActionCat implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            testerJournal();
            new JournalGUI("journal_cat.txt", "Journal du Chat").setVisible(true);
        }
    }

    public void reStartGame() {
        runningGame = true;
    }

    public void startGame() {
        runningGame = true;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        int iterationCount = 0;
        while (running) {
            try {
                if (runningGame) {
                    dashboard.repaint();
                    manager.action((int) ((Math.random() * 100)) % 2);
                    mettreAJourGraphes();
                    iterationCount++;
                    if (iterationCount % 10 == 0) {
                        manager.getDog().getLearning().degradationNaturelle();
                    }
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dashboard.repaint();
        }
    }

    public void testerJournal() {
        Dog dog = manager.getDog();
        dog.afficherJournal();
    }
}
