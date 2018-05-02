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
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class frmCreateOrder extends javax.swing.JFrame {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat moneyFormater = new DecimalFormat("###,###,###");
    private String currentTime;
    private String currentTimeSQL;
    private Vector<Inventory> listInventorys;
    private Vector<Vector> listCustomer;
    private Order order;
    private Vector<String> tableTitle;
    private Vector<Vector> tableData = new Vector<>();
    private Vector<Vector> listSaler;
    private Vector<Vector> listShipper;
    private String idOrder;
    private boolean confirm = false;
    private Vector<Goods> updateInventorys;

    public frmCreateOrder() {
        initComponents();
        loadData();
        addWindowListener(Confirm.disposeListener(this));
    }

    private void loadData() {
        setDefaultValue();
        loadInventory();
        loadCustomer();
        loadSaler();
        loadShipper();
        loadTableTitle();
        showOnTable();
    }

    private void getCurrentTime() {

        currentTime = sdf.format((new Date()).getTime());
        currentTimeSQL = sdfSQL.format((new Date()).getTime());
    }

    private void setDefaultValue() {
        order = new Order();
        order.setOrderdetail(new Vector<Goods>());
        getCurrentTime();
        txtTime.setText(currentTime);
        txtNum.setText("0");
        txtPrice.setText("0");
    }

    private void loadInventory() {
        listInventorys = new Vector<>();
        cbGoods.removeAllItems();
        String sql = "{call sp_getInventory}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                listInventorys.add(new Inventory(rs.getString("maSP"), rs.getString("tenSP"), rs.getInt("soLuong")));
                cbGoods.addItem(rs.getString("tenSP"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }
    }

    private void loadCustomer() {
        listCustomer = new Vector<>();
        cbCustomer.removeAllItems();
        String sql = "{call sp_getCusIDAndName}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql);
                ResultSet rs = cstmt.executeQuery()) {
            while (rs.next()) {
                Vector temp = new Vector();
                temp.add(rs.getString("maKH"));
                temp.add(rs.getString("tenKH"));
                listCustomer.add(temp);
                cbCustomer.addItem(rs.getString("tenKH"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }
    }

    private void loadSaler() {
        listSaler = new Vector<>();
        cbSale.removeAllItems();
        String sql = "{call sp_getEmpIDAndNameWithPosition(?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, "CV003");
            try (ResultSet rs = cstmt.executeQuery()) {;
                while (rs.next()) {
                    Vector temp = new Vector();
                    temp.add(rs.getString("maNV"));
                    temp.add(rs.getString("tenNV"));
                    listSaler.add(temp);
                    cbSale.addItem(rs.getString("tenNV"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
        }
    }

    private void loadShipper() {
        listShipper = new Vector<>();
        cbShipper.removeAllItems();
        String sql = "{call sp_getEmpIDAndNameWithPosition(?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase();
                CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, "CV004");
            try (ResultSet rs = cstmt.executeQuery()) {;
                while (rs.next()) {
                    Vector temp = new Vector();
                    temp.add(rs.getString("maNV"));
                    temp.add(rs.getString("tenNV"));
                    listShipper.add(temp);
                    cbShipper.addItem(rs.getString("tenNV"));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu");
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

        pnCreateOrder = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        lblCustomer = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        txtTime = new javax.swing.JTextField();
        cbCustomer = new javax.swing.JComboBox<>();
        lblShipper = new javax.swing.JLabel();
        lblSale = new javax.swing.JLabel();
        cbSale = new javax.swing.JComboBox<>();
        lblGoods = new javax.swing.JLabel();
        cbShipper = new javax.swing.JComboBox<>();
        cbGoods = new javax.swing.JComboBox<>();
        lblPrice = new javax.swing.JLabel();
        lblNum = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        scOrder = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        btnAccept = new javax.swing.JButton();
        txtTotal = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        txtNum = new javax.swing.JTextField();
        btnDel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        pnCreateOrder.setBackground(new java.awt.Color(27, 157, 221));

        txtID.setColumns(20);
        txtID.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblID.setBackground(new java.awt.Color(255, 255, 255));
        lblID.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblID.setForeground(new java.awt.Color(51, 51, 51));
        lblID.setText("Mã đơn xuất");
        lblID.setToolTipText("");

        lblCustomer.setBackground(new java.awt.Color(255, 255, 255));
        lblCustomer.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCustomer.setForeground(new java.awt.Color(51, 51, 51));
        lblCustomer.setText("Khách hàng");
        lblCustomer.setToolTipText("");

        lblTime.setBackground(new java.awt.Color(255, 255, 255));
        lblTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTime.setForeground(new java.awt.Color(51, 51, 51));
        lblTime.setText("Ngày xuất");
        lblTime.setToolTipText("");

        txtTime.setColumns(20);
        txtTime.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        cbCustomer.setEditable(true);
        cbCustomer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblShipper.setBackground(new java.awt.Color(255, 255, 255));
        lblShipper.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblShipper.setForeground(new java.awt.Color(51, 51, 51));
        lblShipper.setText("Nhân viên giao hàng");
        lblShipper.setToolTipText("");

        lblSale.setBackground(new java.awt.Color(255, 255, 255));
        lblSale.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblSale.setForeground(new java.awt.Color(51, 51, 51));
        lblSale.setText("Nhân viên bán hàng");
        lblSale.setToolTipText("");

        cbSale.setEditable(true);
        cbSale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblGoods.setBackground(new java.awt.Color(255, 255, 255));
        lblGoods.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblGoods.setForeground(new java.awt.Color(51, 51, 51));
        lblGoods.setText("Sản phẩm");
        lblGoods.setToolTipText("");

        cbShipper.setEditable(true);
        cbShipper.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbGoods.setEditable(true);
        cbGoods.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblPrice.setBackground(new java.awt.Color(255, 255, 255));
        lblPrice.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblPrice.setForeground(new java.awt.Color(51, 51, 51));
        lblPrice.setText("Đơn Giá");
        lblPrice.setToolTipText("");

        lblNum.setBackground(new java.awt.Color(255, 255, 255));
        lblNum.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblNum.setForeground(new java.awt.Color(51, 51, 51));
        lblNum.setText("Số lượng");
        lblNum.setToolTipText("");

        txtPrice.setColumns(20);
        txtPrice.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPriceActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/down_arrow_button.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setContentAreaFilled(false);
        btnAdd.setOpaque(true);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        scOrder.setViewportView(tblOrder);

        btnAccept.setBackground(new java.awt.Color(255, 255, 255));
        btnAccept.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        btnAccept.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accept_button.png"))); // NOI18N
        btnAccept.setText("Chấp nhận");
        btnAccept.setContentAreaFilled(false);
        btnAccept.setOpaque(true);
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        txtTotal.setColumns(20);
        txtTotal.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        lblTotal.setBackground(new java.awt.Color(255, 255, 255));
        lblTotal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 51));
        lblTotal.setText("Tổng Tiền");
        lblTotal.setToolTipText("");

        txtNum.setColumns(20);
        txtNum.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

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

        javax.swing.GroupLayout pnCreateOrderLayout = new javax.swing.GroupLayout(pnCreateOrder);
        pnCreateOrder.setLayout(pnCreateOrderLayout);
        pnCreateOrderLayout.setHorizontalGroup(
            pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scOrder)
            .addGroup(pnCreateOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCreateOrderLayout.createSequentialGroup()
                        .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnCreateOrderLayout.createSequentialGroup()
                                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cbCustomer, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnCreateOrderLayout.createSequentialGroup()
                                .addComponent(lblGoods, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbGoods, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblShipper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(cbShipper, 0, 164, Short.MAX_VALUE)
                            .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblSale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnCreateOrderLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDel)
                                .addGap(1, 1, 1))
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(cbSale, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnCreateOrderLayout.createSequentialGroup()
                        .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnCreateOrderLayout.setVerticalGroup(
            pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCreateOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShipper, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSale, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbShipper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGoods, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGoods, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNum, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCreateOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCreateOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnCreateOrder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPriceActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addTolistGoods();
        loadTableTitle();
        loadTableData();
        showOnTable();
        calTotalPrice();
    }//GEN-LAST:event_btnAddActionPerformed

    private void addTolistGoods() {
        int index = cbGoods.getSelectedIndex();
        String snum = txtNum.getText().trim();
        String sprice = txtPrice.getText().trim();
        String name = listInventorys.get(index).getName();
        String id = listInventorys.get(index).getID();

        if (snum.equalsIgnoreCase("")
                || sprice.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền vào đầy đủ thông tin");
            return;

        }

        if (!CheckText.isInteger(snum)
                || !CheckText.isInteger(sprice)) {
            JOptionPane.showMessageDialog(this, "Xin điền đúng kiểu thông tin");
            return;
        }

        int num = Integer.valueOf(snum);
        int price = Integer.valueOf(sprice);
        for (Goods goods : order.getOrderdetail()) {
            if (goods.getGoodsID().equalsIgnoreCase(id)) {
                goods.setNum(goods.getNum() + num);
                return;

            }
        }

        order.getOrderdetail().add(new Goods(id, name, num, price));

    }

    private void loadTableTitle() {
        tableTitle = new Vector<>();
        tableTitle.add("Mã sản phẩm");
        tableTitle.add("Tên sản phẩm");
        tableTitle.add("Số lượng");
        tableTitle.add("Đơn giá");
    }

    private void loadTableData() {
        tableData = new Vector<>();
        for (Goods goods : order.getOrderdetail()) {
            Vector<String> temp = new Vector<>();
            temp.add(goods.getGoodsID());
            temp.add(goods.getGoodsName());
            temp.add(Integer.toString(goods.getNum()));
            temp.add(moneyFormater.format(goods.getPrice()));
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
        tblOrder.setModel(dtm);
    }

    private void calTotalPrice() {
        long total = 0;

        for (Goods goods : order.getOrderdetail()) {
            total += goods.getNum() * goods.getPrice();
        }

        txtTotal.setText(Long.toString(total));
    }

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        if (checkInventoryNumber()) {
            insertOrderToDatabase();
            inserOrderDetail();
            loadInventory();
            loadOrderDetailData();
            subtractGoodsNum();
            updateInventory();
            JOptionPane.showMessageDialog(this, "Đã xử lý xong.\n"
                    + "Chuyển sang về lại màn hình đơn nhập để cập nhật dữ liệu mới");
        }

    }//GEN-LAST:event_btnAcceptActionPerformed

    private void insertOrderToDatabase() {

        idOrder = txtID.getText().trim();
        if (idOrder.equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Xin điền vào đầy đủ thông tin");
            return;
        }
        int indexCus = cbCustomer.getSelectedIndex();
        Vector customer = listCustomer.get(indexCus);
        String idCus = (String) customer.get(0);

        int indexSal = cbSale.getSelectedIndex();
        Vector saler = listSaler.get(indexSal);
        String idSal = (String) saler.get(0);

        int indexShip = cbShipper.getSelectedIndex();
        Vector shipper = listShipper.get(indexShip);
        String idShip = (String) shipper.get(0);

        try (Connection conn = ConnectDatabase.getConnectDatabase()) {
            String sql = "{call sp_addOrder(?,?,?,?,?)}";
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.setString(1, idOrder);
                cstmt.setDate(2, java.sql.Date.valueOf(currentTimeSQL));
                cstmt.setString(3, idCus);
                cstmt.setString(4, idSal);
                cstmt.setString(5, idShip);
                cstmt.executeUpdate();
                confirm = true;

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi thêm dữ liệu");
        }

    }

    private void inserOrderDetail() {
        if (!confirm) {
            return;
        }
        for (Goods goods : order.getOrderdetail()) {
            try (Connection conn = ConnectDatabase.getConnectDatabase()) {
                String sql = "{call sp_addOrderDetails(?,?,?,?)}";
                try (CallableStatement cstmt = conn.prepareCall(sql)) {

                    cstmt.setString(1, idOrder);
                    cstmt.setString(2, goods.getGoodsID());
                    cstmt.setInt(3, goods.getNum());
                    cstmt.setInt(4, goods.getPrice());
                    cstmt.executeUpdate();

                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "lỗi thêm dữ liệu");
            }
        }
        confirm = false;
    }

    private void loadOrderDetailData() {
        updateInventorys = new Vector<>();

        String sql = "{call sp_getOrderDetails(?)}";
        try (Connection conn = ConnectDatabase.getConnectDatabase(); CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, idOrder);

            try (ResultSet rs = cstmt.executeQuery()) {

                while (rs.next()) {
                    updateInventorys.add(new Goods(
                            rs.getString("maSP"), rs.getString("tenSP"), rs.getInt("soLuongXuat"), rs.getInt("giaXuat")));

                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "lỗi tải dữ liệu");
        }
    }

    private void subtractGoodsNum() {
        for (Goods goods : updateInventorys) {
            for (Inventory inventory : listInventorys) {
                if (inventory.getID().equalsIgnoreCase(goods.getGoodsID())) {
                    int num = inventory.getNumInventory() - goods.getNum();
                    goods.setNum(num);
                }
            }

        }
    }

    private void updateInventory() {
        for (Goods goods : updateInventorys) {
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

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        delFromListGoods();
        loadTableTitle();
        loadTableData();
        showOnTable();
    }//GEN-LAST:event_btnDelActionPerformed
    private void delFromListGoods() {
        int selectedRow = tblOrder.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        order.getOrderdetail().remove(selectedRow);

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
            java.util.logging.Logger.getLogger(frmCreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCreateOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JComboBox<String> cbCustomer;
    private javax.swing.JComboBox<String> cbGoods;
    private javax.swing.JComboBox<String> cbSale;
    private javax.swing.JComboBox<String> cbShipper;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblGoods;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNum;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblSale;
    private javax.swing.JLabel lblShipper;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnCreateOrder;
    private javax.swing.JScrollPane scOrder;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNum;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtTime;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private boolean checkInventoryNumber() {
        loadInventory();
        int count = 0;
        for (Goods goods : order.getOrderdetail()) {
            for (Inventory inventory : listInventorys) {
                if (goods.getGoodsID().equalsIgnoreCase(inventory.getID())) {
                    if (goods.getNum() > inventory.getNumInventory()) {

                        JOptionPane.showMessageDialog(this, "Số lượng sản phẩm " + goods.getGoodsID() + " hiện không đủ");
                        count++;
                    }
                }
            }
        }
        if (count != 0) {
            return false;
        } else {
            return true;
        }
    }

}
