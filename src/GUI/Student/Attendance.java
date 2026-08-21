/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import model.MySQL;

/**
 *
 * @author Sandu
 */
public class Attendance extends javax.swing.JPanel {

    HashMap<String, String> LoadStudentMap = new HashMap();
    HashMap<String, String> LoadClassMap = new HashMap();
    HashMap<String, String> LoadStatusMap = new HashMap();

    /**
     * Creates new form StudentManage
     */
    public Attendance() {
        initComponents();
        LoadStudent();
        LoadClass();
        LoadStatus();
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
            jComboBox3.setModel(model);
            jComboBox3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    LoadClass();  // Student selection change වුනාම LoadClass method එක call වෙනවා
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadStatus() {

        try {
            String query = "SELECT * FROM status";
            PreparedStatement ps = MySQL.getConnection().prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            Vector<String> vector = new Vector();
            vector.add("Select status");

            while (rs.next()) {

                vector.add(rs.getString("name"));
                LoadStatusMap.put(rs.getString("name"), rs.getString("id"));
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            jComboBox2.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadClass() {
        try {
            // Get selected student ID
            String selectedStudent = String.valueOf(jComboBox3.getSelectedItem());
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
                    + "INNER JOIN subject s ON s.Subno = c.subject_Subno "
                    + "INNER JOIN teacher t ON t.Tno = c.teacher_Tno "
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();

        setMaximumSize(new java.awt.Dimension(827, 500));
        setMinimumSize(new java.awt.Dimension(827, 500));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Student ID :");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Class :");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Status :");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setBackground(new java.awt.Color(102, 102, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Mark Attendance");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(180, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(221, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(70, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        String student = String.valueOf(jComboBox3.getSelectedItem());
        String clz = String.valueOf(jComboBox1.getSelectedItem());
        String status = String.valueOf(jComboBox2.getSelectedItem());

        if (student.equals("Select student")) {
            JOptionPane.showMessageDialog(this, "Please Select Student",
                    "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        } else if (clz.equals("Select class")) {
            JOptionPane.showMessageDialog(this, "Please Select Class",
                    "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        } else if (status.equals("Select status")) {
            JOptionPane.showMessageDialog(this, "Please Select status",
                    "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                // Extract IDs from maps
                String studentId = LoadStudentMap.get(student);
                String classId = LoadClassMap.get(clz);
                String statusId = LoadStatusMap.get(status);

                if (studentId == null || classId == null || statusId == null) {
                    JOptionPane.showMessageDialog(this, "Invalid selection. Please try again.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Get current date
                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                // First check if attendance already exists for today
                String checkQuery = "SELECT COUNT(*) FROM attendance WHERE Date = ? AND class_ClassNo = ? AND student_Sno = ?";
                try (PreparedStatement check = MySQL.getConnection().prepareStatement(checkQuery)) {
                    check.setDate(1, sqlDate);
                    check.setString(2, classId);
                    check.setString(3, studentId);

                    ResultSet rs = check.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "Attendance already marked for this student today.",
                                "WARNING", JOptionPane.WARNING_MESSAGE);
                        Reset();
                        return;
                        
                    }

                    // If no attendance found, proceed with insertion
                    String insertQuery = "INSERT INTO `attendance` (`Date`, `class_ClassNo`, `student_Sno`, `status_id`) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement insert = MySQL.getConnection().prepareStatement(insertQuery)) {
                        insert.setDate(1, sqlDate);
                        insert.setString(2, classId);
                        insert.setString(3, studentId);
                        insert.setString(4, statusId);

                        insert.executeUpdate();
                        JOptionPane.showMessageDialog(this, "This Student is Attendance Marked successfully.",
                                "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        // Reload data and reset form
                        Reset();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(),
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "An error occurred while adding the student. Please try again.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables

    private void Reset() {
        jComboBox3.setSelectedIndex(0);
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
    }
}
