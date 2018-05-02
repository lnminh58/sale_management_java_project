/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author lnminh
 */
public class Config implements Serializable{
   private String databaseIP;
   private String databaseName;
   private String user;
   private String password;

    public Config() {
        
    }

    public Config(String databaseIP, String databaseName, String user, String password) {
        this.databaseIP = databaseIP;
        this.databaseName = databaseName;
        this.user = user;
        this.password = password;
    }

    public String getDatabaseIP() {
        return databaseIP;
    }

    public void setDatabaseIP(String databaseIP) {
        this.databaseIP = databaseIP;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Config{" + "databaseIP=" + databaseIP + ", databaseName=" + databaseName + ", user=" + user + ", password=" + password + '}';
    }
    
   
}
