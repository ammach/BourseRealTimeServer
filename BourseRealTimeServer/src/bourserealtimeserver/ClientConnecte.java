package bourserealtimeserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by ammach on 4/1/2016.
 */
public class ClientConnecte {
    Socket sock=null;
    
    public void connexion(String host, int port)
    { try
    { 	System.out.println("Le client cherche � se connecter au serveur " + host + "@"+port);
        sock=new Socket(host, port);
        System.out.println("Le client s'est connect� sur serveur " + host + "@"+port);
    }
    catch(IOException e) { }
    }
    public void envoi(String data){
       try {
		DataOutputStream dataInputStream=new DataOutputStream(sock.getOutputStream());
		dataInputStream.writeUTF(data);
		dataInputStream.flush();
		dataInputStream.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    }
    public void envoiObject(Object data){
        try {
            ObjectOutputStream objectOutputStream =new ObjectOutputStream(sock.getOutputStream());
            objectOutputStream.writeObject(data);
            objectOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void recevoir(){
        try{
            System.out.println("Le client cherche � r�cup�rer le canal de communication ");
            BufferedReader entree=new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String text=entree.readLine();
            System.out.println(text);
        }
        catch(IOException e){
        }
    }
    public void fermer(){
        try{
            System.out.println("Le client ferme la connexion au serveur ");
            sock.close();
        }
        catch(IOException e){
        }
    }

    public Socket getSock() {
        return sock;
    }
}
