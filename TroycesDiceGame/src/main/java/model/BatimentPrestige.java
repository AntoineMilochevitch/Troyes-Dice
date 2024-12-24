package main.java.model;

public abstract class BatimentPrestige extends Batiment {
    private Effet effet;
    private Recompense recompense;

    public BatimentPrestige(int ID, Effet effet, Recompense recompense) {
        super(ID);
        this.effet = effet;
        this.recompense = recompense;
    }

    public void appliquerEffet() {
        switch (effet) {
            case PROTEGEr:
                proteger();
                break;
            case MULTIPLICATEUR:
                
                break;
            case RIEN:
                // TODO
                break;
        }
    }


}
