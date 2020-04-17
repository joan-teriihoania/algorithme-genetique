package fr.montpellier.iut;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean quitter = false;
        Plateau plateau = null;

        System.out.println("------------------------{Algorithme Génétique}------------------------");
        while(!quitter) {
            System.out.println("Rentrer une base de données d'individus ?(Y/N) : ");
            char charYOuN = sc.next().charAt(0);
            if (charYOuN == 'y') {
                plateau = new Plateau(10, 5);
                System.out.println("Veuillez rentrer le nom du fichier : ");
                String fileName = sc.next();
                plateau.importBaseDeDonneeIndividus(fileName);
                quitter = true;
            } else if (charYOuN == 'n') {
                System.out.println("Veulliez rentrer le nombre d'individu(s) à générer : ");
                int nbIndividus = sc.nextInt();
                plateau = new Plateau(10, 10, 5, nbIndividus);
                quitter = true;
            } else {
                System.out.println("Erreur : mauvais caractère.");
            }
        }

        System.out.println("Veulliez rentrer le nombre de cycle(s) : ");
        int nbCycles = sc.nextInt();
        quitter = false;
        while(!quitter) {
            System.out.println("Voulez-vous modifier le pourcentage de mutation ?(Y/N) : ");
            char charYOuN = sc.next().charAt(0);
            if (charYOuN == 'y') {
                System.out.println("Rentrer une valeur (en % et utilisez une \",\" si besoin) : ");
                double mutate_chance = sc.nextDouble();
                mutate_chance = mutate_chance / 100;
                Individu.setMutate_chance(mutate_chance);
                quitter = true;
            } else if (charYOuN == 'n') {
                System.out.println("Le pourcentage sera de 5% (par défaut)");
                quitter = true;
            } else {
                System.out.println("Erreur : mauvais caractère.");
            }
        }

        plateau.run(nbCycles);
        System.out.println(plateau.bestIndividus(3));
        System.out.println(plateau.map());
        System.out.println(plateau.getX());
        System.out.println(plateau.getY());
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