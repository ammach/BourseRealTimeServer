/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;


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
public class ActionThread extends Thread{
    
    ArrayList<Action> actions;
    Socket socket;
    String data;
    
    public ActionThread(Socket s,String data) {
        this.socket=s;
        this.data=data;      
        this.start();
    }

    
    
    @Override
    public void run() {
        super.run(); 
        while (true) {              
            try {
                actions=new ArrayList<Action>(); 
                actions.add(new Action("action", "action", "action", "action", "action"));
                JSONObject jSONObject = new JSONObject(data);
                JSONObject list = jSONObject.getJSONObject("list");
                JSONArray resources = list.getJSONArray("resources");

                for (int i = 0; i < resources.length(); i++) {
                    JSONObject item = (JSONObject) resources.getJSONObject(i);
                    JSONObject resource = item.getJSONObject("resource");
                    JSONObject fields = resource.getJSONObject("fields");
                    
                    String name = fields.getString("name");
                    String price = fields.getString("price");
                    String change = fields.getString("change");
                    String day_high = fields.getString("day_high");
                    String day_low = fields.getString("day_low");
                    Action action=new Action(name, price, change, day_high, day_low);
                    actions.add(action);
                    System.out.println("name: " + name + " price: " + price + " change: " + change + " day_high: " + day_high + " day_low: " + day_low);
                }
                System.out.println("");
                System.out.println("");
                ClientConnecte clientConnecte=new ClientConnecte();
	        clientConnecte.connexion(socket.getInetAddress().getHostAddress().toString(), 40000);
	        clientConnecte.envoiObject(actions);
               // clientConnecte.fermer();
            this.sleep(6000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ChangeThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
