package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    private Couleur couleur;
    private int ressource;
    private List<Batiment> batimentsPrestige;
    private List<Batiment> batimentsFonction;

    private int multiplicateur1;
    private int multiplicateur2;

    public Panel(){
        this.couleur = Couleur.VIDE;
        this.ressource = 0;
        // 2 administration, 2 etudiant, 2 enseignant
        this.batimentsPrestige = new ArrayList<>();
        this.batimentsPrestige.add(new BPAdministration());
        this.batimentsPrestige.add(new BPAdministration());
        this.batimentsPrestige.add(new BPEtudiant());
        this.batimentsPrestige.add(new BPEtudiant());
        this.batimentsPrestige.add(new BPEnseignant());
        this.batimentsPrestige.add(new BPEnseignant());
        this.batimentsFonction = new ArrayList<>();
        this.multiplicateur1 = 0;
        this.multiplicateur2 = 0;
    }

    public Panel(Couleur couleur, int ressource, List<Batiment> batimentsPrestige,List<Batiment> batimentsFonction, int multiplicateur1, int multiplicateur2) {
        this.couleur = couleur;
        this.ressource = ressource;
        this.batimentsPrestige = batimentsPrestige;
        this.batimentsFonction = batimentsFonction;
        this.multiplicateur1 = multiplicateur1;
        this.multiplicateur2 = multiplicateur2;
    }

    public int decomptePoints(){
        //TODO
        return 0;
    }

    public void detruireColonne(int col){
        batimentsFonction.get(col).rendreInconstructible();
        batimentsPrestige.get(col).rendreInconstructible();
    }

    public void protegerColonne(int col){
        batimentsPrestige.get(col).proteger();
        batimentsFonction.get(col).proteger();
    }

    public void buildBF(int valDe){
        if (batimentsFonction.get(valDe).getEtat() != Etat.INCONSTRUCTIBLE){
            return;
        }
        BatimentFonction bat = new BatimentFonction();
        bat.onBuild();
        batimentsFonction.add(valDe, bat);

    }

    public void buildBP(int valDe) {
        if (batimentsPrestige.get(valDe).getEtat() != Etat.INCONSTRUCTIBLE){
            return;
        }
        Batiment bat = batimentsPrestige.get(valDe);
        if (bat instanceof BPAdministration){
            ((BPAdministration) bat).buildBPAdministration(valDe);
        }
        else if (bat instanceof BPEtudiant){
            ((BPEtudiant) bat).buildBPEtudiant(valDe);
        }
        else if (bat instanceof BPEnseignant){
            ((BPEnseignant) bat).buildBPEnseignant(valDe);
        }
    }

    public void addRessource(int ressource){
        this.ressource += ressource;
    }
}
