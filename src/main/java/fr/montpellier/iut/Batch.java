package fr.montpellier.iut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Batch {


    final Boolean[][] testPlateau = {
            {false, true, true, true, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, false, true, false, false},
            {false, true, true, false, false},
    };

    public static void run(int iterations) throws IOException {
        Boolean[][] forcePlateau = {};
        do {
            Plateau plateau;
            if (Input.getInputBoolean("Souhaitez-vous importer une population ?")) {
                try (Stream<Path> walk = Files.walk(Paths.get("banque_de_donnees"))) {

                    List<String> result = walk.map(x -> x.toString())
                            .filter(f -> f.endsWith(".txt")).collect(Collectors.toList());

                    for(String display: result){
                        System.out.println("[ENTREE] - " + display.replace("banque_de_donnees\\", ""));
                    }
                    System.out.println("");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                plateau = new Plateau(10, 5);
                String fileName = Input.getInputFilename("Entrez le nom du fichier de stockage", "banque_de_donnees");
                plateau.importIndividus(fileName);
            } else {
                int nbIndividus = Input.getInputInt("Entrez le nombre d'individu(s) à générer");
                plateau = new Plateau(10, 10, 5, nbIndividus);
            }

            int nbCycles = Input.getInputInt("Entrez le nombre de cycle à générer");
            if (Input.getInputBoolean("Voulez-vous modifier le pourcentage de mutation ?")) {
                double mutate_chance = Input.getInputDouble("Rentrez une valeur (en % en utilisant une \",\" si besoin)");
                mutate_chance = mutate_chance / 100;
                Individu.setMutationChance(mutate_chance);
            } else {
                System.out.println("[INFORM] Pourcentage de mutation défini à 5% (par défaut)");
            }

            if (forcePlateau.length > 0) plateau.setCases(forcePlateau);
            System.out.println(plateau.map());
            for (int i = 0; i < iterations; i++) {
                Plateau copy = new Plateau(plateau);
                String save_filename = copy.run(nbCycles);
                System.out.println("[INFORM] Population de l'itération n°" + (i + 1) + " enregistrée dans 'result/" + save_filename + "'");
                System.out.println(copy.parcours());
            }
            forcePlateau = plateau.getCases();
        } while (Input.getInputBoolean("Souhaitez-vous exécuter une nouvelle simulation à partir du même terrain ?"));
        System.out.println("[SUCCES] Exécution terminée.");
    }
}
