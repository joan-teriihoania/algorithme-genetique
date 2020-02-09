package fr.joanter.plateau.main;

import java.util.Arrays;
import java.util.Random;

public class Mouvement {

    private String[] moves;
    private Individu individu;
    static private String[] possibleMove = {"H", "B", "G", "D"};

    @Override
    public String toString() {
        return "Mouvement{" +
                "moves=" + Arrays.toString(moves) +
                '}';
    }

    public Mouvement(Individu individu) {
        this.individu = individu;
        this.moves = getRandomMoves(individu.getPas());
    }

    public Mouvement(String[] moves, Individu individu) {
        this.moves = moves;
        this.individu = individu;
    }

    /*
    * TODO: Check for valid moves
    * */
    private String[] getRandomMoves(int n){
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
}
