/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

/**
 *
 * @author lnminh
 */
public class Confirm {
    public static final WindowListener exitListener = new WindowAdapter() {

    @Override
    public void windowClosing(WindowEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
             null, "Bạn có chắc chắn thoát?", 
             "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
           System.exit(0);
        }
    }
};
    
   public static WindowListener disposeListener(Window dialog){
        WindowListener disposeListener = new WindowAdapter() {

    @Override
    public void windowClosing(WindowEvent e) {
        int confirm = JOptionPane.showConfirmDialog(
             null, "Bạn có chắc chắn tắt màn hình? Mọi dữ liệu đã điền sẽ mất", 
             "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
           dialog.dispose();
        }
    }
};
    return disposeListener;
   }


}
