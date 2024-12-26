package main.java.model;

public class PanelAdministration extends Panel{

    PanelAdministration(Feuille feuille) {
        super(feuille);
    }

    @Override
    void initLists() {
        for (int i = 1; i < 7; i++) {
            super.batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.ROUGE, 2));
        }

        super.batimentsPrestige.add(new BatimentPrestige(1, Effet.PROTEGER, Recompense.HABITANT, Couleur.ROUGE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(2, Effet.PROTEGER, Recompense.HABITANT, Couleur.ROUGE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(3, Effet.PROTEGER, Recompense.HABITANT, Couleur.JAUNE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(4, Effet.PROTEGER, Recompense.HABITANT, Couleur.JAUNE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(5, Effet.PROTEGER, Recompense.HABITANT, Couleur.BLANC, 1));
        super.batimentsPrestige.add(new BatimentPrestige(6, Effet.PROTEGER, Recompense.HABITANT, Couleur.BLANC, 1));
    }
}
