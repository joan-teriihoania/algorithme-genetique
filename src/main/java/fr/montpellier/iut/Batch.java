package fr.montpellier.iut;

import java.io.IOException;

public class Batch {

    public static void run(Plateau plateau, int iterations, int cycles) throws IOException {
        System.out.println(plateau.map());
        for (int i = 0 ; i < iterations ; i++){
            Plateau copy = new Plateau(plateau);
            String save_filename = copy.run(cycles);
            System.out.println("[INFORM] Population de l'itération n°"+(i+1)+" enregistrée dans 'result/"+save_filename+"'");
            System.out.println(copy.parcours());
        }
        System.out.println("[SUCCES] Exécution terminée.");
    }
}
