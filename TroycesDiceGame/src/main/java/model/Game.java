package main.java.model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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
        Joueur joueur1 = new Joueur("Joueur 1", 1);
        Joueur joueur2 = new Joueur("Joueur 2", 2);
        Joueur joueur3 = new Joueur("Joueur 3", 3);
        this.joueurs.add(joueur1);
        this.joueurs.add(joueur2);
        this.joueurs.add(joueur3);
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
                joueur.afficherFeuille();
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
            if (plateau.getCompteurDemiJournee() > 3) {
                indexDeNoir = tirerDeNoir();
                System.out.println("Dé noir actif et la colonne " + indexDeNoir + " a été détruite !");
                plateau.retournerCase(indexDeNoir);
                for (Joueur joueur : joueurs) {
                    joueur.getFeuille().detruireColonne(indexDeNoir);
                }
                deNoirActif = true;
            }

            // AFFICHE LA ROUE
            plateau.afficherRoue();
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