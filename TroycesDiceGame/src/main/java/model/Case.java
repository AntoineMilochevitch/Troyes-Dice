package main.java.model;

public class Case implements Cloneable{
    private Couleur couleurRecto;
    private Couleur couleurVerso;
    private int sensCase;
    private De valDe;
    private int cout;
    private DemiJournee demiJournee;

    public Case(Couleur couleurRecto, Couleur couleurVerso, int sens, De valDe, int cout, DemiJournee demiJournee) {
        this.couleurRecto = couleurRecto;
        this.couleurVerso = couleurVerso;
        this.sensCase = sens;
        this.valDe = valDe;
        this.cout = cout;
        this.demiJournee = demiJournee;
    }

    @Override
    public Case clone() {
        return new Case(couleurRecto, couleurVerso, sensCase, valDe, cout, demiJournee);
    }

    public Couleur getCouleurRecto() {
        return couleurRecto;
    }
    
    public Couleur getCouleurVerso() {
        return couleurVerso;
    }
    

    public int getSenseCase() {
        return sensCase;
    }

    public int getCout() {
        return cout;
    }

    public DemiJournee getDemiJournee() {
        return demiJournee;
    }

    public De getValDe() {
        return valDe;
    }

    public void setDemijournee(DemiJournee demiJournee) {
        this.demiJournee = demiJournee;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }

    public void setSenseCase(int sensCase) {
        this.sensCase = sensCase;
    }

    public void setValDe(De valDe) {
        this.valDe = valDe;
    }
}
