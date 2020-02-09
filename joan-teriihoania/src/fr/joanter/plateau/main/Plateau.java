package fr.joanter.plateau.main;

import com.sun.tools.attach.AgentInitializationException;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Plateau {

    private String id;
    private ArrayList<Individu> individus;
    private Boolean[][] cases;

    // Coordonnées de la position initiale
    private int y;
    private int x;

    // Nombre de pas
    private int pas;

    // Nombre de pièces
    private int nbPieces;

    @Override
    public String toString() {
        StringBuilder map = new StringBuilder("\n");
        for (int i = 0; i < this.cases.length; i++) {
            map.append(i+":|");
            for (int j = 0; j < this.cases.length; j++) {
                if (cases[i][j]) {
                    map.append(" X ");
                } else {
                    map.append("   ");
                }
                if(i == x && j == y){
                    map.append("<");
                } else {
                    map.append("|");
                }
            }
            map.append("\n");
        }
        return "\nPlateau{" +
                ",\n individus=" + individus +
                "\nid='" + id + '\'' +
                ",\n y=" + y +
                ",\n x=" + x +
                ",\n pas=" + pas +
                ",\n nbPieces=" + nbPieces +
                ",\n W =" + cases.length +
                "\n map='" + map +
                '}';
    }

    public int getPas() {
        return pas;
    }

    public int getSize(){
        return this.cases.length;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public Plateau(int pas, int nbPieces, int W, int nbIndividus) {
        this.x = new Random().nextInt(W);
        this.y = new Random().nextInt(W);
        this.pas = pas;
        this.nbPieces = nbPieces;
        this.id = UUID.randomUUID().toString();
        this.individus = new ArrayList<>();

        this.cases = new Boolean[W][W];
        this.genPlateau();

        for (int i = 0; i < nbIndividus; i++){
            Individu individu = new Individu(this);
            this.individus.add(individu);
        }
    }

    private void genPlateau(){
        for (int i = 0;i < this.cases.length;i++){
            for (int j = 0;j < this.cases.length;j++){
                this.cases[i][j] = false;
            }
        }

        int ran_x = new Random().nextInt(this.cases.length);
        int ran_y = new Random().nextInt(this.cases.length);
        for (int i = 0;i < this.nbPieces;i++){
            while(caseHasPiece(ran_x, ran_y)){
                ran_x = new Random().nextInt(this.cases.length);
                ran_y = new Random().nextInt(this.cases.length);
            }
            this.cases[ran_x][ran_y] = true;
        }
    }

    public Boolean caseHasPiece(int x, int y){
        return cases[x][y];
    }

}
