package fr.joanter.plateau.main;

import java.util.Random;

public class Mouvement {

    private String[] moves;
    private Individu individu;
    static private String[] move = {"H", "B", "G", "D"};

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
        String[] moves = new String[n];
        for (int i = 0;i < n;i++){
            moves[i] = getRandomMove(moves);
        }
        return moves;
    }

    private String getRandomMove(String[] validMove){
        int ran = new Random().nextInt(validMove.length);
        return validMove[ran];
    }
}
