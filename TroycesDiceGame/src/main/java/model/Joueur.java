package main.java.model;

import java.util.*;

public class Joueur implements Actionnable {
    private String nom;
    private int id;
    private Feuille feuille;
    private Case caseChoisie;
    private De valDeLocal;
    private Couleur couleurLocal;
    private Actionnable actionChoisie;
    private List<De> des;
    private static final Scanner scanner = new Scanner(System.in); // Single Scanner instance

    public Joueur(String nom, int id) {
        System.out.println("HERE2");
        this.nom = nom;
        this.id = id;
        feuille = new Feuille();
        caseChoisie = null;
        valDeLocal = null;
        couleurLocal = null;
        actionChoisie = null;
        des = null;
    }

    public Case choisirCase(Plateau plateau) {
        System.out.println("Entrez le numéro de la case choisie : ");
        int numCase = scanner.nextInt();

        if (numCase >= 0 && numCase < plateau.getRoue().size()) {
            Case caseChoisie = plateau.getCase(numCase);
            DemiJournee demiJourneeActuelle = plateau.getCompteurDemiJournee() % 2 == 1 ? DemiJournee.APRES_MIDI : DemiJournee.MATIN;

            if (caseChoisie.getDemiJournee() == DemiJournee.NEUTRE) {
                System.out.println("Impossible de choisir une case NEUTRE. Veuillez réessayer.");
                return choisirCase(plateau);
            }

            if (caseChoisie.getDemiJournee() != demiJourneeActuelle) {
                System.out.println("Vous ne pouvez choisir que les cases correspondant à la demi-journée actuelle (" + demiJourneeActuelle + "). Veuillez réessayer.");
                return choisirCase(plateau);
            }

            this.caseChoisie = caseChoisie;
            couleurLocal = caseChoisie.getSenseCase() == 1 ? caseChoisie.getCouleurRecto() : caseChoisie.getCouleurVerso();
            valDeLocal = caseChoisie.getValDe();
            System.out.println("Valeur du dé : " + valDeLocal.getValeur());
            Panel panel = choosePanel();

            if (panel.getRessource() < caseChoisie.getCout()) {
                System.out.println("Pas assez de ressources pour cette case. Veuillez réessayer.");
                return choisirCase(plateau);
            } else {
                panel.setRessource(panel.getRessource() - caseChoisie.getCout());
                System.out.println("Voulez-vous utiliser une ressource supplémentaire ? (oui/non)");
                String reponse = scanner.next();
                if (reponse.equalsIgnoreCase("oui")) {
                    int ressourceDepense = 0;
                    if (couleurLocal == Couleur.BLANC) {
                        modifierCouleurDe();
                    } else if (couleurLocal == Couleur.ROUGE) {
                        System.out.println("Combien de ressource voulez vous dépenser ? : ");
                        ressourceDepense = scanner.nextInt();
                        modifierValeurDe(ressourceDepense);
                    }
                    feuille.utiliserRessource(couleurLocal, caseChoisie.getCout(), ressourceDepense);
                }
            }
        } else {
            System.out.println("Numéro de case invalide. Veuillez réessayer.");
            return choisirCase(plateau);
        }
        return caseChoisie;
    }

    public void modifierCouleurDe() {
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

    public void modifierValeurDe(int ressourceDepense) {
        System.out.println("Vous avez utilisé une ressource rouge. Choisissez l'action : 1. Augmenter la valeur du dé, 2. Diminuer la valeur du dé");
        int choix = scanner.nextInt();
        switch (choix) {
            case 1:
                valDeLocal.setValeur( Math.min(valDeLocal.getValeur() + ressourceDepense, 6)); // La valeur maximale d'un dé est 6
                break;
            case 2:
                valDeLocal.setValeur(Math.max(valDeLocal.getValeur() - ressourceDepense, 1)); // La valeur minimale d'un dé est 1
                break;
            default:
                System.out.println("Choix invalide. Veuillez réessayer.");
                modifierValeurDe(ressourceDepense);
        }
    }

    public Actionnable choisirAction() {
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
        switch (valDeLocal.getValeur()) {
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
        panel.buildBP(valDeLocal, panelMultiplicateur, feuille, des);
    }

    public void buildBF() {
        Panel panel = choosePanel();
        System.out.println("Valeur de dé : " + valDeLocal);
        panel.buildBF(valDeLocal);
    }

    public void getRessource() {
        Panel panel = choosePanel();
        panel.addRessource(valDeLocal.getValeur());
    }

    public Feuille getFeuille() {
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

    public void setListDe(List<De> des) {
        this.des = des;
    }
}