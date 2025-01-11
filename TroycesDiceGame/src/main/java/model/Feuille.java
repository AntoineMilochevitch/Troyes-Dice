package main.java.model;

import main.java.exceptions.InvalidColorException;

import java.util.ArrayList;
import java.util.List;

public class Feuille {
    private Panel etudiant;
    private Panel administration;
    private Panel enseignant;
    private int nbPointEtudiant;
    private int nbPointAdministration;
    private int nbPointEnseignant;
    private List<FeuilleListener> listeners = new ArrayList<>();

    // Constructeur
    public Feuille(){
        this.etudiant = new PanelEtudiant(this);
        this.administration = new PanelAdministration(this);
        this.enseignant = new PanelEnseignant(this);
    }

    // Listeners
    public void addListener(FeuilleListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    private void notifyListeners() {
        System.out.println("SIZE listeners : " + listeners.size());
        try {
            for (FeuilleListener listener : listeners) {
                System.out.println("Feuille updated");
                listener.onFeuilleUpdated(this);
            }
        } catch (Exception e) {
            System.out.println("Error while notifying listeners");
        }
    }

    // Dépenser des ressources
    public void utiliserRessource(Couleur type, int cout, int ressourceDepense) {
        switch (type) {
            case JAUNE -> {
                if (etudiant.getRessource() >= cout) {
                    etudiant.setRessource(etudiant.getRessource() - cout);
                }
            }
            case BLANC -> {
                if (enseignant.getRessource() >= 2) {
                    enseignant.setRessource(enseignant.getRessource() - 2);
                }
            }
            case ROUGE -> {
                if (administration.getRessource() >= ressourceDepense) {
                    administration.setRessource(administration.getRessource() - ressourceDepense);
                }
            }
            default -> {
            }
        }
        notifyListeners();
    }

    // Détruire une colonne (dé noir)
    public void detruireColonne(int col){
        etudiant.rendreInconstructible(col);
        administration.rendreInconstructible(col);
        enseignant.rendreInconstructible(col);
        notifyListeners();
    }

    // Getteurs
    public int getNbPointEtudiant() {
        return nbPointEtudiant;
    }
    public int getNbPointAdministration() {
        return nbPointAdministration;
    }
    public int getNbPointEnseignant() {
        return nbPointEnseignant;
    }
    public Panel getEtudiant() {
        return etudiant;
    }
    public Panel getAdministration() {
        return administration;
    }
    public Panel getEnseignant() {
        return enseignant;
    }
    public int getMultiplier(Couleur couleur, int index){
        try {
            return switch (couleur) {
                case ROUGE -> administration.getMultiplier(index);
                case JAUNE -> etudiant.getMultiplier(index);
                case BLANC -> enseignant.getMultiplier(index);
                default -> throw new InvalidColorException();
            };
        } catch (InvalidColorException e){
            System.out.println("Feuille : getMultiplier");
            return 0;
        }
    }

    // Calculs des Points
    public int calculerSousTotPoints() {
        return etudiant.decomptePoints() + administration.decomptePoints() + enseignant.decomptePoints();
    }
    public int calculerHabitantsPoints() {
        return nbPointEnseignant + nbPointEtudiant + nbPointAdministration;
    }
    public int calculerPoints(){
        return calculerHabitantsPoints() + calculerSousTotPoints();
    }

    // Attribue les multiplieurs
    public void multiplierHandler(int valDe, int multiplicateur) {
        try {
            switch (valDe) {
                case 1 -> administration.appliquerMultiplicateur(1, multiplicateur);
                case 2 -> administration.appliquerMultiplicateur(2, multiplicateur);
                case 3 -> etudiant.appliquerMultiplicateur(1, multiplicateur);
                case 4 -> etudiant.appliquerMultiplicateur(2, multiplicateur);
                case 5 -> enseignant.appliquerMultiplicateur(1, multiplicateur);
                case 6 -> enseignant.appliquerMultiplicateur(2, multiplicateur);
                default -> throw new IllegalArgumentException("Unnexpected valDe value.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // Ajoute des habitants a X type.
    public void addPoints(Couleur type, int points) {
        try {
            if (points < 0) {
                throw new IllegalArgumentException("Points cannot be negative");
            }
            switch (type) {
                case JAUNE -> nbPointEtudiant += points;
                case BLANC -> nbPointEnseignant += points;
                case ROUGE -> nbPointAdministration += points;
                default -> throw new InvalidColorException();
            }
            notifyListeners();
        } catch (InvalidColorException e){
            System.out.println("Feuille : addPoints");
        }
    }

    // Debug
    public final void afficherFeuille(){
        System.out.println("Feuille de l'étudiant : ");
        etudiant.afficherPanel();
        System.out.println("Nb point etudiant : " + nbPointEtudiant);
        System.out.println("Feuille de l'administration : ");
        administration.afficherPanel();
        System.out.println("Nb point administration : " + nbPointAdministration);
        System.out.println("Feuille de l'enseignant : ");
        enseignant.afficherPanel();
        System.out.println("Nb point enseignant : " + nbPointEnseignant);
    }
}

