package main.java.model;

public abstract class AbstractBatimentPrestige implements BatimentPrestige {
    protected Etat etat;

    public AbstractBatimentPrestige() {
        this.etat = Etat.VIDE;
    }
}
