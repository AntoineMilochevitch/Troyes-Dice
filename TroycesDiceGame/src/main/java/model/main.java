package main.java.model;

import main.java.window.FeuilleWindow;
import javafx.application.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class main {
    public static void main(String[] args) {
        List<Joueur> joueurs = new ArrayList<>();
        List<Case> cases = new ArrayList<>();
        Plateau plateau = new Plateau(cases);

        CountDownLatch latch = new CountDownLatch(1);

        Game game = new Game(joueurs, plateau, latch);
        Thread gameThread = new Thread(game);
        gameThread.start();

        try {
            latch.await(); // Wait for the game initialization to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Feuille feuille = joueurs.get(0).getFeuille();
        FeuilleWindow.setFeuilleStatic(feuille); // Set the feuille statically
        Application.launch(FeuilleWindow.class, args);
    }
}