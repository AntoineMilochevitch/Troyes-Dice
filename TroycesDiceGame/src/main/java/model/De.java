package main.java.model;

import main.java.exceptions.InvalidColorException;

public class De {
    private int valeur;
    private Couleur couleur;

    public De(int valeur, Couleur couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) throws InvalidColorException {
        if(couleur == Couleur.VIDE){
            throw new InvalidColorException();
        }
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}