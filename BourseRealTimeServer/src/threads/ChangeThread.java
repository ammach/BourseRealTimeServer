/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import bourserealtimeserver.ClientConecte;
import bourserealtimeserver.ClientConnecte;
import classes.Action;
import classes.Change;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ammach
 */
public  class ChangeThread extends Thread{

    ArrayList<Change> changes;
    String data;
    Socket socket;
    public ChangeThread(Socket s,String data) {
        this.socket=s;
        this.data=data;

        this.start();
    }

    
    
    @Override
    public void run() {
        super.run(); 
        
        while (true) {              
            try {
                changes=new ArrayList<Change>();
                changes.add(new Change("change", "change", "change"));
                JSONObject jSONObject = new JSONObject(data);
                JSONObject list = jSONObject.getJSONObject("list");
                JSONArray resources = list.getJSONArray("resources");
                
                for (int i = 0; i < 4; i++) {
                    JSONObject item = (JSONObject) resources.getJSONObject(i);
                    JSONObject resource = item.getJSONObject("resource");
                    JSONObject fields = resource.getJSONObject("fields");
                    
                    String name = fields.getString("name");
                    String price = fields.getString("price");
                    String utctime = fields.getString("utctime");
                    
                    Change change=new Change(name, price, utctime);
                    changes.add(change);
                    System.out.println("name: " + name + " price: " + price + " utctime: " + utctime);
                }
                    System.out.println("");
                    System.out.println("");
                ClientConecte clientConnecte=new ClientConecte();
	        clientConnecte.connexion(socket.getInetAddress().getHostAddress().toString(), 40000);
	        clientConnecte.envoiObject(changes);           
               // clientConnecte.fermer();
            this.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ChangeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
