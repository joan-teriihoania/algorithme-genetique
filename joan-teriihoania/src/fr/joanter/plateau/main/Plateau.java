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
}
