/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.MessageDigest;
import javax.swing.JOptionPane;

/**
 *
 * @author lnminh
 */
public class Encrypt {
    public static String encryptmd5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
      
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xf0), 16).substring(0,1));
            }
            return  sb.toString();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Mã hóa thất bại");
            return "";
        }
        
    }
    public static void main(String[] args) {
        System.out.println(encryptmd5("123456"));
    }
}
