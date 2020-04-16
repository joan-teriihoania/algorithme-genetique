package fr.montpellier.iut;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(10, 10, 5, 1000);
        plateau.run(5);
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