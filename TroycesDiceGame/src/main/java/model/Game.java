package main.java.model;

import main.java.exceptions.InvalidColorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Game implements Runnable {
    private List<Joueur> joueurs;
    private Plateau plateau;
    private boolean deNoirActif;
    private List<De> listDE;
    private int indexDeNoir;
    private CountDownLatch latch;
    private int currentPlayerIndex;

    public Game(List<Joueur> joueurs, Plateau plateau, CountDownLatch latch) {
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.deNoirActif = false;
        this.latch = latch;
        this.currentPlayerIndex = 0;
    }

    @Override
    public void run() {
        startGame();
    }

    public void startGame() {
        latch.countDown(); // Signal that the initialization is complete
        gameLoop();
    }

    public void gameLoop() {
        System.out.println("Début de la partie");
        System.out.println("Compteur de demi-journée : " + plateau.getCompteurDemiJournee());
        System.out.println("Dé noir actif : " + deNoirActif);
        listDE = lancerDe();
        listDE.sort(Comparator.comparingInt(De::getValeur)); // Trier les dés par valeur croissante
        System.out.print("Les dés sont : " );
        for (De de : listDE) {
            System.out.print(de.getValeur() + " ");
        }

        assignDiceToCases();

        // AFFICHE LA ROUE
        plateau.afficherRoue();
        while (!finDePartie()) {
            Joueur currentPlayer = joueurs.get(currentPlayerIndex);
            System.out.println("Tour du joueur " + currentPlayer.getNom());
            Feuille feuille = currentPlayer.getFeuille();
            currentPlayer.setListDe(listDE);


            // Wait for the player to make a move via the GUI
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Execute the player's action
            currentPlayer.execute();
            feuille.afficherFeuille();

            // Move to the next player
            currentPlayerIndex = (currentPlayerIndex + 1) % joueurs.size();

            // If all players have played, move to the next half-day
            if (currentPlayerIndex == 0) {
                plateau.incrementerCompteurDemiJournee();
                if (plateau.getCompteurDemiJournee() % 2 == 0) {
                    System.out.println("Tourner la roue");
                    plateau.tournerRoue();
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

                // LOGIQUE LANCE DES DéS
                listDE = lancerDe();
                listDE.sort(Comparator.comparingInt(De::getValeur)); // Trier les dés par valeur croissante
                assignDiceToCases();

                // AFFICHE LA ROUE
                System.out.println("Compteur de demi-journée : " + plateau.getCompteurDemiJournee());
                plateau.afficherRoue();
            }
        }
    }

    private void assignDiceToCases() {
        int dieIndex = 0;
        DemiJournee demiJourneeActuelle = plateau.getCompteurDemiJournee() % 2 == 0 ? DemiJournee.MATIN : DemiJournee.APRES_MIDI;
        try {
            for (int i = 0; i < plateau.getRoue().size(); i++) {
                Case currentCase = plateau.getRoue().get(i);
                if (currentCase.getDemiJournee() == demiJourneeActuelle && dieIndex < listDE.size()) {
                    listDE.get(dieIndex).setCouleur(currentCase.getSenseCase() == 1 ? currentCase.getCouleurRecto() : currentCase.getCouleurVerso());
                    currentCase.setValDe(listDE.get(dieIndex));
                    dieIndex++;
                }
            }
        } catch (InvalidColorException e) {}
    }

    public List<De> lancerDe() {
        List<De> des = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int valeur = (int) (Math.random() * 6 + 1);
            Couleur couleur = Couleur.VIDE;
            des.add(new De(valeur, couleur));
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

    public synchronized void notifyPlayerAction() {
        notify();
    }
}