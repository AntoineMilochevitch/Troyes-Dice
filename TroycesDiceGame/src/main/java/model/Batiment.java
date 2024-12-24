package main.java.model;

public class Batiment {
    private Etat etat;
    private int ID;

    public Batiment(int ID) {
        this.etat = Etat.VIDE;
        this.ID = ID;
    }

    public void rendreInconstructible() {
        etat = Etat.INCONSTRUCTIBLE;
    }

    public void onBuild() {
        etat = Etat.CONSTRUIT;
    }

    public void proteger() {
        etat = Etat.PROTEGE;
    }

    public Etat getEtat() {
        return etat;
    }

    
    
}
