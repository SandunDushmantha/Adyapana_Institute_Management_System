/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.StudentPaymentManagement;

import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Sandu
 */
public class AllPayment extends javax.swing.JPanel {

    /**
     * Creates new form AllPayment
     */
    public AllPayment() {
        initComponents();
        loadPayement("i.payment_invoice_NO", "ASC", "");
    }

    private void showPaidAmount(String invoiceNo, String studentNo) {
        try {
            // Modified query to check both invoice_NO and student_Sno
            String query = "SELECT total_amount, paid_amount FROM payment p "
                    + "INNER JOIN invoice i ON p.invoice_NO = i.payment_invoice_NO "
                    + "WHERE p.invoice_NO = ? AND i.student_Sno = ?";

            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement(query);
            preparedStatement.setString(1, invoiceNo);
            preparedStatement.setString(2, studentNo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double totalAmount = resultSet.getDouble("total_amount");
                double paidAmount = resultSet.getDouble("paid_amount");
                double remainingAmount = totalAmount - paidAmount;
                jLabel2.setText(String.format("Rs. %.2f", remainingAmount));
            } else {
                // Get the invoice value if no payment record exists
                String invoiceQuery = "SELECT value FROM invoice WHERE payment_invoice_NO = ? AND student_Sno = ?";
                PreparedStatement invoiceStmt = MySQL.getConnection().prepareStatement(invoiceQuery);
                invoiceStmt.setString(1, invoiceNo);
                invoiceStmt.setString(2, studentNo);
                ResultSet invoiceRs = invoiceStmt.executeQuery();

                if (invoiceRs.next()) {
                    double invoiceValue = invoiceRs.getDouble("value");
                    jLabel2.setText(String.format("Rs. %.2f", invoiceValue));
                } else {
                    jLabel2.setText("No payment record found");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            jLabel2.setText("Error retrieving payment");
        } catch (Exception ex) {
            Logger.getLogger(AllPayment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPayement(String column, String orderby, String searchText) {
        try {
            String query = "SELECT i.*, c.ClassNo, t.Tno, t.Name as TeacherName, s.Subno, s.Description, "
                    + "st.Sno, st.Name as StudentName, p.total_amount, p.paid_amount "
                    + "FROM invoice i "
                    + "INNER JOIN class c ON c.ClassNo = i.class_ClassNo "
                    + "INNER JOIN teacher t ON t.Tno = c.teacher_Tno "
                    + "INNER JOIN subject s ON s.Subno = t.Subject_Subno "
                    + "INNER JOIN student st ON st.Sno = i.student_Sno "
                    + "LEFT JOIN payment p ON p.invoice_NO = i.payment_invoice_NO "
                    + "WHERE st.Sno LIKE ? OR c.ClassNo LIKE ? OR i.month LIKE ? "
                    + "ORDER BY " + column + " " + orderby;

            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement(query);
            String searchPattern = "%" + searchText + "%";

            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel defaultTableModel = (DefaultTableModel) jTable1.getModel();
            defaultTableModel.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("payment_invoice_NO"));
                row.add(resultSet.getString("Sno") + " " + resultSet.getString("StudentName"));
                row.add(resultSet.getString("ClassNo"));
                row.add(resultSet.getString("Tno") + " " + resultSet.getString("TeacherName"));
                row.add(resultSet.getString("Subno") + " " + resultSet.getString("Description"));
                row.add(resultSet.getString("month"));
                row.add(resultSet.getString("value"));

                // Add payment information
                Double totalAmount = resultSet.getDouble("total_amount");
                Double paidAmount = resultSet.getDouble("paid_amount");
                row.add(paidAmount != null ? paidAmount.toString() : "0.00");
                row.add(totalAmount != null ? totalAmount.toString() : "0.00");

                defaultTableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Student No And Name", "Class No", "Teacher No And Name", "Subject No And Name", "Month", "Amount", "Paid Amount", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Seacrh :");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Month");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Amount to pay");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 0, 0));
        jLabel2.setText("Amount");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        loadPayement("i.payment_invoice_NO", "ASC", jTextField2.getText());
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        loadPayement("i.payment_invoice_NO", "ASC", jTextField2.getText());
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String selectedItem = (String) jComboBox3.getSelectedItem();
            if ("Select Month".equals(selectedItem)) {
                return;
            }
            String Month = selectedItem;
            loadPayement("i.payment_invoice_NO", "ASC", Month);
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable1.getSelectedRow();
                if (row != -1) {
                    String invoiceNo = jTable1.getValueAt(row, 0).toString();
                    String studentInfo = jTable1.getValueAt(row, 1).toString();
                    String studentNo = studentInfo.split(" ")[0]; // Get student number from combined string
                    showPaidAmount(invoiceNo, studentNo);
                }
            }
        });
    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
