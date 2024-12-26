package main.java.model;

public class PanelEtudiant extends Panel{
    
    PanelEtudiant(Feuille feuille) {
        super(feuille);
    }

    @Override
    void initLists() {
        for (int i = 1; i < 7; i++) {
            super.batimentsFonction.add(new BatimentFonction(i, Recompense.HABITANT, Couleur.JAUNE, 2));
        }

        super.batimentsPrestige.add(new BatimentPrestige(1, Effet.RIEN, Recompense.RESSOURCE, Couleur.ROUGE, 3));
        super.batimentsPrestige.add(new BatimentPrestige(2, Effet.RIEN, Recompense.HABITANT, Couleur.ROUGE, 2));
        super.batimentsPrestige.add(new BatimentPrestige(3, Effet.RIEN, Recompense.RESSOURCE, Couleur.JAUNE, 3));
        super.batimentsPrestige.add(new BatimentPrestige(4, Effet.RIEN, Recompense.HABITANT, Couleur.JAUNE, 2));
        super.batimentsPrestige.add(new BatimentPrestige(5, Effet.RIEN, Recompense.RESSOURCE, Couleur.BLANC, 3));
        super.batimentsPrestige.add(new BatimentPrestige(6, Effet.RIEN, Recompense.HABITANT, Couleur.BLANC, 2));
    }
}
