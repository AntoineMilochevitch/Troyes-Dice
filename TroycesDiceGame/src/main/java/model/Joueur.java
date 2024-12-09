package main.java.model;

public class Joueur {
    private String nom;
    private int id;
    private Feuille feuille;
    private Case caseChoisie;

    public Joueur(String nom, int id) {
        this.nom = nom;
        this.id = id;
        this.feuille = new Feuille();
    }

    public Case choisirCase() {
        return caseChoisie;
    }

    
}
