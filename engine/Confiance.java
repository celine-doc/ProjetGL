package engine;

import java.util.ArrayList;
import java.util.List;

public class Confiance {
    private List<Double> historiqueConfiance;  // Liste pour stocker l'évolution de la confiance
    private double confianceActuelle;

    public Confiance() {
        this.historiqueConfiance = new ArrayList<>();
        this.confianceActuelle = 0.5;  // Confiance initiale à 50%
        this.historiqueConfiance.add(confianceActuelle);
    }

    // Met à jour la confiance et ajoute cette valeur à l'historique
    public void mettreAJourConfiance(double changement) {
        confianceActuelle = Math.max(0.0, Math.min(1.0, confianceActuelle + changement));  // Limite entre 0 et 1
        historiqueConfiance.add(confianceActuelle);
    }

    // Retourne l'historique des valeurs de confiance
    public List<Double> getHistoriqueConfiance() {
        return historiqueConfiance;
    }

    // Retourne la confiance actuelle
    public double getConfianceActuelle() {
        return confianceActuelle;
    }
}
