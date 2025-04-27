package engine;

public class StateAnimal {
    private double physicalState;
    private double mentalState;
    private static final long INACTIVITY_INTERVAL = 20000; // 20 secondes
    private long lastUpdate;

    public StateAnimal(double physical, double mental) {
        this.physicalState = Math.max(0.0, Math.min(1.0, physical));
        this.mentalState = Math.max(0.0, Math.min(1.0, mental));
        this.lastUpdate = System.currentTimeMillis();
    }

    public void setPhysicalState(double change) {
        this.physicalState = Math.max(0.0, Math.min(1.0, physicalState + change));
        lastUpdate = System.currentTimeMillis();
        System.out.println("État physique mis à jour: " + (int)(physicalState * 100) + "%");
    }

    public void setMentalState(double change) {
        this.mentalState = Math.max(0.0, Math.min(1.0, mentalState + change));
        lastUpdate = System.currentTimeMillis();
        System.out.println("État mental mis à jour: " + (int)(mentalState * 100) + "%");
    }

    public void degradationNaturelle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > INACTIVITY_INTERVAL) {
            physicalState = Math.max(0.0, physicalState - 0.01); // Dégradation de 1%
            mentalState = Math.max(0.0, mentalState - 0.01);     // Dégradation de 1%
            lastUpdate = currentTime;
            System.out.println("Dégradation naturelle - Physique: " + (int)(physicalState * 100) +
                               "%, Mental: " + (int)(mentalState * 100) + "%");
        }
    }

    public double getPhysicalState() {
        return physicalState;
    }

    public double getMentalState() {
        return mentalState;
    }
}
