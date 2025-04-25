package guiT;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import java.util.List;

public class GraphiquesApprentissageGUI extends JFrame {
    private ChartManager chartManager;
    private List<String> actionNames;
    private JPanel graphPanel;
    private String currentAction;
    private ChartPanel currentChartPanel;
    private TimeSeries currentSeries;

    public GraphiquesApprentissageGUI(String title, ChartManager chartManager, List<String> actionNames) {
        this.chartManager = chartManager;
        this.actionNames = actionNames;
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        init();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(actionNames.size(), 1));
        buttonPanel.setPreferredSize(new Dimension(200, 600));

        for (String actionName : actionNames) {
            JButton actionButton = new JButton(formaterNomAction(actionName));
            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    afficherGraphePourAction(actionName);
                }
            });
            buttonPanel.add(actionButton);
        }

        graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout());

        add(buttonPanel, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);
    }

    private void afficherGraphePourAction(String actionName) {
        currentAction = actionName;
        currentSeries = chartManager.getActionSeries(actionName);

        JFreeChart jChart = chartManager.getActionEvolutionChart(actionName);
        if (jChart == null) {
            System.out.println("Erreur : Impossible d'obtenir le graphique pour " + actionName);
            return;
        }

        graphPanel.removeAll();
        currentChartPanel = new ChartPanel(jChart);
        graphPanel.add(currentChartPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
        System.out.println("Graphique affiché pour " + formaterNomAction(actionName));
    }

    public void mettreAJourGrapheAction() {
        if (currentAction == null || currentSeries == null || currentChartPanel == null) {
            return;
        }
        // La série est mise à jour dans ChartManager, juste rafraîchir
        currentChartPanel.repaint();
        graphPanel.revalidate();
        graphPanel.repaint();
        System.out.println("Graphique mis à jour pour " + formaterNomAction(currentAction));
    }

    private String formaterNomAction(String actionName) {
        StringBuilder formatted = new StringBuilder();
        String[] words = actionName.split("(?=[A-Z])");
        for (String word : words) {
            if (!word.isEmpty()) {
                formatted.append(word.substring(0, 1).toUpperCase())
                         .append(word.substring(1).toLowerCase())
                         .append(" ");
            }
        }
        return formatted.toString().trim();
    }
}
