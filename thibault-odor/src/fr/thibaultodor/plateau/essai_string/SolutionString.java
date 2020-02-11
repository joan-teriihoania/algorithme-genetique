package fr.thibaultodor.plateau.essai_string;

public class SolutionString {
    public static void main(String[] args) {
        String tab1[] = {"HB","HB","HB","HB"};
        String tab2[] = {"DG","DG","DG","DG"};

        String tabI[] = {"ZZ","ZZ","ZZ","ZZ"};    //Initialisation à un valeur impossible

        char tabfinal[] = {'Z','Z','Z','Z'};    //Initialisation à un valeur impossible

        IndividusString toto;
        toto = new IndividusString(tabI,tab1,tab2,tabfinal);

        toto.croisement();

        System.out.println("Apres croisment");
        System.out.println(toto.toString());

        toto.mutation();

        System.out.println("\n\nApres mutation");
        System.out.println(toto.toString());

        toto.resfinal();
        System.out.println(toto.toStringF());
    }
}