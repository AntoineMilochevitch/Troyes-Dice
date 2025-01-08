package main.java.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Panel {
    private int ressource;
    protected List<BatimentPrestige> batimentsPrestige;
    protected List<BatimentFonction> batimentsFonction;

    private final Feuille feuille;

    private static int multiplicateur1;
    private static int multiplicateur2;

    public Panel(Feuille feuille) {
        this.ressource = 3;
        this.feuille = feuille;
        this.batimentsPrestige = new ArrayList<>();
        this.batimentsFonction = new ArrayList<>();
        multiplicateur1 = 0;
        multiplicateur2 = 0;
        initLists();
    }

    abstract void initLists();

    public Panel(int ressource, Feuille feuille, List<BatimentPrestige> batimentsPrestige, List<BatimentFonction> batimentsFonction, int multiplicateur1, int multiplicateur2) {
        this.ressource = ressource;
        this.feuille = feuille;
        this.batimentsPrestige = batimentsPrestige;
        this.batimentsFonction = batimentsFonction;
        Panel.multiplicateur1 = multiplicateur1;
        Panel.multiplicateur2 = multiplicateur2;
        initLists();
    }

    public static int getMultiplier(int i){
        return switch (i) {
            case 1 -> multiplicateur1;
            case 2 -> multiplicateur2;
            default -> 0;
        };
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

    public void buildBF(De valDe) {
        if (valDe.getValeur() >= 1 && valDe.getValeur() <= batimentsFonction.size()) {
            BatimentFonction bat = batimentsFonction.get(valDe.getValeur() - 1); // Adjusting index to be 0-based
            System.out.println("valDe : " + valDe);
            // Si le batiment est vide ou la parcelle est protégée (donc implicitement non construite)
            if (bat.getEtat() == Etat.VIDE || bat.getEtat() == Etat.PROTEGE) {
                bat.onBuild();
                System.out.println("Batiment construit");
                feuille.addPoints(bat.getCouleur(), bat.getNombre());
            }
        } else {
            System.out.println("Valeur de dé invalide. Veuillez réessayer.");
        }
    }

    public int getNumberDe(Couleur couleur, List<De> des) {
        int nb = 0;
        for (De de : des) {
            if (de.getCouleur() == couleur) {
                nb++;
            }
        }
        return nb;
    }

    public void buildBP(De valDe, Panel panel, Feuille feuille, List<De> des) {
        if (valDe.getValeur() >= 1 && valDe.getValeur() <= batimentsPrestige.size()) {
            BatimentPrestige bat = batimentsPrestige.get(valDe.getValeur() - 1); // Adjusting index to be 0-based
            if (bat.getEtat() == Etat.VIDE || bat.getEtat() == Etat.PROTEGE) {
                bat.onBuild(); // Set l'état a CONSTRUIT
                int nombreDesRouge = getNumberDe(Couleur.ROUGE, des);
                int nombreDesJaune = getNumberDe(Couleur.JAUNE, des);
                int nombreDesBlanc = getNumberDe(Couleur.BLANC, des);
                bat.appliquerEffet(valDe, panel, feuille, nombreDesRouge, nombreDesJaune, nombreDesBlanc);
            }
        } else {
            System.out.println("Valeur de dé invalide. Veuillez réessayer.");
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

    public final void afficherPanel() {
        System.out.println("Ressource : " + ressource);
        System.out.println("Batiments de prestige : ");
        for (Batiment bat : batimentsPrestige) {
            System.out.println("Etat : " + bat.getEtat());
        }
        System.out.println("Batiments de fonction : ");
        for (Batiment bat : batimentsFonction) {
            System.out.println("Etat : " + bat.getEtat());
        }
        System.out.println("Multiplicateur 1 : " + multiplicateur1);
        System.out.println("Multiplicateur 2 : " + multiplicateur2);
    }

    public List<BatimentPrestige> getBatimentsPrestige() {
        return batimentsPrestige;
    }

    public List<BatimentFonction> getBatimentsFonction() {
        return batimentsFonction;
    }
    
}