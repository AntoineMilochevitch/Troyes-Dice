package main.java.model;

public class BPEtudiant extends AbstractBatimentPrestige {
    public BPEtudiant(){
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
