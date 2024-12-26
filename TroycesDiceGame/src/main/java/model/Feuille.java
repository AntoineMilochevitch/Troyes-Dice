package main.java.model;

public class Feuille {
    private Panel etudiant;
    private Panel administration;
    private Panel enseignant;
    private int nbPointEtudiant;
    private int nbPointAdministration;
    private int nbPointEnseignant;  

    public Feuille(){
        this.etudiant = new Panel(this, Couleur.JAUNE);
        this.administration = new Panel(this, Couleur.ROUGE);
        this.enseignant = new Panel(this, Couleur.BLANC);
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
    }

    public void detruireColonne(int col){
        
        etudiant.rendreInconstructible(col);
        administration.rendreInconstructible(col);
        enseignant.rendreInconstructible(col);
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

    public void afficherFeuille(){
        System.out.println("Feuille de l'étudiant : ");
        etudiant.afficherPanel();
        System.out.println("Feuille de l'administration : ");
        administration.afficherPanel();
        System.out.println("Feuille de l'enseignant : ");
        enseignant.afficherPanel();
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
    }
}