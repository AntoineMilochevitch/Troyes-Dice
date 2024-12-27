package main.java.model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Joueur> joueurs;
    private Plateau plateau;
    private boolean deNoirActif;
    private List<Integer> listDE;
    private int indexDeNoir;

    public Game(List<Joueur> joueurs, Plateau plateau) {
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.deNoirActif = false;
    }

    public void startGame() {
    int nbJoueurs;
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println("Entrez le nombre de joueurs : ");
        nbJoueurs = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()

        // Créez les joueurs
        joueurs = new ArrayList<>();
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Entrez le nom du joueur " + (i + 1) + " : ");
            String nom = scanner.nextLine();
            Joueur joueur = new Joueur(nom, i);
            joueurs.add(joueur);
        }

        // Initialiser les cases du plateau
        plateau.initialiserCases(9);
    } catch (InputMismatchException e) {
        System.out.println("Erreur: Veuillez entrer un nombre valide pour le nombre de joueurs.");
        return;
    } catch (Exception e) {
        System.out.println("Erreur lors de la saisie du nombre de joueurs: " + e.getMessage());
        return;
    }

    deNoirActif = false;

    gameLoop();
}

    public void gameLoop() {
        System.out.println("Début de la partie");
        System.out.println("Compteur de demi-journée : " + plateau.getCompteurDemiJournee());
        System.out.println("Dé noir actif : " + deNoirActif);
        listDE = lancerDe();
        for (int i = 0; i < 4; i++) {
            if (i < plateau.getRoue().size() && i < listDE.size()) {
                plateau.getRoue().get(i).setValDe(listDE.get(i));
            }
        }
        while (!finDePartie()) {

            // ACTION POUR CHAQUE JOUEURS
            for (Joueur joueur : joueurs) {
                Case caseChoisie = joueur.choisirCase(plateau);
                joueur.choisirAction();
                joueur.execute();
                //joueur.afficherFeuille();
            }

            // INCREMENTE COMPTEUR DE DEMI-JOURNéES
            plateau.incrementerCompteurDemiJournee();
            if (plateau.getCompteurDemiJournee() % 2 == 0) {
                plateau.tournerRoue();
            }

            // LOGIQUE LANCE DES DéS
            listDE = lancerDe();
            for (int i = 0; i < 4; i++) {
                if (i < plateau.getRoue().size() && i < listDE.size()) {
                    plateau.getRoue().get(i).setValDe(listDE.get(i));
                }
            }

            // LOGIQUE DES NOIR
            System.out.println("Compteur de demi-journée : " + plateau.getCompteurDemiJournee());
            System.out.println("Dé noir actif : " + deNoirActif);
            if (plateau.getCompteurDemiJournee() > 3 && !deNoirActif) {
                indexDeNoir = tirerDeNoir();
                System.out.println("Dé noir actif et la colonne " + indexDeNoir + " a été détruite !");
                plateau.retournerCase(indexDeNoir);
                for (Joueur joueur : joueurs) {
                    joueur.getFeuille().detruireColonne(indexDeNoir);
                }
                deNoirActif = true;
            }

            // AFFICHE LA ROUE
            //plateau.afficherRoue();
        }
    }

    public List<Integer> lancerDe() {
        List<Integer> des = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // Génère un nombre aléatoire entre 1 et 6
            int de = (int) (Math.random() * 6 + 1);
            des.add(de);
        }
        return des;
    }

    public int tirerDeNoir() {
        // Génère un nombre aléatoire entre 0 et 3
        int indexDeNoir = (int) (Math.random() * 3);
        return indexDeNoir;
    }

    public boolean finDePartie() {
        if (plateau.getCompteurDemiJournee() == 14) {
            System.out.println("Fin de partie");
            return true;
        }
        return false;
    }
}