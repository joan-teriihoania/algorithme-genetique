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

    public int evaluate(){
        // Initialise visited cases map
        Boolean[][] visited_coor = new Boolean[this.plateau.getSize()][this.plateau.getSize()];
        for (int i = 0;i < visited_coor.length;i++){
            for (int j = 0;j < visited_coor.length;j++){
                visited_coor[i][j] = false;
            }
        }

        int x = this.plateau.getX();
        int y = this.plateau.getY();
        int capital = 0;
        // If case has piece and is not visited, add capital
        // If case has no piece or is visited, remove capital
        if (this.plateau.caseHasPiece(x, y) && !visited_coor[x][y]){
            capital++;
        } else {
            capital--;
        }
        visited_coor[x][y] = true;
        for (String move: this.moves.getMoves()) {
            if (move.equals("H")){y++;}
            if (move.equals("B")){y--;}
            if (move.equals("G")){x--;}
            if (move.equals("D")){x++;}
            if (this.plateau.caseHasPiece(x, y) && !visited_coor[x][y]){
                capital++;
            } else {
                capital--;
            }
            visited_coor[x][y] = true;
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
