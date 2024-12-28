package main.java.model;

import java.util.List;

public class BatimentPrestige extends Batiment {
    private final Effet effet;
    private final Recompense recompense;
    private final Couleur couleur;
    private final int nombre;


    public BatimentPrestige(int ID, Effet effet, Recompense recompense, Couleur couleur, int nombre) {
        super(ID);
        this.effet = effet;
        this.recompense = recompense;
        this.couleur = couleur;
        this.nombre = nombre;
    }

    // In BatimentPrestige.java
    public void appliquerEffet(De de, Panel panel, Feuille feuille, int nombreDesRouge, int nombreDesJaune, int nombreDesBlanc) {
        switch (effet) {
            case PROTEGER:
                feuille.getEtudiant().protegerColonne(de.getValeur() - 1);
                feuille.getAdministration().protegerColonne(de.getValeur() - 1);
                feuille.getEnseignant().protegerColonne(de.getValeur() - 1);
                break;
            case MULTIPLICATEUR:
                panel.ajouterMultiplicateur(de.getValeur());
                break;
            case RIEN:
                break;
            case GAGNERHAB:
                if (de.getValeur() % 2 == 1) { // Impair
                    switch (de.getValeur()){
                        case 1:
                            panel.addRessource(3 * nombreDesRouge);
                            break;
                        case 3:
                            panel.addRessource(3 * nombreDesJaune);
                            break;
                        case 5:
                            panel.addRessource(3 * nombreDesBlanc);
                            break;
                    }
                } else { // Pair
                    switch (de.getValeur()){
                        case 2:
                            feuille.addPoints(Couleur.ROUGE, 2 * nombreDesRouge);
                            break;
                        case 4:
                            feuille.addPoints(Couleur.JAUNE, 2 * nombreDesJaune);
                            break;
                        case 6:
                            feuille.addPoints(Couleur.BLANC, 2 * nombreDesBlanc);
                            break;
                    }
                }
                break;
        }
    }

    public Effet getEffet() {
        return effet;
    }

    public int getNombre() {
        return nombre;
    }

    public Recompense getRecompense() {
        return recompense;
    }

    public Couleur getCouleur() {
        return couleur;
    }
}