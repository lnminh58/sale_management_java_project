/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import model.Config;

/**
 *
 * @author lnmin
 */
public class FileFactory {
    private static final String path = "config.dat";
    public static void saveFile(Config config) {
        try {

            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(config);
            oos.close();
            fos.close();
            JOptionPane.showMessageDialog(null, "Lưu thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lưu thành công!");
        }
    }

    public static boolean loadFile(){
        Config config = new Config();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object data = ois.readObject();
            config =  (Config) data;
            ois.close();
            fis.close();
            ConnectDatabase.DB_URL= "jdbc:sqlserver://"+config.getDatabaseIP()+";";
            ConnectDatabase.DATABASENAME ="databaseName="+config.getDatabaseName()+";" ;
            ConnectDatabase.USER = "user="+ config.getUser() +";";
            ConnectDatabase.PASS=   "password="+config.getPassword();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tải cấu hình thất bại!");
            return false;
        }
    }

}
