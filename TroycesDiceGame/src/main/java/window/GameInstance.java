package main.java.window;

public class GameInstance {
    private static GameInstance instance;

    private GameInstance() {
        System.out.println("Game instance created.");
        // TODO: Initialize the game here
    }

    public static GameInstance getInstance() {
        if (instance == null) {
            synchronized (GameInstance.class) {
                if (instance == null) {
                    GameInstance.instance = new GameInstance();
                }
            }
        }
        return instance;
    }

    public void startGame() {
        System.out.println("Game started!");
    }
}