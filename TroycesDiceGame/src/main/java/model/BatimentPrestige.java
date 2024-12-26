package main.java.model;

public class BatimentPrestige extends Batiment {
    private final Effet effet;
    private final Recompense recompense;
    private final Couleur couleur;
    private final int nombre;


    public BatimentPrestige(int ID, Effet effet, Recompense recompense, Couleur couleur, int nombre) {
        super(ID);
        this.effet = effet;
        this.recompense = recompense;
        this.couleur = couleur;
        this.nombre = nombre;
    }

    public void appliquerEffet(int valDe, Panel panel, Feuille feuille) {
        switch (effet) {
            case PROTEGER:
                feuille.getEtudiant().protegerColonne(valDe);
                feuille.getAdministration().protegerColonne(valDe);
                feuille.getEnseignant().protegerColonne(valDe);
                break;
            case MULTIPLICATEUR:
                panel.ajouterMultiplicateur(valDe);
                break;
            case RIEN:
                break;
        }
    }

    public Effet getEffet() {
        return effet;
    }

    public int getNombre() {
        return nombre;
    }

    public Recompense getRecompense() {
        return recompense;
    }

    public Couleur getCouleur() {
        return couleur;
    }
}