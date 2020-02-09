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
                '}';
    }

    public Plateau getPlateau() {
        return plateau;
    }

    /*
    * WARN: Capital++ if individu touch the same piece multiple times
    * TODO: If piece is touched, it is not counted anymore by the evaluation
    * */
    public int evaluate(){
        int x = this.plateau.getX();
        int y = this.plateau.getY();
        int capital = 0;
        if (this.plateau.caseHasPiece(x, y)){
            capital++;
        } else {
            capital--;
        }
        for (String move: this.moves.getMoves()) {
            if (move.equals("H")){y++;}
            if (move.equals("B")){y--;}
            if (move.equals("G")){x--;}
            if (move.equals("D")){x++;}
            if (this.plateau.caseHasPiece(x, y)){
                capital++;
            } else {
                capital--;
            }
        }
        return capital;
    }

    public Individu(Plateau plateau) {
        this.id = UUID.randomUUID().toString();
        this.plateau = plateau;
        this.moves = new Mouvement(this);
    }

    public int getPas(){
        return plateau.getPas();
    }
}
