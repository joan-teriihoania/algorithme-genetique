package fr.montpellier.iut;

import org.junit.Test;

import java.util.Arrays;

class MainTest {

    @Test
    void first_test(){
        System.out.println("test");
    }

    @Test
    void test(){
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

}