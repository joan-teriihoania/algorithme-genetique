package fr.joanter.plateau.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Mouvement {

    private String[] moves;
    private Individu individu;
    static private String[] possibleMove = {"H", "B", "G", "D"};

    @Override
    public String toString() {
        return "\nMouvement{" +
                "\nmoves=" + Arrays.toString(moves) +
                '}';
    }

    public String[] getMoves() {
        return moves;
    }

    public Mouvement(Individu individu) {
        this.individu = individu;
        this.moves = getRandomMoves(individu.getPas(), individu.getPlateau());
    }

    public Mouvement(String[] moves, Individu individu) {
        if (moves.length != individu.getPas()){
            System.out.println("Invalid size of String[] moves on constructor call parameter in Mouvement.");
            moves = new String[individu.getPas()];
        }
        this.moves = moves;
        this.individu = individu;
    }

    public int evaluate(){
        // Initialise visited cases map
        Boolean[][] visited_coor = new Boolean[this.individu.getPlateau().getSize()][this.individu.getPlateau().getSize()];
        for (int i = 0;i < visited_coor.length;i++){
            for (int j = 0;j < visited_coor.length;j++){
                visited_coor[i][j] = false;
            }
        }

        int x = this.individu.getPlateau().getX();
        int y = this.individu.getPlateau().getY();
        int capital = 0;
        // If case has piece and is not visited, add capital
        // If case has no piece or is visited, remove capital
        if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
            capital++;
        } else {
            capital--;
        }
        visited_coor[x][y] = true;
        for (String move: this.moves) {
            if (move.equals("H")){y++;}
            if (move.equals("B")){y--;}
            if (move.equals("G")){x--;}
            if (move.equals("D")){x++;}
            if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
                capital++;
            } else {
                capital--;
            }
            visited_coor[x][y] = true;
        }
        return capital;
    }

    private String[] getRandomMoves(int n, Plateau plateau){
        int x = plateau.getX();
        int y = plateau.getY();
        int size = plateau.getSize();
        int offset_x = 0;
        int offset_y = 0;
        String[] validMoves;
        String[] toreturn = new String[n];

        for (int i = 0;i < n;i++){
            // Réinitialise validMoves
            validMoves = new String[4];

            // Créé un array avec uniquement les mouvements valides
            if(y < size-1){validMoves[0] = "H";}
            if(y > 0){validMoves[1] = "B";}
            if(x < size-1){validMoves[2] = "D";}
            if(x > 0){validMoves[3] = "G";}
            validMoves = removeEmptyEl(validMoves);

            toreturn[i] = getRandomMove(validMoves);
            if(toreturn[i].equals("H")){offset_y = 1;}
            if(toreturn[i].equals("B")){offset_y = -1;}
            if(toreturn[i].equals("G")){offset_x = -1;}
            if(toreturn[i].equals("D")){offset_x = 1;}
            x += offset_x;
            y += offset_y;
            offset_x = 0;
            offset_y = 0;
        }
        return toreturn;
    }

    private String getRandomMove(String[] validMove){
        int ran = new Random().nextInt(validMove.length);
        return validMove[ran];
    }

    private String[] removeEmptyEl(String[] array){
        List<String> values = new ArrayList<>();
        for(String data: array) {
            if(data != null && !data.equals("")) {
                values.add(data);
            }
        }
        return values.toArray(new String[values.size()]);
    }
}
