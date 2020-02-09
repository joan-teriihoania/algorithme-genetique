package fr.joanter.plateau.main;

public class Mouvement {

    private String moves;
    private Individu individu;

    public Mouvement(Individu individu) {
        this.individu = individu;
    }

    public Mouvement(String moves, Individu individu) {
        this.moves = moves;
        this.individu = individu;
    }
}
