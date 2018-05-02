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
 * @author lnminh
 */
public class GoodsReceipt {
    String ID;
    Date time;
    String supplier;
    Vector<Goods> GRdetail;

    public GoodsReceipt(String ID, Date time, String supplier) {
        this.ID = ID;
        this.time = time;
        this.supplier = supplier;
     
    }

    public GoodsReceipt() {
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Vector<Goods> getGRdetail() {
        return GRdetail;
    }

    public void setGRdetail(Vector<Goods> GRdetail) {
        this.GRdetail = GRdetail;
    }
    
    
    
}
