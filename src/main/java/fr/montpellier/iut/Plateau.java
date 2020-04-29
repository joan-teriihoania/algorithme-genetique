package fr.montpellier.iut;

import java.io.*;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public ArrayList<Individu> selectIndividus(int nbToSelect){
        Collection<Individu> selectedIndividus = new HashSet<>();
        int sumEvaluation = 0;
        for(Individu individu: individus){
            sumEvaluation += individu.evaluate();
        }

        while(selectedIndividus.size() < nbToSelect) {
            if (sumEvaluation <= 0) {
                for(int i = 0 ; i < nbToSelect ; i++){
                    selectedIndividus.add(individus.get(new Random().nextInt(individus.size())));
                }
            } else {
                for (Individu individu : individus) {
                    if (selectedIndividus.size() < nbToSelect) {
                        if (individu.evaluate() < new Random().nextInt(sumEvaluation)) {
                            selectedIndividus.add(individu);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(selectedIndividus);
    }

    public void run(int nbCycles) throws IOException {
        double nb_selected_individus = individus.size()*0.3;
        Individu individu_croisement;
        long index = 0;
        for (int i = 0;i < nbCycles ; i++){
            index++;
            ArrayList<Individu> selected_individus;
            selected_individus = selectIndividus((int)nb_selected_individus);
            // We select the best individuals
            for (int j = 0;j < selected_individus.size();j=j+2) {
                System.out.print("[STATUS] Cycle n°" + (i+1) + "/"+nbCycles+" - Individus selectionnés n°" + (j+1) + "/" + selected_individus.size() + "\r");
                individus.remove(selected_individus.get(j));
                if(j+1<selected_individus.size()){
                    individu_croisement = selected_individus.get(j+1);
                    individus.remove(selected_individus.get(j+1));
                } else {
                    individu_croisement = selected_individus.get(j);
                }

                selected_individus.get(j).croiser(individu_croisement,3);
                selected_individus.get(j).muter();
                individu_croisement.muter();
                individus.add(selected_individus.get(j));
                if(j+1<selected_individus.size()){
                    individus.add(selected_individus.get(j+1));
                }

            }
            //long percentage = index * 100 / nbCycles;
        }
        System.out.print("[STATUS] Simulation terminée\n");
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

    public StringBuilder parcours(){
        StringBuilder map = new StringBuilder("\n");
        ArrayList<Individu> best_individus = bestIndividus(3);

        if(!best_individus.isEmpty()) {
            for (Individu bestIndividus : best_individus) {

                map.append("Chemin de l'individu n°" + bestIndividus.getId());
                map.append("\nMoves :" + Arrays.toString(bestIndividus.getMoves()));
                map.append("\nEvaluation : " + bestIndividus.evaluate() + "\n");

                ArrayList<Integer> mouv = new ArrayList<>();
                mouv.add(x);
                mouv.add(y);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bestIndividus.getMoves().length; i++) {
                    sb.append(bestIndividus.getMoves()[i]);
                }
                String str = sb.toString();

                for (int m = 0; m < str.length(); m++) {
                    char mouv_actuel = str.charAt(m);
                    if (mouv_actuel == 'H') {
                        mouv.add(mouv.get(m * 2));
                        mouv.add(mouv.get((m * 2) + 1) - 1);
                    }
                    if (mouv_actuel == 'D') {
                        mouv.add(mouv.get(m * 2) + 1);
                        mouv.add(mouv.get((m * 2) + 1));
                    }
                    if (mouv_actuel == 'B') {
                        mouv.add(mouv.get(m * 2));
                        mouv.add(mouv.get((m * 2) + 1) + 1);
                    }
                    if (mouv_actuel == 'G') {
                        mouv.add(mouv.get(m * 2) - 1);
                        mouv.add(mouv.get((m * 2) + 1));
                    }

                    if (mouv.get(mouv.size() - 1) < 0) mouv.set(mouv.size() - 1, 0);
                    if (mouv.get(mouv.size() - 2) < 0) mouv.set(mouv.size() - 2, 0);
                    if (mouv.get(mouv.size() - 1) > getSize() - 1) mouv.set(mouv.size() - 1, getSize() - 1);
                    if (mouv.get(mouv.size() - 2) > getSize() - 1) mouv.set(mouv.size() - 2, getSize() - 1);
                }
                System.out.println(mouv);

                for (int i = 0; i < this.cases.length; i++) {
                    StringBuilder space = new StringBuilder();
                    int space_needed = String.valueOf(this.getSize()).length();
                    space_needed = space_needed - String.valueOf(i).length();
                    space.append(" ".repeat(Math.max(0, space_needed)));
                    map.append("y=").append(i).append(space).append(":|");
                    for (int j = 0; j < this.cases.length; j++) {
                        if (cases[i][j]) {
                            boolean hasMouv = false;
                            for (int z = 0; z < mouv.size() / 2; z++) {
                                if (j == mouv.get(z * 2) && i == mouv.get((z * 2) + 1) && !hasMouv) {
                                    map.append(" P ");
                                    hasMouv = true;
                                }
                            }
                            if (!hasMouv) {
                                map.append(" X ");
                            }
                        } else {
                            boolean hasMouv = false;
                            for (int z = 0; z < mouv.size() / 2; z++) {
                                if (j == mouv.get(z * 2) && i == mouv.get((z * 2) + 1) && !hasMouv) {
                                    map.append(" O ");
                                    hasMouv = true;
                                }
                            }
                            if (!hasMouv) {
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
            space.append(" ".repeat(Math.max(0, space_needed)));
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

    public Plateau(Plateau plateau){
        this(plateau.pas, plateau.nbPieces, plateau.cases.length, plateau.individus.size());
        cases = Arrays.copyOf(plateau.cases, plateau.cases.length);
        individus = new ArrayList<>(plateau.individus);
        id = plateau.id;
        x = plateau.x;
        y = plateau.y;
    }

    public Plateau(int pas, int nbPieces, int W, int nbIndividus) {
        this(nbPieces, W);
        this.pas = pas;

        for (int i = 0; i < nbIndividus; i++){
            Individu individu = new Individu(this);
            this.individus.add(individu);
        }
    }

    public Plateau(int nbPieces, int W) {
        this.x = new Random().nextInt(W);
        this.y = new Random().nextInt(W);
        this.pas = 0;
        this.nbPieces = nbPieces;
        this.id = UUID.randomUUID().toString();
        this.individus = new ArrayList<>();

        this.cases = new Boolean[W][W];
        this.genPlateau();
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

    public void setCases(Boolean[][] cases) {
        this.cases = cases;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setIndividus(ArrayList<Individu> individus) {
        this.individus = individus;
    }

    public ArrayList<Individu> getIndividus() {
        return individus;
    }

    public Boolean[][] getCases() {
        return cases;
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
                //System.out.println("File already exists.");
                myObj.delete();
                //System.out.println("File deleted");
                myObj.createNewFile();
            }
            //System.out.println("File created: " + myObj.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getTimeFilename(){
        SimpleDateFormat date = new SimpleDateFormat ("dd-MM-yyyy" );
        SimpleDateFormat heure = new SimpleDateFormat ("hh-mm");

        Date today = new Date();

        String dateString = date.format(today);
        String heureString = heure.format(today);

        return "d" + dateString + "_h" + heureString + "_result.txt";
    }

    public void exportPlateau() throws  IOException{
        String filename = "plateau/" + getTimeFilename();

        this.createTxtFile(filename); //en cours de test

        FileWriter myWriter = new FileWriter(filename);
        myWriter.write("X" + x + "\n");
        myWriter.write("Y" + y + "\n");
        for(Boolean[] line: cases){
            myWriter.write(
                    Arrays.toString(line)
                            .replace("true", "1")
                            .replace("false", "0")
                            .replace("[", "")
                            .replace("]", "") +
                            "\n");
        }
        myWriter.close();
        //System.out.println("Fin écriture !");
    }

    public Plateau importPlateau(String fileName){
        try{
            InputStream flux=new FileInputStream("plateau/" + fileName);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            ArrayList<Boolean[]> all_cases = new ArrayList<>();
            while ((ligne=buff.readLine())!=null){
                if (ligne.charAt(0) == 'X'){
                    x = Integer.parseInt(ligne.substring(1));
                } else if (ligne.charAt(0) == 'Y'){
                    y = Integer.parseInt(ligne.substring(1));
                } else {
                    ligne = ligne.replace(" ", "");
                    String[] ligne_array = ligne.split(",");
                    Boolean[] ligne_bool = new Boolean[ligne_array.length];
                    for (int i = 0; i < ligne_array.length; i++) {
                        if (ligne_array[i].equals("1")) {
                            ligne_bool[i] = true;
                        } else {
                            ligne_bool[i] = false;
                        }
                    }
                    all_cases.add(Arrays.copyOf(ligne_bool, ligne_bool.length));
                }
            }

            for(int i = 0;i < all_cases.size() ; i++){
                cases[i] = Arrays.copyOf(all_cases.get(i), all_cases.get(i).length);
            }
            System.out.println(map());
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        return this;
    }

    private void exportIndividus() throws IOException {
        String filename = "result/" + getTimeFilename();


        this.createTxtFile(filename); //en cours de test
        String[] lastIndividusMoves = {};

        FileWriter myWriter = new FileWriter(filename);
        for(Individu individu: bestIndividus()){
            if(!Arrays.equals(individu.getMoves(), lastIndividusMoves)){
                myWriter.write(id + ":" + Arrays.toString(individu.getMoves()) + "\n");
            }
            lastIndividusMoves = individu.getMoves();
        }
        myWriter.close();
        //System.out.println("Fin écriture !");
    }

    public void importIndividus(String fileName){
        try{
            InputStream flux=new FileInputStream("banque_de_donnees/" + fileName);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            while ((ligne=buff.readLine())!=null){
                ligne = ligne.substring(ligne.indexOf("[")+1,ligne.length()-1);
                ligne = ligne.replace(" ","");
                pas = ligne.split(",").length;
                individus.add(new Individu(ligne.split(","), this));
            }
            buff.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
