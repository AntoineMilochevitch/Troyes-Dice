package main.java.model;

import java.util.Scanner;

public class Joueur implements Actionnable {
    private final String nom;
    private final int id;
    private final Feuille feuille;
    private Case caseChoisie;
    private int valDeLocal;
    private Couleur couleurLocal;
    private Actionnable actionChoisie;

    public Joueur(String nom, int id) {
        System.err.println("HERE2");
        this.nom = nom;
        this.id = id;
        this.feuille = new Feuille();
        caseChoisie = null;
        valDeLocal = 0;
        couleurLocal = null;
        actionChoisie = null;
        System.err.println("HERE2");
    }

    public Case choisirCase(Plateau plateau) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Entrez le numéro de la case choisie : ");
            int numCase = scanner.nextInt();
            
            if (numCase >= 0 && numCase < plateau.getRoue().size()) {
                caseChoisie = plateau.getCase(numCase);
                couleurLocal = caseChoisie.getSenseCase() == 1 ? caseChoisie.getCouleurRecto() : caseChoisie.getCouleurVerso();
                valDeLocal = caseChoisie.getValDe();
                Panel panel = choosePanel();

                if (panel.getRessource() < caseChoisie.getCout()) {
                    System.out.println("Pas assez de ressources pour cette case. Veuillez réessayer.");
                    return choisirCase(plateau);
                } else {
                    int ressourceDepense = 0;

                    if (couleurLocal == Couleur.BLANC) {
                        modifierCouleurDe();
                    } else if (couleurLocal == Couleur.ROUGE) {
                        try (Scanner scanner2 = new Scanner(System.in)) {
                            System.out.println("Combien de ressource voulez vous dépenser ? : ");
                            ressourceDepense = scanner2.nextInt();
                        }
                        modifierValeurDe(ressourceDepense);
                    }
                    feuille.utiliserRessource(couleurLocal, caseChoisie.getCout(), ressourceDepense);
                }

            } else {
                System.out.println("Numéro de case invalide. Veuillez réessayer.");
                return choisirCase(plateau);
            }
        }
        return caseChoisie;
    }

    public void modifierCouleurDe() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Vous avez utilisé une ressource blanche. Choisissez la nouvelle couleur du dé : 1. Rouge, 2. Jaune, 3. Blanc");
            int choix = scanner.nextInt();
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
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    modifierCouleurDe();
            }
        }
    }

    public void modifierValeurDe(int ressourceDepense) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Vous avez utilisé une ressource rouge. Choisissez l'action : 1. Augmenter la valeur du dé, 2. Diminuer la valeur du dé");
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                    valDeLocal = Math.min(valDeLocal + 1*ressourceDepense, 6); // La valeur maximale d'un dé est 6
                    break;
                case 2:
                    valDeLocal = Math.max(valDeLocal - 1*ressourceDepense, 1); // La valeur minimale d'un dé est 1
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    modifierValeurDe(ressourceDepense);
            }
        }
    }

    public Actionnable choisirAction() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Choisissez une action : 1. Construire un bâtiment de prestige, 2. Construire un bâtiment de fonction, 3. Récolter des ressources");
            int choix = scanner.nextInt();
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
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    return choisirAction();
            }
        }
        return actionChoisie;
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
        switch (valDeLocal) {
            case 1:
                return feuille.getAdministration();
            case 2:
                return feuille.getAdministration();
            case 3:
                return feuille.getEtudiant();
            case 4:
                return feuille.getEtudiant();
            case 5:
                return feuille.getEnseignant();
            case 6:
                return feuille.getEnseignant();
            default:
                return null;
        }
    }

    public void buildBP() {
        Panel panel = choosePanel();
        Panel panelMultiplicateur = chooseMultiplicateurPanel();
        panel.buildBP(valDeLocal, panelMultiplicateur, feuille);
    }

    public void buildBF() {
        Panel panel = choosePanel();
        panel.buildBF(valDeLocal);
    }

    public void getRessource() {
        Panel panel = choosePanel();
        panel.addRessource(valDeLocal);
    }

    public Feuille getFeuille(){
        return feuille;
    }

    public void execute() {
        if (actionChoisie != null) {
            actionChoisie.execute();
        }
    }

    public void afficherFeuille() {
        System.out.println("Feuille de " + nom + " : ");
        feuille.afficherFeuille();
    }
}