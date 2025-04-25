package guiT;

import java.util.ArrayList;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import config.GameConfiguration;
import mobile.Dog;

public class ChartManager {
    private HashMap<String, ArrayList<Integer>> actionScores = new HashMap<>();
    private ArrayList<Double> trustLevels = new ArrayList<>();
    private ArrayList<Double> mentalStates = new ArrayList<>();
    private ArrayList<Double> physicalStates = new ArrayList<>();
    
    // Utiliser TimeSeries au lieu de XYSeries
    private TimeSeries trustSeries = new TimeSeries("Confiance");
    private TimeSeries mentalStateSeries = new TimeSeries("État mental");
    private TimeSeries physicalStateSeries = new TimeSeries("État physique");
    private HashMap<String, TimeSeries> actionSeries = new HashMap<>();

    public ChartManager() {
        for (String actionName : GameConfiguration.listNomActionChien) {
            actionScores.put(actionName, new ArrayList<>());
            actionScores.get(actionName).add(0);
            actionSeries.put(actionName, new TimeSeries(formaterNomAction(actionName)));
            actionSeries.get(actionName).add(new Second(), 0); // Score initial
        }
        trustLevels.add(0.5);
        mentalStates.add(0.5);
        physicalStates.add(0.5);
        trustSeries.add(new Second(), 0.5); // Valeur initiale
        mentalStateSeries.add(new Second(), 0.5);
        physicalStateSeries.add(new Second(), 0.5);
    }

    public synchronized void registerActionScores(Dog dog) {
        Second currentTime = new Second();
        for (String actionName : GameConfiguration.listNomActionChien) {
            Integer score = dog.getScore(GameConfiguration.listActionChien.get(actionName));
            if (score != null) {
                actionScores.get(actionName).add(score);
                actionSeries.get(actionName).addOrUpdate(currentTime, score);
                System.out.println("Score enregistré pour " + actionName + ": " + score);
            } else {
                System.out.println("Score null pour " + actionName + ", ignoré.");
            }
        }
    }

    public synchronized void registerTrustByStep(Double trust) {
        if (trust != null && trust >= 0 && trust <= 1) {
            trustLevels.add(trust);
            trustSeries.addOrUpdate(new Second(), trust);
            System.out.println("Confiance enregistrée: " + trust);
        } else {
            System.out.println("Confiance invalide: " + trust + ", ignorée.");
        }
    }

    public synchronized void registerMentalStateByStep(Double mentalState) {
        if (mentalState != null && mentalState >= 0 && mentalState <= 1) {
            mentalStates.add(mentalState);
            mentalStateSeries.addOrUpdate(new Second(), mentalState);
            System.out.println("État mental enregistré: " + mentalState);
        } else {
            System.out.println("État mental invalide: " + mentalState + ", ignorée.");
        }
    }

    public synchronized void registerPhysicalStateByStep(Double physicalState) {
        if (physicalState != null && physicalState >= 0 && physicalState <= 1) {
            physicalStates.add(physicalState);
            physicalStateSeries.addOrUpdate(new Second(), physicalState);
            System.out.println("État physique enregistré: " + physicalState);
        } else {
            System.out.println("État physique invalide: " + physicalState + ", ignorée.");
        }
    }

    public JFreeChart getActionEvolutionChart(String actionName) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(actionSeries.get(actionName));
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Évolution de " + formaterNomAction(actionName),
            "Temps",
            "Score",
            dataset,
            true,
            true,
            false
        );
        chart.getXYPlot().getRenderer().setSeriesPaint(0, obtenirCouleurPourAction(actionName));
        return chart;
    }

    public JFreeChart getTrustEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(trustSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Évolution de la confiance",
            "Temps",
            "Niveau de confiance",
            dataset,
            true,
            true,
            false
        );
        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.BLUE);
        return chart;
    }

    public JFreeChart getMentalStateEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(mentalStateSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Évolution de l'état mental",
            "Temps",
            "État mental",
            dataset,
            true,
            true,
            false
        );
        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.GREEN);
        return chart;
    }

    public JFreeChart getPhysicalStateEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(physicalStateSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Évolution de l'état physique",
            "Temps",
            "État physique",
            dataset,
            true,
            true,
            false
        );
        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.ORANGE);
        return chart;
    }

    // Exposer les séries pour accès direct si nécessaire
    public TimeSeries getTrustSeries() {
        return trustSeries;
    }

    public TimeSeries getMentalStateSeries() {
        return mentalStateSeries;
    }

    public TimeSeries getPhysicalStateSeries() {
        return physicalStateSeries;
    }

    public TimeSeries getActionSeries(String actionName) {
        return actionSeries.get(actionName);
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
