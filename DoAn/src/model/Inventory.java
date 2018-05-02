/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lnminh
 */
public class Inventory {
    private String ID;
    private String name;
    private int numInventory;

    public Inventory() {
    }

    public Inventory(String ID, String name, int numInventory) {
        this.ID = ID;
        this.name = name;
        this.numInventory = numInventory;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumInventory() {
        return numInventory;
    }

    public void setNumInventory(int numInventory) {
        this.numInventory = numInventory;
    }

    
}
