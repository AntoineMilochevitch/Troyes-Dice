# Troyes Dice

## Description
**Troyes Dice** est une version numérique du jeu de société *Troyes Dice*. Ce jeu repose sur des mécanismes de dés et de stratégie, et permet aux joueurs de prendre des décisions tactiques en utilisant des dés pour accomplir des actions.

Le projet est développé en Java et utilise **JavaFX** pour l'interface graphique.

## Prérequis

Avant de pouvoir exécuter le jeu, vous devez avoir les éléments suivants installés :

1. **Java JDK 21 ou version supérieure**  
   Vous pouvez télécharger la dernière version de Java à partir de [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) ou utiliser un OpenJDK tel que [AdoptOpenJDK](https://adoptium.net/).

2. **JavaFX SDK**  
   Le projet utilise JavaFX pour l'interface graphique. la bibliothèque JavaFX est déjà incluse dans le projet.

## Installation

1. Clonez le projet depuis GitHub :

   ```bash
   git clone <url_du_repository>
   ```
   
2. Accédez au répertoire du projet :

   ```bash
   cd troyes-dice
   ```
   
## Exécution du fichier JAR

1. Assurez-vous que vous avez Java JDK 21 ou version supérieure installé.

2. Exécutez le fichier JAR à l'aide de la commande suivante :

   ```bash
   java --module-path "openjfx-23.0.1_windows-x64_bin-sdk/javafx-sdk-23.0.1/lib" --add-modules javafx.controls,javafx.fxml -jar artifacts\Troyes-Dice.jar
   ```
   
3. Le jeu devrait se lancer et vous devriez voir l'interface graphique.

## Auteurs

- [Antoine Milochevitch](https://github.com/AntoineMilochevitch)
- [Mateo Chartier](https://github.com/Malgawin)
- [Mathis Fanigliulo](https://github.com/MathisFngl)
