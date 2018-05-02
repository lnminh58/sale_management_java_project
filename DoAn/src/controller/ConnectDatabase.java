/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lnminh
 */
public class ConnectDatabase {
 
    static String JDBC_DRIVER =  "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static  String DB_URL =    "";          //  "jdbc:sqlserver://localhost;";
    static  String DATABASENAME = "";       //  "databaseName=quanlybanhang;";
    static  String USER =  "";              //  "user=sa;";
    static  String PASS ="";                //  "password=ngocminh58";

    public static Connection getConnectDatabase() {
        Connection conn = null;
        try {

            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL + DATABASENAME + USER + PASS);
        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới Cơ sở dữ liệu!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    public static void main(String[] args) {
        getConnectDatabase();
    }
}
