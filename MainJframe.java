package asignmantGd1;

import asignmantGd1.model.NhanVien;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import main.thread.ClockThread;

public class MainJframe extends javax.swing.JFrame {

    List<NhanVien> nhanVienList = new ArrayList<>();
    DefaultTableModel tblModel;
    private int index = -1;
    public static final String P_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public MainJframe() {
        initComponents();
        setLocationRelativeTo(null);
        initTable();
        fillTable();
        ClockThread clockThread = new ClockThread(lblTime);
        Thread t1 = new Thread(clockThread);
        t1.start();
    }

    public void fillTable() {
        tblModel.setRowCount(0);
        for (NhanVien nv : nhanVienList) {
            tblModel.addRow(new Object[]{nv.getMa(), nv.getHoTen(), nv.getTuoi(), nv.getEmail(), nv.getLuong()});
        }

    }

    public void initTable() {
        tblModel = new DefaultTableModel(); // Tạo một đối tượng DefaultTableModel mới
        tblModel.addColumn("Mã");
        tblModel.addColumn("Họ và tên");
        tblModel.addColumn("Tuổi");
        tblModel.addColumn("Email");
        tblModel.addColumn("Lương");

        tblNhanVien.setModel(tblModel); // Gán DefaultTableModel cho JTable

        nhanVienList.add(new NhanVien("Hung", "NV01", "hunglnph45965@fpt.edu.vn", 40411, 18));
        nhanVienList.add(new NhanVien("hao", "NV02", "hunglnph46965@fpt.edu.vn", 30411, 18));
        nhanVienList.add(new NhanVien("chinh", "NV03", "hunglnph47965@fpt.edu.vn", 20411, 25));
        nhanVienList.add(new NhanVien("hoang", "NV04", "hunglnph48965@fpt.edu.vn", 10411, 18));
        nhanVienList.add(new NhanVien("thong", "NV05", "hunglnph49965@fpt.edu.vn", 411, 45));

    }

    public void clearForm() {
        txtHoTen.setText("");
        txtMaNv.setText("");
        txtLuong.setText("");
        txtTuoi.setText("");
        txtEmail.setText("");
        index = -1;
    }

    public NhanVien readForm() {
        return new NhanVien(txtHoTen.getText(), txtMaNv.getText(), txtEmail.getText(), Double.parseDouble(txtLuong.getText()), Integer.valueOf(txtTuoi.getText()));

    }

    public void ThemNv() {
        if (validateForm()) {
            if (index == -1) {
                nhanVienList.add(readForm());
                fillTable();
                JOptionPane.showMessageDialog(this, "Da them");
            } else {
                capNhat(readForm());
                fillTable();
                JOptionPane.showMessageDialog(this, "Da cap nhat");
            }
        }
//        if (index == -1) {
//            // Adding a new employee
//            nhanVienList.add(readForm());
//            fillTable();
//            JOptionPane.showMessageDialog(this, "Đã thêm");
//        } else {
//            // Updating an existing employee
//            capNhat(readForm());
//            fillTable();
//            JOptionPane.showMessageDialog(this, "Đã cập nhật");
//            index = -1; // Reset the index after updating
//        }
//        clearForm(); // Clear the form fields after adding or updating
    }

    public NhanVien timTheoMa(String ID) {
        for (NhanVien nv : nhanVienList) {
            if (nv.getMa().equalsIgnoreCase(ID)) {
                return nv;
            }
        }
        return null;
    }

    public void capNhat(NhanVien newnv) {
        NhanVien nv1 = timTheoMa(newnv.getMa());
        if (nv1 != null) {
            nv1.setHoTen(newnv.getHoTen());
            nv1.setTuoi(newnv.getTuoi());
            nv1.setEmail(newnv.getEmail());
            nv1.setLuong(newnv.getLuong());
        }
    }

    public void fillNhanVienLenForm(int index) {
        txtMaNv.setText(nhanVienList.get(index).getMa());
        txtHoTen.setText(nhanVienList.get(index).getHoTen());
        txtEmail.setText(nhanVienList.get(index).getEmail());
        txtTuoi.setText(String.valueOf(nhanVienList.get(index).getTuoi()));
        txtLuong.setText(String.valueOf(nhanVienList.get(index).getLuong()));
    }

    public void fillNhanVienLenForm2(NhanVien nv) {
        txtMaNv.setText(nv.getMa());
        txtHoTen.setText(nv.getHoTen());
        txtEmail.setText(nv.getEmail());
        txtTuoi.setText(String.valueOf(nv.getTuoi()));
        txtLuong.setText(String.valueOf(nv.getLuong()));
    }

    public void updateInfo() {

        tblNhanVien.setRowSelectionInterval(index, index);
        fillNhanVienLenForm(index);
        lblBanGhi.setText(layThongTinBanGhi());
    }

    public void firstNv() {

        if (nhanVienList.size() != 0) {
            index = 0;
            updateInfo();
        }

    }

    public void lastNv() {
        index = nhanVienList.size() - 1;
        updateInfo();
    }

    public void pre() {
        if (index == 0) {
            lastNv();

        } else {
            index--;
        }
        updateInfo();
    }

    public void next() {
        if (index == nhanVienList.size() - 1) {
            firstNv();

        } else {
            index++;
        }
        updateInfo();
    }

    public String layThongTinBanGhi() {
        return "Record: " + (index + 1) + " of" + nhanVienList.size();
    }

    public boolean validateForm() {
        if (txtMaNv.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "chua nhap ma nhan vien");
            return false;
        }
        if (txtHoTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "chua nhap ho ten nhan vien");
            return false;
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "chua nhap email nhan vien");
            return false;
        }
        Matcher matcher = Pattern.compile(P_EMAIL).matcher(txtEmail.getText());
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Email sai dinh dang");
            return false;
        }
        if (txtTuoi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "chua nhap tuoi cua nhan vien");
            return false;
        }
        try {
            Integer.parseInt(txtTuoi.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "tuoi phai la so");
            return false;

        }
        if (txtLuong.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "chua nhap luong cua nhan vien");
            return false;
        }
        try {
            Integer.parseInt(txtLuong.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "luong phai la so");
            return false;

        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnLuu = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnTim = new javax.swing.JButton();
        btnMo = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        txtMaNv = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtTuoi = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblBanGhi = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Quản Lí Nhân Viên");

        jLabel2.setText("MÃ NHÂN VIÊN");

        jLabel3.setText("HỌ VÀ TÊN");

        jLabel4.setText("TUỔI");

        jLabel5.setText("EMAIL");

        jLabel6.setText("LƯƠNG");

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        btnReset.setText("New ");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnLuu.setText("Save");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnXoa.setText("Delete");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnTim.setText("Find");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        btnMo.setText("Open");

        btnThoat.setText("Exit");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLuu)
                    .addComponent(btnReset)
                    .addComponent(btnXoa)
                    .addComponent(btnMo)
                    .addComponent(btnThoat))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTim)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThoat))
        );

        btnPre.setText("<<");
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblBanGhi.setForeground(new java.awt.Color(255, 51, 0));
        lblBanGhi.setText("Record: 1 of 10");

        lblTime.setFont(new java.awt.Font("Helvetica Neue", 2, 18)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 51, 0));
        lblTime.setText("00:00 AM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTen)
                            .addComponent(txtTuoi)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtLuong)
                                .addGap(5, 5, 5))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaNv, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPre, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBanGhi))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(jLabel1)))
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTime)))
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtMaNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTime, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtTuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5))
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPre)
                            .addComponent(btnNext)
                            .addComponent(btnFirst)
                            .addComponent(btnLast)
                            .addComponent(lblBanGhi)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
        ThemNv();
    }//GEN-LAST:event_btnLuuActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        // TODO add your handling code here:
        index = tblNhanVien.getSelectedRow();
        fillNhanVienLenForm(index);
        lblBanGhi.setText(layThongTinBanGhi());
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        index = tblNhanVien.getSelectedRow();

        xoaNhanVien(index);
    }//GEN-LAST:event_btnXoaActionPerformed

    public void xoaNhanVien(int index) {
        nhanVienList.remove(index);
        fillTable();
        clearForm();
        JOptionPane.showMessageDialog(this, "da xoa");
    }
    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
        if (timTheoMa(txtMaNv.getText()) == null) {
            JOptionPane.showMessageDialog(this, "Khong tim thay nhan vien");
        } else {
            fillNhanVienLenForm2(timTheoMa(txtMaNv.getText()));
        }
//        // TODO add your handling code here:
//        String maNvToFind = txtMaNv.getText(); // Get the ID from the text field
//        NhanVien foundNv = timTheoMa(maNvToFind); // Search for the employee by ID
//
//        if (foundNv == null) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên");
//        } else {
//            // Fill the form with the found employee's information
//            fillNhanVienLenForm2(foundNv);
//        }

    }//GEN-LAST:event_btnTimActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        firstNv();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        lastNv();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        // TODO add your handling code here:
        pre();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

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
            java.util.logging.Logger.getLogger(MainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJframe().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnMo;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBanGhi;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNv;
    private javax.swing.JTextField txtTuoi;
    // End of variables declaration//GEN-END:variables
}
