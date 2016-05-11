/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.Serializable;

/**
 *
 * @author ammach
 */
public class Change implements Serializable{
    
    private static final long serialVersionUID = 52L;
    
    String name;
    String price;
    String utctime;

    public Change(String name, String price, String utctime) {
        this.name = name;
        this.price = price;
        this.utctime = utctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUtctime() {
        return utctime;
    }

    public void setUtctime(String utctime) {
        this.utctime = utctime;
    }

   
}
