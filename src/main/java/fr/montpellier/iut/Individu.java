package fr.montpellier.iut;

import java.util.*;

public class Individu {

    static private int autoIncrement = 1;
    static private String[] possibleMove = {"H", "B", "G", "D"};
    static private double mutate_chance = 0.05;

    private int id;
    private String[] moves;
    private Plateau plateau;
    private Boolean[][] visited_coor;


    @Override
    public String toString() {
        return "\nMouvement{" +
                "\n moves=" + Arrays.toString(moves) +
                "\n eval =" + evaluate() +
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
        visited_coor = new Boolean[this.plateau.getSize()][this.plateau.getSize()];
        evaluate();

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

    public int getId() {
        return id;
    }

    public void muter(){
        double ran;
        for (int i = 0;i < moves.length;i++){
            ran = new Random().nextDouble();
            if(ran<mutate_chance){

                String[] save_moves = new String[moves.length];
                System.arraycopy(moves, 0, save_moves, 0, moves.length);
                moves[i] = getRandomMove(possibleMove);
            }
        }
    }

    public void croiser(Individu croisement_moves){
        croiser(croisement_moves, 1);
    }

    public void croiser(Individu individu_to_croiser, int nb){
        ArrayList<Individu> individus_a_croiser = new ArrayList<>();
        individus_a_croiser.add(this);
        individus_a_croiser.add(individu_to_croiser);

        for(Individu individu_en_traitement: individus_a_croiser) {
            String[] movesA = Arrays.copyOf(moves, moves.length);
            String[] movesB = Arrays.copyOf(individu_to_croiser.moves, individu_to_croiser.moves.length);
            double move_choosen;

            for (int i = 0; i < individu_en_traitement.moves.length; i = i + nb) {
                String[] move_to_change;
                String[] move_to_get;
                move_choosen = new Random().nextDouble();

                if (move_choosen > 0.5) {
                    move_to_change = movesA;
                    move_to_get = movesB;
                } else {
                    move_to_change = movesB;
                    move_to_get = movesA;
                }

                for (int j = i; j < i + nb; j++) {
                    move_to_change[j] = move_to_get[j];
                }
            }

            individu_en_traitement.moves = Arrays.copyOf(movesA, movesA.length);
        }
    }

    public int evaluate(){
        return evaluate("nbPiece");
    }

    public int evaluate(String mode){
        // Initialise visited cases map
        for (int i = 0;i < visited_coor.length;i++){
            for (int j = 0;j < visited_coor.length;j++){
                visited_coor[i][j] = false;
            }
        }

        int x = this.plateau.getX();
        int y = this.plateau.getY();
        int score = 0;
        int nbPiece = 0;
        // If case has piece and is not visited, add capital
        // If case has no piece or is visited, remove capital
        if (this.plateau.caseHasPiece(x, y)){
            score = score + 3;
            nbPiece++;
        }

        visited_coor[x][y] = true;
        for (String move: this.moves) {
            if(move.equals("H")){
                if(this.plateau.caseExist(x, y-1)){
                    y--;
                } else {
                    score = score - 5;
                }
            }
            if(move.equals("B")) {
                if(this.plateau.caseExist(x, y+1)){
                    y++;
                } else {
                    score = score - 5;
                }
            }
            if (move.equals("G")) {
                if(this.plateau.caseExist(x-1, y)){
                    x--;
                } else {
                    score = score - 5;
                }
            }
            if (move.equals("D")){
                if(this.plateau.caseExist(x+1, y)){
                    x++;
                } else {
                    score = score - 5;
                }
            }

            if (this.plateau.caseHasPiece(y, x) && !visited_coor[x][y]){
                nbPiece++;
                score = score+3;
            }
            visited_coor[x][y] = true;
        }

        if(mode.equals("score")) return score;
        if(mode.equals("nbPiece")) return nbPiece;
        return score;
    }

    public Boolean[][] getVisitedCoor(){
        return visited_coor;
    }

    private String[] getRandomMoves(int n, Plateau plateau){
        int x = plateau.getX();
        int y = plateau.getY();
        int size = plateau.getSize();
        String[] toreturn = new String[n];

        for (int i = 0;i < n;i++){
            toreturn[i] = getRandomMove(possibleMove);
        }

        return toreturn;
    }

    private String getRandomMove(String[] validMove){
        int ran = new Random().nextInt(validMove.length);
        return validMove[ran];
    }

    public static void setMutationChance(double mutate_chance) {
        Individu.mutate_chance = mutate_chance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individu individu = (Individu) o;
        return id == individu.id &&
                Arrays.equals(moves, individu.moves) &&
                Objects.equals(plateau, individu.plateau);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, plateau);
        result = 31 * result + Arrays.hashCode(moves);
        return result;
    }
}
