package fr.montpellier.iut;

import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel{

    private static ArrayList<Individu> data = new ArrayList<Individu>();   //au lieu de String, va contenir un tableau d'individu (contenant ex les 16 meilleurs solutions de la generation courante)
    private static ArrayList<Double> table_moyenne = new ArrayList<>();
    private static ArrayList<Double> table_best = new ArrayList<>();
    private static ArrayList<Double> table_worst = new ArrayList<>();

    private static JFrame frame;

    private static Plateau plateau;
    private static int largeurFen=750;
    private static int hauteurFen=750;
    private static int nbSols=2; //nb de solutions à afficher dans la fenetre, typiquement nbSols = c^2, et on créera donc une grille de c x c solutions

    public void setPlateau(Plateau p){plateau = p;}

    public void setData(Individu m) {
        data.add(m);
    }

    public void setAllData(ArrayList<Individu> m){
        data.clear();
        data.addAll(m);
    }

    public void setAllMoyenne(ArrayList<Double> m){
        table_moyenne.clear();
        table_moyenne.addAll(m);
    }

    public void setAllBest(ArrayList<Double> m){
        table_best.clear();
        table_best.addAll(m);
    }

    public void setAllWorst(ArrayList<Double> m){
        table_worst.clear();
        table_worst.addAll(m);
    }

    public void paint(Graphics g) {//méthode appelée entre autre à chaque fois que l'on fait repaint() (et donc on fera repaint() à chaque nouvelle génération)
        int plateauSize = plateau.getSize();
        int sizeCase = largeurFen / (nbSols + 1);
        int sizeInnerCase = sizeCase / plateauSize;

        int margin = sizeInnerCase * 2;
        int x = margin;
        int y = margin;

        for (Individu individu : data) {
            if (y > hauteurFen - (sizeCase + sizeInnerCase * 2)) {
                break;
            }

            Boolean[][] visited = individu.getVisitedCoor();
            int ix = 0;
            int iy = 0;
            for (int i = 0; i < plateauSize; i++) {
                for (int j = 0; j < plateauSize; j++) {
                    ix = x + (i * sizeInnerCase);
                    iy = y + (j * sizeInnerCase);

                    g.setColor(Color.black);
                    g.drawRect(ix, iy, sizeInnerCase, sizeInnerCase);

                    if (visited[i][j]) {
                        g.setColor(Color.green);
                        int innerMarginPiece = (int) Math.round(sizeInnerCase * 0.05);
                        g.fillRect(ix + innerMarginPiece, iy + innerMarginPiece, sizeInnerCase - innerMarginPiece * 2, sizeInnerCase - innerMarginPiece * 2);
                    }

                    if (plateau.caseHasPiece(j, i)) {
                        if (visited[i][j]) {
                            g.setColor(Color.blue);
                        } else {
                            g.setColor(Color.red);
                        }
                        int innerMarginPiece = (int) Math.round(sizeInnerCase * 0.2);
                        g.fillRect(ix + innerMarginPiece, iy + innerMarginPiece, sizeInnerCase - innerMarginPiece * 2, sizeInnerCase - innerMarginPiece * 2);
                    }

                    if (plateau.caseIsBeginning(i, j)){
                        g.setColor(Color.orange);
                        int innerMarginPiece = (int) Math.round(sizeInnerCase * 0.2);
                        g.fillOval(ix + innerMarginPiece, iy + innerMarginPiece, sizeInnerCase - innerMarginPiece * 2, sizeInnerCase - innerMarginPiece * 2);
                    }
                }
            }

            drawStringCentered(g, "Individu " + individu.getId() + " (Score: "+individu.evaluate()+")", x, y - sizeInnerCase / 2, sizeCase);
            x += sizeCase + sizeInnerCase * 2;
            if (x > largeurFen - (sizeCase + sizeInnerCase * 2)) {
                x = margin;
                y += sizeCase + sizeInnerCase * 2;
            }
        }

        x = (sizeCase + sizeInnerCase * 2) * nbSols + margin;
        y = margin;
        int width = getWidth() - x - sizeInnerCase * 2;
        int height = getHeight() - margin * 2;
        int timeStep = width / (table_moyenne.size() + 1);
        int bottom = y + height;

        double maxMoyenne = Collections.max(table_moyenne);
        double maxBest = Collections.max(table_best);
        double maxGraph = Math.max(maxBest, maxMoyenne);

        double minMoyenne = Collections.min(table_moyenne);
        double minWorst = Collections.min(table_worst);
        double minGraph = Math.min(minWorst, minMoyenne);

        double betweenGraph = maxGraph - minGraph;

        g.setColor(Color.black);
        g.drawRect(x, y, ((table_best.size() - 1) * timeStep + 1), height);
        g.drawString("Max: " + maxGraph, x + sizeInnerCase / 2, y - sizeInnerCase / 2);
        g.drawString("Min: " + minGraph, x + sizeInnerCase / 2, y + height + sizeInnerCase);

        int tableIndex = 0;
        Color[] tablesColor = new Color[]{Color.green, Color.orange, Color.red};
        ArrayList<ArrayList<Double>> tables = new ArrayList<>();
        tables.add(table_best);
        tables.add(table_moyenne);
        tables.add(table_worst);

        for (ArrayList<Double> table : tables){
            int dx = x;
            int dy = bottom;
            int prevLineX = -1;
            int prevLineY = -1;
            for (Double value : table) {
                dy = bottom - (int) (((value - minGraph) / (maxGraph - minGraph)) * height);
                dy = Math.min(dy, bottom);

                g.setColor(Color.black);
                // Affichage des moyennes
                //g.drawString("" + value, dx, dy - sizeInnerCase/2);

                g.setColor(tablesColor[tableIndex]);
                // Affichage de points
                //g.fillRect(dx, dy, 5, 5);

                if(prevLineX >= 0 && prevLineY >= 0) g.drawLine(prevLineX, prevLineY, dx, dy);
                prevLineX = dx;
                prevLineY = dy;
                dx += timeStep;
            }
            tableIndex++;
        }

        //parcourir data, et
        //    pour chaque Individu ind = data[i],
        //      faire ind.dessine(g,largeurFen,hauteuFen,nbSols,i);
        //    qui doit faire en sorte que ind se dessine dans g, et dans le petit carré
        //    qui correspond à son indice :
    }

    private void drawStringCentered(Graphics g, String string, int x, int y, int width){
        g.setColor(Color.black);
        g.drawString(string, x + getXCoordinateCentered(g, string, width),y);
    }

    private int getXCoordinateCentered(Graphics g, String string, int width) {
        String message2 = string;
        int stringWidth = 0;
        int stringAccent = 0;
        int xCoordinate = 0;
        int yCoordinate = 0;
        // get the FontMetrics for the current font
        FontMetrics fm = g.getFontMetrics();


        /** display new message */
        /** Centering the text */
        // find the center location to display
        stringWidth = fm.stringWidth(message2);
        // get the position of the leftmost character in the baseline
        xCoordinate = width / 2 - stringWidth / 2;
        return xCoordinate;
    }

    public static void run() throws InterruptedException{
        frame= new JFrame("Generation");
        GUI m = new GUI();
        frame.getContentPane().add(m);
        frame.setSize(largeurFen + 200, hauteurFen);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        TimeUnit.SECONDS.sleep(1);
        frame.repaint();
        //ici, une des options du main lancera un calcul, et à chaque generation (qui sera stockée dans data), on fera frame.repaint();
    }

    public static void update(){
        update("Génération");
    }

    public static void update(String title){
        frame.repaint();
        frame.setTitle(title);
    }
}