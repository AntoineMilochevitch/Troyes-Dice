package main.java.window;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.Joueur;

import java.util.List;

public class GameWindow extends Application {

    private List<Joueur> joueurs;

    public GameWindow(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Troyes Dice - Game");

        VBox root = new VBox(10);
        root.setStyle("-fx-alignment: center;");

        for (int i = 0; i < joueurs.size(); i++) {
            Joueur joueur = joueurs.get(i);
            Button feuilleButton = new Button("Ouvrir la Feuille de " + joueur.getNom());
            feuilleButton.setOnAction(e -> openFeuilleWindow(joueur));
            root.getChildren().add(feuilleButton);
        }

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFeuilleWindow(Joueur joueur) {
        Stage feuilleStage = new Stage();
        FeuilleWindow feuilleWindow = new FeuilleWindow();
        FeuilleWindow.setFeuilleStatic(joueur.getFeuille(), joueur.getNom());
        try {
            feuilleWindow.start(feuilleStage);
            FeuilleWindow.updateAll(joueur.getFeuille()); // Update all information when the window is opened
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}