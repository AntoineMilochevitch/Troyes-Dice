package main.java.model;

public class BatimentPrestige extends Batiment {
    private Effet effet;
    private Recompense recompense;


    public BatimentPrestige(int ID, Effet effet, Recompense recompense) {
        super(ID);
        this.effet = effet;
        this.recompense = recompense;
    }

    public void appliquerEffet(int valDe, int multiplicateur, Panel panel, Feuille feuille) {
        switch (effet) {
            case PROTEGER:
                feuille.getEtudiant().protegerColonne(valDe);
                feuille.getAdministration().protegerColonne(valDe);
                feuille.getEnseignant().protegerColonne(valDe);
                break;
            case MULTIPLICATEUR:
                panel.ajouterMultiplicateur(valDe, multiplicateur);
                break;
            case RIEN:
                // TODO
                break;
        }
    }

    public Effet getEffet() {
        return effet;
    }
}