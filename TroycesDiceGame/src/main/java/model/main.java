package main.java.model;
//import main.java.window.*;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Joueur> joueurs = new ArrayList<>();
        List<Case> cases = new ArrayList<>();
        Plateau plateau = new Plateau(cases);

        Game game = new Game(joueurs, plateau);
        Thread gameThread = new Thread(game);
        gameThread.start();

        //GameWindow game = GameWindow.getInstance();
    }
}