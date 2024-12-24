package main.java.model;

import java.util.ArrayList;
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

            // Créez les joueurs
            joueurs = new ArrayList<>();
            for (int i = 0; i < nbJoueurs; i++) {
                System.out.println("Entrez le nom du joueur " + i + " : ");
                String nom = scanner.next();
                Joueur joueur = new Joueur(nom, i);
                joueurs.add(joueur);
            }

            // Initialiser les cases du plateau
            plateau.initialiserCases(9); 
        }
        gameLoop();
    }

    public void gameLoop() {
        listDE = new ArrayList<>();
        listDE = lancerDe();
        for (int i = 0; i < 4; i++) {
            plateau.getRoue().get(i).setValDe(listDE.get(i));
        }
        while (!finDePartie()) {
            for (Joueur joueur : joueurs) {
                Case caseChoisie = joueur.choisirCase(plateau);
                joueur.choisirAction();
                // Exécutez l'action choisie par le joueur
                joueur.execute();
            }
            plateau.incrementerCompteurDemiJournee();
            if (plateau.getCompteurDemiJournee() % 2 == 0) {
                plateau.tournerRoue();
            }
            listDE = lancerDe();
            indexDeNoir = tirerDeNoir();
            if (plateau.getCompteurDemiJournee() > 3 && !deNoirActif) {
                System.out.println("Dé noir actif");
                deNoirActif = true;
                indexDeNoir = tirerDeNoir();
            }
        }
    }

    public List<Integer> lancerDe() {
        List<Integer> des = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
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