package main.java.model;

public interface Batiment {
    void onBuild();
    void rendreInconstructible();
    void proteger();
    Etat getEtat();
}
