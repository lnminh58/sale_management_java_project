/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PT
 */
public class Employee {
    private String ID;
    private String name;
    private String posittion;
    private String phoneNumber;
    private String address;
    private boolean status;

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

    public String getPosittion() {
        return posittion;
    }

    public void setPosittion(String posittion) {
        this.posittion = posittion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    

    public Employee(String ID, String name, String posittion, String phoneNumber, String address, boolean status) {
        this.ID = ID;
        this.name = name;
        this.posittion = posittion;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
    }

    

    public Employee() {
    }
    
    
}
