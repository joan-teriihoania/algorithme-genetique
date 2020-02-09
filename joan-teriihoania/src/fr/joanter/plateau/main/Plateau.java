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
                ", y=" + y +
                ", x=" + x +
                ", pas=" + pas +
                ", nbPieces=" + nbPieces +
                ", W =" + cases.length +
                ", individus=" + individus +
                '}';
    }

    public int getPas() {
        return pas;
    }

    public int getSize(){
        return this.cases.length;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
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
