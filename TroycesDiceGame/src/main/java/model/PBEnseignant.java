package main.java.model;

public class PBEnseignant extends AbstractBatimentPrestige{
    PBEnseignant(){
        super();
    }
    
    @Override
    public void rendreInconstructible() {
        etat = Etat.INCONSTRUCTIBLE;
    }

    @Override
    public void onBuild() {
        etat = Etat.CONSTRUIT;
    }
}
