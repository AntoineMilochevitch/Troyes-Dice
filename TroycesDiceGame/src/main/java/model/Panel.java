package main.java.model;

public class Panel {
    private Couleur couleur;
    private int ressource;
    private BatimentPrestige[] batimentsPrestige;
    private Boolean[] batimentsFonction;
    private Boolean[] batimentsInconstructibles;

    private int multiplicateur1;
    private int multiplicateur2;

    public Panel(){
        this.couleur = Couleur.VIDE;
        this.ressource = 0;
        this.batimentsPrestige = null;
        this.batimentsFonction = null;
        this.batimentsInconstructibles = null;
        this.multiplicateur1 = 0;
        this.multiplicateur2 = 0;
    }

    public Panel(Couleur couleur, int ressource, BatimentPrestige[] batimentsPrestige, Boolean[] batimentsFonction, Boolean[] batimentsInconstructibles, int multiplicateur1, int multiplicateur2) {
        this.couleur = couleur;
        this.ressource = ressource;
        this.batimentsPrestige = batimentsPrestige;
        this.batimentsFonction = batimentsFonction;
        this.batimentsInconstructibles = batimentsInconstructibles;
        this.multiplicateur1 = multiplicateur1;
        this.multiplicateur2 = multiplicateur2;
    }

    public int decomptePoints(){
        //TODO
        return 0;
    }

    public void detruireColonne(){
        //TODO
    }
}
