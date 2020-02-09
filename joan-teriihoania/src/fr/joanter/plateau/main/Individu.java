package fr.joanter.plateau.main;

import java.util.UUID;

public class Individu {

    private String id;
    private Plateau plateau;
    private Mouvement moves;

    public Individu(Plateau plateau) {
        this.id = UUID.randomUUID().toString();
        this.plateau = plateau;
        this.moves = new Mouvement(this);
    }

    public int getPas(){
        return plateau.getPas();
    }
}
