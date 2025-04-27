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
import mobile.Cat;

public class ChartManager {
    private HashMap<String, ArrayList<Integer>> actionProbasDog = new HashMap<>();
    private HashMap<String, ArrayList<Integer>> actionProbasCat = new HashMap<>();
    private ArrayList<Double> trustLevelsDog = new ArrayList<>();
    private ArrayList<Double> mentalStatesDog = new ArrayList<>();
    private ArrayList<Double> physicalStatesDog = new ArrayList<>();
    private ArrayList<Double> trustLevelsCat = new ArrayList<>();
    private ArrayList<Double> mentalStatesCat = new ArrayList<>();
    private ArrayList<Double> physicalStatesCat = new ArrayList<>();
    
    private TimeSeries trustSeriesDog = new TimeSeries("Confiance Chien");
    private TimeSeries mentalStateSeriesDog = new TimeSeries("État mental Chien");
    private TimeSeries physicalStateSeriesDog = new TimeSeries("État physique Chien");
    private TimeSeries trustSeriesCat = new TimeSeries("Confiance Chat");
    private TimeSeries mentalStateSeriesCat = new TimeSeries("État mental Chat");
    private TimeSeries physicalStateSeriesCat = new TimeSeries("État physique Chat");
    private HashMap<String, TimeSeries> actionSeriesDog = new HashMap<>();
    private HashMap<String, TimeSeries> actionSeriesCat = new HashMap<>();
    private int stepCounter = 0;

    public ChartManager() {
        // Initialize for Dog
        for (String actionName : GameConfiguration.listNomActionChien) {
            actionProbasDog.put(actionName, new ArrayList<>());
            int initialProba = GameConfiguration.listActionChien.get(actionName).getProba();
            actionProbasDog.get(actionName).add(initialProba);
            actionSeriesDog.put(actionName, new TimeSeries(formaterNomAction(actionName) + " (Chien)"));
            actionSeriesDog.get(actionName).add(new Second(), initialProba);
        }
        trustLevelsDog.add(0.5);
        mentalStatesDog.add(0.5);
        physicalStatesDog.add(0.5);
        trustSeriesDog.add(new Second(), 0.5);
        mentalStateSeriesDog.add(new Second(), 0.5);
        physicalStateSeriesDog.add(new Second(), 0.5);

        // Initialize for Cat
        for (String actionName : GameConfiguration.listNomActionChat) {
            actionProbasCat.put(actionName, new ArrayList<>());
            int initialProba = GameConfiguration.listActionChat.get(actionName).getProba();
            actionProbasCat.get(actionName).add(initialProba);
            actionSeriesCat.put(actionName, new TimeSeries(formaterNomAction(actionName) + " (Chat)"));
            actionSeriesCat.get(actionName).add(new Second(), initialProba);
        }
        trustLevelsCat.add(0.5);
        mentalStatesCat.add(0.5);
        physicalStatesCat.add(0.5);
        trustSeriesCat.add(new Second(), 0.5);
        mentalStateSeriesCat.add(new Second(), 0.5);
        physicalStateSeriesCat.add(new Second(), 0.5);
    }

    public synchronized void registerActionScores(Dog dog, Cat cat) {
        int currentStep = stepCounter++;
        // Dog actions
        for (String actionName : GameConfiguration.listNomActionChien) {
            Integer proba = dog.getProba(GameConfiguration.listActionChien.get(actionName));
            if (proba != null && proba >= 0 && proba <= 100) {
                actionProbasDog.get(actionName).add(proba);
                actionSeriesDog.get(actionName).addOrUpdate(new Second(), proba);
                System.out.println("Probabilité enregistrée pour Chien " + actionName + ": " + proba + "%");
            } else {
                System.out.println("Probabilité invalide pour Chien " + actionName + ": " + proba + ", ignorée.");
            }
        }
        //  Cat actions
        for (String actionName : GameConfiguration.listNomActionChat) {
            Integer proba = cat.getProba(GameConfiguration.listActionChat.get(actionName));
            if (proba != null && proba >= 0 && proba <= 100) {
                actionProbasCat.get(actionName).add(proba);
                actionSeriesCat.get(actionName).addOrUpdate(new Second(), proba);
                System.out.println("Probabilité enregistrée pour Chat " + actionName + ": " + proba + "%");
            } else {
                System.out.println("Probabilité invalide pour Chat " + actionName + ": " + proba + ", ignorée.");
            }
        }
    }

    public synchronized void registerTrustByStep(Dog dog, Cat cat) {
        Double trustDog = dog.getConfiance();
        Double trustCat = cat.getConfiance();
        if (trustDog != null && trustDog >= 0 && trustDog <= 1) {
            trustLevelsDog.add(trustDog);
            trustSeriesDog.addOrUpdate(new Second(), trustDog);
            System.out.println("Confiance enregistrée pour Chien: " + trustDog);
        } else {
            System.out.println("Confiance invalide pour Chien: " + trustDog + ", ignorée.");
        }
        if (trustCat != null && trustCat >= 0 && trustCat <= 1) {
            trustLevelsCat.add(trustCat);
            trustSeriesCat.addOrUpdate(new Second(), trustCat);
            System.out.println("Confiance enregistrée pour Chat: " + trustCat);
        } else {
            System.out.println("Confiance invalide pour Chat: " + trustCat + ", ignorée.");
        }
    }

    public synchronized void registerMentalStateByStep(Dog dog, Cat cat) {
        Double mentalStateDog = dog.getMentalState();
        Double mentalStateCat = cat.getMentalState();
        if (mentalStateDog != null && mentalStateDog >= 0 && mentalStateDog <= 1) {
            mentalStatesDog.add(mentalStateDog);
            mentalStateSeriesDog.addOrUpdate(new Second(), mentalStateDog);
            System.out.println("État mental enregistré pour Chien: " + mentalStateDog);
        } else {
            System.out.println("État mental invalide pour Chien: " + mentalStateDog + ", ignorée.");
        }
        if (mentalStateCat != null && mentalStateCat >= 0 && mentalStateCat <= 1) {
            mentalStatesCat.add(mentalStateCat);
            mentalStateSeriesCat.addOrUpdate(new Second(), mentalStateCat);
            System.out.println("État mental enregistré pour Chat: " + mentalStateCat);
        } else {
            System.out.println("État mental invalide pour Chat: " + mentalStateCat + ", ignorée.");
        }
    }

    public synchronized void registerPhysicalStateByStep(Dog dog, Cat cat) {
        Double physicalStateDog = dog.getPhysicalState();
        Double physicalStateCat = cat.getPhysicalState();
        if (physicalStateDog != null && physicalStateDog >= 0 && physicalStateDog <= 1) {
            physicalStatesDog.add(physicalStateDog);
            physicalStateSeriesDog.addOrUpdate(new Second(), physicalStateDog);
            System.out.println("État physique enregistré pour Chien: " + physicalStateDog);
        } else {
            System.out.println("État physique invalide pour Chien: " + physicalStateDog + ", ignorée.");
        }
        if (physicalStateCat != null && physicalStateCat >= 0 && physicalStateCat <= 1) {
            physicalStatesCat.add(physicalStateCat);
            physicalStateSeriesCat.addOrUpdate(new Second(), physicalStateCat);
            System.out.println("État physique enregistré pour Chat: " + physicalStateCat);
        } else {
            System.out.println("État physique invalide pour Chat: " + physicalStateCat + ", ignorée.");
        }
    }

    public JFreeChart getActionEvolutionChart(String actionName) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        boolean isDogAction = actionSeriesDog.containsKey(actionName);
        boolean isCatAction = actionSeriesCat.containsKey(actionName);

        if (actionName.equals("dormirNiche")) {
            dataset.addSeries(actionSeriesDog.get(actionName));
        } else if (actionName.equals("jouerArbreAChat")) {
            dataset.addSeries(actionSeriesCat.get(actionName));
        } else {
            if (isDogAction) {
                dataset.addSeries(actionSeriesDog.get(actionName));
            }
            if (isCatAction) {
                dataset.addSeries(actionSeriesCat.get(actionName));
            }
        }

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

        // Set colors: Dog (Red), Cat (Blue)
        int seriesIndex = 0;
        if (actionName.equals("dormirNiche")) {
            plot.getRenderer().setSeriesPaint(seriesIndex, java.awt.Color.RED);
        } else if (actionName.equals("jouerArbreAChat")) {
            plot.getRenderer().setSeriesPaint(seriesIndex, java.awt.Color.BLUE);
        } else {
            if (isDogAction) {
                plot.getRenderer().setSeriesPaint(seriesIndex++, java.awt.Color.RED);
            }
            if (isCatAction) {
                plot.getRenderer().setSeriesPaint(seriesIndex, java.awt.Color.BLUE);
            }
        }

        return chart;
    }

    public JFreeChart getTrustEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(trustSeriesDog);
        dataset.addSeries(trustSeriesCat);
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
        plot.getRenderer().setSeriesPaint(0, java.awt.Color.RED); // Dog
        plot.getRenderer().setSeriesPaint(1, java.awt.Color.BLUE); // Cat
        return chart;
    }

    public JFreeChart getMentalStateEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(mentalStateSeriesDog);
        dataset.addSeries(mentalStateSeriesCat);
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
        plot.getRenderer().setSeriesPaint(0, java.awt.Color.RED); // Dog
        plot.getRenderer().setSeriesPaint(1, java.awt.Color.BLUE); // Cat
        return chart;
    }

    public JFreeChart getPhysicalStateEvolutionChart() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(physicalStateSeriesDog);
        dataset.addSeries(physicalStateSeriesCat);
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
        plot.getRenderer().setSeriesPaint(0, java.awt.Color.RED); // Dog
        plot.getRenderer().setSeriesPaint(1, java.awt.Color.BLUE); // Cat
        return chart;
    }

    public TimeSeries getTrustSeriesDog() {
        return trustSeriesDog;
    }

    public TimeSeries getMentalStateSeriesDog() {
        return mentalStateSeriesDog;
    }

    public TimeSeries getPhysicalStateSeriesDog() {
        return physicalStateSeriesDog;
    }

    public TimeSeries getTrustSeriesCat() {
        return trustSeriesCat;
    }

    public TimeSeries getMentalStateSeriesCat() {
        return mentalStateSeriesCat;
    }

    public TimeSeries getPhysicalStateSeriesCat() {
        return physicalStateSeriesCat;
    }

    public TimeSeries getActionSeries(String actionName) {
        return actionSeriesDog.getOrDefault(actionName, actionSeriesCat.get(actionName));
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
            case "jouerArbreAChat": return "Jouer Arbre à Chat";
            default: return actionName;
        }
    }
}
