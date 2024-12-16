package main.java.model;

public class BPAdministration extends BatimentPrestige{
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

    @Override
    public void proteger() {
        if (etat == Etat.CONSTRUIT){
            etat = Etat.CONSTRUIT_PROTEGE;
        }
        else{
            etat = Etat.PROTEGE;
        }
    }

    public void buildBPAdministration(int valDe){
        if (etat == Etat.INCONSTRUCTIBLE){
            onBuild();
        }
    }
}
