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
public class Goods {
    String goodsID;
    String goodsName;
    int num;
    int price;

    public Goods() {
    }

    public Goods(String goodsID, String goodsName, int num, int price) {
        this.goodsID = goodsID;
        this.goodsName = goodsName;
        this.num = num;
        this.price = price;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

   
     
}
