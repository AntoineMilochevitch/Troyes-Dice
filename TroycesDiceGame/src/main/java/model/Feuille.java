package main.java.model;

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

    public Feuille(){
        this.etudiant = new PanelEtudiant(this);
        this.administration = new PanelAdministration(this);
        this.enseignant = new PanelEnseignant(this);
    }

    public void addListener(FeuilleListener listener) {
        listeners.add(listener);
        System.out.println("Listener added");
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
    
    public void utiliserRessource(Couleur type, int cout, int ressourceDepense) {
        switch (type) {
            case JAUNE:
                if (etudiant.getRessource() >= cout) {
                    etudiant.setRessource(etudiant.getRessource() - cout);
                }
                break;
            case BLANC:
                if (enseignant.getRessource() >= 2) {
                    enseignant.setRessource(enseignant.getRessource() - 2);
                }
                break;
            case ROUGE:
                if (administration.getRessource() >= ressourceDepense) {
                    administration.setRessource(administration.getRessource() - ressourceDepense);
                }
                break;
            default:
                break;
        }
        notifyListeners();
    }

    public void detruireColonne(int col){
        
        etudiant.rendreInconstructible(col);
        administration.rendreInconstructible(col);
        enseignant.rendreInconstructible(col);
        notifyListeners();
    }

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

    public int calculerPoints() {
        int pointsEtudiant = etudiant.decomptePoints();
        int pointsAdministration = administration.decomptePoints();
        int pointsEnseignant = enseignant.decomptePoints();
        return pointsEtudiant + pointsAdministration + pointsEnseignant;
    }

    private void addNBPointEtudiant(int points) {
        this.nbPointEtudiant += points;
    }

    private void addNBPointAdministration(int points) {
        this.nbPointAdministration += points;
    }

    private void addNBPointEnseignant(int points) {
        this.nbPointEnseignant += points;
    }

    public void multiplierHandler(int valDe, int multiplicateur) {
        switch (valDe) {
            case 1:
                administration.appliquerMultiplicateur(1, multiplicateur);
                break;
            case 2:
                administration.appliquerMultiplicateur(2, multiplicateur);
                break;
            case 3:
                etudiant.appliquerMultiplicateur(1, multiplicateur);
                break;
            case 4:
                etudiant.appliquerMultiplicateur(2, multiplicateur);
                break;
            case 5: 
                enseignant.appliquerMultiplicateur(1, multiplicateur);
                break;
            case 6:
                enseignant.appliquerMultiplicateur(2, multiplicateur);
                break;
            default:
                break;
        }
    }

    public void addPoints(Couleur type, int points) {
        switch (type) {
            case JAUNE:
                addNBPointEtudiant(points);
                break;
            case BLANC:
                addNBPointEnseignant(points);
                break;
            case ROUGE:
                addNBPointAdministration(points);
                break;
            default:
                break;
        }
        notifyListeners();
    }

}