package fr.montpellier.iut;

import java.io.IOException;

public class Batch {
    private Plateau plateau;

    public Batch(Plateau plateau){
        this.plateau = plateau;
    }

    public void run(int iterations, int cycles) throws IOException {
        Plateau copy = new Plateau(plateau);
        for (int i = 0 ; i < iterations ; i++){
            copy.run(cycles);
        }
    }
}
