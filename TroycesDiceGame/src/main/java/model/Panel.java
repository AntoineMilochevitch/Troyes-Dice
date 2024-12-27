package main.java.model;

import java.util.ArrayList;
import java.util.List;

abstract class Panel {
    private int ressource;
    protected  List<BatimentPrestige> batimentsPrestige;
    protected  List<BatimentFonction> batimentsFonction;

    private final Feuille feuille;

    private int multiplicateur1;
    private int multiplicateur2;

    {
        initLists();
    }

    public Panel(Feuille feuille) {
        this.ressource = 0;
        this.feuille = feuille;
        this.batimentsPrestige = new ArrayList<>();
        this.batimentsFonction = new ArrayList<>();
        this.multiplicateur1 = 0;
        this.multiplicateur2 = 0;
    }

    abstract void initLists();

    public Panel(int ressource, Feuille feuille, List<BatimentPrestige> batimentsPrestige, List<BatimentFonction> batimentsFonction, int multiplicateur1, int multiplicateur2) {
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
            feuille.addPoints(bat.getCouleur(), bat.getNombre());
        }
    }

    public void buildBP(int valDe, Panel panel, Feuille feuille) {
        BatimentPrestige bat = batimentsPrestige.get(valDe);
        if (bat.getEtat() == Etat.VIDE || bat.getEtat() == Etat.PROTEGE){ 
            bat.onBuild(); // Set l'état a CONSTRUIT
            feuille.addPoints(bat.getCouleur(), bat.getNombre());
            bat.appliquerEffet(valDe, panel, feuille);
        }
        
    }

    public int getRessource() {
        return ressource;
    }

    public void setRessource(int ressource) {
        this.ressource = ressource;
    }

    public void addRessource(int ressource){
        this.ressource += ressource;
    }

    public void appliquerMultiplicateur(int numero, int multiplicateur){
        switch (numero) {
            case 1 :
                multiplicateur1 = multiplicateur;
                break;
            case 2 :
                multiplicateur2 = multiplicateur;
                break;
        }
    }

    public void ajouterMultiplicateur(int valDe){

        int nbBat = nbBatimentPrestige();
        int mult;
        switch (nbBat) {
            case 1:
            case 2:
                mult = 1;
                break;
            case 3:
            case 4:
                mult = 2;
                break;
            case 5:
            case 6:
                mult = 3;
                break;
            default:
                mult = 0;
        }
        feuille.multiplierHandler(valDe, mult);
    }

    private int nbBatimentPrestige(){
        int nbCathédrale = 0;
        for (Batiment batiment : batimentsPrestige){
            if (batiment.getEtat() == Etat.CONSTRUIT){
                nbCathédrale++;
            }
        }
        return nbCathédrale;
    }

    public final void afficherPanel(){
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