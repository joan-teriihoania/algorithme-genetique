package fr.thibaultodor.plateau.essai_string;

import  java.util.Random;

public class IndividusString {

    private String[] CodeGen;
    private String[] GenPre1;
    private String[] GenPre2;
    private char[] Tabfinal;

    public IndividusString(String[] codeGen, String[] genPre1, String[] genPre2, char[] tabfinal) {
        CodeGen = codeGen;
        GenPre1 = genPre1;
        GenPre2 = genPre2;
        Tabfinal = tabfinal;
    }

    private static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void croisement(){
        int r;
        int r2;
        char premierCaractere;
        for (int i = 0;i<GenPre1.length;i++){
            r = generateRandomIntIntRange(0,1);
            r2 = generateRandomIntIntRange(0,1);
            String courant = "";
            if (r==0) {
                courant = courant + GenPre1[i].charAt(0);
                if (r2 == 0){
                    courant = courant + GenPre2[i].charAt(0);
                }
                else{
                    courant = courant + GenPre2[i].charAt(1);
                }
                CodeGen[i] = courant;
            }
            else {
                courant = courant + GenPre1[i].charAt(1);
                if (r2 == 0){
                    courant = courant + GenPre2[i].charAt(0);
                }
                else{
                    courant = courant + GenPre2[i].charAt(1);
                }
                CodeGen[i] = courant;
            }
        }
    }

    public String toString() {
        int longtab = GenPre1.length;
        String res = "Individus GenPre1 :\n";
        for (int i = 0;i<longtab;i++){
            res = res + GenPre1[i] + " ";
        }
        res = res + "\nIndividus GenPre2 :\n";
        for (int i = 0;i<longtab;i++){
            res = res + GenPre2[i] + " ";
        }
        res = res + "\nIndividus Solution :\n";
        for (int i = 0; i < longtab; i++) {
            res = res + CodeGen[i] + " ";
        }
        return res;
    }

    public String toStringF() {
        int longtab = Tabfinal.length;
        String res = "\n\nResultat final :\n";
        for (int i = 0;i<longtab;i++){
            res = res + Tabfinal[i] + " ";
        }
        return res;
    }

    public void mutation(){
        int rmouv;
        int rmut;
        int nbmut = 0;
        for (int i = 0; i < CodeGen.length; i++) {
            String courant = "";
            rmouv = generateRandomIntIntRange(0,3);
            rmut = generateRandomIntIntRange(0,9);
            if (rmut == 0){
                if (rmouv == 0){
                    courant = courant + 'H';
                }
                else if (rmouv == 1){
                    courant = courant + 'D';
                }
                else if (rmouv == 2){
                    courant = courant + 'B';
                }
                else{
                    courant = courant + 'G';
                }
                nbmut++;
            }
            else{
                courant = courant + CodeGen[i].charAt(0);
            }
            rmouv = generateRandomIntIntRange(0,3);
            rmut = generateRandomIntIntRange(0,4);
            if (rmut == 0){
                if (rmouv == 0){
                    courant = courant + 'H';
                }
                else if (rmouv == 1){
                    courant = courant + 'D';
                }
                else if (rmouv == 2){
                    courant = courant + 'B';
                }
                else{
                    courant = courant + 'G';
                }
                nbmut++;
            }
            else{
                courant = courant + CodeGen[i].charAt(1);
            }
            CodeGen[i] = courant;
        }
        System.out.println("\nNombre de mutations : "+nbmut);
    }

    public void resfinal() {
        for (int i = 0; i < CodeGen.length; i++) {  //Dans ce cas la l'ordre hierarchique H>D>B>G
            if ((CodeGen[i].charAt(0) == 'H') || (CodeGen[i].charAt(1) == 'H')){
                Tabfinal[i] = 'H';
            }
            else if ((CodeGen[i].charAt(0) == 'D') || (CodeGen[i].charAt(1) == 'D')){
                Tabfinal[i] = 'D';
            }
            else if ((CodeGen[i].charAt(0) == 'B') || (CodeGen[i].charAt(1) == 'B')){
                Tabfinal[i] = 'B';
            }
            else {
                Tabfinal[i] = 'G';
            }
        }
    }
}