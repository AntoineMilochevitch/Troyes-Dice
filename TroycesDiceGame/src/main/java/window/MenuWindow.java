package main.java.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MenuWindow extends Application {

    private GameInstance game;

    @Override
    public void start(Stage primaryStage) {
        game = GameInstance.getInstance();

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

        Button startButton = new Button("Start Game");
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
            panel.getChildren().clear();
            // TODO: next state
        } else {
            Label player = new Label("Player " + id);
            player.getStyleClass().add("player-label");

            Label nameLabel = new Label("Name:");
            nameLabel.getStyleClass().add("name-label");

            TextField nameText = new TextField("Player " + id);
            nameText.getStyleClass().add("name-text");

            Button confirmed = new Button("Confirmed");
            confirmed.getStyleClass().add("confirmed-button");

            confirmed.setOnAction(e -> {
                panel.getChildren().clear();
                String namePlayer = nameText.getText();
                // TODO: add namePlayer to the list of players
                panel.getChildren().clear();
                addPlayers(panel, id - 1);
            });

            panel.getChildren().addAll(player, nameLabel, nameText, confirmed);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}