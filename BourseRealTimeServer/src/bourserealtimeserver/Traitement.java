/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourserealtimeserver;

import classes.Client;
import dao.Users;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ammach
 */
public class Traitement extends Thread {

    Socket sock;
    ObjectInputStream objectInputStream;
    int i;

    public Traitement(Socket socket) {
        sock = socket;
        this.i=i;
        try {
            objectInputStream =new ObjectInputStream(sock.getInputStream());        
        } catch (IOException e) {
        }
        this.start();
    }

    public void run() {
        try {   
            Client  client = (Client) objectInputStream.readObject();
            Users.clients.add(client);
            System.out.println("liste des clients: ");
            for (Client c   : Users.clients) {
                System.out.println("name: "+c.getName()+" password: "+c.getPassword()); 
            }
            ClientConnecte clientConnecte=new ClientConnecte();
	    clientConnecte.connexion(sock.getInetAddress().getHostAddress().toString(), 40000);
	    clientConnecte.envoi("vous etes connecté");           
            clientConnecte.fermer();
       //     ChangeThread changeThread=new ChangeThread(sock);      
         //   ActionThread actionThread=new ActionThread(sock);  
            sock.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
        }
    }

}
