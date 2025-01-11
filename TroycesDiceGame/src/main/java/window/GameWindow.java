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
import main.java.model.*;

import java.util.List;

public class GameWindow extends Application {

    private final List<Joueur> joueurs;
    private Game game;
    public Joueur currentPlayer;
    public Plateau plateau;
    private Stage primaryStage;
    private Label currentPlayerLabel;
    private Label currentDayLabel;

    public GameWindow(List<Joueur> joueurs, Plateau plateau, Game game, Stage primaryStage) { // Modify constructor
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.game = game; // Initialize game
        this.currentPlayer = joueurs.get(0); // Initialize currentPlayer with the first player
        this.primaryStage = primaryStage; // Initialize primaryStage
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
            final int caseIndex = i;
            Button dayButton = new Button("Case : " + (i + 1));
            Label valDeLabel = new Label("");
            Label costLabel = new Label("");

            if (plateau.getCase(i).getDemiJournee() != demiJourneeActuelle) {
                switch (plateau.getCase(i).getCouleurRecto()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayGrayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayGrayYellowButton");
                    default -> dayButton.getStyleClass().add("dayGrayWhiteButton");
                }
            } else {
                valDeLabel = new Label("Val Dé : " + plateau.getCase(i).getValDe().getValeur());
                costLabel = new Label("Coût : " + plateau.getCase(i).getCout());
                switch (plateau.getCase(i).getCouleurRecto()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayYellowButton");
                    default -> dayButton.getStyleClass().add("dayWhiteButton");
                }
                dayButton.setOnAction(e -> handleCaseSelection(caseIndex));
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

    private void handleCaseSelection(int caseIndex) {
        Case selectedCase = plateau.getCase(caseIndex);
        currentPlayer.setCaseChoisie(selectedCase);
        currentPlayer.setCouleurLocal(selectedCase.getCouleurRecto()); // Set couleurLocal
        currentPlayer.setValDeLocal(selectedCase.getValDe()); // Set valDeLocal
        Feuille feuille = currentPlayer.getFeuille();
        Couleur couleur = Couleur.JAUNE;
        feuille.utiliserRessource(couleur, selectedCase.getCout(),0);

        // Afficher les boutons d'action
        VBox actionBox = new VBox(10);
        actionBox.setAlignment(Pos.CENTER);

        Stage actionStage = new Stage();
        Scene actionScene = new Scene(actionBox, 300, 200);

        Button buildBPButton = new Button("Construire un bâtiment de prestige");
        buildBPButton.setOnAction(e -> handleActionSelection(() -> {
            currentPlayer.buildBP();
            game.notifyPlayerAction(); // Notify the Game class that the player has completed their action
            actionStage.close();
            changePlayer();
        }));

        Button buildBFButton = new Button("Construire un bâtiment de fonction");
        buildBFButton.setOnAction(e -> handleActionSelection(() -> {
            currentPlayer.buildBF();
            game.notifyPlayerAction(); // Notify the Game class that the player has completed their action
            actionStage.close();
            changePlayer();
        }));

        Button getRessourceButton = new Button("Récolter des ressources");
        getRessourceButton.setOnAction(e -> handleActionSelection(() -> {
            currentPlayer.getRessource();
            game.notifyPlayerAction(); // Notify the Game class that the player has completed their action
            actionStage.close();
            changePlayer();
        }));

        actionBox.getChildren().addAll(buildBPButton, buildBFButton, getRessourceButton);

        actionStage.setScene(actionScene);
        actionStage.show();
    }

    private void handleActionSelection(Runnable action) {
        action.run();
        updateGameWindow();
    }

    private void changePlayer() {
        int currentIndex = joueurs.indexOf(currentPlayer); // Find the current player's index
        int nextIndex = (currentIndex + 1) % joueurs.size(); // Calculate the next index, wrapping around using modulus
        currentPlayer = joueurs.get(nextIndex); // Set the current player to the next player
    }

    private void updateGameWindow() {
        // Mettre à jour l'interface graphique pour refléter les changements dans le modèle
        VBox root = mainGamePlate();
        VBox feuilleButton = feuilleButtonBox();
        root.getChildren().add(feuilleButton);

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/main_game.css").toExternalForm());

        currentPlayerLabel.setText("Joueur actuel : " + currentPlayer.getNom());
        currentDayLabel.setText("Jour actuel : " + plateau.getCompteurDemiJournee() / 2);
        primaryStage.setScene(scene); // Use the instance variable
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // Initialize primaryStage
        primaryStage.setTitle("Troyes Dice - Game");

        VBox root = mainGamePlate();
        VBox feuilleButton = feuilleButtonBox();

        currentPlayerLabel = new Label("Joueur actuel : " + currentPlayer.getNom());
        currentDayLabel = new Label("Jour actuel : " + plateau.getCompteurDemiJournee() / 2);

        root.getChildren().addAll(currentDayLabel, currentPlayerLabel, feuilleButtonBox());
        Scene scene = new Scene(root, 1280, 720);

        scene.getStylesheets().add(getClass().getResource("/main_game.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFeuilleWindow(Joueur joueur) {
        FeuilleWindow feuilleWindow = new FeuilleWindow();
        feuilleWindow.setFeuille(joueur.getFeuille(), joueur);
        if (!FeuilleWindow.isOpen()) {
            Stage feuilleStage = new Stage();
            try {
                feuilleWindow.start(feuilleStage);
                feuilleWindow.updateAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            feuilleWindow.updateAll();
        }
    }
}