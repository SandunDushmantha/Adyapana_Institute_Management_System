/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.StudentPaymentManagement;

import java.awt.event.ItemEvent;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.MySQL;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Sandu
 */
public class RecordNewPayment extends javax.swing.JPanel {

    HashMap<String, String> LoadStudentMap = new HashMap();
    HashMap<String, String> LoadClassMap = new HashMap();

    /**
     * Creates new form StudentEnrollment
     */
    public RecordNewPayment() {
        initComponents();
        LoadStudent();

    }

    public void LoadStudent() {

        try {
            String query = "SELECT * FROM student";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Vector<String> vector = new Vector();
            vector.add("Select student");

            while (rs.next()) {
                String Sno = rs.getString("Sno");
                String Name = rs.getString("Name");
                String combined = Sno + " " + Name;

                vector.add(combined);
                LoadStudentMap.put(combined, Sno);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);
            jComboBox1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    LoadClass();  // Student selection change වුනාම LoadClass method එක call වෙනවා
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadClass() {
        try {
            // Get selected student ID
            String selectedStudent = String.valueOf(jComboBox1.getSelectedItem());
            if (selectedStudent.equals("Select student")) {
                // If no student selected, clear the class combo box
                Vector<String> vector = new Vector<>();
                vector.add("Select class");
                DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
                jComboBox1.setModel(model);
                return;
            }

            String studentId = LoadStudentMap.get(selectedStudent);

            // Corrected query with consistent table references
            String query = "SELECT DISTINCT * FROM class_enrollment ce "
                    + "INNER JOIN class c ON c.ClassNo = ce.class_ClassNo "
                    + "INNER JOIN teacher t ON t.Tno = c.teacher_Tno "
                    + "INNER JOIN subject s ON s.Subno = t.subject_Subno "
                    + "WHERE ce.student_Sno = ?";

            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            Vector<String> vector = new Vector<>();
            vector.add("Select class");

            while (rs.next()) {
                String ClassNo = rs.getString("c.ClassNo");
                String Time = rs.getString("c.timeslot");
                String teacherName = rs.getString("t.Name");
                String subjectDesc = rs.getString("s.Description");
                String Price = rs.getString("s.Price");

                String combined = ClassNo + " / " + Time + " / " + teacherName + " / "
                        + subjectDesc + " / " + Price;
                vector.add(combined);
                LoadClassMap.put(combined, ClassNo);

            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class No", "Student No", "Teacher No", "Teacher Name", "Subject No", "Subject Description", "Month", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Total  Rs.");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Payment  Rs.");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Balance  Rs.");

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("PAY");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jTextField5))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel3.setBackground(new java.awt.Color(229, 229, 229));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Payment Details");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Student No");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Class No");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select class" }));
        jComboBox2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox2ItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Month");

        jMonthChooser1.setBackground(new java.awt.Color(242, 242, 242));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Amount  Rs.");

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("UPDATE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 102, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2, 0, 194, Short.MAX_VALUE)
                            .addComponent(jTextField1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Reset();
    }//GEN-LAST:event_jButton3ActionPerformed


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            // Get selected values
            String selectedClass = (String) jComboBox2.getSelectedItem();
            String selectedStudent = (String) jComboBox1.getSelectedItem();

            // Validate selections
            if ("Select class".equals(selectedClass) || "Select student".equals(selectedStudent)) {
                JOptionPane.showMessageDialog(this,
                        "Please select both student and class",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get ClassNo and StudentNo from maps
            String classNo = LoadClassMap.get(selectedClass);
            String studentNo = LoadStudentMap.get(selectedStudent);

            // Get selected month
            String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
            String selectedMonth = monthNames[jMonthChooser1.getMonth()];

            // Check if payment already exists in the table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String tableClassNo = (String) model.getValueAt(i, 0);  // Class No column
                String tableMonth = (String) model.getValueAt(i, 5);    // Month column

                if (classNo.equals(tableClassNo) && selectedMonth.equals(tableMonth)) {
                    JOptionPane.showMessageDialog(this,
                            "Payment for this class in " + selectedMonth + " already exists in the table",
                            "Duplicate Payment",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Check if payment already exists in database
            String checkQuery = "SELECT * FROM invoice WHERE class_ClassNo = ? "
                    + "AND student_Sno = ? AND month = ?";
            PreparedStatement checkPs = MySQL.getConnection().prepareStatement(checkQuery);
            checkPs.setString(1, classNo);
            checkPs.setString(2, studentNo);
            checkPs.setString(3, selectedMonth);
            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {
                JOptionPane.showMessageDialog(this,
                        "Payment for this class in " + selectedMonth + " has already been made",
                        "Duplicate Payment",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // If no duplicates found, proceed with adding payment
            String query = "SELECT ce.class_ClassNo, t.Tno, t.Name AS TeacherName, "
                    + "s.Subno, s.Description, s.Price, st.Sno "
                    + "FROM class_enrollment ce "
                    + "INNER JOIN class c ON ce.class_ClassNo = c.ClassNo "
                    + "INNER JOIN teacher t ON c.teacher_Tno = t.Tno "
                    + "INNER JOIN subject s ON t.subject_Subno = s.Subno "
                    + "INNER JOIN student st ON ce.student_Sno = st.Sno "
                    + "WHERE c.ClassNo = ? AND st.Sno = ?";

            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ps.setString(1, classNo);
            ps.setString(2, studentNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // Create vector for table row

                Vector<String> rowData = new Vector<>();
                rowData.add(rs.getString("class_ClassNo"));
                rowData.add(rs.getString("Sno"));
                rowData.add(rs.getString("Tno"));
                rowData.add(rs.getString("TeacherName"));
                rowData.add(rs.getString("Subno"));
                rowData.add(rs.getString("Description"));
                rowData.add(selectedMonth);
                rowData.add(rs.getString("Price"));

                // Add row to table
                model.addRow(rowData);

                // Update total amount
                updateTotalAmount();

                // Clear amount field
                jTextField1.setText("");
                jComboBox2.setSelectedIndex(0);

            } else {
                // No data found - reset fields
                jTextField1.setText("");
                LoadClass();
                jMonthChooser1.setMonth(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred while processing payment",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed
// Helper method to update total amount

    private void updateTotalAmount() {
        double total = 0;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        // Sum up all amounts in the table
        for (int i = 0; i < model.getRowCount(); i++) {
            String amount = (String) model.getValueAt(i, 7); // Amount is in column 6
            total += Double.parseDouble(amount);
        }

        // Update total field
        jTextField3.setText(String.format("%.2f", total));

        // Get payment amount
        String paymentText = jTextField4.getText();
        if (!paymentText.isEmpty()) {
            try {
                double payment = Double.parseDouble(paymentText);
                // Calculate and update balance
                double balance = payment - total;
                jTextField5.setText(String.format("%.2f", balance));
            } catch (NumberFormatException e) {
                jTextField5.setText("0.00");
            }
        } else {
            jTextField5.setText("0.00");
        }
    }
    private void jComboBox2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox2ItemStateChanged

        // Only respond to selection (not deselection)
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // Get the selected class from jComboBox2
            String selectedClass = (String) jComboBox2.getSelectedItem();

            // Skip if the default "Select class" is selected
            if ("Select class".equals(selectedClass)) {
                jTextField1.setEditable(false);
                jTextField1.setText(""); // Clear the price if no class is selected
                return;
            }

            // Extract the Price from the selected class string
            String[] classDetails = selectedClass.split(" / ");

            // Assuming the Price is the last element in the array
            String price = classDetails[classDetails.length - 1];  // Get the last part (Price)

            // Set the price in the text field and make it non-editable
            jTextField1.setEditable(false);
            jTextField1.setText(price);  // Set the extracted price into the text field
        }
    }//GEN-LAST:event_jComboBox2ItemStateChanged

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            // Get the selected row index
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                return; // No row selected
            }
            // Get the class number and month from the selected row
            String classNo = (String) jTable1.getValueAt(selectedRow, 0); // Class No
            String month = (String) jTable1.getValueAt(selectedRow, 6);   // Month

            // Set the month in month chooser
            String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
            for (int i = 0; i < monthNames.length; i++) {
                if (monthNames[i].equals(month)) {
                    jMonthChooser1.setMonth(i);
                    break;
                }
            }

            // Find and select the matching class in jComboBox2
            for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                String item = jComboBox2.getItemAt(i);
                if (item.startsWith(classNo + " /")) {
                    jComboBox2.setSelectedIndex(i);
                    break;
                }
            }

            // Get the amount from the selected row and set it in jTextField1
            String amount = (String) jTable1.getValueAt(selectedRow, 7);
            jTextField1.setText(amount);

            if (evt.getClickCount() == 2) { // Check for double click
                try {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.removeRow(selectedRow);

                    // Update total amount after removing the row
                    updateTotalAmount();

                    // Show success message
                    JOptionPane.showMessageDialog(null,
                            "Row removed successfully",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error removing row",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred while loading selected row data",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateTotalAmount();
            }
        });
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateTotalAmount();
            }
        });
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // Get selected row
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                        "Please select a row to update",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get values from the form
            String selectedClass = (String) jComboBox2.getSelectedItem();
            String selectedStudent = (String) jComboBox1.getSelectedItem();

            // Validate selections
            if ("Select class".equals(selectedClass) || "Select student".equals(selectedStudent)) {
                JOptionPane.showMessageDialog(this,
                        "Please select both student and class",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Get ClassNo and StudentNo from maps
            String classNo = LoadClassMap.get(selectedClass);
            String studentNo = LoadStudentMap.get(selectedStudent);

            // Get selected month
            String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
            String selectedMonth = monthNames[jMonthChooser1.getMonth()];

            // Get the original class number and month from the table
            String originalClassNo = (String) jTable1.getValueAt(selectedRow, 0);
            String originalMonth = (String) jTable1.getValueAt(selectedRow, 5);
            String originalStudentNo = LoadStudentMap.get(jTable1.getValueAt(selectedRow, 3));

            // Check if payment already exists for the new class and month (if changed)
            if (!originalClassNo.equals(classNo) || !originalMonth.equals(selectedMonth)) {
                // First check in table
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (i != selectedRow) { // Skip the current row being updated
                        String tableClassNo = (String) model.getValueAt(i, 0);
                        String tableMonth = (String) model.getValueAt(i, 6);

                        if (classNo.equals(tableClassNo) && selectedMonth.equals(tableMonth)) {
                            JOptionPane.showMessageDialog(this,
                                    "Payment for this class in " + selectedMonth + " already exists in the table",
                                    "Duplicate Payment",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                // Then check in database
                String checkQuery = "SELECT * FROM invoice WHERE class_ClassNo = ? "
                        + "AND student_Sno = ? AND month = ? "
                        + "AND NOT (class_ClassNo = ? AND student_Sno = ? AND month = ?)";
                PreparedStatement checkPs = MySQL.getConnection().prepareStatement(checkQuery);
                checkPs.setString(1, classNo);
                checkPs.setString(2, studentNo);
                checkPs.setString(3, selectedMonth);
                checkPs.setString(4, originalClassNo);
                checkPs.setString(5, originalStudentNo);
                checkPs.setString(6, originalMonth);
                ResultSet checkRs = checkPs.executeQuery();

                if (checkRs.next()) {
                    JOptionPane.showMessageDialog(this,
                            "Payment for this class in " + selectedMonth + " has already been made",
                            "Duplicate Payment",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            // Get class details for the update
            String query = "SELECT ce.class_ClassNo, t.Tno, t.Name AS TeacherName, "
                    + "s.Subno, s.Description, s.Price, st.Sno "
                    + "FROM class_enrollment ce "
                    + "INNER JOIN class c ON ce.class_ClassNo = c.ClassNo "
                    + "INNER JOIN teacher t ON c.teacher_Tno = t.Tno "
                    + "INNER JOIN subject s ON t.subject_Subno = s.Subno "
                    + "INNER JOIN student st ON ce.student_Sno = st.Sno "
                    + "WHERE c.ClassNo = ? AND st.Sno = ?";

            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ps.setString(1, classNo);
            ps.setString(2, studentNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Update the table row with new values
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setValueAt(rs.getString("class_ClassNo"), selectedRow, 0);
                model.setValueAt(rs.getString("Sno"), selectedRow, 1);
                model.setValueAt(rs.getString("Tno"), selectedRow, 2);
                model.setValueAt(rs.getString("TeacherName"), selectedRow, 3);
                model.setValueAt(rs.getString("Subno"), selectedRow, 4);
                model.setValueAt(rs.getString("Description"), selectedRow, 5);
                model.setValueAt(selectedMonth, selectedRow, 6);
                model.setValueAt(rs.getString("Price"), selectedRow, 7);

                // Update database
                String updateQuery = "UPDATE invoice SET class_ClassNo = ?, student_Sno = ?, month = ? "
                        + "WHERE class_ClassNo = ? AND student_Sno = ? AND month = ?";
                PreparedStatement updatePs = MySQL.getConnection().prepareStatement(updateQuery);
                updatePs.setString(1, classNo);
                updatePs.setString(2, studentNo);
                updatePs.setString(3, selectedMonth);
                updatePs.setString(4, originalClassNo);
                updatePs.setString(5, originalStudentNo);
                updatePs.setString(6, originalMonth);
                updatePs.executeUpdate();

                // Update total amount
                updateTotalAmount();

                // Show success message
                JOptionPane.showMessageDialog(this,
                        "Payment details updated successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Reset form
                Reset();

            } else {
                JOptionPane.showMessageDialog(this,
                        "Class details not found",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error occurred while updating payment",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            // Validate if there are items in the table
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Please add payment items before proceeding",
                        "No Items",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validate payment amount
            String paymentText = jTextField4.getText().trim();
            if (paymentText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter payment amount",
                        "Missing Payment",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Check if the input is numeric
            if (!paymentText.matches("\\d+(\\.\\d{1,2})?")) { // Allows integers and decimals with up to two decimal places
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric payment amount (e.g., 100 or 100.50)",
                        "Invalid Payment",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double paymentAmount = Double.parseDouble(paymentText);
            String totalAmountText = jTextField3.getText().trim();
            if (totalAmountText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Total amount is missing",
                        "Missing Total",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            double totalAmount = Double.parseDouble(totalAmountText);

            // Optional: Validate if payment is sufficient
            if (paymentAmount < totalAmount) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Payment amount is less than total amount. Continue anyway?",
                        "Payment Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Generate unique invoice number
            long invoice_NO = System.currentTimeMillis();

            // Begin transaction
            MySQL.getConnection().setAutoCommit(false);

            try {
                // Insert into payment table
                String paymentQuery = "INSERT INTO payment (total_amount, paid_amount, invoice_NO, payment_date) "
                        + "VALUES (?, ?, ?, NOW())";
                PreparedStatement paymentPs = MySQL.getConnection().prepareStatement(paymentQuery);
                paymentPs.setDouble(1, totalAmount);
                paymentPs.setDouble(2, paymentAmount);
                paymentPs.setLong(3, invoice_NO);
                paymentPs.executeUpdate();

                // Insert invoice records
                String invoiceQuery = "INSERT INTO invoice (month, value, student_Sno, class_ClassNo, payment_invoice_NO) "
                        + "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement invoicePs = MySQL.getConnection().prepareStatement(invoiceQuery);

                // Insert each item from table
                for (int i = 0; i < model.getRowCount(); i++) {
                    String month = (String) model.getValueAt(i, 6);
                    String amount = (String) model.getValueAt(i, 7);
                    String studentNo = (String) model.getValueAt(i, 1);
                    String classNo = (String) model.getValueAt(i, 0);

                    invoicePs.setString(1, month);
                    invoicePs.setDouble(2, Double.parseDouble(amount));
                    invoicePs.setString(3, studentNo);
                    invoicePs.setString(4, classNo);
                    invoicePs.setLong(5, invoice_NO);
                    invoicePs.executeUpdate();
                }

                // Commit transaction
                MySQL.getConnection().commit();

                // Show success message
                JOptionPane.showMessageDialog(this,
                        "Payment processed successfully",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                // Ensure the correct path to the .jasper file
                InputStream s = getClass().getClassLoader().getResourceAsStream("Report/Adyapana.jasper");

                if (s == null) {
                    throw new FileNotFoundException("Report template 'Adyapana.jasper' not found.");
                }

                HashMap<String, Object> params = new HashMap<>();
                params.put("Parameter1", String.valueOf(invoice_NO));
                params.put("Parameter6", jTextField3.getText());
                params.put("Parameter9", jTextField4.getText());
                params.put("Parameter10", jTextField5.getText());

                JRTableModelDataSource dataSource = new JRTableModelDataSource(jTable1.getModel());

                JasperPrint jasperPrint = JasperFillManager.fillReport(s, params, dataSource);

                JasperViewer.viewReport(jasperPrint, false);

                // Clear form and table
                Reset();
                model.setRowCount(0);

            } catch (Exception e) {
                // Rollback on error
                MySQL.getConnection().rollback();
                throw e;
            } finally {
                // Restore auto-commit
                MySQL.getConnection().setAutoCommit(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error processing payment: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void Reset() {
        jComboBox1.setSelectedIndex(0);  // Reset student selection
        jComboBox2.setSelectedIndex(0);  // Reset teacher selection
        LoadStudent();
        jTextField1.setEditable(true);
        jMonthChooser1.setMonth(0);      // Reset month selection
        jTextField1.setText("");         // Reset amount field
        jTextField3.setText("");         // Reset amount field
        jTextField4.setText("");         // Reset amount field
        jTextField5.setText("");         // Reset amount field

//        loadPayement("invoice.Inoice_No", "ASC", jTextField2.getText());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
