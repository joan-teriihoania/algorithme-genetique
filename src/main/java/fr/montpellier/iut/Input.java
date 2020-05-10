package fr.montpellier.iut;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Input {
    public static boolean getInputBoolean(String string){
        char toreturn = 'm';
        while(toreturn != 'y' && toreturn != 'n'){
            System.out.print("[ENTREE] " + string + " (y/n) : ");
            Scanner sc = new Scanner(System.in);
            toreturn = Character.toLowerCase(sc.nextLine().charAt(0));
            if(toreturn != 'y' && toreturn != 'n') System.out.println("[ERREUR] Caractère '"+toreturn+"' entré invalide.\n");
        }

        return toreturn == 'y';
    }

    public static String getInputString(String string) {
        String toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.next();
        return toreturn;
    }

    public static String getInputFilename(String string) {
        return getInputFilename(string, "", true);
    }

    public static String getInputFilename(String string, String path){
        return getInputFilename(string, path, true);
    }

    public static String getInputFilename(String string, String path, boolean verif) {
        String toreturn = getInputString(string);
        File f = new File(path + "/" + toreturn);
        if (verif && !f.exists()){
            System.out.println("[ERREUR] Le fichier '"+toreturn+"' n'existe pas.\n");
            return getInputFilename(string, path, verif);
        }
        return toreturn;
    }

    public static int getInputInt(String string) {
        int toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.nextInt();
        return toreturn;
    }

    public static double getInputDouble(String string) {
        double toreturn;
        System.out.print("[ENTREE] " + string + " : ");
        Scanner sc = new Scanner(System.in);
        toreturn = sc.nextDouble();
        return toreturn;
    }

    public static String executeGet(String targetURL, String urlParameters) throws IOException {
        URLConnection connection = new URL(targetURL + "?" + urlParameters).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        InputStream response = connection.getInputStream();
        try (Scanner scanner = new Scanner(response)) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
