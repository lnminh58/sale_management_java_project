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
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Goods;
import model.Inventory;
import model.Order;

/**
 *
 * @author lnminh
 */
public class frmOrderList extends javax.swing.JFrame {

    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Vector<String> tableOrderTitle;
    private Vector<Vector> tableOrderData;
    private Vector<Order> listOrder;
    private Vector<String> tableOrderDetailTitle;
    private Vector<Vector> tableOrderDetailData;
    private HashMap<String, String> listEmp;
    private int selectedIndex = -1;
    private int selectedGoods = -1;
    private long total;
    private Vector<Inventory> listInventorys;
    private Vector<Goods> updateInventorys;

    /**
     * Creates new form NewJFrame
     */
    public frmOrderList() {
        initComponents();
        loadData();
    }

    private void loadData() {
        loadListEmp();
        loadTableOrder();
        loadTableOrderDetailTitle();
        tableOrderDetailData = new Vector<>();
        showOnOrderDetailTable();

    }

    private void loadTableOrder() {
        loadTableOrderTitle();
        loadTableOrderData();
        showOnOderTable();
    }

    private void loadListEmp() {
        listEmp = new HashMap<>();

        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_getEmpIDAndName}";
            try (CallableStatement cstmt = conn.prepareCall(sql); ResultSet rs = cstmt.executeQuery()) {
                while (rs.next()) {
                    String key = rs.getString("maNV");
                    String value = rs.getString("tenNV");
                    listEmp.put(key, value);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
    }

    private void loadTableOrderTitle() {
        tableOrderTitle = new Vector<>();
        tableOrderTitle.add("Mã Phiếu Xuất");
        tableOrderTitle.add("Ngày Xuất");
        tableOrderTitle.add("Khách Hàng");
        tableOrderTitle.add("Nhân Viên Bán Hàng");
        tableOrderTitle.add("Nhân Viên Giao Hàng");
    }

    private void loadTableOrderData() {
        tableOrderData = new Vector<>();
        listOrder = new Vector<>();

        String sql = "{call sp_getOrder}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                listOrder.add(new Order(rs.getString("maPX"),
                        rs.getDate("ngayXuat"),
                        rs.getString("tenKH"),
                        rs.getString("maNVBanHang"),
                        rs.getString("maNVGiaoHang")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }

        for (Order order : listOrder) {
            Vector<String> temp = new Vector<>();
            temp.add(order.getID());
            temp.add(sdf.format(order.getTime()));
            temp.add(order.getCustomer());
            temp.add(listEmp.get(order.getSalerID()));
            temp.add(listEmp.get(order.getShipperID()));
            tableOrderData.add(temp);

        }
    }

    private void showOnOderTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableOrderData, tableOrderTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblOrderList.setModel(dtm);
    }

    private void loadTableOrderDetail() {
        loadTableOrderDetailTitle();
        try {
            loadTableOrderDetailData(selectedIndex);
        } catch (Exception e) {

        }
        showOnOrderDetailTable();
    }

    private void loadTableOrderDetailTitle() {
        tableOrderDetailTitle = new Vector<>();
        tableOrderDetailTitle.add("Mã Sản Phẩm");
        tableOrderDetailTitle.add("Tên Sản Phẩm");
        tableOrderDetailTitle.add("Số Lượng Xuất");
        tableOrderDetailTitle.add("Đơn Giá");
    }

    private void loadTableOrderDetailData(int selectedIndex) {
        tableOrderDetailData = new Vector<>();

        String sql = "{call sp_getOrderDetails(?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, listOrder.get(selectedIndex).getID());

            try (ResultSet rs = cstmt.executeQuery()) {
                listOrder.get(selectedIndex).setOrderdetail(new Vector<>());

                while (rs.next()) {
                    listOrder.get(selectedIndex).getOrderdetail().add(new Goods(
                            rs.getString("maSP"), rs.getString("tenSP"), rs.getInt("soLuongXuat"), rs.getInt("giaXuat")));

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
        total = 0;
        for (Goods goods : listOrder.get(selectedIndex).getOrderdetail()) {
            Vector<String> temp = new Vector<>();
            temp.add(goods.getGoodsID());
            temp.add(goods.getGoodsName());
            temp.add(Integer.toString(goods.getNum()));
            temp.add(moneyFormater.format(goods.getPrice()));
            total += goods.getNum() * goods.getPrice();
            tableOrderDetailData.add(temp);
        }

    }

    private void showOnOrderDetailTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableOrderDetailData, tableOrderDetailTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblListDetail.setModel(dtm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnOrderList = new javax.swing.JPanel();
        spOrderList = new javax.swing.JSplitPane();
        pnList = new javax.swing.JPanel();
        scList = new javax.swing.JScrollPane();
        tblOrderList = new javax.swing.JTable();
        pnDetail = new javax.swing.JPanel();
        scDetail = new javax.swing.JScrollPane();
        tblListDetail = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnCreateNew = new javax.swing.JButton();
        lblIcon = new javax.swing.JLabel();
        btnDel = new javax.swing.JButton();
        bnNewRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnOrderList.setBackground(new java.awt.Color(27, 157, 221));

        spOrderList.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        spOrderList.setOneTouchExpandable(true);

        pnList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 157, 221), 2), "Danh Sách Phiếu Xuất", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(27, 157, 221))); // NOI18N
        pnList.setMinimumSize(new java.awt.Dimension(0, 200));

        tblOrderList.setModel(new javax.swing.table.DefaultTableModel(
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
        tblOrderList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderListMouseClicked(evt);
            }
        });
        scList.setViewportView(tblOrderList);

        javax.swing.GroupLayout pnListLayout = new javax.swing.GroupLayout(pnList);
        pnList.setLayout(pnListLayout);
        pnListLayout.setHorizontalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
        );
        pnListLayout.setVerticalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );

        spOrderList.setTopComponent(pnList);

        pnDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(27, 157, 221), 2), "Chi Tiết Phiếu Xuất", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(27, 157, 221))); // NOI18N

        tblListDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14))); // NOI18N
        tblListDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tblListDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListDetailMouseClicked(evt);
            }
        });
        scDetail.setViewportView(tblListDetail);

        javax.swing.GroupLayout pnDetailLayout = new javax.swing.GroupLayout(pnDetail);
        pnDetail.setLayout(pnDetailLayout);
        pnDetailLayout.setHorizontalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
        );
        pnDetailLayout.setVerticalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
        );

        spOrderList.setRightComponent(pnDetail);

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblTotal.setText("Tổng Tiền");
        lblTotal.setToolTipText("");

        txtTotal.setColumns(20);
        txtTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btnCreateNew.setBackground(new java.awt.Color(255, 255, 255));
        btnCreateNew.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnCreateNew.setForeground(new java.awt.Color(51, 51, 51));
        btnCreateNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add_button.png"))); // NOI18N
        btnCreateNew.setText("Tạo bảng mới");
        btnCreateNew.setContentAreaFilled(false);
        btnCreateNew.setOpaque(true);
        btnCreateNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateNewActionPerformed(evt);
            }
        });

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sale.png"))); // NOI18N

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

        javax.swing.GroupLayout pnOrderListLayout = new javax.swing.GroupLayout(pnOrderList);
        pnOrderList.setLayout(pnOrderListLayout);
        pnOrderListLayout.setHorizontalGroup(
            pnOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spOrderList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnOrderListLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnCreateNew, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel)
                .addGap(40, 40, 40)
                .addComponent(lblTotal)
                .addGap(30, 30, 30)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        pnOrderListLayout.setVerticalGroup(
            pnOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnOrderListLayout.createSequentialGroup()
                .addComponent(spOrderList, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnOrderListLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnOrderListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreateNew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnOrderListLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewActionPerformed
        frmCreateOrder fc = new frmCreateOrder();
        fc.setLocationRelativeTo(this);
        fc.setVisible(true);
    }//GEN-LAST:event_btnCreateNewActionPerformed

    private void tblOrderListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderListMouseClicked
        // TODO add your handling code here:
        showOnDetailTable();
    }//GEN-LAST:event_tblOrderListMouseClicked

    private void bnNewRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnNewRefreshActionPerformed
        loadData();
    }//GEN-LAST:event_bnNewRefreshActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delOrderAndOrderDetail();

    }//GEN-LAST:event_btnDelActionPerformed
    private void delOrderAndOrderDetail() {
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Xin chọn dữ liệu cần xóa ");
            return;
        }
        if (selectedGoods == -1) {
            updateInventorys = listOrder.get(selectedIndex).getOrderdetail();
            delAll();
        } else {
            updateInventorys = new Vector<>();
            updateInventorys.add(listOrder.get(selectedIndex).getOrderdetail().get(selectedGoods));
            delDetail();
        }
    }
    
    private void delAll() {
        
            delOrder();
            loadInventory();
            sumGoodsNum();
            updateInventory();
            loadData();
        
    }
    private void delDetail() {
      
            delOrder();
            loadInventory();
            sumGoodsNum();
            updateInventory();
            loadTableOrderDetail();
            selectedGoods=-1;
        
    }
    
    private void loadInventory() {
        listInventorys=new Vector<>();
       
        String sql = "{call sp_getInventory}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                listInventorys.add(new Inventory(rs.getString("maSP"), rs.getString("tenSP"), rs.getInt("soLuong")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }
    }
    

    
    private void delOrder(){
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delOrder(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, listOrder.get(selectedIndex).getID() );
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }

    }
    private void delGRDetail() {
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delOrderDetail(?,?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, listOrder.get(selectedIndex).getID() );
                cstmt.setString(2, listOrder.get(selectedIndex).getOrderdetail().get(selectedGoods).getGoodsID());
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }

    }

    private void sumGoodsNum() {
        for (Goods goods : updateInventorys) {
               for (Inventory inventory : listInventorys) {
                   if (inventory.getID().equalsIgnoreCase(goods.getGoodsID())) {
                       int num=inventory.getNumInventory()+goods.getNum();
                       goods.setNum(num);
                   }
               }
           }
    }

    private void updateInventory() {
        for (Goods  goods : updateInventorys) {
             try (Connection conn = ConnectDatabase.getConnectDatabase()) {
                String sql = "{call sp_updateInventory(?,?)}";
                try (CallableStatement cstmt = conn.prepareCall(sql)) {

                    cstmt.setString(1, goods.getGoodsID());
                    cstmt.setInt(2, goods.getNum());
                    cstmt.executeUpdate();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "lỗi cập nhật dữ liệu");
            }
        }
    }
   

    private void tblListDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListDetailMouseClicked
        // TODO add your handling code here:
        selectedGoods = tblListDetail.getSelectedRow();


    }//GEN-LAST:event_tblListDetailMouseClicked

    private void showOnDetailTable() {
        selectedIndex = tblOrderList.getSelectedRow();
        selectedGoods = -1;
        loadTableOrderDetail();
        txtTotal.setText(moneyFormater.format(total));
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
            java.util.logging.Logger.getLogger(frmOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmOrderList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmOrderList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bnNewRefresh;
    private javax.swing.JButton btnCreateNew;
    private javax.swing.JButton btnDel;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnDetail;
    private javax.swing.JPanel pnList;
    private javax.swing.JPanel pnOrderList;
    private javax.swing.JScrollPane scDetail;
    private javax.swing.JScrollPane scList;
    private javax.swing.JSplitPane spOrderList;
    private javax.swing.JTable tblListDetail;
    private javax.swing.JTable tblOrderList;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

}
