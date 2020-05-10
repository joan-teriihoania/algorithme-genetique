package fr.montpellier.iut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/*
 * www.codeurjava.com
 */
public class Serveur {

    public static void main(String[] test) {

        final ServerSocket serveurSocket  ;
        final Socket clientSocket ;
        final BufferedReader in;
        final PrintWriter out;
        final Scanner sc=new Scanner(System.in);

        try {
            int port = getAvailablePort(1000, 2000);
            serveurSocket = new ServerSocket(port);

            String ipParameter = "";
            if (Input.getInputBoolean("Souhaitez-vous utiliser une IP spéciale ?")){
                ipParameter = Input.getInputString("Entrez l'IP que vous souhaitez utiliser");
                ipParameter = "ip=" + ipParameter + "&special=true&";
            }


            String portParameter = "port=" + port;
            if (Input.getInputBoolean("Souhaitez-vous utiliser un port spécifique ?")){
                port = Input.getInputInt("Entrez le port que vous souhaitez utiliser");
                portParameter = "port=" + port;
            }

            String webResponse = Input.executeGet("https://algo-genetique.glitch.me/register",
                    ipParameter + portParameter);

            while (webResponse.equals("Port " + port + " not reachable.")){
                System.out.println("[ERREUR] " + webResponse);
                System.out.println("[INFORM] Utilisez une adresse spéciale pour désactiver la vérification de port.");
                System.out.println("[INFORM] Cette manipulation n'est recommandée que pour des réseaux locaux.\n");
                port = Input.getInputInt("Entrez le port que vous souhaitez utiliser");
                portParameter = "port=" + port;
                webResponse = Input.executeGet("https://algo-genetique.glitch.me/register",
                        ipParameter + portParameter);
            }

            String[] registerResponse = webResponse.split(";");
            String id = registerResponse[0];
            String ip = registerResponse[1];
            System.out.println("Server ID registered : " + id);
            System.out.println("Server IP registered : " + ip + ":" + port);

            Thread ping= new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(5*1000);
                            Input.executeGet("https://algo-genetique.glitch.me/ping", "session_id=" + id);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {

                        }
                    }
                }
            });
            ping.start();

            clientSocket = serveurSocket.accept();
            ping.interrupt();
            System.out.println("Connection established");

            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));
            Thread envoi= new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            envoi.start();

            Thread recevoir= new Thread(new Runnable() {
                String msg ;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        //tant que le client est connecté
                        while(msg!=null){
                            System.out.println("Client : "+msg);
                            msg = in.readLine();
                        }
                        //sortir de la boucle si le client a déconecté
                        System.out.println("Client déconecté");
                        //fermer le flux et la session socket
                        out.close();
                        clientSocket.close();
                        serveurSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recevoir.start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getAvailablePort(int min, int max){
        for (int i = min; i < max;i++){
            if (isPortAvailable(i, min, max)) return i;
        }
        return 5000;
    }

    /**
     * Checks to see if a specific port is available.
     *
     * @param port the port to check for availability
     */
    public static boolean isPortAvailable(int port, int min, int max) {
        if (port < min || port > max) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

}