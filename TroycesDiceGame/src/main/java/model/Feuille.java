package main.java.model;

public class Feuille {
    private Panel etudiant;
    private Panel administration;
    private Panel enseignant;
    private int nbPointEtudiant;
    private int nbPointAdministration;
    private int nbPointEnseignant;

    public Feuille(){
        this.etudiant = new Panel();
        this.administration = new Panel();
        this.enseignant = new Panel();
    }
    
    public void utiliserRessource(Couleur type){
        //TODO
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
}
