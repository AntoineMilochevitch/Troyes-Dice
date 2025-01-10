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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FeuilleWindow extends Application implements FeuilleListener {

    private static final int CASE_SIZE = 45;
    private static final int RESOURCE_PANEL_SIZE = 20;
    private static FeuilleWindow instance;

    private Feuille feuille;
    private Joueur joueur;
    private static boolean isOpen = false;

    private String playerName;
    private GridPane leftTable;
    private static GridPane rightTable;

    private static Map<String, Label> multiplierLabels = new HashMap<>();
    private static Map<String, Label> valueLabels = new HashMap<>();

    private static GridPane enseignant;
    private static GridPane administration;
    private static GridPane etudiant;
    private static VBox enseignantPanel;
    private static VBox AdministrationPanel;
    private static VBox EtudiantPanel;

    public void setFeuille(Feuille feuille, Joueur joueur) {
        this.feuille = feuille;
        this.joueur = joueur;
        feuille.addListener(this);
    }

    public static boolean isOpen() {
        return isOpen;
    }

    public static FeuilleWindow getInstance() {
        if (instance == null) {
            instance = new FeuilleWindow();
        }
        return instance;
    }

    public static void updateFeuilleStatic(Feuille feuille) {
        if (instance != null) {
            instance.updateFeuille(feuille);
        }
    }
    @Override
    public void start(Stage primaryStage) {
        instance = this;
        isOpen = true;
        primaryStage.setOnCloseRequest(event -> {
            FeuilleWindow.isOpen = false;
        });
        // Use instance variables instead of static ones
        Label titleLabel = new Label("Feuille de " + playerName);
        titleLabel.getStyleClass().add("panel-title");

        // Création des panneaux principaux
        AdministrationPanel = createPanel("panel-administration");
        EtudiantPanel = createPanel("panel-etudiant");
        enseignantPanel = createPanel("panel-enseignant");

        // Réduire la largeur des panneaux
        AdministrationPanel.setMinWidth(200);
        EtudiantPanel.setMinWidth(200);
        enseignantPanel.setMinWidth(200);

        //Include les Panels dans leur Grid Respectif.
        administration = new GridPane();
        etudiant = new GridPane();
        enseignant = new GridPane();

        GridPane adminGrid = create3x3Grid(Couleur.ROUGE);
        GridPane etudiantGrid = create3x3Grid(Couleur.JAUNE);
        GridPane enseignantGrid = create3x3Grid(Couleur.BLANC);

        administration.add(AdministrationPanel, 0, 0);
        administration.add(adminGrid, 1, 0);

        etudiant.add(EtudiantPanel, 0, 0);
        etudiant.add(etudiantGrid, 1, 0);

        enseignant.add(enseignantPanel, 0, 0);
        enseignant.add(enseignantGrid, 1, 0);

        // Layout principal
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));
        mainLayout.setStyle("-fx-alignment: center;");
        mainLayout.getChildren().addAll(titleLabel, administration, etudiant, enseignant);

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
        rightTable = new GridPane();
        rightTable.setAlignment(Pos.CENTER);
        String[] colors = {"red", "yellow", "white"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 20; j++) {
                Label cell = new Label(" ");
                cell.getStyleClass().add("grid-cell");
                cell.getStyleClass().add("pion-" + colors[i]);
                cell.setMinSize(20, 25); // Taille réduite
                rightTable.add(cell, j, i);
            }
        }

        GridPane subtotal = new GridPane();
        GridPane totalTable = new GridPane();
        totalTable.setAlignment(Pos.CENTER);
        subtotal.setAlignment(Pos.CENTER);
        for (int i = 0; i < 2; i++) {
            Label cell = new Label("0");
            cell.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
            cell.setMaxWidth(Double.MAX_VALUE);
            cell.setMaxHeight(Double.MAX_VALUE);
            cell.setAlignment(Pos.CENTER);
            cell.getStyleClass().add("grid-cell");
            String key = ("TOTAL_" + i);
            valueLabels.put(key, cell);
            cell.setMinSize(CASE_SIZE*0.75, CASE_SIZE*0.75);
            subtotal.add(cell, i, 0);
        }
        Label cell = new Label("1");
        cell.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
        cell.setMaxWidth(Double.MAX_VALUE);
        cell.setMaxHeight(Double.MAX_VALUE);
        cell.setAlignment(Pos.CENTER);
        cell.getStyleClass().add("grid-cell");
        String key = ("GRAND_TOTAL");
        valueLabels.put(key, cell);
        cell.setMinSize(CASE_SIZE*1.5, CASE_SIZE*1.5);
        totalTable.add(subtotal, 0, 0);
        totalTable.add(cell, 0, 1);

        additionalContent.getChildren().addAll(leftTable, rightTable, totalTable);
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
        System.out.println("this.feuille : " + feuille);
        feuille.addListener(this);
        updateAll(); // Update all information when the window is opened
    }

    @Override
    public void onFeuilleUpdated(Feuille feuille) {
        if (this.feuille == feuille) {
            System.out.println("Feuille mise à jour");
            Platform.runLater(() -> this.updateFeuille(feuille));
        }
    }

    public void updateAll() {
        updateFeuille(feuille);
        updateValues();
        updateMultipliers();
    }

    private GridPane create3x3Grid(Couleur couleur) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setStyle("-fx-alignment: center;");
        grid.setPadding(new Insets(10));

        // Adding placeholders or components to the grid
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(row != 2 || col != 2) {
                    VBox box = new VBox();
                    box.setMinSize(CASE_SIZE, CASE_SIZE);  // Example size
                    switch (couleur){
                        case ROUGE -> box.setStyle("-fx-border-color: black; -fx-background-color: #ff6666;");
                        case JAUNE -> box.setStyle("-fx-border-color: black; -fx-background-color: #ffff99;");
                        case BLANC -> box.setStyle("-fx-border-color: black; -fx-background-color: #ffffff;");
                    }

                    if(col == 2 && row == 0){
                        Label imageSpace = new Label();
                        ImageView specialImage;
                        switch (couleur){
                            case ROUGE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/administration_1.png"))));
                            case JAUNE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/etudiant-1.png"))));
                            case BLANC -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/enseignant-1.png"))));
                            default -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/etudiant-2.png"))));
                        }
                        specialImage.setFitWidth(CASE_SIZE);
                        specialImage.setFitHeight(CASE_SIZE);
                        imageSpace.setGraphic(specialImage);
                        box.getChildren().add(imageSpace);
                    }
                    if(col == 2 && row == 1){
                        Label imageSpace = new Label();
                        ImageView specialImage;
                        switch (couleur){
                            case ROUGE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/administration-2.png"))));
                            case JAUNE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/etudiant-2.png"))));
                            case BLANC -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/enseignant-2.png"))));
                            default -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/etudiant-2.png"))));
                        }
                        specialImage.setFitWidth(CASE_SIZE);
                        specialImage.setFitHeight(CASE_SIZE);
                        imageSpace.setGraphic(specialImage);
                        box.getChildren().add(imageSpace);
                    }
                    if(col == 0 && row == 2){
                        Label imageSpace = new Label();
                        ImageView specialImage;
                        switch (couleur){
                            case ROUGE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Rec1.png"))));
                            case JAUNE -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Rec2.png"))));
                            case BLANC -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Rec3.png"))));
                            default -> specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Rec1.png"))));
                        }
                        box.setStyle("");
                        specialImage.setFitWidth(CASE_SIZE);
                        specialImage.setFitHeight(CASE_SIZE);
                        imageSpace.setGraphic(specialImage);
                        box.getChildren().add(imageSpace);
                    }
                    if(col == 0 && row == 0){
                        Label multiplier = new Label();
                        multiplier.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
                        multiplier.setMaxWidth(Double.MAX_VALUE);
                        multiplier.setMaxHeight(Double.MAX_VALUE);
                        multiplier.setAlignment(Pos.CENTER);
                        multiplier.setText("X 0");
                        String key = couleur.name() + "_MULTIPLIER1";
                        multiplierLabels.put(key, multiplier);
                        VBox.setVgrow(multiplier, Priority.ALWAYS);
                        box.getChildren().add(multiplier);
                    }

                    if(col == 1 && row == 0){
                        Label multiplier = new Label();
                        multiplier.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
                        multiplier.setMaxWidth(Double.MAX_VALUE);
                        multiplier.setMaxHeight(Double.MAX_VALUE);
                        multiplier.setAlignment(Pos.CENTER);
                        multiplier.setText("0");
                        String key = couleur.name() + "_VALUE1";
                        valueLabels.put(key, multiplier);
                        VBox.setVgrow(multiplier, Priority.ALWAYS);
                        box.getChildren().add(multiplier);
                    }

                    if(col == 1 && row == 1){
                        Label multiplier = new Label();
                        multiplier.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
                        multiplier.setMaxWidth(Double.MAX_VALUE);
                        multiplier.setMaxHeight(Double.MAX_VALUE);
                        multiplier.setAlignment(Pos.CENTER);
                        multiplier.setText("0");
                        String key = couleur.name() + "_VALUE2";
                        valueLabels.put(key, multiplier);
                        VBox.setVgrow(multiplier, Priority.ALWAYS);
                        box.getChildren().add(multiplier);
                    }

                    if(col == 0 && row == 1){
                        Label multiplier = new Label();
                        multiplier.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
                        multiplier.setMaxWidth(Double.MAX_VALUE);
                        multiplier.setMaxHeight(Double.MAX_VALUE);
                        multiplier.setAlignment(Pos.CENTER);
                        multiplier.setText("X 0");
                        String key = couleur.name() + "_MULTIPLIER2";
                        multiplierLabels.put(key, multiplier);
                        VBox.setVgrow(multiplier, Priority.ALWAYS);
                        box.getChildren().add(multiplier);
                    }

                    if(col == 1 && row == 2){
                        Label multiplier = new Label();
                        multiplier.setStyle("-fx-alignment: center; -fx-font-size: 16; -fx-font-weight: bold;");
                        multiplier.setMaxWidth(Double.MAX_VALUE);
                        multiplier.setMaxHeight(Double.MAX_VALUE);
                        multiplier.setAlignment(Pos.CENTER);
                        multiplier.setText("0");
                        String key = couleur.name() + "_TOTAL";
                        valueLabels.put(key, multiplier);
                        VBox.setVgrow(multiplier, Priority.ALWAYS);
                        box.getChildren().add(multiplier);
                    }
                    grid.add(box, col, row);
                }
            }
        }
        return grid;
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
            imagePath = "/etudiant-2.png";
        } else if (cssClass.equals("panel-etudiant")) {
            imagePath = "/ECTS.png";
        } else if (cssClass.equals("panel-enseignant")) {
            imagePath = "/note.png";
        }
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < 3; i++) {
            Label imageSpace;
            if (cssClass.equals("panel-administration") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/tour_penchee.png"))));
                specialImage.setFitWidth(30);
                specialImage.setFitHeight(30);
                imageSpace.setGraphic(specialImage);
            } else if (cssClass.equals("panel-etudiant") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/amphitheatre.png"))));
                specialImage.setFitWidth(30);
                specialImage.setFitHeight(30);
                imageSpace.setGraphic(specialImage);
            } else if (cssClass.equals("panel-enseignant") && i == 1) {
                imageSpace = new Label();
                ImageView specialImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/laboratoire.png"))));
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
                        ImageView cellImageView3 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image3))));
                        cellImageView3.setFitWidth(CASE_SIZE);
                        cellImageView3.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView3);
                        break;

                    case "panel-etudiant:1":
                        // Add images to the second row of the yellow panel
                        String[] image4 = {"/deAdministration.png", "/deRouge.png", "/deEcts.png", "/deJaune.png", "/dePotion.png", "/deBlanc.png"};
                        ImageView cellImageView4 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image4[j - 1]))));
                        cellImageView4.setFitWidth(CASE_SIZE);
                        cellImageView4.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView4);
                        break;

                    case "panel-etudiant:2":
                        // Add images to the third row of the yellow panel
                        String image5 = "/BatF2-Recompense.png";
                        ImageView cellImageView5 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image5))));
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

                    case "panel-enseignant:1":
                        // Add images to the second row of the yellow panel
                        String[] image7 = {"/administration_1.png", "/administration-2.png", "/etudiant-1.png", "/etudiant-2.png", "/enseignant-1.png", "/enseignant-2.png"};
                        System.out.println(image7[j-1]);
                        ImageView cellImageView7 = new ImageView(new Image(getClass().getResourceAsStream(image7[j-1])));
                        cellImageView7.setFitWidth(CASE_SIZE);
                        cellImageView7.setFitHeight(CASE_SIZE);
                        cell = new Label();
                        cell.setGraphic(cellImageView7);
                        break;

                    case "panel-enseignant:2":
                        // Add images to the third row of the white panel
                        String image9 = "/BatF3-Recompense.png";
                        ImageView cellImageView9 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(image9))));
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
        spacer.setMinHeight(RESOURCE_PANEL_SIZE/3); // Hauteur de l'espace

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

    void updateFeuille(Feuille feuille) {
        System.out.println("Feuille mise à jour");
        // Update points
        updatePoints(feuille.getNbPointEtudiant(), rightTable, 0);
        updatePoints(feuille.getNbPointAdministration(), rightTable, 1);
        updatePoints(feuille.getNbPointEnseignant(), rightTable, 2);

        // Update resources
        updateResources(feuille.getEtudiant(), EtudiantPanel);
        updateResources(feuille.getAdministration(), AdministrationPanel);
        updateResources(feuille.getEnseignant(), enseignantPanel);

        // Update buildings
        updateBuildings(feuille.getEtudiant(), EtudiantPanel);
        updateBuildings(feuille.getAdministration(), AdministrationPanel);
        updateBuildings(feuille.getEnseignant(), enseignantPanel);

        // Call non-static methods using an instance
        this.updateValues();
        this.updateMultipliers();
    }

    private static void updatePoints(int points, GridPane table, int row) {
        for (int i = 0; i < 20; i++) {
            Label cell = (Label) table.getChildren().get(row * 20 + i);
            if (i < points) {
                cell.setText("○");
            } else {
                cell.setText(" ");
            }
        }
    }

    private static void updateResources(Panel panel, VBox Panel) {
        HBox resources = (HBox) Panel.getChildren().get(2);
        Platform.runLater(() -> {
            for (int i = 0; i < 18; i++) {
                Label cell = (Label) resources.getChildren().get(i);
                if (i < panel.getRessource()) {
                    cell.setText("○");
                } else {
                    cell.setText(" ");
                }
                cell.setStyle("-fx-alignment: center; -fx-font-size: 25px;"); // Adjust font size as needed
            }
        });
    }

    private static void updateBuildings(Panel panel, VBox Panel) {
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
                Label cell = (Label) table.getChildren().get(13 + i); // Second row
                updateBuildingCell(batiment, cell);
            }

            // Update prestige buildings in the third row
            for (int i = 0; i < panel.getBatimentsPrestige().size(); i++) {
                BatimentPrestige batiment = panel.getBatimentsPrestige().get(i);
                Label cell = (Label) table.getChildren().get(7 + i); // Third row
                updateBuildingCell(batiment, cell);
            }
        });
    }

    private void updateMultipliers() {
        updateMultiplierLabel("ROUGE_MULTIPLIER1", feuille.getMultiplier(Couleur.ROUGE, 1));
        updateMultiplierLabel("ROUGE_MULTIPLIER2", feuille.getMultiplier(Couleur.ROUGE, 2));
        updateMultiplierLabel("JAUNE_MULTIPLIER1", feuille.getMultiplier(Couleur.JAUNE, 1));
        updateMultiplierLabel("JAUNE_MULTIPLIER2", feuille.getMultiplier(Couleur.JAUNE, 2));
        updateMultiplierLabel("BLANC_MULTIPLIER1", feuille.getMultiplier(Couleur.BLANC, 1));
        updateMultiplierLabel("BLANC_MULTIPLIER2", feuille.getMultiplier(Couleur.BLANC, 2));
    }

    private static void updateMultiplierLabel(String key, int newValue) {
        Label label = multiplierLabels.get(key);
        if (label != null) {
            label.setText("X " + newValue);
        }
    }

    private void updateValues() {
        updateValueLabel("ROUGE_VALUE1", feuille.getAdministration().nbBatimentPrestige() * feuille.getMultiplier(Couleur.ROUGE, 1));
        updateValueLabel("ROUGE_VALUE2", feuille.getAdministration().nbBatimentFonction() * feuille.getMultiplier(Couleur.ROUGE, 2));
        updateValueLabel("JAUNE_VALUE1", feuille.getEtudiant().nbBatimentPrestige() * feuille.getMultiplier(Couleur.JAUNE, 1));
        updateValueLabel("JAUNE_VALUE2", feuille.getEtudiant().nbBatimentFonction() * feuille.getMultiplier(Couleur.JAUNE, 2));
        updateValueLabel("BLANC_VALUE1", feuille.getEnseignant().nbBatimentFonction() * feuille.getMultiplier(Couleur.BLANC, 1));
        updateValueLabel("BLANC_VALUE2", feuille.getEnseignant().nbBatimentFonction() * feuille.getMultiplier(Couleur.BLANC, 2));

        updateValueLabel("ROUGE_TOTAL", feuille.getAdministration().getRessource() / 2);
        updateValueLabel("JAUNE_TOTAL", feuille.getEtudiant().getRessource() / 2);
        updateValueLabel("BLANC_TOTAL", feuille.getEnseignant().getRessource() / 2);

        System.out.println(feuille.calculerPoints());

        updateValueLabel("TOTAL_0", feuille.calculerHabitantsPoints());
        updateValueLabel("TOTAL_1", feuille.calculerSousTotPoints());
        updateValueLabel("GRAND_TOTAL", feuille.calculerPoints());
    }

    private static void updateValueLabel(String key, int newValue) {
        Label label = valueLabels.get(key);
        if (label != null) {
            label.setText(String.valueOf(newValue));
        }
    }

    private static void updateBuildingCell(Batiment batiment, Label cell) {
        switch (batiment.getEtat()) {
            case CONSTRUIT:
                cell.setStyle("-fx-background-color: #63ff63;");
                break;
            case INCONSTRUCTIBLE:
                cell.setStyle("-fx-background-color: #1c1c1c;");
                break;
            case PROTEGE:
                cell.setStyle("-fx-background-color: #6161ff;");
                break;
            default:
                cell.setStyle("");
                break;
        }
    }
}