package guiT;

import java.util.ArrayList;
import java.util.HashMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import config.GameConfiguration;
import mobile.Dog;

public class ChartManager {
    private HashMap<String, ArrayList<Integer>> actionProbas = new HashMap<>();
    private ArrayList<Double> trustLevels = new ArrayList<>();
    private ArrayList<Double> mentalStates = new ArrayList<>();
    private ArrayList<Double> physicalStates = new ArrayList<>();
    
    private TimeSeries trustSeries = new TimeSeries("Confiance");
    private TimeSeries mentalStateSeries = new TimeSeries("État mental");
    private TimeSeries physicalStateSeries = new TimeSeries("État physique");
    private HashMap<String, TimeSeries> actionSeries = new HashMap<>();
    private int stepCounter = 0; // Compteur d'étapes pour les TimeSeries

    public ChartManager() {
        for (String actionName : GameConfiguration.listNomActionChien) {
            actionProbas.put(actionName, new ArrayList<>());
            int initialProba = GameConfiguration.listActionChien.get(actionName).getProba();
            actionProbas.get(actionName).add(initialProba);
            actionSeries.put(actionName, new TimeSeries(formaterNomAction(actionName)));
            actionSeries.get(actionName).add(new Second(), initialProba);
        }
        trustLevels.add(0.5);
        mentalStates.add(0.5);
        physicalStates.add(0.5);
        trustSeries.add(new Second(), 0.5);
        mentalStateSeries.add(new Second(), 0.5);
        physicalStateSeries.add(new Second(), 0.5);
    }

    public synchronized void registerActionScores(Dog dog) {
        int currentStep = stepCounter++;
        for (String actionName : GameConfiguration.listNomActionChien) {
            Integer proba = dog.getProba(GameConfiguration.listActionChien.get(actionName));
            if (proba != null && proba >= 0 && proba <= 100) {
                actionProbas.get(actionName).add(proba);
                actionSeries.get(actionName).addOrUpdate(new Second(), proba);
                System.out.println("Probabilité enregistrée pour " + actionName + ": " + proba + "%");
            } else {
                System.out.println("Probabilité invalide pour " + actionName + ": " + proba + ", ignorée.");
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
            "Probabilité (%)",
            dataset,
            true,
            true,
            false
        );
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 100);
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setLowerBound(0);
        plot.getRenderer().setSeriesPaint(0, obtenirCouleurPourAction(actionName));
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
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 1);
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setLowerBound(0);
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
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 1);
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setLowerBound(0);
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
        XYPlot plot = chart.getXYPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(0, 1);
        rangeAxis.setAutoRangeIncludesZero(true);
        rangeAxis.setLowerBound(0);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, java.awt.Color.ORANGE);
        return chart;
    }

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
