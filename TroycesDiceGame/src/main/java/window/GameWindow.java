package main.java.window;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.DemiJournee;
import main.java.model.Joueur;
import main.java.model.Plateau;

import java.util.List;

public class GameWindow extends Application {

    private final List<Joueur> joueurs;
    public Joueur currentPlayer;
    public Plateau plateau;

    public GameWindow(List<Joueur> joueurs, Plateau plateau) {
        this.joueurs = joueurs;
        this.plateau = plateau;
    }

    private VBox feuilleButtonBox(){
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(10); // Vertical gap between rows

        for (int i = 0; i < joueurs.size(); i++) {
            Joueur joueur = joueurs.get(i);
            Button feuilleButton = new Button("Ouvrir la Feuille de " + joueur.getNom());
            feuilleButton.setOnAction(e -> openFeuilleWindow(joueur));
            gridPane.add(feuilleButton, i, 0);
        }

        VBox root = new VBox();
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().add(gridPane);
        root.setPrefHeight(720);
        root.setPadding(new Insets(0, 0, 20, 0));

        return root;
    }

    private VBox mainGamePlate() {

        DemiJournee demiJourneeActuelle = plateau.getCompteurDemiJournee() % 2 == 1 ? DemiJournee.APRES_MIDI : DemiJournee.MATIN;

        VBox root = new VBox();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        for (int i = 0; i < 9; i++) {
            Button dayButton = new Button("Case : " + (i + 1));
            Label valDeLabel = new Label("");
            Label costLabel = new Label("");;

            if (plateau.getCase(i).getDemiJournee() != demiJourneeActuelle) {
                switch (plateau.getCase(i).getUprightColor()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayGrayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayGrayYellowButton");
                    default -> dayButton.getStyleClass().add("dayGrayWhiteButton");
                }
            } else {
                valDeLabel = new Label("Val Dé : " + plateau.getCase(i).getValDe().getValeur());
                costLabel = new Label("Coût : " + plateau.getCase(i).getCout());
                switch (plateau.getCase(i).getUprightColor()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayYellowButton");
                    default -> dayButton.getStyleClass().add("dayWhiteButton");
                }
            }
            VBox buttonWithLabels = new VBox(5);
            buttonWithLabels.setAlignment(Pos.CENTER);

            buttonWithLabels.getChildren().addAll(dayButton, valDeLabel, costLabel);
            gridPane.add(buttonWithLabels, i, 0);
        }

        root.setAlignment(Pos.CENTER);
        root.getChildren().add(gridPane);

        root.setPadding(new Insets(50, 0, 50, 0));

        return root;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Troyes Dice - Game");

        VBox root = mainGamePlate();
        VBox feuilleButton = feuilleButtonBox();

        root.getChildren().add(feuilleButton);
        Scene scene = new Scene(root, 1280, 720);

        scene.getStylesheets().add(getClass().getResource("/main_game.css").toExternalForm());
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