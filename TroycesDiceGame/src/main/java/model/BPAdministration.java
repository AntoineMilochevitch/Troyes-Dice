package main.java.model;

public class BPAdministration extends AbstractBatimentPrestige{
    public BPAdministration(){
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
