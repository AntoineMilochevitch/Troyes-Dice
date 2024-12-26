package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    private Couleur couleur;
    private int ressource;
    private List<BatimentPrestige> batimentsPrestige;
    private List<BatimentFonction> batimentsFonction;

    private Feuille feuille;

    private int multiplicateur1;
    private int multiplicateur2;

    public Panel(Feuille feuille, Couleur couleur) {
        this.couleur = Couleur.VIDE;
        this.ressource = 0;
        this.feuille = feuille;
        this.batimentsPrestige = new ArrayList<>();
        this.batimentsFonction = new ArrayList<>();
        this.multiplicateur1 = 0;
        this.multiplicateur2 = 0;
        initLists();
    }

    private void initLists(){
        switch (this.couleur){
            case ROUGE:
                initRouge();
                break;
            case JAUNE:
                initJaune();
                break;
            case BLANC:
                initBlanc();
                break;
        }
    }

    private void initRouge(){

        for (int i = 1; i < 7; i++) {
            batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.ROUGE, 2));
        }

        batimentsPrestige.add(new BatimentPrestige(1, Effet.PROTEGER, Recompense.HABITANT, Couleur.ROUGE, 1));
        batimentsPrestige.add(new BatimentPrestige(2, Effet.PROTEGER, Recompense.HABITANT, Couleur.ROUGE, 1));
        batimentsPrestige.add(new BatimentPrestige(3, Effet.PROTEGER, Recompense.HABITANT, Couleur.JAUNE, 1));
        batimentsPrestige.add(new BatimentPrestige(4, Effet.PROTEGER, Recompense.HABITANT, Couleur.JAUNE, 1));
        batimentsPrestige.add(new BatimentPrestige(5, Effet.PROTEGER, Recompense.HABITANT, Couleur.BLANC, 1));
        batimentsPrestige.add(new BatimentPrestige(6, Effet.PROTEGER, Recompense.HABITANT, Couleur.BLANC, 1));
    }

    private void initJaune(){

        for (int i = 1; i < 7; i++) {
            batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.JAUNE, 2));
        }

        batimentsPrestige.add(new BatimentPrestige(1, Effet.RIEN, Recompense.RESSOURCE, Couleur.ROUGE, 3));
        batimentsPrestige.add(new BatimentPrestige(2, Effet.RIEN, Recompense.HABITANT, Couleur.ROUGE, 2));
        batimentsPrestige.add(new BatimentPrestige(3, Effet.RIEN, Recompense.RESSOURCE, Couleur.JAUNE, 3));
        batimentsPrestige.add(new BatimentPrestige(4, Effet.RIEN, Recompense.HABITANT, Couleur.JAUNE, 2));
        batimentsPrestige.add(new BatimentPrestige(5, Effet.RIEN, Recompense.RESSOURCE, Couleur.BLANC, 3));
        batimentsPrestige.add(new BatimentPrestige(6, Effet.RIEN, Recompense.HABITANT, Couleur.BLANC, 2));
    }

    private void initBlanc(){

        for (int i = 1; i < 7; i++) {
            batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.BLANC, 2));
        }

        // Si nombre = 1, prend effet sur le multiplicateur 1, si nombre = 2 prend effet sur le multiplicateur 2
        batimentsPrestige.add(new BatimentPrestige(1, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.ROUGE, 1));
        batimentsPrestige.add(new BatimentPrestige(2, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.ROUGE, 2));
        batimentsPrestige.add(new BatimentPrestige(3, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.JAUNE, 1));
        batimentsPrestige.add(new BatimentPrestige(4, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.JAUNE, 2));
        batimentsPrestige.add(new BatimentPrestige(5, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.BLANC, 1));
        batimentsPrestige.add(new BatimentPrestige(6, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.BLANC, 2));
    }

    public Panel(Couleur couleur, int ressource, Feuille feuille, List<BatimentPrestige> batimentsPrestige, List<BatimentFonction> batimentsFonction, int multiplicateur1, int multiplicateur2) {
        this.couleur = couleur;
        this.ressource = ressource;
        this.feuille = feuille;
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
        BatimentFonction bat = batimentsFonction.get(valDe);
        // Si le batiment est vide ou la parcelle est protégée (donc implicitement non construite)
        if (bat.getEtat() == Etat.VIDE || bat.getEtat() == Etat.PROTEGE){ 
            bat.onBuild();
            feuille.addPoints(this.couleur, bat.getNombre());
        }
    }

    public void buildBP(int valDe, Panel panel, Feuille feuille) {
        BatimentPrestige bat = batimentsPrestige.get(valDe);
        if (bat.getEtat() == Etat.VIDE || bat.getEtat() == Etat.PROTEGE){ 
            bat.onBuild();
            feuille.addPoints(bat.getCouleur(), bat.getNombre());
        }
        bat.appliquerEffet(valDe, 0, panel, feuille);
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

    public void ajouterMultiplicateur(int valDe, int multiplicateur){
        if (valDe == 1 || valDe == 3 || valDe == 5){
            this.multiplicateur1 = multiplicateur;
        } else {
            this.multiplicateur2 = multiplicateur;
        }

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