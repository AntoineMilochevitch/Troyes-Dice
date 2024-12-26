package main.java.model;

public class PanelEnseignant extends Panel{

    PanelEnseignant(Feuille feuille) {
        super(feuille);
    }

    @Override
    void initLists() {
        for (int i = 1; i < 7; i++) {
            super.batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.BLANC, 2));
        }

        // Si nombre = 1, prend effet sur le multiplicateur 1, si nombre = 2 prend effet sur le multiplicateur 2
        super.batimentsPrestige.add(new BatimentPrestige(1, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.ROUGE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(2, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.ROUGE, 2));
        super.batimentsPrestige.add(new BatimentPrestige(3, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.JAUNE, 1));
        super.batimentsPrestige.add(new BatimentPrestige(4, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.JAUNE, 2));
        super.batimentsPrestige.add(new BatimentPrestige(5, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.BLANC, 1));
        super.batimentsPrestige.add(new BatimentPrestige(6, Effet.MULTIPLICATEUR, Recompense.RIEN, Couleur.BLANC, 2));
    }
}
