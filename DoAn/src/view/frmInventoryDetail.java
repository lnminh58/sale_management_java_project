/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ConnectDatabase;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lnminh
 */
public class frmInventoryDetail extends javax.swing.JFrame {

    static String nameInventory = "";
    static String idInventory = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private String selectedTime="2016/04";
    private TreeSet<String> listTime;
    private Vector<Vector> tableGRData = new Vector<>();
    private Vector<Vector> tableOrderData= new Vector<>();
    private Vector<String> tableTitle= new Vector<>();

    /**
     * Creates new form frmIventoryDetail
     */
    public frmInventoryDetail() {
        initComponents();
        loadData();
        txtName.setText(nameInventory);

    }

    private void loadData() {
        loadListTime();
        loadTableGR();
        loadTableOrder();
    }

    private void loadListTime() {
        listTime = new TreeSet<>();
        cbListTime.removeAllItems();

        String sql = "{call sp_getTimeInOut}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    listTime.add(sdf.format(rs.getDate("thoigian")));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }

        for (String string : listTime) {
            cbListTime.addItem(string);
        }
    }

    private void loadTableGR() {
        loadTableTitle();
        loadTableGRData();
        showOnGRTable();

    }

    private void loadTableOrder() {
        loadTableTitle();
        loadTableOrderData();
        showOnOrderTable();

    }

    private void loadTableTitle() {
        tableTitle = new Vector<>();
        tableTitle.add("Số lượng");
        tableTitle.add("Đơn giá");
        tableTitle.add("Thời gian");
    }

    private void loadTableGRData() {
        Vector<Vector> listNumPrice = new Vector<>();
        tableGRData = new Vector<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String sql = "{call sp_getInventoryGRWithDate(?,?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            String[] split = selectedTime.split("/");
            String date = split[1] + "-" + split[0] + "%";
//            String date ="";
            cstmt.setString(1, idInventory);
            cstmt.setString(2, date);

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Vector temp = new Vector();
                    temp.add(rs.getString("soLuongNhap"));
                    temp.add(moneyFormater.format(rs.getInt("giaNhap")));
                    temp.add(sdf.format(rs.getDate("ngayNhap")));
                    tableGRData.add(temp);
                    temp = new Vector();
                    temp.add(rs.getInt("soLuongNhap"));
                    temp.add(rs.getInt("giaNhap"));
                    listNumPrice.add(temp);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }

        long totalNum = 0;
        long totalPrice = 0;
        for (Vector vector : listNumPrice) {
      
            totalNum += (int) vector.get(0);
            totalPrice += ((int) vector.get(0) * (int) vector.get(1));
        }
        txtTotalGRNum.setText(Long.toString(totalNum));
        txtTotalGRPrice.setText(moneyFormater.format(totalPrice));

    }

    private void showOnGRTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableGRData, tableTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblGR.setModel(dtm);
    }
    
    private void loadTableOrderData() {
        
        Vector<Vector> listNumPrice = new Vector<>();
        tableOrderData = new Vector<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String sql = "{call sp_getInventoryOrderWithDate(?,?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            String[] split = selectedTime.split("/");
            String date = split[1] + "-" + split[0] + "%";
//String date ="";
            cstmt.setString(1, idInventory);
            cstmt.setString(2, date);

            try (ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    Vector temp = new Vector();
                    temp.add(rs.getString("soLuongXuat"));
                    temp.add(moneyFormater.format(rs.getInt("giaXuat")));
                    temp.add(sdf.format(rs.getDate("ngayXuat")));
                    tableOrderData.add(temp);
                    temp = new Vector();
                    temp.add(rs.getInt("soLuongXuat"));
                    temp.add(rs.getInt("giaXuat"));
                    listNumPrice.add(temp);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }

        long totalNum = 0;
        long totalPrice = 0;
        for (Vector vector : listNumPrice) {
              
            totalNum += (int) vector.get(0);
            totalPrice += ((int) vector.get(0) * (int) vector.get(1));
        }
        txtTotalOrderNum.setText(Long.toString(totalNum));
        txtTotalOrderPrice.setText(moneyFormater.format(totalPrice));

    }
    
    private void showOnOrderTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableOrderData, tableTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblOrder.setModel(dtm);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnDetail = new javax.swing.JPanel();
        lblTimes = new javax.swing.JLabel();
        cbListTime = new javax.swing.JComboBox<>();
        spInOut = new javax.swing.JSplitPane();
        pnGR = new javax.swing.JPanel();
        scGR = new javax.swing.JScrollPane();
        tblGR = new javax.swing.JTable();
        pnOrder = new javax.swing.JPanel();
        scOrder = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        lblTotalOrderNum = new javax.swing.JLabel();
        txtTotalOrderNum = new javax.swing.JTextField();
        txtTotalGRNum = new javax.swing.JTextField();
        lblTotalGRNum = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtTotalGRPrice = new javax.swing.JTextField();
        lblTotalGRNum2 = new javax.swing.JLabel();
        lblTotalOrderNum2 = new javax.swing.JLabel();
        txtTotalOrderPrice = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 560));
        setPreferredSize(new java.awt.Dimension(800, 560));
        setResizable(false);

        pnDetail.setBackground(new java.awt.Color(23, 187, 146));
        pnDetail.setPreferredSize(new java.awt.Dimension(800, 539));

        lblTimes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTimes.setForeground(new java.awt.Color(51, 51, 51));
        lblTimes.setText("Thời gian");
        lblTimes.setToolTipText("");

        cbListTime.setBackground(new java.awt.Color(255, 255, 255));
        cbListTime.setEditable(true);
        cbListTime.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        cbListTime.setForeground(new java.awt.Color(0, 0, 0));
        cbListTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbListTime.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        cbListTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbListTimeActionPerformed(evt);
            }
        });

        pnGR.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 51), 2), "Chi Tiết Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 102, 51))); // NOI18N
        pnGR.setMinimumSize(new java.awt.Dimension(300, 100));
        pnGR.setPreferredSize(new java.awt.Dimension(400, 399));

        tblGR.setModel(new javax.swing.table.DefaultTableModel(
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
        scGR.setViewportView(tblGR);

        javax.swing.GroupLayout pnGRLayout = new javax.swing.GroupLayout(pnGR);
        pnGR.setLayout(pnGRLayout);
        pnGRLayout.setHorizontalGroup(
            pnGRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scGR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        );
        pnGRLayout.setVerticalGroup(
            pnGRLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scGR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        spInOut.setLeftComponent(pnGR);

        pnOrder.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 51), 2), "Chi Tiết Xuất", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(255, 102, 51))); // NOI18N

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        scOrder.setViewportView(tblOrder);

        javax.swing.GroupLayout pnOrderLayout = new javax.swing.GroupLayout(pnOrder);
        pnOrder.setLayout(pnOrderLayout);
        pnOrderLayout.setHorizontalGroup(
            pnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );
        pnOrderLayout.setVerticalGroup(
            pnOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        spInOut.setRightComponent(pnOrder);

        lblTotalOrderNum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalOrderNum.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalOrderNum.setText("Tổng lượng xuất");
        lblTotalOrderNum.setToolTipText("");

        txtTotalOrderNum.setEditable(false);
        txtTotalOrderNum.setColumns(20);
        txtTotalOrderNum.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txtTotalGRNum.setEditable(false);
        txtTotalGRNum.setColumns(20);
        txtTotalGRNum.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblTotalGRNum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalGRNum.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalGRNum.setText("Tổng lượng nhập");
        lblTotalGRNum.setToolTipText("");

        txtName.setEditable(false);
        txtName.setColumns(20);
        txtName.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblName.setForeground(new java.awt.Color(51, 51, 51));
        lblName.setText("Tên Sản Phẩm");
        lblName.setToolTipText("");

        txtTotalGRPrice.setEditable(false);
        txtTotalGRPrice.setColumns(20);
        txtTotalGRPrice.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblTotalGRNum2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalGRNum2.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalGRNum2.setText("Tổng phí nhập");
        lblTotalGRNum2.setToolTipText("");

        lblTotalOrderNum2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotalOrderNum2.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalOrderNum2.setText("Tổng phí xuất");
        lblTotalOrderNum2.setToolTipText("");

        txtTotalOrderPrice.setEditable(false);
        txtTotalOrderPrice.setColumns(20);
        txtTotalOrderPrice.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout pnDetailLayout = new javax.swing.GroupLayout(pnDetail);
        pnDetail.setLayout(pnDetailLayout);
        pnDetailLayout.setHorizontalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spInOut)
            .addGroup(pnDetailLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnDetailLayout.createSequentialGroup()
                        .addComponent(lblTotalGRNum2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalGRPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createSequentialGroup()
                        .addComponent(lblTimes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbListTime, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createSequentialGroup()
                        .addComponent(lblTotalGRNum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalGRNum, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnDetailLayout.createSequentialGroup()
                        .addComponent(lblTotalOrderNum2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTotalOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createSequentialGroup()
                        .addComponent(lblTotalOrderNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalOrderNum, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnDetailLayout.setVerticalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnDetailLayout.createSequentialGroup()
                .addComponent(spInOut, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalOrderNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalOrderNum)
                    .addComponent(txtTotalGRNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotalGRNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTotalGRPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotalGRNum2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotalOrderNum2)
                        .addComponent(txtTotalOrderPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblName))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTimes)
                        .addComponent(cbListTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(115, 115, 115))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 519, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbListTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbListTimeActionPerformed

        selectedTime = (String) cbListTime.getSelectedItem();

        if (selectedTime == null) {
            return;
        }

        loadTableGR();
        loadTableOrder();

    }//GEN-LAST:event_cbListTimeActionPerformed
    
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
            java.util.logging.Logger.getLogger(frmInventoryDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmInventoryDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmInventoryDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmInventoryDetail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmInventoryDetail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbListTime;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblTimes;
    private javax.swing.JLabel lblTotalGRNum;
    private javax.swing.JLabel lblTotalGRNum2;
    private javax.swing.JLabel lblTotalOrderNum;
    private javax.swing.JLabel lblTotalOrderNum2;
    private javax.swing.JPanel pnDetail;
    private javax.swing.JPanel pnGR;
    private javax.swing.JPanel pnOrder;
    private javax.swing.JScrollPane scGR;
    private javax.swing.JScrollPane scOrder;
    private javax.swing.JSplitPane spInOut;
    private javax.swing.JTable tblGR;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtTotalGRNum;
    private javax.swing.JTextField txtTotalGRPrice;
    private javax.swing.JTextField txtTotalOrderNum;
    private javax.swing.JTextField txtTotalOrderPrice;
    // End of variables declaration//GEN-END:variables

  

}
