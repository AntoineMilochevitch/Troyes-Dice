package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    private Couleur couleur;
    private int ressource;
    private List<Batiment> batimentsPrestige;
    private List<Batiment> batimentsFonction;

    private int multiplicateur1;
    private int multiplicateur2;

    public Panel(){
        this.couleur = Couleur.VIDE;
        this.ressource = 0;
        this.batimentsPrestige = new ArrayList<>();
        this.batimentsFonction = new ArrayList<>();
        this.multiplicateur1 = 0;
        this.multiplicateur2 = 0;
    }

    public Panel(Couleur couleur, int ressource, List<Batiment> batimentsPrestige, List<Batiment> batimentsFonction, int multiplicateur1, int multiplicateur2) {
        this.couleur = couleur;
        this.ressource = ressource;
        this.batimentsPrestige = batimentsPrestige;
        this.batimentsFonction = batimentsFonction;
        this.multiplicateur1 = multiplicateur1;
        this.multiplicateur2 = multiplicateur2;
    }

    public int decomptePoints(){
        //get number of batiments prestige built
        int nbBP = 0;
        for (Batiment bat : batimentsPrestige){
            if (bat.getEtat() == Etat.CONSTRUIT){
                nbBP++;
            }
        }
        //get number of batiments fonction built
        int nbBF = 0;
        for (Batiment bat : batimentsFonction){
            if (bat.getEtat() == Etat.CONSTRUIT){
                nbBF++;
            }
        }
        int points = nbBF * multiplicateur1 + nbBP * multiplicateur2 + ressource;
        return points;
    }

    public void rendreInconstructible(int col){
        batimentsFonction.get(col).rendreInconstructible();
        batimentsPrestige.get(col).rendreInconstructible();
    }

    public void protegerColonne(int col){
        batimentsPrestige.get(col).proteger();
        batimentsFonction.get(col).proteger();
    }

    public void buildBF(int valDe){
        if (batimentsFonction.get(valDe).getEtat() != Etat.INCONSTRUCTIBLE){
            return;
        }
        BatimentFonction bat = new BatimentFonction(valDe, null);
        bat.onBuild();
        batimentsFonction.add(valDe, bat);
    }

    public void buildBP(int valDe) {
        if (batimentsPrestige.get(valDe).getEtat() != Etat.INCONSTRUCTIBLE){
            return;
        }
        Batiment bat = batimentsPrestige.get(valDe);
        bat.onBuild();
    }

    public int getRessource() {
        return ressource;
    }

    public Couleur getCouleur(){
        return couleur;
    }

    public void setRessource(int ressource) {
        this.ressource = ressource;
    }

    public void addRessource(int ressource){
        this.ressource += ressource;
    }

    public void afficherPanel(){
        System.out.println("Ressource : " + ressource);
        System.out.println("Batiments de prestige : ");
        for (Batiment bat : batimentsPrestige){
            System.out.println(bat.toString());
        }
        System.out.println("Batiments de fonction : ");
        for (Batiment bat : batimentsFonction){
            System.out.println(bat.toString());
        }
    }
    
}