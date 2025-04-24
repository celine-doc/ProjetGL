package guiT;

import java.util.ArrayList;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import config.GameConfiguration;
import mobile.Dog;

/**
 * Cette classe gère les graphiques du jeu, incluant :
 * 1) Les courbes d'évolution de la confiance, de l'état mental et de l'état physique.
 * 2) Les courbes d'évolution des scores pour chaque action du chien.
 * 
 * Les données sont fournies par {@link MobileElementManager} via les méthodes register*.
 * 
 * @author 
 */
public class ChartManager {
    private HashMap<String, ArrayList<Integer>> actionScores = new HashMap<>(); // action + liste des scores
    private ArrayList<Double> trustLevels = new ArrayList<>();
    private ArrayList<Double> mentalStates = new ArrayList<>();
    private ArrayList<Double> physicalStates = new ArrayList<>();

    /**
     * Initialise le gestionnaire en créant des listes vides pour les scores des actions
     * et des valeurs initiales pour la confiance, l'état mental et l'état physique.
     */
    public ChartManager() {
        for (String actionName : GameConfiguration.listNomActionChien) {
            actionScores.put(actionName, new ArrayList<>());
            actionScores.get(actionName).add(0); // Score initial = 0
        }
        trustLevels.add(0.5); // Confiance initiale = 0.5
        mentalStates.add(0.5); // État mental initial = 0.5
        physicalStates.add(0.5); // État physique initial = 0.5
    }

    /**
     * Enregistre les scores actuels de toutes les actions du chien.
     * 
     * @param dog le chien dont les scores sont enregistrés
     */
    public synchronized void registerActionScores(Dog dog) {
        for (String actionName : GameConfiguration.listNomActionChien) {
            Integer score = dog.getScore(GameConfiguration.listActionChien.get(actionName));
            if (score != null) {
                actionScores.get(actionName).add(score);
                System.out.println("Score enregistré pour " + actionName + ": " + score);
            } else {
                System.out.println("Score null pour " + actionName + ", ignoré.");
            }
        }
    }

    /**
     * Enregistre l'évolution de la confiance à chaque étape.
     * 
     * @param trust la valeur de confiance
     */
    public synchronized void registerTrustByStep(Double trust) {
        if (trust != null && trust >= 0 && trust <= 1) {
            trustLevels.add(trust);
            System.out.println("Confiance enregistrée: " + trust);
        } else {
            System.out.println("Confiance invalide: " + trust + ", ignorée.");
        }
    }

    /**
     * Enregistre l'évolution de l'état mental à chaque étape.
     * 
     * @param mentalState l'état mental
     */
    public synchronized void registerMentalStateByStep(Double mentalState) {
        if (mentalState != null && mentalState >= 0 && mentalState <= 1) {
            mentalStates.add(mentalState);
            System.out.println("État mental enregistré: " + mentalState);
        } else {
            System.out.println("État mental invalide: " + mentalState + ", ignoré.");
        }
    }

    /**
     * Enregistre l'évolution de l'état physique à chaque étape.
     * 
     * @param physicalState l'état physique
     */
    public synchronized void registerPhysicalStateByStep(Double physicalState) {
        if (physicalState != null && physicalState >= 0 && physicalState <= 1) {
            physicalStates.add(physicalState);
            System.out.println("État physique enregistré: " + physicalState);
        } else {
            System.out.println("État physique invalide: " + physicalState + ", ignoré.");
        }
    }

    /**
     * Génère la courbe d'évolution pour une action spécifique.
     * 
     * @param actionName le nom de l'action
     * @return la courbe
     */
    public JFreeChart getActionEvolutionChart(String actionName) {
        XYSeries series = new XYSeries(formaterNomAction(actionName));
        ArrayList<Integer> scores = actionScores.get(actionName);
        if (scores != null) {
            for (int index = 0; index < scores.size(); index++) {
                Integer score = scores.get(index);
                if (score != null) {
                    series.add(index, score);
                }
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Évolution de " + formaterNomAction(actionName),
            "Étape du jeu",
            "Score",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        chart.getXYPlot().getRenderer().setSeriesPaint(0, obtenirCouleurPourAction(actionName));
        return chart;
    }

    /**
     * Génère la courbe d'évolution de la confiance.
     * 
     * @return la courbe
     */
    public JFreeChart getTrustEvolutionChart() {
        XYSeries series = new XYSeries("Confiance");
        for (int index = 0; index < trustLevels.size(); index++) {
            Double trust = trustLevels.get(index);
            if (trust != null) {
                series.add(index, trust);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Évolution de la confiance",
            "Étape du jeu",
            "Niveau de confiance",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.BLUE);
        return chart;
    }

    /**
     * Génère la courbe d'évolution de l'état mental.
     * 
     * @return la courbe
     */
    public JFreeChart getMentalStateEvolutionChart() {
        XYSeries series = new XYSeries("État mental");
        for (int index = 0; index < mentalStates.size(); index++) {
            Double mentalState = mentalStates.get(index);
            if (mentalState != null) {
                series.add(index, mentalState);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Évolution de l'état mental",
            "Étape du jeu",
            "État mental",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.GREEN);
        return chart;
    }

    /**
     * Génère la courbe d'évolution de l'état physique.
     * 
     * @return la courbe
     */
    public JFreeChart getPhysicalStateEvolutionChart() {
        XYSeries series = new XYSeries("État physique");
        for (int index = 0; index < physicalStates.size(); index++) {
            Double physicalState = physicalStates.get(index);
            if (physicalState != null) {
                series.add(index, physicalState);
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Évolution de l'état physique",
            "Étape du jeu",
            "État physique",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.ORANGE);
        return chart;
    }

    private String formaterNomAction(String actionName) {
        switch (actionName) {
            case "dormirPanier": return "Dormir Panier";
            case "dormirNiche": return "Dormir Niche";
            case "dormirLit": return "Dormir Lit";
            case "dormirCanape": return "Dormir Canapé";
            case "mangerGamelle": return "Manger Gamelle";
            case "mangerGamelle2": return "Manger Gamelle Chat";
            case "monterTable": return "Monter Table";
            default: return actionName;
        }
    }

    private java.awt.Color obtenirCouleurPourAction(String actionName) {
        switch (actionName) {
            case "dormirPanier": return java.awt.Color.BLUE;
            case "dormirNiche": return java.awt.Color.GREEN;
            case "dormirLit": return java.awt.Color.RED;
            case "dormirCanape": return java.awt.Color.ORANGE;
            case "mangerGamelle": return java.awt.Color.MAGENTA;
            case "mangerGamelle2": return java.awt.Color.CYAN;
            case "monterTable": return java.awt.Color.PINK;
            default: return java.awt.Color.BLACK;
        }
    }
}