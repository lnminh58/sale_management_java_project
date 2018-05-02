/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Confirm;
import controller.ConnectDatabase;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Customer;

/**
 *
 * @author lnminh
 */
public class frmCustomer extends javax.swing.JFrame {

    private Vector<String> TableTitle;
    private Vector<Vector> TableData;
    private Vector<Customer> listCuss;
    /**
     * Creates new form frmEmployee
     */
    public frmCustomer() {
        initComponents();
        addWindowListener(Confirm.disposeListener(this));
        loadData();

    }

    public void loadData() {
        loadTableCus();
    }


    private void loadTableCus() {
        loadTableTitle();
        loadTableData();
        showOnTable();
    }

    private void loadTableTitle() {
        TableTitle =new Vector<>();
        TableTitle.add("Mã số");
        TableTitle.add("Tên");
        TableTitle.add("Số điện thoại");
        TableTitle.add("Địa chỉ");
    }

    private void loadTableData() {
        listCuss = new Vector<>();
                
        TableData =new Vector<>();
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_getCustomerInfo}";
            try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    listCuss.add(new Customer(rs.getString("maKH"),rs.getString("tenKh"),rs.getString("diachiKH"),rs.getString("sdtKH")));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
        for (Customer cus : listCuss) {
            Vector<String> temp=new Vector<>();
            temp.add(cus.getID());
            temp.add(cus.getName());
            temp.add(cus.getAddress());
            temp.add(cus.getPhoneNumber());
            TableData.add(temp);
        }
        
    }
       private void showOnTable() {
        DefaultTableModel dtm = new DefaultTableModel(TableData,TableTitle){
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
            
        };
        tblCustomer.setModel(dtm);
    }
       private void showToEditText() {
        int selectedRow = tblCustomer.getSelectedRow();
        txtID.setText((String) tblCustomer.getValueAt(selectedRow, 0));
        txtName.setText((String) tblCustomer.getValueAt(selectedRow, 1));
        txtPhoneNum.setText((String) tblCustomer.getValueAt(selectedRow, 3));
        txtAddress.setText((String) tblCustomer.getValueAt(selectedRow, 2));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnCustomer = new javax.swing.JPanel();
        scCustomer = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtPhoneNum = new javax.swing.JTextField();
        lblPhoneNum = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        btnDel = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        lblIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        pnCustomer.setBackground(new java.awt.Color(225, 112, 112));

        tblCustomer.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        scCustomer.setViewportView(tblCustomer);

        lblID.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(51, 51, 51));
        lblID.setText("Mã khách hàng");
        lblID.setToolTipText("");

        txtID.setColumns(20);

        txtName.setColumns(20);

        lblName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(51, 51, 51));
        lblName.setText("Tên khách hàng");
        lblName.setToolTipText("");

        txtPhoneNum.setColumns(20);

        lblPhoneNum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPhoneNum.setForeground(new java.awt.Color(51, 51, 51));
        lblPhoneNum.setText("Số điện thoại");
        lblPhoneNum.setToolTipText("");

        lblAddress.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAddress.setForeground(new java.awt.Color(51, 51, 51));
        lblAddress.setText("Địa chỉ");
        lblAddress.setToolTipText("");

        txtAddress.setColumns(20);

        btnDel.setBackground(new java.awt.Color(255, 255, 255));
        btnDel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnDel.setForeground(new java.awt.Color(0, 0, 0));
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/del_button.png"))); // NOI18N
        btnDel.setText("Xóa");
        btnDel.setContentAreaFilled(false);
        btnDel.setOpaque(true);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_button.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setContentAreaFilled(false);
        btnAdd.setOpaque(true);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/customer.png"))); // NOI18N

        javax.swing.GroupLayout pnCustomerLayout = new javax.swing.GroupLayout(pnCustomer);
        pnCustomer.setLayout(pnCustomerLayout);
        pnCustomerLayout.setHorizontalGroup(
            pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCustomerLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 40, Short.MAX_VALUE))
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCustomerLayout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDel))
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAddress))
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addComponent(lblPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(scCustomer)
        );
        pnCustomerLayout.setVerticalGroup(
            pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCustomerLayout.createSequentialGroup()
                .addComponent(scCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnCustomerLayout.createSequentialGroup()
                        .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblIcon))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
      showToEditText();
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
 
        addNewCus();
        loadData();
        
  // TODO add your handling code here:
    }//GEN-LAST:event_btnAddActionPerformed
    
     private void addNewCus() {
               if (txtID.getText().trim().equalsIgnoreCase("")||
               txtName.getText().trim().equalsIgnoreCase("")||
                txtAddress.getText().trim().equalsIgnoreCase("")||
                 txtPhoneNum.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Điền vào đầy đủ");
            return;
        }
             try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_addNewCustomer(?,?,?,?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, txtID.getText());
                cstmt.setString(2, txtName.getText());
                cstmt.setString(3, txtAddress.getText());
                cstmt.setString(4, txtPhoneNum.getText());
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi thêm dữ liệu");
        }
            
    }
    
    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delCus();
        loadData();
    }//GEN-LAST:event_btnDelActionPerformed
    private void delCus() {
               if (txtID.getText().trim().equalsIgnoreCase(""))
                {
            JOptionPane.showMessageDialog(this, "Xin điền vào mã khách hàng");
            return;
        }
             try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delCustomer(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, txtID.getText());
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhoneNum;
    private javax.swing.JPanel pnCustomer;
    private javax.swing.JScrollPane scCustomer;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhoneNum;
    // End of variables declaration//GEN-END:variables

    
   
}
