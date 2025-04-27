package engine;

import java.util.ArrayList;
import java.util.List;

public class Confiance {
    private List<Double> historiqueConfiance;
    private double confianceActuelle;
    private static final long INACTIVITY_INTERVAL = 20000; // 20 secondes
    private long lastUpdate;

    public Confiance() {
        this.historiqueConfiance = new ArrayList<>();
        this.confianceActuelle = 0.5;  // Confiance initiale à 50%
        this.historiqueConfiance.add(confianceActuelle);
        this.lastUpdate = System.currentTimeMillis();
    }

    public void mettreAJourConfiance(double changement) {
        confianceActuelle = Math.max(0.0, Math.min(1.0, confianceActuelle + changement));
        historiqueConfiance.add(confianceActuelle);
        lastUpdate = System.currentTimeMillis();
        System.out.println("Confiance mise à jour: " + (int)(confianceActuelle * 100) + "%");
    }

    public void degradationNaturelle() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate > INACTIVITY_INTERVAL) {
            confianceActuelle = Math.max(0.0, confianceActuelle - 0.01); // Dégradation de 1%
            historiqueConfiance.add(confianceActuelle);
            lastUpdate = currentTime;
            System.out.println("Dégradation naturelle de la confiance: " + (int)(confianceActuelle * 100) + "%");
        }
    }

    public List<Double> getHistoriqueConfiance() {
        return historiqueConfiance;
    }

    public double getConfianceActuelle() {
        return confianceActuelle;
    }
}
