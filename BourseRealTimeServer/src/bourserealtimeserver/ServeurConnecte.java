/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourserealtimeserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ammach
 */
public class ServeurConnecte extends Thread {

    ServerSocket reception;
    static final int port = 40000;

    public ServeurConnecte() {
        try {
            reception = new ServerSocket(port);
            System.out.println("Le serveur est en écoute sur le " + port);
        } catch (IOException e) {
            System.exit(1);
        }
        this.start();
    }

    public void run() {
        Socket sock;
        Traitement t;
        try {
            System.out.println("votre adresse est  " + InetAddress.getLocalHost().getHostName());
            while (true) {
                System.out.println("Le serveur est en attente ");
                sock = reception.accept();
                System.out.println("Le serveur a accepté la connexion avec " + sock.getInetAddress());
                t = new Traitement(sock);
                t.join();
                sock.close();
            }
        } catch (IOException e) {
        } catch (InterruptedException ex) {
            Logger.getLogger(ServeurConnecte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
