package main.java.model;

import java.util.List;

public class Plateau {
    private List<Case> roue;
    private int compteurDemiJournee;

    public Plateau(List<Case> roue) {
        this.roue = roue;
        this.compteurDemiJournee = 0;
    }

    public void tournerRoue() {
        if (compteurDemiJournee % 2 == 0) {
            int jour = compteurDemiJournee / 2;
            // Calcule l'index de départ en fonction du jour actuel
            int indexDepart = jour % roue.size();
            Case temp = roue.get((indexDepart + roue.size() - 1) % roue.size()).clone();
            Case premiereApresMidi = roue.get((indexDepart + 4) % roue.size()).clone();
            Case caseNeutrePrecedente = roue.get(indexDepart).clone();
    
            for (int i = 0; i < roue.size(); i++) {
                // Calcule l'index actuel et l'index suivant de manière circulaire
                int currentIndex = (indexDepart + i) % roue.size();
                int nextIndex = (indexDepart + i + 1) % roue.size();
                // Met à jour le coût de la case actuelle avec le coût de la case suivante
                roue.get(currentIndex).setCout(roue.get(nextIndex).getCout());
            }
    
            // Met à jour la première case de la matinée avec la première case de l'après-midi de la journée précédente
            roue.get((indexDepart + 3) % roue.size()).setCout(premiereApresMidi.getCout());
            roue.get((indexDepart + 3) % roue.size()).setDemijournee(DemiJournee.MATIN);
    
            // Met à jour la dernière case de l'après-midi avec la case neutre précédente
            roue.get((indexDepart + 7) % roue.size()).setCout(caseNeutrePrecedente.getCout());
            roue.get((indexDepart + 7) % roue.size()).setDemijournee(DemiJournee.APRES_MIDI);
    
            // Met à jour le coût de la première case avec le coût sauvegardé
            roue.get(indexDepart).setCout(temp.getCout());
            roue.get(indexDepart).setDemijournee(DemiJournee.NEUTRE);
        }
        compteurDemiJournee++;
    }

    public void retournerCase(int index) {
        if (roue.get(index).getSenseCase() == 1) {
            roue.get(index).setSenseCase(-1);
        } else {
            roue.get(index).setSenseCase(1);
        }
    }

    public void incrementerCompteurDemiJournee() {
        compteurDemiJournee++;
    }

    public int getCompteurDemiJournee() {
        return compteurDemiJournee;
    }
}
