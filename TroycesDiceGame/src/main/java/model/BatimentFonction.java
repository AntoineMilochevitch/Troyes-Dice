package main.java.model;

public class BatimentFonction extends Batiment{
    private Recompense recompense;
    private int nombre;
    private Couleur couleur;

    public BatimentFonction(int ID, Recompense recompense) {
        super(ID);
        this.recompense = Recompense.HABITANT;
    }
    
    public Couleur getCouleur(){
        return couleur;
    }

    public Recompense getRecompense(){
        return recompense;
    }

    public int getNombre(){
        return nombre;
    }

}
