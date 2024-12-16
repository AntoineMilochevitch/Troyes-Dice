package main.java.model;

public abstract class BatimentPrestige implements Batiment {
    protected Etat etat;

    public BatimentPrestige() {
        this.etat = Etat.VIDE;
    }

    public Etat getEtat() {
        return etat;
    }
}
