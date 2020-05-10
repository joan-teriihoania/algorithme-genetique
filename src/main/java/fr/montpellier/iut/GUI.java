package fr.montpellier.iut;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel{

    private static ArrayList<Individu> data = new ArrayList<Individu>();   //au lieu de String, va contenir un tableau d'individu (contenant ex les 16 meilleurs solutions de la generation courante)
    private static int largeurFen=500;
    private static int hauteurFen=500;
    private static int nbSols=5; //nb de solutions à afficher dans la fenetre, typiquement nbSols = c^2, et on créera donc une grille de c x c solutions

    public void setData(Individu m) {
        data.add(m);
    }

    public void paint(Graphics g){//méthode appelée entre autre à chaque fois que l'on fait repaint() (et donc on fera repaint() à chaque nouvelle génération)
        for(int i = 1;i!=nbSols+1;i++){
            for(int j = 1;j!=nbSols+1;j++) {
                g.drawString("Chemin de l'individus n°" + data.get(0).getId(),0,hauteurFen/10); //Problem HERE
                g.drawRect((largeurFen / nbSols) * i, (hauteurFen / nbSols) * j, largeurFen / nbSols, hauteurFen / nbSols);
                if(data.get(0).getVisitedCoor()[i-1][j-1]){
                    g.setColor(Color.red);
                    g.fillOval((largeurFen / nbSols) * i, (hauteurFen / nbSols) * j,largeurFen / nbSols, hauteurFen / nbSols);
                    g.setColor(Color.black);
                }
            }
        }
        //parcourir data, et
        //    pour chaque Individu ind = data[i],
        //      faire ind.dessine(g,largeurFen,hauteuFen,nbSols,i);
        //    qui doit faire en sorte que ind se dessine dans g, et dans le petit carré
        //    qui correspond à son indice :
    }

    public static void run() throws InterruptedException{
        JFrame frame= new JFrame("Generation");
        GUI m = new GUI();
        frame.getContentPane().add(m);
        frame.setSize(largeurFen+(largeurFen/nbSols)*2, hauteurFen+(hauteurFen/nbSols)*2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TimeUnit.SECONDS.sleep(1);
        frame.repaint();
        //ici, une des options du main lancera un calcul, et à chaque generation (qui sera stockée dans data), on fera frame.repaint();
    }
}