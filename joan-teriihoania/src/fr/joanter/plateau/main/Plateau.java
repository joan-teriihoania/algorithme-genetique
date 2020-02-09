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
    private int n;

    // Nombre de pièces
    private int nbPieces;

    public Plateau(int n, int nbPieces, int W, int nbIndividus) {
        this.cases = new Boolean[W][W];
        this.n = n;
        this.nbPieces = nbPieces;
        this.id = UUID.randomUUID().toString();

        for (int i = 0; i < nbIndividus; i++){
            Individu individu = new Individu(this);
            this.individus.add(individu);
        }
    }
}
