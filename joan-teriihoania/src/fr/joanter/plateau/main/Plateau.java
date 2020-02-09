package fr.joanter.plateau.main;

import java.util.ArrayList;
import java.util.UUID;

public class Plateau {

    private String id;
    private ArrayList<Individu> individus;
    private Boolean[][] cases;

    // Coordonnées de la position initiale
    private int y;
    private int x;

    // Nombre de pas
    private int pas;

    // Nombre de pièces
    private int nbPieces;

    @Override
    public String toString() {
        return "Plateau{" +
                "id='" + id + '\'' +
                ", individus=" + individus +
                ", y=" + y +
                ", x=" + x +
                ", pas=" + pas +
                ", nbPieces=" + nbPieces +
                '}';
    }

    public int getPas() {
        return pas;
    }

    public Plateau(int pas, int nbPieces, int W, int nbIndividus) {
        this.cases = new Boolean[W][W];
        this.pas = pas;
        this.nbPieces = nbPieces;
        this.id = UUID.randomUUID().toString();
        this.individus = new ArrayList<>();

        for (int i = 0; i < nbIndividus; i++){
            Individu individu = new Individu(this);
            this.individus.add(individu);
        }
    }

}
