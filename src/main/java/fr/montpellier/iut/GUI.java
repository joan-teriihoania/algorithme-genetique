package fr.montpellier.iut;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel{

    private String data; //au lieu de String, va contenir un tableau d'individu (contenant ex les 16 meilleurs solutions de la generation courante)
    private int largeurFen;
    private int hauteurFen;
    private int nbSols; //nb de solutions à afficher dans la fenetre, typiquement nbSols = c^2, et on créera donc une grille de c x c solutions

    public void paint(Graphics g){//méthode appelée entre autre à chaque fois que l'on fait repaint() (et donc on fera repaint() à chaque nouvelle génération)
        g.drawString(data, 15, 15);
        //parcourir data, et
        //    pour chaque Individu ind = data[i],
        //      faire ind.dessine(g,largeurFen,hauteuFen,nbSols,i);
        //    qui doit faire en sorte que ind se dessine dans g, et dans le petit carré
        //    qui correspond à son indice :

    }

    public static void run() throws InterruptedException{
        JFrame frame= new JFrame("JavaTutorial.net");
        GUI m = new GUI();
        m.data  = "chaine 1";
        frame.getContentPane().add(m);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TimeUnit.SECONDS.sleep(1);
        m.data  = "chaine 2";
        frame.repaint();

        //ici, une des options du main lancera un calcul, et à chaque generation (qui sera stockée dans data), on fera frame.repaint();
    }
}