package fr.montpellier.iut;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        inputs();
    }

    public static boolean getInputBoolean(String string){
        char toreturn = 'm';
        while(toreturn != 'y' && toreturn != 'n'){
            System.out.print("[ENTREE] " + string + " (y/n) : ");
            Scanner sc = new Scanner(System.in);
            toreturn = Character.toLowerCase(sc.nextLine().charAt(0));
            if(toreturn != 'y' && toreturn != 'n') System.out.println("[ERREUR] Caractère '"+toreturn+"' entré invalide.\n");
        }

        return toreturn == 'y';
    }

    public static String getInputString(String string) {
        String toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.next();
        return toreturn;
    }

    public static String getInputFilename(String string) {
        return getInputFilename(string, "", true);
    }

    public static String getInputFilename(String string, String path){
        return getInputFilename(string, path, true);
    }

    public static String getInputFilename(String string, String path, boolean verif) {
        String toreturn = getInputString(string);
        File f = new File(path + "/" + toreturn);
        if (verif && !f.exists()){
            System.out.println("[ERREUR] Le fichier '"+toreturn+"' n'existe pas.\n");
            return getInputFilename(string, path, verif);
        }
        return toreturn;
    }

    public static int getInputInt(String string) {
        int toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.nextInt();
        return toreturn;
    }

    public static double getInputDouble(String string) {
        double toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.nextDouble();
        return toreturn;
    }

    public static void inputs() throws IOException {
        Plateau plateau;

        System.out.println("------------------------{Algorithme Génétique}------------------------");
        if (getInputBoolean("Souhaitez-vous importer une population ?")) {
            plateau = new Plateau(10, 5);
            String fileName = getInputFilename("Entrez le nom du fichier de stockage", "banque_de_donnees");
            plateau.importIndividus(fileName);
        } else {
            int nbIndividus = getInputInt("Entrez le nombre d'individu(s) à générer");
            plateau = new Plateau(10, 10, 5, nbIndividus);
        }

        int nbCycles = getInputInt("Entrez le nombre de cycle à générer");
        if (getInputBoolean("Voulez-vous modifier le pourcentage de mutation ?")) {
            double mutate_chance = getInputDouble("Rentrez une valeur (en % en utilisant une \",\" si besoin)");
            mutate_chance = mutate_chance / 100;
            Individu.setMutate_chance(mutate_chance);
        } else {
            System.out.println("[INFORM] Pourcentage de mutation défini à 5% (par défaut)");
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