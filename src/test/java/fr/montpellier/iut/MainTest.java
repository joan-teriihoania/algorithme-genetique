package fr.montpellier.iut;

import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainTest {

    @Test
    public void first_test(){
        System.out.println("test");
    }

    @Test
    public void test(){
        Plateau plateau1 = new Plateau(30, 10, 10, 10);
        Individu ind1 = new Individu(plateau1);
        Individu ind2 = new Individu(plateau1);

        System.out.println(Arrays.toString(ind1.getMoves()));
        System.out.println(Arrays.toString(ind2.getMoves()));

        ind1.croiser(ind2, 5);
        System.out.println();

        System.out.println(Arrays.toString(ind1.getMoves()));
        System.out.println(Arrays.toString(ind2.getMoves()));
    }

    @Test
    public void select_test(){
        Plateau plateau1 = new Plateau(30, 10, 10, 10);
        assert(plateau1.selectIndividus(2).size() == 2);
    }

    @Test
    public void evaluate_test() throws IOException {
        Boolean[][] cases = {
                {false, true, true, true, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, false, true, false, false},
                {false, true, true, false, false},
        };
        Plateau plateau = new Plateau(6, 10, 10, 3);
        ArrayList<Individu> listIndividus = new ArrayList<>();

        listIndividus.add(new Individu(new String[]{"B", "D", "D", "G", "D", "G"}, plateau));
        listIndividus.add(new Individu(new String[]{"D", "D", "D", "B", "G", "B"}, plateau));

        plateau.setIndividus(listIndividus);
        plateau.setX(0);
        plateau.setY(0);
        plateau.setCases(cases);
        plateau.run(1);
        System.out.println(plateau.parcours());
        if(plateau.getIndividus().get(0).evaluate() == 3) System.out.println("1 = c bon");
        if(plateau.getIndividus().get(1).evaluate() == 15) System.out.println("2 = c bon");
    }

}