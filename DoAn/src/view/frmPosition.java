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
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Position;


/**
 *
 * @author lnminh
 */
public class frmPosition extends javax.swing.JFrame {
    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private Vector<String> TableTitle;
    private Vector<Vector> TableData;
    private Vector<Position> listPosition;
    /**
     * Creates new form frmPosition
     */
    public frmPosition() {
        initComponents();
        loadData();
        addWindowListener(Confirm.disposeListener(this));
    }
    private void loadData() {
        loadTablePosition();
    }
    private void loadTablePosition() {
        loadTableTitle();
        loadTableData();
        showOnTable();
    }

    private void loadTableTitle() {
        TableTitle = new Vector<>();
        TableTitle.add("Mã Chức Vụ");
        TableTitle.add("Tên Chức Vụ");
        TableTitle.add("Lương Cơ Bản");
    }

    private void loadTableData() {
        listPosition = new Vector<>();
        TableData = new Vector<>();
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_getPosition}";
            try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    listPosition.add(new Position(rs.getString("maChucVu"),rs.getString("tenChucVu"),
                                                  rs.getInt("luongCoBan")));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
        for (Position pos : listPosition) {
            Vector<String> temp = new Vector<>();
            temp.add(pos.getID());
            temp.add(pos.getName());
            temp.add(moneyFormater.format(pos.getBaseSalary()));
            TableData.add(temp);
        }
    }
    
    private void showOnTable() {
        DefaultTableModel dtm = new DefaultTableModel(TableData, TableTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblPosition.setModel(dtm);
    }
    
    private void showToEditText() {
        int selectedRow = tblPosition.getSelectedRow();
        txtID.setText((String) tblPosition.getValueAt(selectedRow,0));
        txtName.setText((String) tblPosition.getValueAt(selectedRow,1));
        txtBaseSalary.setText((String) tblPosition.getValueAt(selectedRow,2));
    }
    
    private void addNewPosition() {
        if (txtID.getText().trim().equalsIgnoreCase("")||
            txtName.getText().trim().equalsIgnoreCase("")||
            txtBaseSalary.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền đầy đủ các thông tin");
            txtID.requestFocus();
            return;
        }
        String sql = "{call sp_addNewPos(?,?,?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
             CallableStatement cstmt = conn.prepareCall(sql);) {
             cstmt.setString(1, txtID.getText());
             cstmt.setString(2, txtName.getText());
             cstmt.setString(3, txtBaseSalary.getText());
             cstmt.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate key")) {
                JOptionPane.showMessageDialog(this, "Lỗi trùng dữ liệu");
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu \n" + ex.getMessage());
            }
        }
    }
    private void delPosition() {
        if (txtID.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền vào mã chức vụ");
            return;
        }
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delPos(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, txtID.getText());
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }
    }
    private void ediPosition() {
        if (txtID.getText().trim().equalsIgnoreCase("")||
            txtName.getText().trim().equalsIgnoreCase("")||
            txtBaseSalary.getText().trim().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền đầy đủ các thông tin");
            txtID.requestFocus();
            return;
        }
        String sql = "{call sp_editPos(?,?,?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
             CallableStatement cstmt = conn.prepareCall(sql);) {
             cstmt.setString(1, txtID.getText());
             cstmt.setString(2, txtName.getText());
             cstmt.setString(3, txtBaseSalary.getText());
             cstmt.executeUpdate();
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật dữ liệu \n");
            
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnPosition = new javax.swing.JPanel();
        scPosition = new javax.swing.JScrollPane();
        tblPosition = new javax.swing.JTable();
        lblID = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lbName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lbBaseSalary = new javax.swing.JLabel();
        txtBaseSalary = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        lblIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        pnPosition.setBackground(new java.awt.Color(18, 185, 45));

        tblPosition.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tblPosition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tblPosition.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPositionMouseClicked(evt);
            }
        });
        scPosition.setViewportView(tblPosition);

        lblID.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(255, 255, 255));
        lblID.setText("Mã chức vụ");
        lblID.setToolTipText("");

        txtID.setColumns(20);

        lbName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbName.setForeground(new java.awt.Color(255, 255, 255));
        lbName.setText("Tên chức vụ");
        lbName.setToolTipText("");

        txtName.setColumns(20);

        lbBaseSalary.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbBaseSalary.setForeground(new java.awt.Color(255, 255, 255));
        lbBaseSalary.setText("Lương cơ bản");
        lbBaseSalary.setToolTipText("");

        txtBaseSalary.setColumns(20);

        btnEdit.setBackground(new java.awt.Color(255, 255, 255));
        btnEdit.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit_button.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setContentAreaFilled(false);
        btnEdit.setOpaque(true);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_button.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setContentAreaFilled(false);
        btnAdd.setOpaque(true);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setBackground(new java.awt.Color(255, 255, 255));
        btnDel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/del_button.png"))); // NOI18N
        btnDel.setText("Xóa");
        btnDel.setContentAreaFilled(false);
        btnDel.setOpaque(true);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/position.png"))); // NOI18N

        javax.swing.GroupLayout pnPositionLayout = new javax.swing.GroupLayout(pnPosition);
        pnPosition.setLayout(pnPositionLayout);
        pnPositionLayout.setHorizontalGroup(
            pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scPosition)
            .addGroup(pnPositionLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnPositionLayout.createSequentialGroup()
                        .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtID))
                    .addGroup(pnPositionLayout.createSequentialGroup()
                        .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnPositionLayout.createSequentialGroup()
                        .addComponent(lbBaseSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBaseSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnPositionLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(34, 34, 34))
        );
        pnPositionLayout.setVerticalGroup(
            pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPositionLayout.createSequentialGroup()
                .addComponent(scPosition, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnPositionLayout.createSequentialGroup()
                        .addGroup(pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbBaseSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBaseSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnPositionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPosition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPositionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPositionMouseClicked
        showToEditText();
    }//GEN-LAST:event_tblPositionMouseClicked
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addNewPosition();
        loadTablePosition();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delPosition();
        loadTablePosition();   
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        ediPosition();
        loadTablePosition(); 
    }//GEN-LAST:event_btnEditActionPerformed

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
            java.util.logging.Logger.getLogger(frmPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPosition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPosition().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel lbBaseSalary;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JPanel pnPosition;
    private javax.swing.JScrollPane scPosition;
    private javax.swing.JTable tblPosition;
    private javax.swing.JTextField txtBaseSalary;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    

    
}
