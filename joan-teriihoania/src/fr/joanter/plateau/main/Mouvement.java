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

    public String[] getNextValidMoves(String[] moves, int x, int y, int size, int n){
        String[] validMoves;

        for (int i = 0;i < n;i++){
            if(moves[i] != null) {
                if(moves[i].equals("H")){y--;}
                if(moves[i].equals("B")){y++;}
                if(moves[i].equals("G")){x--;}
                if(moves[i].equals("D")){x++;}
            }
        }

        validMoves = new String[4];
        if(y < size-1){validMoves[0] = "B";}
        if(y != 0){validMoves[1] = "H";}
        if(x < size-1){validMoves[2] = "D";}
        if(x > 0){validMoves[3] = "G";}
        validMoves = removeEmptyEl(validMoves);

        return validMoves;
    }

    public Boolean nextMoveIsValid(String move, String[] moves, int x, int y, int size, int n){
        String[] validMoves = getNextValidMoves(moves, x, y, size, n);
        for (String validMove: validMoves){
            if(validMove.equals(move)) {return true;}
        }
        return false;
    }

    public static<T> T[] subArray(T[] array, int beg, int end) {
        return Arrays.copyOfRange(array, beg, end + 1);
    }

    public Boolean isValid(){
        int x = this.individu.getPlateau().getX();
        int y = this.individu.getPlateau().getY();
        int size = this.individu.getPlateau().getSize();
        int n = this.individu.getPas();

        for (int i = 0;i < this.moves.length;i++){
            if(!nextMoveIsValid(moves[i], subArray(moves, 0, i), x, y, size, n)){
                return false;
            }
        }
        return true;
    }

    public void muter(){
        double mutate_chance = 0.05;
        double ran;
        for (int i = 0;i < moves.length;i++){
            ran = new Random().nextDouble();
            if(ran<mutate_chance){

                String[] save_moves = new String[moves.length];
                for (int j = 0;j < moves.length;j++){
                    save_moves[j] = moves[j];
                }

                String[] validMoves = getNextValidMoves(subArray(moves, 0, i), this.individu.getPlateau().getX(), this.individu.getPlateau().getY(), this.individu.getPlateau().getSize(), i);
                moves[i] = getRandomMove(validMoves);
            }
        }
    }

    public void croiser(Mouvement croisement_moves){
        int move_choosen;
        for (int i = 0; i < croisement_moves.moves.length;i++){
            move_choosen = new Random().nextInt(1);
                if(move_choosen == 0){
                    moves[i] = croisement_moves.moves[i];
                } else {
                    croisement_moves.moves[i] = moves[i];
                }
        }
    }

    public int getNbPiece(){
        // Initialise visited cases map
        Boolean[][] visited_coor = new Boolean[this.individu.getPlateau().getSize()][this.individu.getPlateau().getSize()];
        for (int i = 0;i < visited_coor.length;i++){
            for (int j = 0;j < visited_coor.length;j++){
                visited_coor[i][j] = false;
            }
        }

        int x = this.individu.getPlateau().getX();
        int y = this.individu.getPlateau().getY();
        int nbPiece = 0;
        // If case has piece and is not visited, add capital
        // If case has no piece or is visited, remove capital
        if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
            nbPiece++;
        }

        visited_coor[x][y] = true;
        for (String move: this.moves) {
            if(move.equals("H")){
                if(this.individu.getPlateau().caseExist(x, y-1)){
                    y--;
                }
            }
            if(move.equals("B")) {
                if(this.individu.getPlateau().caseExist(x, y+1)){
                    y++;
                }
            }
            if (move.equals("G")) {
                if(this.individu.getPlateau().caseExist(x-1, y)){
                    x--;
                }
            }
            if (move.equals("D")){
                if(this.individu.getPlateau().caseExist(x+1, y)){
                    x++;
                }
            }
            if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
                nbPiece++;
            }
            visited_coor[x][y] = true;
        }
        return nbPiece;
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
        int nbPiece = 0;
        // If case has piece and is not visited, add capital
        // If case has no piece or is visited, remove capital
        if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
            capital++;
        } else {
            capital--;
        }
        if(this.individu.getPlateau().caseHasPiece(x, y)){
            nbPiece++;
        }

        visited_coor[x][y] = true;
        for (String move: this.moves) {
            if(move.equals("H")){
                if(this.individu.getPlateau().caseExist(x, y-1)){
                    y--;
                }
            }
            if(move.equals("B")) {
                if(this.individu.getPlateau().caseExist(x, y+1)){
                    y++;
                }
            }
            if (move.equals("G")) {
                if(this.individu.getPlateau().caseExist(x-1, y)){
                    x--;
                }
            }
            if (move.equals("D")){
                if(this.individu.getPlateau().caseExist(x+1, y)){
                    x++;
                }
            }
            if (this.individu.getPlateau().caseHasPiece(x, y) && !visited_coor[x][y]){
                capital = capital+3;
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
            validMoves = getNextValidMoves(toreturn, plateau.getX(), plateau.getY(), size, n);

            toreturn[i] = getRandomMove(validMoves);
            if(toreturn[i].equals("H")){
                if(this.individu.getPlateau().caseExist(x, y-1)){
                    y--;
                }
            }
            if(toreturn[i].equals("B")) {
                if(this.individu.getPlateau().caseExist(x, y+1)){
                    y++;
                }
            }
            if (toreturn[i].equals("G")) {
                if(this.individu.getPlateau().caseExist(x-1, y)){
                    x--;
                }
            }
            if (toreturn[i].equals("D")){
                if(this.individu.getPlateau().caseExist(x+1, y)){
                    x++;
                }
            }
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
