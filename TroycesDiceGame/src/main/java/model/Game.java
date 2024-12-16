package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Joueur> joueurs;
    private Plateau plateau;
    private boolean deNoirActif;

    public Game(List<Joueur> joueurs, Plateau plateau) {
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.deNoirActif = false;
    }

    public void startGame() {
        while (!finDePartie()) {
            for (Joueur joueur : joueurs) {
                Case caseChoisie = joueur.choisirCase();
                Actionnable action = joueur.choisirAction();
            }
            plateau.incrementerCompteurDemiJournee();
        }
    }

    public List<Integer> lancerDe() {
        List<Integer> des = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            // generate random number between 1 and 6
            int de = (int) (Math.random() * 6 + 1);
            des.add(de);
        }
        return des;
    }

    public int tirerDeNoir() {
        //generate random between 0 and 3
        int indexDeNoir = (int) (Math.random() * 3);
        return indexDeNoir;
    }

    public boolean finDePartie() {
        if (plateau.getCompteurDemiJournee() == 14) {
            return true;
        }
        return false;
    }
}
