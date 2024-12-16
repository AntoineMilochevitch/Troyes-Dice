package main.java.model;

public class BatimentFonction implements Batiment{
    private Etat etat;
    
    public BatimentFonction() {
        this.etat = Etat.VIDE;
    }

    @Override
    public void rendreInconstructible() {
        etat = Etat.INCONSTRUCTIBLE;
    }

    @Override
    public void onBuild() {
        etat = Etat.CONSTRUIT;
    }

    @Override
    public void proteger() {
        if (etat == Etat.CONSTRUIT){
            etat = Etat.CONSTRUIT_PROTEGE;
        }
        else{
            etat = Etat.PROTEGE;
        }
    }

    @Override
    public Etat getEtat() {
        return etat;
    }
}
