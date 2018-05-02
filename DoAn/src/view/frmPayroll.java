/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ConnectDatabase;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Payroll;

/**
 *
 * @author PC
 */
public class frmPayroll extends javax.swing.JFrame {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private String selectedDay;
    private Vector<String> tableTitle;
    private Vector<Vector> tableData;
    private Vector<Payroll> listPayroll;

    /**
     * Creates new form frmPayroll
     */
    public frmPayroll() {
        initComponents();
        loadData();

    }

    private void loadData() {
        loadPayrollTime();
        loadTablePayroll();
    }

    private void loadPayrollTime() {
        cbPayday.removeAllItems();
        cbPayday.addItem("Tất cả");

        String sql = "{call sp_getPayrollTime}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    cbPayday.addItem(sdf.format(rs.getDate("ngayTinhLuong")));
                }
            }
            ;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }

    }

    private void loadTablePayroll() {
        loadTableTitle();
        loadTableData();
        showOnTable();
    }

    private void loadTableTitle() {
        tableTitle = new Vector<>();
        tableTitle.add("Mã nhân viên");
        tableTitle.add("Tên nhân viên");
        tableTitle.add("Ngày tính lương");
        tableTitle.add("Lương cơ bản");
        tableTitle.add("Thưởng");
        tableTitle.add("Trợ cấp");
        tableTitle.add("Buổi vắng");
        tableTitle.add("Thực Nhận");
    }

    private void loadTableData() {
        listPayroll = new Vector<>();
        tableData = new Vector<>();
        if (selectedDay.equalsIgnoreCase("Tất cả")) {
           
            try (Connection conn = ConnectDatabase.getConnectDatabase()) {
                String sql="";
                if(cboStatus.isSelected()){
                     sql = "{call sp_getPayrollAll}";
                }else{
                     sql = "{call sp_getPayroll}";
                }
                try (CallableStatement cstmt = conn.prepareCall(sql)) {
                    try (ResultSet rs = cstmt.executeQuery()) {
                        while (rs.next()) {
                            listPayroll.add(new Payroll(rs.getString("maNV"), rs.getString("tenNV"),
                                    rs.getDate("ngayTinhLuong"),
                                    rs.getInt("luongCoBan"),
                                    rs.getInt("thuong"),
                                    rs.getInt("phuCap"),
                                    rs.getInt("buoiVang"),
                                    rs.getInt("thucNhan")));
                        }
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
            }
        } else {
            
              String sql="";
                if(cboStatus.isSelected()){
                     sql = "{call sp_getPayrollWithDateAll(?)}";
                }else{
                     sql = "{call sp_getPayrollWithDate(?)}";
                }
            try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
                String[] split = selectedDay.split("/");
                String date = split[1] + "-" + split[0] + "-01";
                cstmt.setDate(1, Date.valueOf(date));

                try (ResultSet rs = cstmt.executeQuery()) {
                    while (rs.next()) {
                        listPayroll.add(new Payroll(rs.getString("maNV"), rs.getString("tenNV"),
                                rs.getDate("ngayTinhLuong"),
                                rs.getInt("luongCoBan"),
                                rs.getInt("thuong"),
                                rs.getInt("phuCap"),
                                rs.getInt("buoiVang"),
                                rs.getInt("thucNhan")));
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
            }
        }
        for (Payroll payroll : listPayroll) {
            Vector<String> temp = new Vector<>();
            temp.add(payroll.getIDEmp());
            temp.add(payroll.getNameEmp());
            temp.add(sdf.format(payroll.getTime()));
            temp.add(moneyFormater.format(payroll.getBaseSalary()));
            temp.add(moneyFormater.format(payroll.getBonus()));
            temp.add(moneyFormater.format(payroll.getAllowance()));
            temp.add(Integer.toString(payroll.getDayOff()));
            temp.add(moneyFormater.format(payroll.getRealSalary()));
            tableData.add(temp);
        }
    }

    private void showOnTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableData, tableTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblPayroll.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnPayroll = new javax.swing.JPanel();
        scPayroll = new javax.swing.JScrollPane();
        tblPayroll = new javax.swing.JTable();
        cbPayday = new javax.swing.JComboBox<>();
        bnNewTable = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        lblIcon = new javax.swing.JLabel();
        bnNewRefresh = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        cboStatus = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 500));
        setResizable(false);

        pnPayroll.setBackground(new java.awt.Color(18, 185, 45));
        pnPayroll.setPreferredSize(new java.awt.Dimension(670, 350));

        tblPayroll.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "null", "Title 2", "Title 3", "Title 4"
            }
        ));
        scPayroll.setViewportView(tblPayroll);

        cbPayday.setBackground(new java.awt.Color(255, 255, 255));
        cbPayday.setEditable(true);
        cbPayday.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cbPayday.setForeground(new java.awt.Color(0, 0, 0));
        cbPayday.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbPayday.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbPayday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPaydayActionPerformed(evt);
            }
        });

        bnNewTable.setBackground(new java.awt.Color(255, 255, 255));
        bnNewTable.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        bnNewTable.setForeground(new java.awt.Color(0, 0, 0));
        bnNewTable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_button.png"))); // NOI18N
        bnNewTable.setText("Tạo Bảng Mới");
        bnNewTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bnNewTable.setContentAreaFilled(false);
        bnNewTable.setOpaque(true);
        bnNewTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnNewTableActionPerformed(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblTotal.setText("Ngày tính lương");
        lblTotal.setToolTipText("");

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/salary.png"))); // NOI18N

        bnNewRefresh.setBackground(new java.awt.Color(255, 255, 255));
        bnNewRefresh.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        bnNewRefresh.setForeground(new java.awt.Color(0, 0, 0));
        bnNewRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh_button.png"))); // NOI18N
        bnNewRefresh.setText("Làm Mới");
        bnNewRefresh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bnNewRefresh.setContentAreaFilled(false);
        bnNewRefresh.setOpaque(true);
        bnNewRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bnNewRefreshActionPerformed(evt);
            }
        });

        btnDel.setBackground(new java.awt.Color(255, 255, 255));
        btnDel.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnDel.setForeground(new java.awt.Color(0, 0, 0));
        btnDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/del_button.png"))); // NOI18N
        btnDel.setText("Xóa");
        btnDel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnDel.setContentAreaFilled(false);
        btnDel.setOpaque(true);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        cboStatus.setBackground(new java.awt.Color(18, 185, 45));
        cboStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cboStatus.setText("Đã Nghỉ Việc");
        cboStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboStatusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnPayrollLayout = new javax.swing.GroupLayout(pnPayroll);
        pnPayroll.setLayout(pnPayrollLayout);
        pnPayrollLayout.setHorizontalGroup(
            pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPayrollLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbPayday, 0, 164, Short.MAX_VALUE)
                    .addComponent(bnNewTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnPayrollLayout.createSequentialGroup()
                        .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cboStatus))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(scPayroll)
        );
        pnPayrollLayout.setVerticalGroup(
            pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPayrollLayout.createSequentialGroup()
                .addComponent(scPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnPayrollLayout.createSequentialGroup()
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))
                    .addGroup(pnPayrollLayout.createSequentialGroup()
                        .addGap(0, 14, Short.MAX_VALUE)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbPayday, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboStatus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bnNewTable, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPayroll, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPayroll, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbPaydayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPaydayActionPerformed
        selectedDay = (String) cbPayday.getSelectedItem();

        if (selectedDay == null) {
            return;
        }

        loadTablePayroll();

    }//GEN-LAST:event_cbPaydayActionPerformed

    private void bnNewRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnNewRefreshActionPerformed
        loadData();
    }//GEN-LAST:event_bnNewRefreshActionPerformed

    private void bnNewTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnNewTableActionPerformed
        // TODO add your handling code here:
        requestCreatePayroll();
    }//GEN-LAST:event_bnNewTableActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delPayroll();
    }//GEN-LAST:event_btnDelActionPerformed

    private void cboStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboStatusMouseClicked
        loadTablePayroll();
    }//GEN-LAST:event_cboStatusMouseClicked

    private void delPayroll() {
        int selectedRow = tblPayroll.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        
        String ID =  (String) tblPayroll.getValueAt(selectedRow, 0);
        String time = (String) tblPayroll.getValueAt(selectedRow, 2);
        
        
        String sql = "{call sp_delPayroll(?,?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1,ID);
            String[] split = time.split("/");
            String date = split[1] + "-" + split[0] + "-01";
            cstmt.setDate(2, Date.valueOf(date));
            cstmt.executeUpdate();
            loadTablePayroll();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }
    }

    private void requestCreatePayroll() {
        frmCreatePayroll cp = new frmCreatePayroll();
        cp.setLocationRelativeTo(this);
        cp.setVisible(true);
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
            java.util.logging.Logger.getLogger(frmPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPayroll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnNewRefresh;
    private javax.swing.JButton bnNewTable;
    private javax.swing.JButton btnDel;
    private javax.swing.JComboBox<String> cbPayday;
    private javax.swing.JCheckBox cboStatus;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnPayroll;
    private javax.swing.JScrollPane scPayroll;
    private javax.swing.JTable tblPayroll;
    // End of variables declaration//GEN-END:variables

}
