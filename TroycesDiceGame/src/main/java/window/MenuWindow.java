package main.java.window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.java.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MenuWindow extends Application {

    private GameInstance game;
    private List<Joueur> joueurs;
    private Plateau plateau;
    private CountDownLatch latch;
    private Button startButton;

    @Override
    public void start(Stage primaryStage) {
        game = GameInstance.getInstance();
        joueurs = new ArrayList<>();
        plateau = new Plateau(new ArrayList<>());
        latch = new CountDownLatch(1);

        primaryStage.setTitle("Troyes Dice");
        primaryStage.setWidth(1500);
        primaryStage.setHeight(1000);
        primaryStage.setResizable(false);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #fff3e6;");

        VBox menu = new VBox(10);
        menu.setStyle("-fx-alignment: center;");

        Label statusLabel = new Label("Game Status: Not Started");
        statusLabel.getStyleClass().add("status-label");

        startButton = new Button("Start Game");
        startButton.getStyleClass().add("start-button");

        startButton.setOnAction(e -> {
            menu.getChildren().clear();
            numberPlayers(menu);
        });

        menu.getChildren().addAll(startButton, statusLabel);
        root.setCenter(menu);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void numberPlayers(Pane panel) {
        Label playersLabel = new Label("Number of Players:");
        playersLabel.getStyleClass().add("players-label");

        TextField playersText = new TextField("2");
        playersText.getStyleClass().add("players-text");

        Button confirmed = new Button("Confirmed");
        confirmed.getStyleClass().add("confirmed-button");

        confirmed.setOnAction(e -> {
            panel.getChildren().clear();
            Integer nbPlayers = Integer.parseInt(playersText.getText());
            if (nbPlayers >= 2) {
                panel.getChildren().clear();
                addPlayers(panel, nbPlayers);
            }
        });

        panel.getChildren().addAll(playersLabel, playersText, confirmed);
    }

    private void addPlayers(Pane panel, Integer id) {
        if (id == 0) {
            startGame();
            return;
        }

        Label nameLabel = new Label("Enter name for Player " + id + ":");
        nameLabel.getStyleClass().add("name-label");

        TextField nameText = new TextField();
        nameText.getStyleClass().add("name-text");

        Button confirmed = new Button("Confirmed");
        confirmed.getStyleClass().add("confirmed-button");

        confirmed.setOnAction(e -> {
            String namePlayer = nameText.getText();
            joueurs.add(new Joueur(namePlayer, id));
            System.out.println("name : " + namePlayer);
            panel.getChildren().clear();
            addPlayers(panel, id - 1);
        });

        panel.getChildren().addAll(nameLabel, nameText, confirmed);
    }

    private void startGame() {
        Game game = new Game(joueurs, plateau, latch);
        Thread gameThread = new Thread(game);
        gameThread.start();

        try {
            latch.await(); // Wait for the game initialization to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add listeners to each player's Feuille
        for (Joueur joueur : joueurs) {
            joueur.getFeuille().addListener(new FeuilleListener() {
                @Override
                public void onFeuilleUpdated(Feuille feuille) {
                    // Update the FeuilleWindow if it is open
                    Platform.runLater(() -> {
                        if (FeuilleWindow.isOpen()) {
                            FeuilleWindow.updateFeuille(feuille);
                        }
                    });
                }
            });
        }

        // Launch the main game window
        Platform.runLater(() -> {
            Stage gameStage = new Stage();
            GameWindow gameWindow = new GameWindow(joueurs);
            try {
                gameWindow.start(gameStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}