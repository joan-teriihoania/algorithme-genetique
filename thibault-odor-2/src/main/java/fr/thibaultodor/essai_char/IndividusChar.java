package main.java.fr.thibaultodor.essai_char;

import  java.util.Random;

public class IndividusChar {

    private char[] CodeGen;
    private char[] GenPre1;
    private char[] GenPre2;

    public IndividusChar(char[] codeGen, char[] genPre1, char[] genPre2) {
        CodeGen = codeGen;
        GenPre1 = genPre1;
        GenPre2 = genPre2;
    }

    private static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void croisement(){
        int r;
        for (int i = 0;i<GenPre1.length;i++){
            r = generateRandomIntIntRange(0,1);
            if (r==0) {
                CodeGen[i] = GenPre1[i];
            }
            else {
                CodeGen[i] = GenPre2[i];
            }
        }
    }

    public String toString() {
        int longtab = GenPre1.length;
        String res = "Individus GenPre1 :\n";
        for (int i = 0;i<longtab;i++){
            res = res + GenPre1[i];
        }
        res = res + "\nIndividus GenPre2 :\n";
        for (int i = 0;i<longtab;i++){
            res = res + GenPre2[i];
        }
        res = res + "\nIndividus Solution :\n";
        for (int i = 0; i < longtab; i++) {
            res = res + CodeGen[i];
        }
        return res;
    }

    public void mutation(){
        int rmouv;
        int rmut;
        for (int i = 0; i < CodeGen.length; i++) {
            rmouv = generateRandomIntIntRange(0,3);
            rmut = generateRandomIntIntRange(0,4);
            if (rmut == 0){
                if (rmouv == 0){
                    CodeGen[i] = 'H';
                }
                else if (rmouv == 1){
                    CodeGen[i] = 'D';
                }
                else if (rmouv == 2){
                    CodeGen[i] = 'B';
                }
                else{
                    CodeGen[i] = 'G';
                }
            }
        }
    }
}