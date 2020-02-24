package fr.joanter.plateau.main;

import java.util.UUID;

public class Individu {

    private String id;
    private Plateau plateau;
    private Mouvement moves;

    @Override
    public String toString() {
        return "\nIndividu{" +
                "\nid='" + id + '\'' +
                ",\n moves=" + moves +
                ",\n eval=" + this.evaluate() +
                ",\n nbPiece=" + this.getNbPiece() +
                '}';
    }


    public int getNbPiece(){
        return moves.getNbPiece();
    }


    public Plateau getPlateau() {
        return plateau;
    }

    public String getId() {
        return id;
    }

    public int evaluate(){
        return moves.evaluate();
    }

    public Individu(Plateau plateau) {
        this.id = UUID.randomUUID().toString();
        this.plateau = plateau;
        this.moves = new Mouvement(this);
    }

    public int getPas(){
        return plateau.getPas();
    }

    public void croiser(Individu individu_croisement){
        moves.croiser(individu_croisement.moves);
    }

    public void muter(){
        moves.muter();
    }

}
