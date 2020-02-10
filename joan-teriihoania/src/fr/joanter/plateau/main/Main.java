package fr.joanter.plateau.main;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(10, 10, 5, 500);
        plateau.run(500);
        System.out.println(plateau.bestIndividus(3));
        System.out.println(plateau.map());
        System.out.println(plateau.getX());
        System.out.println(plateau.getY());
    }
}
