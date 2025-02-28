package main.java.model;

public class BatimentFonction extends Batiment{
    private final Recompense recompense;
    private final int nombre;
    private final Couleur couleur;

    public BatimentFonction(int ID, Recompense recompense, Couleur couleur, int nombre){
        super(ID);
        this.recompense = Recompense.HABITANT;
        this.couleur = couleur;
        this.nombre = nombre;
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
