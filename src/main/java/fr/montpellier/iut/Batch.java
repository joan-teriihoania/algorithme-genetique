package fr.montpellier.iut;

import java.io.IOException;

public class Batch {

    public static void run(Plateau plateau, int iterations, int cycles) throws IOException {
        Plateau copy = new Plateau(plateau);
        for (int i = 0 ; i < iterations ; i++){
            String save_filename = copy.run(cycles);
            System.out.println("Population de l'itération n°"+(i+1)+" enregistrée dans 'result/"+save_filename+"'");
        }
        System.out.println("Exécution terminée.");
    }
}
