package guiT;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;

public class GraphiquesApprentissageGUI extends JFrame {
    private ChartManager chartManager; 
    private List<String> actionNames; // Liste des actions
    private JPanel graphPanel;
    private String currentAction;
    private ChartPanel currentChartPanel;
    private XYSeries currentSeries;

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
        currentSeries = new XYSeries(formaterNomAction(actionName));

        JFreeChart jChart = chartManager.getActionEvolutionChart(actionName);
        if (jChart == null) {
            System.out.println("Erreur : Impossible d'obtenir le graphique pour " + actionName);
            return;
        }

        XYSeries series = ((XYSeriesCollection) jChart.getXYPlot().getDataset()).getSeries(0);
        for (int i = 0; i < series.getItemCount(); i++) {
            currentSeries.add(series.getX(i), series.getY(i));
        }

        graphPanel.removeAll();
        currentChartPanel = new ChartPanel(jChart);
        graphPanel.add(currentChartPanel, BorderLayout.CENTER);
        graphPanel.revalidate();
        graphPanel.repaint();
        System.out.println("Graphique affiché pour " + formaterNomAction(actionName));
    }

    /**
     * Met à jour le graphique de l'action actuellement sélectionnée.
     */
    public void mettreAJourGrapheAction() {
        if (currentAction == null || currentSeries == null || currentChartPanel == null) {
            return;
        }
        currentSeries.clear();

        JFreeChart jChart = chartManager.getActionEvolutionChart(currentAction);
        if (jChart == null) {
            System.out.println("Erreur : Impossible d'obtenir le graphique pour " + currentAction);
            return;
        }

        XYSeries series = ((XYSeriesCollection) jChart.getXYPlot().getDataset()).getSeries(0);
        for (int i = 0; i < series.getItemCount(); i++) {
            currentSeries.add(series.getX(i), series.getY(i));
        }

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