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
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Goods;
import model.GoodsReceipt;
import model.Inventory;


/**
 *
 * @author lnminh
 */
public class frmGRList extends javax.swing.JFrame {

    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Vector<String> tableGRTitle;
    private Vector<Vector> tableGRData;
    private Vector<GoodsReceipt> listGR;
    private Vector<String> tableGRDetailTitle;
    private Vector<Vector> tableGRDetailData;
    private int selectedIndex=-1;
    private int selectedGoods=-1;
    private long total;
    private Vector<Inventory> listInventorys;
    private Vector<Goods> updateInventorys;
    /**
     * Creates new form frmGRList
     */
    public frmGRList() {
        initComponents();
        loadData();
    }

    private void loadData() {
        loadTableGR();
        loadTableGRDetailTitle();
        tableGRDetailData= new Vector<>();
        showOnGRDetailTable();

    }

    private void loadTableGR() {
        loadTableGRTitle();
        loadTableGRData();
        showOnGRTable();
    }

    private void loadTableGRTitle() {
        tableGRTitle = new Vector<>();
        tableGRTitle.add("Mã Phiếu Nhập");
        tableGRTitle.add("Ngày Nhập");
        tableGRTitle.add("Nhà cung cấp");

    }

    private void loadTableGRData() {
        tableGRData = new Vector<>();
        listGR = new Vector<>();

        String sql = "{call sp_getGR}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                listGR.add(new GoodsReceipt(rs.getString("maPN"), rs.getDate("ngayNhap"), rs.getString("tenNCC")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }

        for (GoodsReceipt goodsReceipt : listGR) {
            Vector<String> temp = new Vector<>();
            temp.add(goodsReceipt.getID());
            temp.add(sdf.format(goodsReceipt.getTime()));
            temp.add(goodsReceipt.getSupplier());
            tableGRData.add(temp);
        }

    }

    private void showOnGRTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableGRData, tableGRTitle) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }

        };
        tblGRList.setModel(dtm);
    }

    private void loadTableGRDetail() {
        loadTableGRDetailTitle();
        try {
            loadTableGRDetailData(selectedIndex);
        } catch (Exception e) {
        }
        showOnGRDetailTable();
    }

    private void loadTableGRDetailTitle() {
        tableGRDetailTitle = new Vector<>();
        tableGRDetailTitle.add("Mã Sản Phẩm");
        tableGRDetailTitle.add("Tên Sản Phẩm");
        tableGRDetailTitle.add("Số Lượng Nhập");
        tableGRDetailTitle.add("Đơn Giá");
    }

    private void loadTableGRDetailData(int selectedIndex) {
        tableGRDetailData = new Vector<>();

        String sql = "{call sp_getGRDetails(?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, listGR.get(selectedIndex).getID());

            try (ResultSet rs = cstmt.executeQuery()) {
                listGR.get(selectedIndex).setGRdetail(new Vector<>());

                while (rs.next()) {
                    listGR.get(selectedIndex).getGRdetail().add(new Goods(
                            rs.getString("maSP"), rs.getString("tenSP"), rs.getInt("soLuongNhap"), rs.getInt("giaNhap")));

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
        total = 0;
        for (Goods goods : listGR.get(selectedIndex).getGRdetail()) {
            Vector<String> temp = new Vector<>();
            temp.add(goods.getGoodsID());
            temp.add(goods.getGoodsName());
            temp.add(Integer.toString(goods.getNum()));
            temp.add(moneyFormater.format(goods.getPrice()));
            total += goods.getNum() * goods.getPrice();
            tableGRDetailData.add(temp);
        }

    }

    private void showOnGRDetailTable() {
        DefaultTableModel dtm = new DefaultTableModel(tableGRDetailData, tableGRDetailTitle) {
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

        pnGRList = new javax.swing.JPanel();
        spGRList = new javax.swing.JSplitPane();
        pnList = new javax.swing.JPanel();
        scList = new javax.swing.JScrollPane();
        tblGRList = new javax.swing.JTable();
        pnDetail = new javax.swing.JPanel();
        scDetail = new javax.swing.JScrollPane();
        tblListDetail = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnCreateNew = new javax.swing.JButton();
        lblIcon = new javax.swing.JLabel();
        bnNewRefresh = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnGRList.setBackground(new java.awt.Color(232, 134, 96));

        spGRList.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        spGRList.setOneTouchExpandable(true);

        pnList.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 134, 96), 2), "Danh Sách Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(232, 134, 96))); // NOI18N
        pnList.setMinimumSize(new java.awt.Dimension(0, 200));

        tblGRList.setModel(new javax.swing.table.DefaultTableModel(
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
        tblGRList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGRListMouseClicked(evt);
            }
        });
        scList.setViewportView(tblGRList);

        javax.swing.GroupLayout pnListLayout = new javax.swing.GroupLayout(pnList);
        pnList.setLayout(pnListLayout);
        pnListLayout.setHorizontalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
        );
        pnListLayout.setVerticalGroup(
            pnListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
        );

        spGRList.setTopComponent(pnList);

        pnDetail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(232, 134, 96), 2), "Chi Tiết Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 14), new java.awt.Color(232, 134, 96))); // NOI18N

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
            .addComponent(scDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
        );
        pnDetailLayout.setVerticalGroup(
            pnDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );

        spGRList.setRightComponent(pnDetail);

        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblTotal.setText("Tổng Tiền");
        lblTotal.setToolTipText("");

        txtTotal.setColumns(20);
        txtTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        btnCreateNew.setBackground(new java.awt.Color(255, 255, 255));
        btnCreateNew.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnCreateNew.setForeground(new java.awt.Color(0, 0, 0));
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
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/buy.png"))); // NOI18N

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
        btnDel.setContentAreaFilled(false);
        btnDel.setOpaque(true);
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnGRListLayout = new javax.swing.GroupLayout(pnGRList);
        pnGRList.setLayout(pnGRListLayout);
        pnGRListLayout.setHorizontalGroup(
            pnGRListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(spGRList, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(pnGRListLayout.createSequentialGroup()
                .addComponent(lblIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCreateNew, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addGap(30, 30, 30)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        pnGRListLayout.setVerticalGroup(
            pnGRListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGRListLayout.createSequentialGroup()
                .addComponent(spGRList, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnGRListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGRListLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnGRListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCreateNew, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bnNewRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDel)))
                    .addGroup(pnGRListLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIcon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnGRList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnGRList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateNewActionPerformed
        requestCreatNewGR();
    }//GEN-LAST:event_btnCreateNewActionPerformed
    private void requestCreatNewGR() {
        frmCreateGR fcGR=new frmCreateGR();
        fcGR.setLocationRelativeTo(this);
        fcGR.setVisible(true);
    }

    
    
    private void tblGRListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGRListMouseClicked
        showOnDetailTable();
    }//GEN-LAST:event_tblGRListMouseClicked

    private void bnNewRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bnNewRefreshActionPerformed
        loadData();
    }//GEN-LAST:event_bnNewRefreshActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delGRAndGRDetail();
        
        
    }//GEN-LAST:event_btnDelActionPerformed
    private void delGRAndGRDetail() {
        if(selectedIndex==-1){
             JOptionPane.showMessageDialog(this, "Xin chọn dữ liệu cần xóa ");
             return;
        }
        if(selectedGoods==-1){
            updateInventorys=listGR.get(selectedIndex).getGRdetail();
            delAll();
        }else{
            updateInventorys=new Vector<>();
            updateInventorys.add(listGR.get(selectedIndex).getGRdetail().get(selectedGoods));
            delDetail();
        }
    }
    private void delAll() {
        if(checkInventoryNumber()){
            delGR();
            loadInventory();
            subtractGoodsNum();
            updateInventory();
            loadData();
        }
    }
    private void delDetail() {
        if(checkInventoryNumber()){
            delGRDetail();
            loadInventory();
            subtractGoodsNum();
            updateInventory();
            loadTableGRDetail();
            selectedGoods=-1;
        }
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
    
    private boolean checkInventoryNumber() {
        loadInventory();
        int count = 0;
        
        for (Goods goods : updateInventorys) {
            for (Inventory inventory : listInventorys) {
                if(goods.getGoodsID().equalsIgnoreCase(inventory.getID())){
                    if(goods.getNum()>inventory.getNumInventory()){
                         JOptionPane.showMessageDialog(this, "Số lượng sản phẩm "+ goods.getGoodsID()+ " hiện không đủ");
                        count++;
                    }
                }
            }
        }
        if(count!=0){
            return false;
        }else{
            return true;
        }
    }
    private void delGR(){
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delGR(?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, listGR.get(selectedIndex).getID() );
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }

    }
    private void delGRDetail() {
        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_delGRDetail(?,?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, listGR.get(selectedIndex).getID() );
                cstmt.setString(2, listGR.get(selectedIndex).getGRdetail().get(selectedGoods).getGoodsID());
                cstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi xóa dữ liệu");
        }

    }

    
    
    private void subtractGoodsNum() {
        for (Goods goods : updateInventorys) {
               for (Inventory inventory : listInventorys) {
                   if (inventory.getID().equalsIgnoreCase(goods.getGoodsID())) {
                       int num=inventory.getNumInventory()-goods.getNum();
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
        selectedGoods = tblListDetail.getSelectedRow();
        
    }//GEN-LAST:event_tblListDetailMouseClicked
     private void showOnDetailTable() {

        selectedIndex = tblGRList.getSelectedRow();
        selectedGoods=-1;
        loadTableGRDetail();
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
            java.util.logging.Logger.getLogger(frmGRList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmGRList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmGRList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmGRList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmGRList().setVisible(true);
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
    private javax.swing.JPanel pnGRList;
    private javax.swing.JPanel pnList;
    private javax.swing.JScrollPane scDetail;
    private javax.swing.JScrollPane scList;
    private javax.swing.JSplitPane spGRList;
    private javax.swing.JTable tblGRList;
    private javax.swing.JTable tblListDetail;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables



  

  

    
   

}
