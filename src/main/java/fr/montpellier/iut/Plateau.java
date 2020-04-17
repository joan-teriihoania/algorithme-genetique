package fr.montpellier.iut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void run(int nbCycles) throws IOException {
        double nb_best = individus.size()*0.3;
        Individu individu_croisement;
        long index = 0;
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

                best_individus.get(j).croiser(individu_croisement);
                best_individus.get(j).muter();
                individu_croisement.muter();
                individus.add(best_individus.get(j));
                if(j+1<best_individus.size()){
                    individus.add(best_individus.get(j+1));
                }

            }
            //long percentage = index * 100 / nbCycles;
        }

        saveIndividus("individus.txt");
    }

    public ArrayList<Individu> bestIndividus(){
        return bestIndividus(individus.size());
    }

    /*
    * Return the nb best subjects of individus
    * depending on their evaluation.
    * */
    public ArrayList<Individu> bestIndividus(int nb){
        ArrayList<Individu> sorted_individus = new ArrayList<>();
        ArrayList<Individu> best_individus = new ArrayList<>();
        ArrayList<Individu> all_individus = new ArrayList<>(individus);
        int max;
        Individu max_individu;
        max_individu = individus.get(0);
        for (Individu individu: individus) {
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

    public StringBuilder parcours(){ //Modif HERE
        StringBuilder map = new StringBuilder("\n");
        ArrayList<Individu> best_individus = bestIndividus(3);

        if(best_individus.isEmpty() == false) {
            for (int a=0;a<best_individus.size();a++) {

                map.append("Chemin de l'individus "+best_individus.get(a).getId());
                map.append("\n");

                ArrayList<Integer> mouv = new ArrayList<>();
                mouv.add(x);
                mouv.add(y);

                StringBuffer sb = new StringBuffer();
                for(int i = 0; i < best_individus.get(a).getMoves().length; i++) {
                    sb.append(best_individus.get(a).getMoves()[i]);
                }
                String str = sb.toString();

                for(int m=0;m < str.length();m++){
                    char mouv_actuel = str.charAt(m);
                    if(mouv_actuel == 'H'){
                        mouv.add(mouv.get(m*2));
                        mouv.add(mouv.get((m*2)+1)-1);
                    }
                    if(mouv_actuel == 'D'){
                        mouv.add(mouv.get(m * 2) + 1);
                        mouv.add(mouv.get((m * 2) + 1));
                    }
                    if(mouv_actuel == 'B'){
                        mouv.add(mouv.get(m * 2));
                        mouv.add(mouv.get((m * 2) + 1) + 1);
                    }
                    if(mouv_actuel == 'G'){
                        mouv.add(mouv.get(m * 2) - 1);
                        mouv.add(mouv.get((m * 2) + 1));
                    }

                    if(mouv.get(mouv.size()-1) < 0) mouv.set(mouv.size()-1, 0);
                    if(mouv.get(mouv.size()-2) < 0) mouv.set(mouv.size()-2, 0);
                    if(mouv.get(mouv.size()-1) > getSize()-1) mouv.set(mouv.size()-1, getSize()-1);
                    if(mouv.get(mouv.size()-2) > getSize()-1) mouv.set(mouv.size()-2, getSize()-1);
                }
                System.out.println(mouv);

                for (int i = 0; i < this.cases.length; i++) {
                    StringBuilder space = new StringBuilder();
                    int space_needed = String.valueOf(this.getSize()).length();
                    space_needed = space_needed - String.valueOf(i).length();
                    for (int j = 0; j < space_needed; j++) {
                        space.append(" ");
                    }
                    map.append("y=" + i + space + ":|");
                    for (int j = 0; j < this.cases.length; j++) {
                        if (cases[i][j]) {
                            boolean ilyaunmouv = false;
                            for (int z = 0; z < mouv.size()/2; z++) {
                                if (j == mouv.get(z*2) && i == mouv.get((z*2)+1) && ilyaunmouv == false) {
                                    map.append(" P ");
                                    ilyaunmouv = true;
                                }
                            }
                            if(ilyaunmouv == false) {
                                map.append(" X ");
                            }
                        }
                        else {
                            boolean ilyaunmouv = false;
                            for (int z = 0; z < mouv.size()/2; z++) {
                                if (j == mouv.get(z*2) && i == mouv.get((z*2)+1) && ilyaunmouv == false) {
                                    map.append(" O ");
                                    ilyaunmouv = true;
                                }
                            }
                            if(ilyaunmouv == false) {
                                map.append("   ");
                            }
                        }

                        if (j == x && i == y) {
                            map.append("<");
                        } else {
                            map.append("|");
                        }
                    }
                    map.append("\n");
                }
                map.append("\n");
            }
            return map;
        }
        else{
            return map;
        }
    }

    public StringBuilder map(){
        StringBuilder map = new StringBuilder("\n");
        for (int i = 0; i < this.cases.length; i++) {

            StringBuilder space = new StringBuilder();
            int space_needed = String.valueOf(this.getSize()).length();
            space_needed = space_needed - String.valueOf(i).length();
            for (int j = 0; j < space_needed;j++) space.append(" ");
            map.append("y=").append(i).append(space).append(":|");


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
        return x <= cases.length - 1 && x >= 0 && y <= cases.length - 1 && y >= 0;
    }

    public Boolean caseHasPiece(int x, int y){
        if (!caseExist(x, y)){return false;}
        return cases[x][y];
    }

    private void createTxtFile(String filename){
        try {
            File myObj = new File(filename);
            if (!myObj.createNewFile()) {
                System.out.println("File already exists.");
                myObj.delete();
                System.out.println("File deleted");
                myObj.createNewFile();
            }
            System.out.println("File created: " + myObj.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void saveIndividus(String filename) throws IOException {
        //int ts = (int) (new Date().getTime()/1000)
        this.createTxtFile(filename); //en cours de test
        String[] lastIndividusMoves = {};

        FileWriter myWriter = new FileWriter(filename);
        for(Individu individu: bestIndividus()){
            if(!Arrays.equals(individu.getMoves(), lastIndividusMoves)){
                myWriter.write(individu.getId()+":"+ Arrays.toString(individu.getMoves()) + "\n");
            }
            lastIndividusMoves = individu.getMoves();
        }
        myWriter.close();
        System.out.println("Fin écriture !");
    }

}
