package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
    private List<Case> roue;
    private int compteurDemiJournee;

    public Plateau(List<Case> roue) {
        this.roue = roue;
        this.compteurDemiJournee = 0;
        initialiserCases(9);
        afficherRoue();
    }

    public final void initialiserCases(int nombreDeCases) {
        Random random = new Random();
        roue = new ArrayList<>();
        Couleur[] couleurs = Couleur.values();

        for (int i = 0; i < nombreDeCases; i++) {
            Couleur couleurRecto = couleurs[random.nextInt(couleurs.length-1)]; // N'inclut pas VIDE dans la couleur donc -1
            Couleur couleurVerso = couleurs[random.nextInt(couleurs.length-1)];
            int cout = random.nextInt(3) + 1; // Coût compris entre 1 et 3
            Case nouvelleCase;
            De de = new De(0, Couleur.VIDE);
            if(i < 4){
                nouvelleCase = new Case(couleurRecto, couleurVerso, 1, de , cout, DemiJournee.MATIN);
            } else if(i < 8){
                nouvelleCase = new Case(couleurRecto, couleurVerso, 1, de, cout, DemiJournee.APRES_MIDI);
            } else {
                nouvelleCase = new Case(couleurRecto, couleurVerso, 1, de, cout, DemiJournee.NEUTRE);
            }
            roue.add(nouvelleCase);
        }
    }

    public void tournerRoue() {
        if (compteurDemiJournee % 2 == 0) {
            int jour = compteurDemiJournee / 2;
            // Calcule l'index de départ en fonction du jour actuel
            int indexDepart = jour % roue.size();
            System.out.println("Index de départ : " + indexDepart);
            Case temp = roue.get((indexDepart + roue.size() - 1) % roue.size()).clone();
            Case premiereApresMidi = roue.get((indexDepart + 4) % roue.size()).clone();
            Case caseNeutrePrecedente = roue.get(indexDepart).clone();

            for (int i = roue.size() - 1; i > 0; i--) {
                // Calcule l'index actuel et l'index suivant de manière circulaire
                int currentIndex = (indexDepart + i) % roue.size();
                System.out.println("Index actuel : " + currentIndex);
                int previousIndex = (indexDepart + i - 1) % roue.size();
                // Met à jour le coût de la case actuelle avec le coût de la case suivante
                roue.get(currentIndex).setCout(roue.get(previousIndex).getCout());
                System.out.println("Cout de la case " + currentIndex + " : " + roue.get(currentIndex).getCout());
            }

            roue.get((indexDepart + 4) % roue.size()).setCout(premiereApresMidi.getCout());
            roue.get((indexDepart + 4) % roue.size()).setDemijournee(DemiJournee.MATIN);

            roue.get((indexDepart + 8) % roue.size()).setCout(caseNeutrePrecedente.getCout());
            roue.get((indexDepart + 8) % roue.size()).setDemijournee(DemiJournee.APRES_MIDI);

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

    public Case getCase(int index) {
        return roue.get(index);
    }

    public List<Case> getRoue() {
        return roue;
    }

    public final void afficherRoue() {
        for (int i = 0; i < roue.size(); i++) {
            System.out.println(i + " : " + roue.get(i).getCouleurRecto() + " " + roue.get(i).getCouleurVerso() + " " + roue.get(i).getCout() + " " + roue.get(i).getDemiJournee());
        }
    }
}