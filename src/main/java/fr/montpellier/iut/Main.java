package fr.montpellier.iut;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        inputs();
    }

    public static void inputs() throws IOException {
        Boolean[][] cases = {
                {false, true, true, true, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, true, true, false, false},
        };
        Plateau plateau;

        System.out.println("------------------------{Algorithme Génétique}------------------------");
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
            plateau.setCases(cases);
            String fileName = Input.getInputFilename("Entrez le nom du fichier de stockage", "banque_de_donnees");
            plateau.importIndividus(fileName);
        } else {
            int nbIndividus = Input.getInputInt("Entrez le nombre d'individu(s) à générer");
            plateau = new Plateau(10, 10, 5, nbIndividus);
            plateau.setCases(cases);
        }

        int nbCycles = Input.getInputInt("Entrez le nombre de cycle à générer");
        if (Input.getInputBoolean("Voulez-vous modifier le pourcentage de mutation ?")) {
            double mutate_chance = Input.getInputDouble("Rentrez une valeur (en % en utilisant une \",\" si besoin)");
            mutate_chance = mutate_chance / 100;
            Individu.setMutationChance(mutate_chance);
        } else {
            System.out.println("[INFORM] Pourcentage de mutation défini à 5% (par défaut)");
        }
        Batch.run(plateau, Input.getInputInt("Entrez le nombre d'itération"), nbCycles);

        /*System.out.println(plateau.bestIndividus(3));
        System.out.println(plateau.map());
        System.out.println(plateau.getX());
        System.out.println(plateau.getY());*/
    }
}

/*
* TODO: Ne pas sélectionner les individus les plus performants pour le croisement
*  TODO: Générer les individus en fonction de l'ordre de récupération des pièces
*   selon un ordre aléatoire préétabli à la génération 0 pour chaque individu
* TODO: Permettre la modification des méthodes sélection, mutation et croisement
*  selon un paramètre. (Ex: chance de mutation)
* TODO: A l'affichage d'un individu, dessiner le plateau en ajoutant le parcours
*  de l'indivdu
* TODO: Plus discuter sur la gestion du projet et la répartition des tâches
*
* Pour la prochaine fois
*
* DONE: Clarification du croisement d'un individu
* DONE: Discussion du croisement avec la classification hiérarchique génétique
 * Question sur l'utilité
* */