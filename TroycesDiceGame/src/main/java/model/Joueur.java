package main.java.model;

public class Joueur implements Actionnable{
    private String nom;
    private int id;
    private Feuille feuille;
    private Case caseChoisie;

    public Joueur(String nom, int id) {
        this.nom = nom;
        this.id = id;
        this.feuille = new Feuille();
    }

    public Case choisirCase() {
        //TODO
        return caseChoisie;
    }

    public Actionnable choisirAction() {
        //TODO
        return null;
    }

    public Panel choosePanel() {
        Couleur couleurPanel;
        if (caseChoisie.getSenseCase() == 1) {
            couleurPanel = caseChoisie.getCouleurRecto();
        } else {
            couleurPanel = caseChoisie.getCouleurVerso();
        }
        switch (couleurPanel) {
            case ROUGE:
                return feuille.getAdministration();
            case JAUNE:
                return feuille.getEtudiant();
            case BLANC:
                return feuille.getEnseignant();
            default:
                return null;
        }

    }

    @Override
    public void buildBP() {
        Panel panel = choosePanel();
        //TODO
    }

    @Override
    public void buildBF() {
        Panel panel = choosePanel();
        int valDe = caseChoisie.getValDe();
        panel.buildBF(valDe);
    }

    @Override
    public void getRessource() {
        Panel panel = choosePanel();
        int valDe = caseChoisie.getValDe();
        panel.addRessource(valDe);
    }
}
