/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CheckText;
import controller.Confirm;
import controller.ConnectDatabase;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Payroll;

/**
 *
 * @author lnminh
 */
public class frmCreatePayroll extends javax.swing.JFrame {

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    private SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM");
    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private String currentTime;
    private String currentTimeSQL;
    private Vector<Vector> listEmp;
    private Vector<Payroll> listNewPayroll = new Vector<>();
    private Vector<String> tableTitle;
    private Vector<Vector> tableData;
    private int cboSelectedIndex;

    /**
     * Creates new form frmCreatePayroll
     */
    public frmCreatePayroll() {
        initComponents();
        loadData();
        addWindowListener(Confirm.disposeListener(this));

    }

    private void loadData() {
        
        loadCbEmp();
        setDefaultValue();
        loadTablePayroll();
    }

    private void loadTablePayroll() {
        loadTableTitle();
        loadTableData();
        showOnTable();
    }

    private void setDefaultValue() {
        getCurrentTime();
        txtTime.setText(currentTime);
        txtBonus.setText("0");
        txtAllowance.setText("0");
        txtDayOff.setText("0");
    }

    private void getCurrentTime() {

        currentTime = sdf.format((new Date()).getTime());
        currentTimeSQL = sdfSQL.format((new Date()).getTime()) + "-01";
    }

    private void loadCbEmp() {
        listEmp = new Vector<>();
        cbEmp.removeAllItems();
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
                String sql = "{call sp_getEmpForPayroll}";
            try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("maNV");
                    String name = rs.getString("tenNV");
                    int baseSalary = rs.getInt("luongCoBan");
                    cbEmp.addItem(name);
                    Vector temp = new Vector();
                    temp.add(id);
                    temp.add(name);
                    temp.add(baseSalary);
                    listEmp.add(temp);

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }

    }

    private void loadTableTitle() {
        tableTitle = new Vector<>();
        tableTitle.add("mã nhân viên");
        tableTitle.add("Tên nhân viên");
        tableTitle.add("Ngày tính lương");
        tableTitle.add("Lương cơ bản");
        tableTitle.add("Thưởng");
        tableTitle.add("Trợ cấp");
        tableTitle.add("Buổi vắng");
        tableTitle.add("Thực Nhận");
    }

    private void addToListPayroll() {
        cboSelectedIndex = cbEmp.getSelectedIndex();
        Vector emp = listEmp.get(cboSelectedIndex);
        int baseSalary = (int) emp.get(2);
        String name = (String) emp.get(1);
        String id = (String) emp.get(0);

        String sbonus = txtBonus.getText().trim().replace(",","");
        String sallowance = txtAllowance.getText().trim().replace(",","");
        String sdayOff =  txtDayOff.getText().trim();
     

        if (sbonus.equalsIgnoreCase("")
                || sallowance.equalsIgnoreCase("")
                || sdayOff.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền vào đầy đủ thông tin");
            return;
        }
        if (!CheckText.isInteger(sbonus)
                    || !CheckText.isInteger(sallowance)
                    || !CheckText.isInteger(sdayOff)) {
                JOptionPane.showMessageDialog(this, "Xin điền đúng kiểu thông tin");
                return;
         }
        
        for (Payroll payroll : listNewPayroll) {
            if(payroll.getIDEmp().equalsIgnoreCase(id)){
                JOptionPane.showMessageDialog(this, "Người này đã được chọn");
                return;
            }
        }
        int bonus = Integer.valueOf(sbonus);
        int allowance = Integer.valueOf(sallowance);
        int dayOff = Integer.valueOf(sdayOff);
        int realSalary = baseSalary + bonus + allowance - (baseSalary * dayOff / 60);

        listNewPayroll.add(new Payroll(id, name, new Date(), baseSalary, bonus, allowance, dayOff, realSalary));

    }

    private void loadTableData() {
        tableData = new Vector<>();
        for (Payroll payroll : listNewPayroll) {
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
        txtTime = new javax.swing.JTextField();
        lblTime = new javax.swing.JLabel();
        lblBonus = new javax.swing.JLabel();
        txtBonus = new javax.swing.JTextField();
        txtAllowance = new javax.swing.JTextField();
        lblAllowance = new javax.swing.JLabel();
        txtDayOff = new javax.swing.JTextField();
        lblDayOff = new javax.swing.JLabel();
        cbEmp = new javax.swing.JComboBox<>();
        lblEmp = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        scPayroll = new javax.swing.JScrollPane();
        tblPayroll = new javax.swing.JTable();
        btnAccept = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        pnPayroll.setBackground(new java.awt.Color(18, 185, 45));

        txtTime.setEditable(false);
        txtTime.setColumns(20);
        txtTime.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(51, 51, 51));
        lblTime.setText("Ngày Tính Lương");
        lblTime.setToolTipText("");

        lblBonus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblBonus.setForeground(new java.awt.Color(51, 51, 51));
        lblBonus.setText("Thưởng");
        lblBonus.setToolTipText("");

        txtBonus.setColumns(20);
        txtBonus.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtBonus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBonusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBonusFocusLost(evt);
            }
        });

        txtAllowance.setColumns(20);
        txtAllowance.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtAllowance.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAllowanceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAllowanceFocusLost(evt);
            }
        });

        lblAllowance.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAllowance.setForeground(new java.awt.Color(51, 51, 51));
        lblAllowance.setText("Phụ Cấp");
        lblAllowance.setToolTipText("");

        txtDayOff.setColumns(20);
        txtDayOff.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblDayOff.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblDayOff.setForeground(new java.awt.Color(51, 51, 51));
        lblDayOff.setText("Buổi Vắng");
        lblDayOff.setToolTipText("");

        cbEmp.setBackground(new java.awt.Color(255, 255, 255));
        cbEmp.setEditable(true);
        cbEmp.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        cbEmp.setForeground(new java.awt.Color(0, 0, 0));
        cbEmp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblEmp.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblEmp.setForeground(new java.awt.Color(51, 51, 51));
        lblEmp.setText("Nhân Viên");
        lblEmp.setToolTipText("");

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(0, 0, 0));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/down_arrow_button.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setContentAreaFilled(false);
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdd.setOpaque(true);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

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

        tblPayroll.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scPayroll.setViewportView(tblPayroll);

        btnAccept.setBackground(new java.awt.Color(255, 255, 255));
        btnAccept.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAccept.setForeground(new java.awt.Color(0, 0, 0));
        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accept_button.png"))); // NOI18N
        btnAccept.setText("Chấp nhận");
        btnAccept.setContentAreaFilled(false);
        btnAccept.setOpaque(true);
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnPayrollLayout = new javax.swing.GroupLayout(pnPayroll);
        pnPayroll.setLayout(pnPayrollLayout);
        pnPayrollLayout.setHorizontalGroup(
            pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scPayroll, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnPayrollLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnPayrollLayout.createSequentialGroup()
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbEmp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBonus)
                            .addComponent(lblAllowance))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBonus, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                            .addComponent(txtAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnPayrollLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDel))
                            .addGroup(pnPayrollLayout.createSequentialGroup()
                                .addComponent(lblDayOff)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDayOff, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))))
                    .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pnPayrollLayout.setVerticalGroup(
            pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPayrollLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBonus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDayOff, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDayOff, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnPayrollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAllowance, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEmp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scPayroll, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPayroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnPayroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addToListPayroll(); // TODO add your handling code here:
        loadTablePayroll();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delFromListPayroll();
        loadTablePayroll();
    }//GEN-LAST:event_btnDelActionPerformed
    private void delFromListPayroll() {
        int selectedRow = tblPayroll.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        listNewPayroll.remove(selectedRow);
    }

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        insertPayrollToDatabase();

    }//GEN-LAST:event_btnAcceptActionPerformed

    private void txtBonusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBonusFocusLost
        if (CheckText.isInteger(txtBonus.getText())) {
            txtBonus.setText(moneyFormater.format(Integer.valueOf(txtBonus.getText())));
        }

    }//GEN-LAST:event_txtBonusFocusLost

    private void txtAllowanceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllowanceFocusLost
        if (CheckText.isInteger(txtAllowance.getText())) {
            txtAllowance.setText(moneyFormater.format(Integer.valueOf(txtAllowance.getText())));
        }

    }//GEN-LAST:event_txtAllowanceFocusLost

    private void txtBonusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBonusFocusGained
        try {
            Number parse = moneyFormater.parse(txtBonus.getText());
            txtBonus.setText(parse.toString());
        } catch (ParseException ex) {

        }


    }//GEN-LAST:event_txtBonusFocusGained

    private void txtAllowanceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAllowanceFocusGained
        try {
            Number parse = moneyFormater.parse(txtAllowance.getText());
            txtAllowance.setText(parse.toString());
        } catch (ParseException ex) {

        }
    }//GEN-LAST:event_txtAllowanceFocusGained
    private void insertPayrollToDatabase() {
        for (Payroll payroll : listNewPayroll) {
            try (Connection conn = ConnectDatabase.getConnectDatabase()) {
                String sql = "{call sp_addPayroll(?,?,?,?,?,?,?)}";
                try (CallableStatement cstmt = conn.prepareCall(sql)) {
                    cstmt.setString(1, payroll.getIDEmp());
                    cstmt.setDate(2, java.sql.Date.valueOf(currentTimeSQL));
                    cstmt.setInt(3, payroll.getBaseSalary());
                    cstmt.setInt(4, payroll.getBonus());
                    cstmt.setInt(5, payroll.getAllowance());
                    cstmt.setInt(6, payroll.getDayOff());
                    cstmt.setInt(7, payroll.getRealSalary());
                    cstmt.executeUpdate();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi thêm dữ liệu");
            }
        }
        JOptionPane.showMessageDialog(this, "Đã xử lý xong.\nChuyển sang về lại bảng lương để cập nhật dữ liệu mới");

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
            java.util.logging.Logger.getLogger(frmCreatePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCreatePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCreatePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCreatePayroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCreatePayroll().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JComboBox<String> cbEmp;
    private javax.swing.JLabel lblAllowance;
    private javax.swing.JLabel lblBonus;
    private javax.swing.JLabel lblDayOff;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pnPayroll;
    private javax.swing.JScrollPane scPayroll;
    private javax.swing.JTable tblPayroll;
    private javax.swing.JTextField txtAllowance;
    private javax.swing.JTextField txtBonus;
    private javax.swing.JTextField txtDayOff;
    private javax.swing.JTextField txtTime;
    // End of variables declaration//GEN-END:variables

}
