/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourserealtimeserver;

import classes.Client;
import dao.Users;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import threads.ActionThread;
import threads.ChangeThread;

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
        URL url;
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
 
            url = new URL(null, "http://finance.yahoo.com/webservice/v1/symbols/YHOO,AAPL,BAC,CHK,LC,NOC,VRX,SODA,CROX,MKTO/quote?format=json&view=detail", new sun.net.www.protocol.https.Handler());
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            InputStream is = con.getInputStream();
            InputStreamReader isreader = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isreader);
            String s = "";
            String resultAction = "";
            while ((s = reader.readLine()) != null) {
                resultAction += s;
            }
            ActionThread actionThread=new ActionThread(sock,resultAction);  
            
            url = new URL(null, "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json", new sun.net.www.protocol.https.Handler());
            con = (HttpsURLConnection) url.openConnection();
            is = con.getInputStream();
            isreader = new InputStreamReader(is);
            reader = new BufferedReader(isreader);
            s = "";
            String resultChange = "";
            while ((s = reader.readLine()) != null) {
                resultChange += s;
            }
            new ChangeThread(sock,resultChange);
            sock.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException ex) {
            System.out.println("class not found");
        }
    }

}
