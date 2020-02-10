package fr.joanter.plateau.main;

import com.sun.tools.attach.AgentInitializationException;
import me.tongfei.progressbar.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

import static java.util.Collections.*;

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

    public void run(int nbCycles){
        double nb_best = individus.size()*0.3;
        Individu individu_croisement;
        long index = 0;
        ProgressBar pb = new ProgressBar("Simulation: "+this.id, nbCycles, 100);
        pb.start();
        for (int i = 0;i < nbCycles ; i++){
            index++;
            ArrayList<Individu> best_individus;
            best_individus = bestIndividus((int)nb_best);
            // We select the best individuals
            for (int j = 0;j < best_individus.size();j=j+2) {
                individus.remove(best_individus.get(j));
                if(j+1<best_individus.size()){
                    individu_croisement = best_individus.get(j+1);
                    individus.remove(best_individus.get(j+1));
                } else {
                    individu_croisement = best_individus.get(j);
                }

                pb.setExtraMessage("Croisement: "+best_individus.get(j).getId()+" et "+individu_croisement.getId());

                best_individus.get(j).croiser(individu_croisement);
                best_individus.get(j).muter();
                individu_croisement.muter();
                individus.add(best_individus.get(j));
                if(j+1<best_individus.size()){
                    individus.add(best_individus.get(j+1));
                }

            }
            //long percentage = index * 100 / nbCycles;
            pb.step();
        }
        pb.stop();
    }

    /*
    * Return the nb best subjects of individus
    * depending on their evaluation.
    * */
    public ArrayList<Individu> bestIndividus(int nb){
        ArrayList<Individu> all_individus = new ArrayList<>();
        ArrayList<Individu> sorted_individus = new ArrayList<>();
        ArrayList<Individu> best_individus = new ArrayList<>();
        all_individus.addAll(individus);
        int max;
        Individu max_individu;
        max_individu = individus.get(0);
        for (Individu individu: individus) {;
            max = -999999;
            for (Individu individub: all_individus) {
                if(individub.evaluate()>max){
                    max = individub.evaluate();
                    max_individu = individub;
                }
            }
            sorted_individus.add(max_individu);
            all_individus.remove(max_individu);
        }

        if (nb > sorted_individus.size()){
            nb = sorted_individus.size();
        }

        for (int i = 0;i < nb; i++) {
            best_individus.add(sorted_individus.get(i));
        }
        return best_individus;
    }

    public StringBuilder map(){
        StringBuilder map = new StringBuilder("\n");
        for (int i = 0; i < this.cases.length; i++) {

            StringBuilder space = new StringBuilder();
            int space_needed = String.valueOf(this.getSize()).length();
            space_needed = space_needed - String.valueOf(i).length();
            for (int j = 0; j < space_needed;j++){
                space.append(" ");
            }
            map.append("y="+i+space+":|");


            for (int j = 0; j < this.cases.length; j++) {
                if (cases[i][j]) {
                    map.append(" X ");
                } else {
                    map.append("   ");
                }
                if(j == x && i == y){
                    map.append("<");
                } else {
                    map.append("|");
                }
            }
            map.append("\n");
        }
        return map;
    }

    @Override
    public String toString() {
        StringBuilder map = map();
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

    public Boolean caseExist(int x, int y){
        if(x > cases.length-1 || x < 0 || y > cases.length-1 || y < 0){
            return false;
        }
        return true;
    }

    public Boolean caseHasPiece(int x, int y){
        if (!caseExist(x, y)){return false;}
        return cases[x][y];
    }

}
