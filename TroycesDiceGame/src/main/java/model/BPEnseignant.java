package main.java.model;

public class BPEnseignant extends BatimentPrestige{
    BPEnseignant(){
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
    
    public void buildBPEnseignant(int valDe){
        if (etat == Etat.INCONSTRUCTIBLE){
            onBuild();
        }
        
    }
}
