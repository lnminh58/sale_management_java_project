/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author PC
 */
public class Order {
    String ID;
    Date time;
    String customer;    
    String salerID;
    String shipperID;
    Vector<Goods> Orderdetail;

    public Order() {
    }

    public Order(String ID, Date time, String customer, String salerID, String shipperID) {
        this.ID = ID;
        this.time = time;
        this.customer = customer;
        this.salerID = salerID;
        this.shipperID = shipperID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSalerID() {
        return salerID;
    }

    public void setSalerID(String salerID) {
        this.salerID = salerID;
    }

    public String getShipperID() {
        return shipperID;
    }

    public void setShipperID(String shipperID) {
        this.shipperID = shipperID;
    }

    public Vector<Goods> getOrderdetail() {
        return Orderdetail;
    }

    public void setOrderdetail(Vector<Goods> Orderdetail) {
        this.Orderdetail = Orderdetail;
    }
    
    
}
