public class Solution {
    public static void main(String[] args) {
        char tab1[] = {'H','H','H','H'};
        char tab2[] = {'B','B','B','B'};

        char tabI[] = {'Z','Z','Z','Z'};    //Initialisation à un valeur impossible

        individus toto;
        toto = new individus(tabI,tab1,tab2);

        toto.croisement();

        System.out.println("Apres croisment");
        System.out.println(toto.toString());

        toto.mutation();

        System.out.println("Apres mutation");
        System.out.println(toto.toString());
    }
}