package main.java.model;

import java.util.List;

public class Joueur implements Actionnable {
    private String nom;
    private int id;
    private Feuille feuille;
    private Case caseChoisie;
    private De valDeLocal;
    private Couleur couleurLocal;
    private Actionnable actionChoisie;
    private List<De> des;

    public Joueur(String nom, int id) {
        this.nom = nom;
        this.id = id;
        feuille = new Feuille();
        caseChoisie = null;
        valDeLocal = null;
        couleurLocal = null;
        actionChoisie = null;
        des = null;
    }

    public void choisirCase(Case caseChoisie) {
        this.caseChoisie = caseChoisie;
        couleurLocal = caseChoisie.getSenseCase() == 1 ? caseChoisie.getCouleurRecto() : caseChoisie.getCouleurVerso();
        valDeLocal = caseChoisie.getValDe();
    }

    public void modifierCouleurDe(int choix) {
        switch (choix) {
            case 1:
                couleurLocal = Couleur.ROUGE;
                break;
            case 2:
                couleurLocal = Couleur.JAUNE;
                break;
            case 3:
                couleurLocal = Couleur.BLANC;
                break;
            default:
                throw new IllegalArgumentException("Choix invalide");
        }
    }

    public void modifierValeurDe(int ressourceDepense, int choix) {
        switch (choix) {
            case 1:
                valDeLocal.setValeur(Math.min(valDeLocal.getValeur() + ressourceDepense, 6));
                break;
            case 2:
                valDeLocal.setValeur(Math.max(valDeLocal.getValeur() - ressourceDepense, 1));
                break;
            default:
                throw new IllegalArgumentException("Choix invalide");
        }
    }

    public void choisirAction(int choix) {
        switch (choix) {
            case 1:
                actionChoisie = this::buildBP;
                break;
            case 2:
                actionChoisie = this::buildBF;
                break;
            case 3:
                actionChoisie = this::getRessource;
                break;
            default:
                throw new IllegalArgumentException("Choix invalide");
        }
    }

    public void buildBP() {
        Panel panel = choosePanel();
        Panel panelMultiplicateur = chooseMultiplicateurPanel();
        panel.buildBP(valDeLocal, panelMultiplicateur, feuille, des);
    }

    public void buildBF() {
        Panel panel = choosePanel();
        panel.buildBF(valDeLocal);
    }

    public void getRessource() {
        Panel panel = choosePanel();
        panel.addRessource(valDeLocal.getValeur());
    }

    public Panel choosePanel() {
        switch (couleurLocal) {
            case JAUNE:
                return feuille.getEtudiant();
            case BLANC:
                return feuille.getEnseignant();
            case ROUGE:
                return feuille.getAdministration();
            default:
                return null;
        }
    }

    public Panel chooseMultiplicateurPanel() {
        switch (valDeLocal.getValeur()) {
            case 1:
            case 2:
                return feuille.getAdministration();
            case 3:
            case 4:
                return feuille.getEtudiant();
            case 5:
            case 6:
                return feuille.getEnseignant();
            default:
                return null;
        }
    }

    public Feuille getFeuille() {
        return feuille;
    }

    public void execute() {
        if (actionChoisie != null) {
            actionChoisie.execute();
        }
    }

    public void setListDe(List<De> des) {
        this.des = des;
    }

    public String getNom() {
        return nom;
    }

    public List<De> getDe(){
        return des;
    }

    public void setCaseChoisie(Case caseChoisie) {
        this.caseChoisie = caseChoisie;
    }

    public void setCouleurLocal(Couleur couleurLocal) {
        this.couleurLocal = couleurLocal;
    }

    public void setValDeLocal(De valDeLocal) {
        this.valDeLocal = valDeLocal;
    }
}