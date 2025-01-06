package main.java.window;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FeuilleWindow extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Création du titre
        Label titleLabel = new Label("Feuille Joueur 1");
        titleLabel.getStyleClass().add("panel-title");

        // Création des panneaux principaux
        VBox buildingsPanel = createPanel("Administration", "panel-buildings");
        VBox citizensPanel = createPanel("Elèves", "panel-citizens");
        VBox resourcesPanel = createPanel("Enseignants", "panel-resources");

        // Réduire la largeur des panneaux
        buildingsPanel.setMinWidth(200);
        citizensPanel.setMinWidth(200);
        resourcesPanel.setMinWidth(200);

        // Layout principal
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-alignment: center;");
        mainLayout.getChildren().addAll(titleLabel, buildingsPanel, citizensPanel, resourcesPanel);

        HBox additionalContent = new HBox(10);
        additionalContent.setPadding(new Insets(10));
        additionalContent.setStyle("-fx-alignment: center;");

        // Tableau de 3x3 à gauche
        GridPane leftTable = new GridPane();
        leftTable.setAlignment(Pos.CENTER);
        int[][] numbers = {{1, 3, 5}, {2, 4, 6}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label cell;
                if (i < 2) {
                    cell = new Label(String.valueOf(numbers[i][j]));
                } else {
                    cell = new Label("x" + (j + 1));
                }
                cell.getStyleClass().add("grid-cell");
                cell.setMinSize(25, 25); // Taille réduite
                leftTable.add(cell, j, i);
            }
        }

        // Tableau de 3x20 à droite
        GridPane rightTable = new GridPane();
        rightTable.setAlignment(Pos.CENTER);
        String[] colors = {"red", "yellow", "white"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                Label cell = new Label(" ");
                cell.getStyleClass().add("grid-cell");
                cell.getStyleClass().add("pion-" + colors[i]);
                cell.setMinSize(25, 25); // Taille réduite
                rightTable.add(cell, j, i);
            }
        }

        additionalContent.getChildren().addAll(leftTable, rightTable);
        mainLayout.getChildren().add(additionalContent);

        // Style global
        mainLayout.getStyleClass().add("root");

        // Scène
        Scene scene = new Scene(mainLayout, 680, 800); // Dimensions ajustées
        scene.getStylesheets().add(getClass().getResource("/feuille.css").toExternalForm());

        // Configuration de la fenêtre
        primaryStage.setTitle("Feuille de jeu - Troyes Dice");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode utilitaire pour créer un panneau
    private VBox createPanel(String title, String cssClass) {
        VBox panel = new VBox();
        panel.getStyleClass().add(cssClass);
        panel.setStyle("-fx-alignment: center;");

        // Titre du panneau
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("panel-title");

        // Contenu du tableau avec images
        GridPane table = new GridPane();
        table.setAlignment(Pos.CENTER);
        table.setHgap(10); // Espace entre les colonnes
        table.setVgap(5);  // Espace entre les lignes

        // Image à gauche
        String imagePath = "";
        if (cssClass.equals("panel-buildings")) {
            imagePath = "/pierre.png";
        } else if (cssClass.equals("panel-citizens")) {
            imagePath = "/ECTS.png";
        } else if (cssClass.equals("panel-resources")) {
            imagePath = "/note.png";
        }
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        VBox imageBox = new VBox(imageView);
        imageBox.setAlignment(Pos.CENTER);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < 3; i++) {
            Label imageSpace = new Label("Image");
            imageSpace.setMinSize(30, 30); // Taille réduite
            textBox.getChildren().add(imageSpace);
        }

        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getChildren().addAll(imageBox, textBox);
        table.add(row, 0, 0, 1, 3); // Span the row across 3 rows

        // Cellules du tableau
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 6; j++) {
                Label cell;
                if (cssClass.equals("panel-citizens") && i == 1) {
                    // Add images to the second row of the yellow panel
                    String[] imagePaths = {"/deAdministration.png", "/deRouge.png", "/deEcts.png", "/deJaune.png", "/dePotion.png", "/deBlanc.png"};
                    ImageView cellImageView = new ImageView(new Image(getClass().getResourceAsStream(imagePaths[j - 1])));
                    cellImageView.setFitWidth(30);
                    cellImageView.setFitHeight(30);
                    cell = new Label();
                    cell.setGraphic(cellImageView);
                } else {
                    cell = new Label(" ");
                }
                cell.getStyleClass().add("grid-cell");
                cell.setMinSize(30, 30); // Taille réduite
                if (i == 1 && j == 6) {
                    cell.getStyleClass().add("special-cell");
                }
                table.add(cell, j, i);
            }
        }

        // Espace entre le tableau et les ressources
        VBox spacer = new VBox();
        spacer.setMinHeight(20); // Hauteur de l'espace

        // Contenu des petites ressources
        HBox resources = new HBox();
        resources.setSpacing(5);
        resources.setStyle("-fx-alignment: center;");
        for (int i = 0; i < 18; i++) {
            Label resource = new Label(" ");
            resource.getStyleClass().add("resource-cell");
            resource.setMinSize(20, 20); // Taille réduite
            resources.getChildren().add(resource);
        }

        // Ajouter le titre, le tableau, l'espace et les ressources au panneau
        panel.getChildren().addAll(titleLabel, table, spacer, resources);
        return panel;
    }

    public static void main(String[] args) {
        launch(args);
    }
}