/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Class;

import GUI.Student.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.MySQL;

/**
 *
 * @author Sandu
 */
public class ClassManagement extends javax.swing.JPanel {

    HashMap<String, String> loadSubMap = new HashMap();
    HashMap<String, String> loadTeaMap = new HashMap();

    /**
     * Creates new form StudentManage
     */
    public ClassManagement() {
        initComponents();
        loadClass("ClassNo", "ASC", "");
        LoadSub();
        LoadTea();
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.setDefaultRenderer(Object.class, renderer);
    }

    private void loadClass(String column, String orderby, String searchText) {

        try {
            String query = "SELECT * FROM class INNER JOIN teacher ON teacher.Tno = class.teacher_Tno  "
                    + "INNER JOIN subject ON subject.Subno = class.Subject_Subno "
                    + "WHERE ClassNo LIKE ? OR teacher.Name LIKE ? OR timeslot LIKE ? "
                    + "OR subject.Description LIKE ? "
                    + "ORDER BY " + column + " " + orderby;

            PreparedStatement preparedStatement = MySQL.getConnection().prepareStatement(query);
            String searchPattern = "%" + searchText + "%";

            // Bind the same search pattern to all fields
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            preparedStatement.setString(4, searchPattern);

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel defaultTableModel = (DefaultTableModel) jTable1.getModel();
            defaultTableModel.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> row = new Vector<>();
                row.add(resultSet.getString("ClassNo"));
                row.add(resultSet.getString("teacher.Name"));
                row.add(resultSet.getString("subject.Description"));
                row.add(resultSet.getString("timeslot"));

                defaultTableModel.addRow(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LoadSub() {

        try {
            String query = "SELECT * FROM subject";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Vector<String> vector = new Vector();
            vector.add("Select Subject");

            while (rs.next()) {
                String subno = rs.getString("Subno");
                String description = rs.getString("Description");
                String combined = subno + " " + description;

                vector.add(combined);
                loadSubMap.put(combined, subno);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadTea() {

        try {
            String query = "SELECT * FROM teacher";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Vector<String> vector = new Vector();
            vector.add("Select Teacher");

            while (rs.next()) {
                String Tno = rs.getString("Tno");
                String Name = rs.getString("Name");
                String combined = Tno + " " + Name;

                vector.add(combined);
                loadTeaMap.put(combined, Tno);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);
// Add ItemListener to teacher combo box
            jComboBox2.addItemListener(e -> {
                if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    loadTeacherSubjects((String) jComboBox2.getSelectedItem());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // New method to load subjects based on selected teacher
    private void loadTeacherSubjects(String selectedTeacher) {
        try {
            if (selectedTeacher.equals("Select Teacher")) {
                jComboBox1.setModel(new DefaultComboBoxModel<>(new String[]{"Select Subject"}));
                return;
            }

            String teacherId = loadTeaMap.get(selectedTeacher);
            String query = "SELECT subject_Subno FROM teacher WHERE Tno = ?";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();

            Vector<String> subjectVector = new Vector<>();
            subjectVector.add("Select Subject");
            loadSubMap.clear();

            if (rs.next()) {
                String subjects = rs.getString("subject_Subno");
                if (subjects != null && !subjects.isEmpty()) {
                    String[] subjectCodes = subjects.split(",");

                    for (String subCode : subjectCodes) {
                        subCode = subCode.trim();
                        // Get subject details for each subject code
                        String subQuery = "SELECT Subno, Description FROM subject WHERE Subno = ?";
                        PreparedStatement subPs = MySQL.getConnection().prepareStatement(subQuery);
                        subPs.setString(1, subCode);
                        ResultSet subRs = subPs.executeQuery();

                        if (subRs.next()) {
                            String combined = subRs.getString("Subno") + " " + subRs.getString("Description");
                            subjectVector.add(combined);
                            loadSubMap.put(combined, subRs.getString("Subno"));
                        }
                    }
                }
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(subjectVector);
            jComboBox1.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField6 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(827, 500));
        setMinimumSize(new java.awt.Dimension(827, 500));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setBackground(new java.awt.Color(204, 204, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Class Details");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Class ID");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Subject");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Teacher");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Time Slot :");

        jButton1.setBackground(new java.awt.Color(51, 51, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ADD CLASS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 153, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("UPDATE CLASS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 51, 51));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("DELETE CLASS");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(153, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("CLEAR ALL");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, 220, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addGap(6, 6, 6))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField6)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Search  :");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class ID", "Teacher", "Subject", "Time Slot"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String CNo = jTextField2.getText().trim();
        String teacher = String.valueOf(jComboBox2.getSelectedItem());
        String Subject = String.valueOf(jComboBox1.getSelectedItem());
        String TimeSlot = jTextField6.getText().trim();

        // Validate inputs
        if (CNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Class No", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Subject.equals("Select Teacher")) {
            JOptionPane.showMessageDialog(this, "Please Select Teacher", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (Subject.equals("Select Subject")) {
            JOptionPane.showMessageDialog(this, "Please Select Subject", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (TimeSlot.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Time Slot", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {

            try {

                String query = "SELECT * FROM `class` WHERE `ClassNo` = ?";
                try (PreparedStatement ps = MySQL.getConnection().prepareStatement(query)) {
                    ps.setString(1, CNo);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(this, "This Class is Already Added.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String query2 = "INSERT INTO `class` (`ClassNo`, `timeslot`, `teacher_Tno`, `subject_Subno`) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insert = MySQL.getConnection().prepareStatement(query2)) {
                    insert.setString(1, CNo);
                    insert.setString(2, TimeSlot);
                    insert.setString(3, loadTeaMap.get(teacher));
                    insert.setString(4, loadSubMap.get(Subject));
                    insert.executeUpdate();
                    JOptionPane.showMessageDialog(this, "This Class is Added successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    loadClass("ClassNo", "ASC", jTextField1.getText());
                    Reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while creating the account. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String CNo = jTextField2.getText().trim();
        String teacher = String.valueOf(jComboBox2.getSelectedItem());
        String Subject = String.valueOf(jComboBox1.getSelectedItem());
        String TimeSlot = jTextField6.getText().trim();

        // Validate inputs
        if (CNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Class No", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (Subject.equals("Select Teacher")) {
            JOptionPane.showMessageDialog(this, "Please Select Teacher", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (Subject.equals("Select Subject")) {
            JOptionPane.showMessageDialog(this, "Please Select Subject", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (TimeSlot.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Time Slot", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else {
            try {
                String subjectSubno = loadSubMap.get(Subject);
                String TeacherTeaNo = loadTeaMap.get(teacher);

                if (subjectSubno == null) {
                    JOptionPane.showMessageDialog(this, "Invalid Subject selected. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (TeacherTeaNo == null) {
                    JOptionPane.showMessageDialog(this, "Invalid Teacher selected. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String updateQuery = "UPDATE `class` SET `timeslot` = ?, `teacher_Tno` = ?, `subject_Subno` = ? WHERE `ClassNo` = ?";
                try (PreparedStatement ps = MySQL.getConnection().prepareStatement(updateQuery)) {
                    ps.setString(1, TimeSlot);
                    ps.setString(2, TeacherTeaNo);
                    ps.setString(3, subjectSubno);
                    ps.setString(4, CNo);

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "This Class is Updated successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "No Class found with the provided Class No.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                    loadClass("ClassNo", "ASC", jTextField1.getText());
                    Reset();
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while updating the Class. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String CNo = jTextField2.getText().trim();

        // Validate input
        if (CNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Class No to Delete", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Class?", "CONFIRMATION", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Prepare the DELETE query
                String deleteQuery = "DELETE FROM `class` WHERE `ClassNo` = ?";
                try (PreparedStatement ps = MySQL.getConnection().prepareStatement(deleteQuery)) {
                    ps.setString(1, CNo);

                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "This Class is Deleted successfully.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        loadClass("ClassNo", "ASC", jTextField1.getText());
                        Reset();
                    } else {
                        JOptionPane.showMessageDialog(this, "No Class found with the provided Class No.", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while deleting the Class. Please try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Reset();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int row = jTable1.getSelectedRow();
        if (row != -1) {
            try {
                // Disable button and set basic fields
                jButton1.setEnabled(false);
                jTextField2.setEditable(false);
                jTextField2.setText(String.valueOf(jTable1.getValueAt(row, 0))); // ClassNo
                jTextField6.setText(String.valueOf(jTable1.getValueAt(row, 3))); // TimeSlot

                String teacherName = String.valueOf(jTable1.getValueAt(row, 1)).trim();
                String subjectDescription = String.valueOf(jTable1.getValueAt(row, 2)).trim();

                // 1. First get teacher details and temporarily disable the ItemListener
                java.awt.event.ItemListener[] listeners = jComboBox2.getItemListeners();
                for (java.awt.event.ItemListener listener : listeners) {
                    jComboBox2.removeItemListener(listener);
                }

                // 2. Fetch and set teacher
                String teacherQuery = "SELECT t.Tno, t.Name, t.subject_Subno, s.Description "
                        + "FROM teacher t "
                        + "LEFT JOIN subject s ON FIND_IN_SET(s.Subno, t.subject_Subno) "
                        + "WHERE t.Name = ?";
                PreparedStatement teacherPS = MySQL.getConnection().prepareStatement(teacherQuery);
                teacherPS.setString(1, teacherName);
                ResultSet teacherRS = teacherPS.executeQuery();

                if (teacherRS.next()) {
                    String tnoWithName = teacherRS.getString("Tno") + " " + teacherName;
                    jComboBox2.setSelectedItem(tnoWithName);

                    // 3. Manually load subjects for this teacher
                    Vector<String> subjectVector = new Vector<>();
                    subjectVector.add("Select Subject");
                    loadSubMap.clear();

                    String subjects = teacherRS.getString("subject_Subno");
                    if (subjects != null && !subjects.isEmpty()) {
                        String[] subjectCodes = subjects.split(",");

                        for (String subCode : subjectCodes) {
                            subCode = subCode.trim();
                            String subQuery = "SELECT Subno, Description FROM subject WHERE Subno = ?";
                            PreparedStatement subPs = MySQL.getConnection().prepareStatement(subQuery);
                            subPs.setString(1, subCode);
                            ResultSet subRs = subPs.executeQuery();

                            if (subRs.next()) {
                                String combined = subRs.getString("Subno") + " " + subRs.getString("Description");
                                subjectVector.add(combined);
                                loadSubMap.put(combined, subRs.getString("Subno"));
                            }
                        }
                    }

                    // 4. Update subject combo box
                    DefaultComboBoxModel model = new DefaultComboBoxModel(subjectVector);
                    jComboBox1.setModel(model);

                    // 5. Set the correct subject
                    String subjectQuery = "SELECT Subno FROM subject WHERE Description = ?";
                    PreparedStatement subjectPS = MySQL.getConnection().prepareStatement(subjectQuery);
                    subjectPS.setString(1, subjectDescription);
                    ResultSet subjectRS = subjectPS.executeQuery();

                    if (subjectRS.next()) {
                        String subNoWithDescription = subjectRS.getString("Subno") + " " + subjectDescription;
                        jComboBox1.setSelectedItem(subNoWithDescription);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Teacher not found in database.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                // 6. Restore the ItemListener
                for (java.awt.event.ItemListener listener : listeners) {
                    jComboBox2.addItemListener(listener);
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while retrieving data: " + e.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        loadClass("ClassNo", "ASC", jTextField1.getText());
    }//GEN-LAST:event_jTextField1KeyReleased
    public void Reset() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField2.setEditable(true);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jTextField6.setText("");
        loadClass("ClassNo", "ASC", jTextField1.getText());
        jButton1.setEnabled(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
