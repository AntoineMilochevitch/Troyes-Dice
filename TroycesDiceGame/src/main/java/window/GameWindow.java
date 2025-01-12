package main.java.window;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
            feuilleButton.getStyleClass().add("feuilleButton"); // Apply feuilleButton style
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

        StackPane root = new StackPane();
        Group buttonGroup = new Group();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/utbm.png")));
        imageView.setFitWidth(300);
        imageView.setFitHeight(120);

        double radius = 250; // Radius of the circle
        double centerX = 300; // Center X of the circle
        double centerY = 300; // Center Y of the circle

        root.getChildren().add(imageView); // Add the image first

        int activeCaseCount = 0;
        for (int i = 0; i < 9; i++) {
            final int caseIndex = i;
            Case currentCase = plateau.getCase(i);
            Button dayButton = new Button("Case : " + (i + 1));

            if (currentCase.getDemiJournee() == demiJourneeActuelle && activeCaseCount < 4) {
                activeCaseCount++;
                Label valDeLabel = new Label("Val Dé : " + currentCase.getValDe().getValeur());
                Label costLabel = new Label("Coût : " + currentCase.getCout());

                switch (currentCase.getCouleurRecto()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayYellowButton");
                    default -> dayButton.getStyleClass().add("dayWhiteButton");
                }
                dayButton.setOnAction(e -> handleCaseSelection(caseIndex));

                double angle = 2 * Math.PI * i / 9; // Calculate angle for each button
                double x = centerX + radius * Math.cos(angle) - 50; // Calculate X position
                double y = centerY + radius * Math.sin(angle) - 50; // Calculate Y position

                dayButton.setLayoutX(x);
                dayButton.setLayoutY(y);

                valDeLabel.setLayoutX(x - 70); // Shift text to the left
                valDeLabel.setLayoutY(y + 30); // Position below the button

                costLabel.setLayoutX(x - 70); // Shift text to the left
                costLabel.setLayoutY(y + 50); // Position below the value label

                buttonGroup.getChildren().addAll(dayButton, valDeLabel, costLabel); // Add buttons and labels to the group
            } else {
                switch (currentCase.getCouleurRecto()) {
                    case ROUGE -> dayButton.getStyleClass().add("dayGrayRedButton");
                    case JAUNE -> dayButton.getStyleClass().add("dayGrayYellowButton");
                    default -> dayButton.getStyleClass().add("dayGrayWhiteButton");
                }
                dayButton.setOnAction(e -> handleCaseSelection(caseIndex));

                double angle = 2 * Math.PI * i / 9; // Calculate angle for each button
                double x = centerX + radius * Math.cos(angle) - 50; // Calculate X position
                double y = centerY + radius * Math.sin(angle) - 50; // Calculate Y position

                dayButton.setLayoutX(x);
                dayButton.setLayoutY(y);

                buttonGroup.getChildren().add(dayButton); // Add button to the group
            }
        }

        root.getChildren().add(buttonGroup); // Add the button group to the root
        VBox vbox = new VBox(root);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(50, 0, 50, 0));
        return vbox;
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
        actionScene.getStylesheets().add(getClass().getResource("/main_game.css").toExternalForm());

        Button buildBPButton = new Button("Construire un bâtiment de prestige");
        buildBPButton.getStyleClass().add("feuilleButton");
        buildBPButton.setOnAction(e -> handleActionSelection(() -> {
            currentPlayer.buildBP();
            game.notifyPlayerAction(); // Notify the Game class that the player has completed their action
            actionStage.close();
            changePlayer();
        }));

        Button buildBFButton = new Button("Construire un bâtiment de fonction");
        buildBFButton.getStyleClass().add("feuilleButton");
        buildBFButton.setOnAction(e -> handleActionSelection(() -> {
            currentPlayer.buildBF();
            game.notifyPlayerAction(); // Notify the Game class that the player has completed their action
            actionStage.close();
            changePlayer();
        }));

        Button getRessourceButton = new Button("Récolter des ressources");
        getRessourceButton.getStyleClass().add("feuilleButton");
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