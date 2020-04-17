package fr.montpellier.iut;

import java.util.*;

public class Individu {

    static private int autoIncrement = 1;
    static private String[] possibleMove = {"H", "B", "G", "D"};
    static private double mutate_chance = 0.05;

    private int id;
    private String[] moves;
    private Plateau plateau;


    @Override
    public String toString() {
        return "\nMouvement{" +
                "\nmoves=" + Arrays.toString(moves) +
                '}';
    }

    public String[] getMoves() {
        return moves;
    }

    public Individu(Plateau plateau) {
        this.plateau = plateau;
        this.moves = getRandomMoves(plateau.getPas(), plateau);
        this.id = autoIncrement;
        autoIncrement++;

    }

    public Individu(String[] moves, Plateau plateau) {
        this.plateau = plateau;
        if (moves.length != plateau.getPas()){
            System.out.println("Invalid size of String[] moves on constructor call parameter in Individu.");
            moves = new String[plateau.getPas()];
        }
        this.moves = moves;
        this.id = autoIncrement;
        autoIncrement++;
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

    public int getId() {
        return id;
    }

    public Boolean isValid(){
        int x = this.plateau.getX();
        int y = this.plateau.getY();
        int size = this.plateau.getSize();
        int n = this.plateau.getPas();

        for (int i = 0;i < this.moves.length;i++){
            if(!nextMoveIsValid(moves[i], subArray(moves, 0, i), x, y, size, n)){
                return false;
            }
        }
        return true;
    }

    public void muter(){
        double ran;
        for (int i = 0;i < moves.length;i++){
            ran = new Random().nextDouble();
            if(ran<mutate_chance){

                String[] save_moves = new String[moves.length];
                for (int j = 0;j < moves.length;j++){
                    save_moves[j] = moves[j];
                }

                String[] validMoves = getNextValidMoves(subArray(moves, 0, i), this.plateau.getX(), this.plateau.getY(), this.plateau.getSize(), i);
                moves[i] = getRandomMove(validMoves);
            }
        }
    }

    public void croiser(Individu croisement_moves){
        croiser(croisement_moves, 1);
    }

    public void croiser(Individu croisement_moves, int nb){
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
        if (this.plateau.caseHasPiece(x, y)){
            capital++;
        } else {
            capital--;
        }

        visited_coor[x][y] = true;
        for (String move: this.moves) {
            if(move.equals("H")){
                if(this.plateau.caseExist(x, y-1)){
                    y--;
                }
                else{
                    capital =- 5;
                }
            }
            if(move.equals("B")) {
                if(this.plateau.caseExist(x, y+1)){
                    y++;
                }
                else{
                    capital =- 5;
                }
            }
            if (move.equals("G")) {
                if(this.plateau.caseExist(x-1, y)){
                    x--;
                }
                else{
                    capital =- 5;
                }
            }
            if (move.equals("D")){
                if(this.plateau.caseExist(x+1, y)){
                    x++;
                }
                else{
                    capital =- 5;
                }
            }
            if (this.plateau.caseHasPiece(x, y) && !visited_coor[x][y]){
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
        String[] validMoves;
        String[] toreturn = new String[n];

        for (int i = 0;i < n;i++){
            // Réinitialise validMoves
            validMoves = new String[4];

            // Créé un array avec uniquement les mouvements valides
            validMoves = getNextValidMoves(toreturn, plateau.getX(), plateau.getY(), size, n);

            toreturn[i] = getRandomMove(validMoves);
            if(toreturn[i].equals("H")){
                if(this.plateau.caseExist(x, y-1)){
                    y--;
                }
            }
            if(toreturn[i].equals("B")) {
                if(this.plateau.caseExist(x, y+1)){
                    y++;
                }
            }
            if (toreturn[i].equals("G")) {
                if(this.plateau.caseExist(x-1, y)){
                    x--;
                }
            }
            if (toreturn[i].equals("D")){
                if(this.plateau.caseExist(x+1, y)){
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

    public static void setMutate_chance(double mutate_chance) {
        Individu.mutate_chance = mutate_chance;
    }
}
