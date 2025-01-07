// FeuilleWindow.java
package main.java.window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.*;

public class FeuilleWindow extends Application implements FeuilleListener {

    private static final int CASE_SIZE = 45;
    private static final int RESOURCE_PANEL_SIZE = 20;
    private static Feuille feuille;
    private GridPane leftTable;
    private GridPane rightTable;
    private VBox enseignantPanel;
    private VBox AdministrationPanel;
    private VBox EtudiantPanel;


    public static void setFeuilleStatic(Feuille feuille) {
        FeuilleWindow.feuille = feuille;
    }

    @Override
    public void start(Stage primaryStage) {
        // Création du titre
        Label titleLabel = new Label("Feuille Joueur 1");
        titleLabel.getStyleClass().add("panel-title");

        // Création des panneaux principaux
        AdministrationPanel = createPanel("panel-administration");
        EtudiantPanel= createPanel("panel-etudiant");
        enseignantPanel = createPanel("panel-enseignant");

        // Réduire la largeur des panneaux
        AdministrationPanel.setMinWidth(200);
        EtudiantPanel.setMinWidth(200);
        enseignantPanel.setMinWidth(200);

        // Layout principal
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-alignment: center;");
        mainLayout.getChildren().addAll(titleLabel, AdministrationPanel, EtudiantPanel, enseignantPanel);

        HBox additionalContent = new HBox(10);
        additionalContent.setPadding(new Insets(10));
        additionalContent.setStyle("-fx-alignment: center;");

        // Tableau de 3x3 à gauche
        leftTable = new GridPane();
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
        feuille.addListener(this);
    }

    @Override
    public void onFeuilleUpdated(Feuille feuille) {
        System.out.println("Listener reçu");
        Platform.runLater(() -> updateFeuille(feuille));
    }

    // Méthode utilitaire pour créer un panneau
    private VBox createPanel(String cssClass) {
        VBox panel = new VBox();
        panel.getStyleClass().add(cssClass);
        panel.setStyle("-fx-alignment: center;");

        // Contenu du tableau avec images
        GridPane table = new GridPane();
        table.setAlignment(Pos.CENTER);
        table.setHgap(10); // Espace entre les colonnes
        table.setVgap(5);  // Espace entre les lignes

        // Image à gauche
        String imagePath = "";
        if (cssClass.equals("panel-administration")) {
            imagePath = "/pierre.png";
        } else if (cssClass.equals("panel-etudiant")) {
            imagePath = "/ECTS.png";
        } else if (cssClass.equals("panel-enseignant")) {
            imagePath = "/note.png";
        }
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);


        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < 3; i++) {
            Label imageSpace;
            if (cssClass.equals("panel-administration") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(getClass().getResourceAsStream("/tour_penchee.png")));
                specialImage.setFitWidth(30);
                specialImage.setFitHeight(30);
                imageSpace.setGraphic(specialImage);
            } else if (cssClass.equals("panel-etudiant") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(getClass().getResourceAsStream("/amphitheatre.png")));
                specialImage.setFitWidth(30);
                specialImage.setFitHeight(30);
                imageSpace.setGraphic(specialImage);
            } else if (cssClass.equals("panel-enseignant") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(getClass().getResourceAsStream("/laboratoire.png")));
                specialImage.setFitWidth(30);
                specialImage.setFitHeight(30);
                imageSpace.setGraphic(specialImage);
            } else {
                imageSpace = new Label("Image");
                imageSpace.setMinSize(30, 30); // Taille réduite
            }
            textBox.getChildren().add(imageSpace);
        }

        HBox row = new HBox(5);
        row.setAlignment(Pos.CENTER_LEFT);
        table.add(row, 0, 0, 1, 3); // Span the row across 3 rows

        // Cellules du tableau
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 6; j++) {
                Label cell;
                String key = cssClass + ":" + i;
                switch (key) {

                    case "panel-administration:1":
                        // Add images to the third row of the red panel
                        String image2 = "/tour-penchee-recompense.png";
                        ImageView cellImageView2 = new ImageView(new Image(getClass().getResourceAsStream(image2)));
                        cellImageView2.setFitWidth(CASE_SIZE);
                        cellImageView2.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView2);
                        break;

                    case "panel-administration:2":
                        // Add images to the third row of the red panel
                        String image3 = "/BatF1-Recompense.png";
                        ImageView cellImageView3 = new ImageView(new Image(getClass().getResourceAsStream(image3)));
                        cellImageView3.setFitWidth(CASE_SIZE);
                        cellImageView3.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView3);
                        break;

                    case "panel-etudiant:1":
                        // Add images to the second row of the yellow panel
                        String[] image4 = {"/deAdministration.png", "/deRouge.png", "/deEcts.png", "/deJaune.png", "/dePotion.png", "/deBlanc.png"};
                        ImageView cellImageView4 = new ImageView(new Image(getClass().getResourceAsStream(image4[j - 1])));
                        cellImageView4.setFitWidth(CASE_SIZE);
                        cellImageView4.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView4);
                        break;

                    case "panel-etudiant:2":
                        // Add images to the third row of the yellow panel
                        String image5 = "/BatF2-Recompense.png";
                        ImageView cellImageView5 = new ImageView(new Image(getClass().getResourceAsStream(image5)));
                        cellImageView5.setFitWidth(CASE_SIZE);
                        cellImageView5.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView5);
                        break;

                    case "panel-administration:0":
                    case "panel-enseignant:0":
                    case "panel-etudiant:0":
                        // Add images to the first row of the white panel
                        String[] image8 = {"/De/1-blanc.png", "/De/2-blanc.png", "/De/3-blanc.png", "/De/4-blanc.png", "/De/5-blanc.png", "/De/6-blanc.png"};
                        ImageView cellImageView8 = new ImageView(new Image(getClass().getResourceAsStream(image8[j-1])));
                        cellImageView8.setFitWidth(CASE_SIZE);
                        cellImageView8.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView8);
                        break;

                    case "panel-enseignant:2":
                        // Add images to the third row of the white panel
                        String image9 = "/BatF3-Recompense.png";
                        ImageView cellImageView9 = new ImageView(new Image(getClass().getResourceAsStream(image9)));
                        cellImageView9.setFitWidth(CASE_SIZE);
                        cellImageView9.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView9);
                        break;

                    default:
                        cell = new Label(" ");
                        break;
                }
                cell.getStyleClass().add("grid-cell");
                cell.setMinSize(CASE_SIZE, CASE_SIZE); // Taille réduite
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
            resource.setMinSize(RESOURCE_PANEL_SIZE, RESOURCE_PANEL_SIZE); // Taille réduite
            resources.getChildren().add(resource);
        }

        // Ajouter le titre, le tableau, l'espace et les ressources au panneau
        panel.getChildren().addAll(table, spacer, resources);
        return panel;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void updateFeuille(Feuille feuille) {
        System.out.println("Feuille mise à jour");
        // Update points
        //updatePoints(feuille.getNbPointEtudiant(), rightTable, 0);
        //updatePoints(feuille.getNbPointAdministration(), rightTable, 1);
        //updatePoints(feuille.getNbPointEnseignant(), rightTable, 2);

        // Update resources
        updateResources(feuille.getEtudiant(), EtudiantPanel);
        updateResources(feuille.getAdministration(), AdministrationPanel);
        updateResources(feuille.getEnseignant(), enseignantPanel);

        // Update buildings
        updateBuildings(feuille.getEtudiant(), EtudiantPanel, 0);
        updateBuildings(feuille.getAdministration(), AdministrationPanel, 1);
        updateBuildings(feuille.getEnseignant(), enseignantPanel, 2);
    }

    private void updatePoints(int points, GridPane table, int row) {
        for (int i = 0; i < 20; i++) {
            Label cell = (Label) table.getChildren().get(row * 20 + i);
            if (i < points) {
                cell.setText("●");
            } else {
                cell.setText(" ");
            }
        }
    }

    private void updateResources(Panel panel, VBox Panel) {
        HBox resources = (HBox) Panel.getChildren().get(2);
        Platform.runLater(() -> {
            for (int i = 0; i < 18; i++) {
                Label cell = (Label) resources.getChildren().get(i);
                if (i < panel.getRessource()) {
                    cell.setText("●");
                } else {
                    cell.setText(" ");
                }
            }
        });
    }

    private void updateBuildings(Panel panel, VBox Panel, int panelIndex) {
        GridPane table = (GridPane) Panel.getChildren().get(0);
        Platform.runLater(() -> {
            // Clear previous buildings
            for (int i = 0; i < table.getChildren().size(); i++) {
                if (table.getChildren().get(i) instanceof Label) {
                    ((Label) table.getChildren().get(i)).setText(" ");
                }
            }

            // Update function buildings in the second row
            for (int i = 0; i < panel.getBatimentsFonction().size(); i++) {
                BatimentFonction batiment = panel.getBatimentsFonction().get(i);
                Label cell = (Label) table.getChildren().get(7 + i); // Second row
                updateBuildingCell(batiment, cell);
            }

            // Update prestige buildings in the third row
            for (int i = 0; i < panel.getBatimentsPrestige().size(); i++) {
                BatimentPrestige batiment = panel.getBatimentsPrestige().get(i);
                Label cell = (Label) table.getChildren().get(13 + i); // Third row
                updateBuildingCell(batiment, cell);
            }
        });
    }

    private void updateBuildingCell(Batiment batiment, Label cell) {
        switch (batiment.getEtat()) {
            case CONSTRUIT:
                cell.setText("X");
                cell.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center;");
                break;
            case INCONSTRUCTIBLE:
                cell.setStyle("-fx-background-color: black;");
                break;
            case PROTEGE:
                cell.setStyle("-fx-background-color: blue;");
                break;
            default:
                cell.setStyle("");
                break;
        }
    }
}