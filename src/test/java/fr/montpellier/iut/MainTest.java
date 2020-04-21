package fr.montpellier.iut;

import org.junit.*;
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

        //System.out.println(Arrays.toString(ind1.getMoves()));
        //System.out.println(Arrays.toString(ind2.getMoves()));

        ind1.croiser(ind2, 5);
        //System.out.println();

        //System.out.println(Arrays.toString(ind1.getMoves()));
        //System.out.println(Arrays.toString(ind2.getMoves()));
    }

}