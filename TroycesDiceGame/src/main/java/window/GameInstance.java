package main.java.window;

// This class is a singleton, in order to avoid having more than one instance of the Game running at once.

public class GameInstance {
    private static GameInstance instance;

    private GameInstance() {
        System.out.println("Game instance created.");
        // TODO : Initialize the game here
    }

    public static GameInstance getInstance() {
        if(instance == null) {
            synchronized (GameInstance.class) {
                if(instance == null){
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
