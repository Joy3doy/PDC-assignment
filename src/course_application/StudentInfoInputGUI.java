package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentInfoInputGUI {

    private JFrame frame;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentIDField;
    String url = "jdbc:derby://localhost:1527/StudentInformation;create=true";

    public StudentInfoInputGUI() {
        initialize();
    }

    public static void main(String[] args) {
        StudentInfoInputGUI studentInfoInputGUI = new StudentInfoInputGUI();
        studentInfoInputGUI.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Student Information Input");
        frame.setBounds(100, 100, 400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 2));

        JLabel lblFirstName = new JLabel("First Name:");
        frame.getContentPane().add(lblFirstName);

        firstNameField = new JTextField();
        frame.getContentPane().add(firstNameField);
        firstNameField.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name:");
        frame.getContentPane().add(lblLastName);

        lastNameField = new JTextField();
        frame.getContentPane().add(lastNameField);
        lastNameField.setColumns(10);

        JLabel lblStudentID = new JLabel("Student ID:");
        frame.getContentPane().add(lblStudentID);

        studentIDField = new JTextField();
        frame.getContentPane().add(studentIDField);
        studentIDField.setColumns(10);

        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmInformation();
            }
        });
        frame.getContentPane().add(btnNext);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void confirmInformation() {
    // Get student information
    String firstName = firstNameField.getText().trim();
    String lastName = lastNameField.getText().trim();
    String studentID = studentIDField.getText().trim();

    // Validate input
    if (!firstName.matches("[A-Za-z]+") || !lastName.matches("[A-Za-z]+")) {
        JOptionPane.showMessageDialog(frame, "First name and last name should contain only alphabetical characters.");
        return;
    }

    // Capitalize first letter and convert the rest to lowercase
    firstName = capitalize(firstName);
    lastName = capitalize(lastName);

    // Validate student ID
    if (!studentID.matches("\\d{8}")) {
        JOptionPane.showMessageDialog(frame, "Student ID should contain exactly 8 digits.");
        return;
    }

    // Show confirmation dialog
    int option = JOptionPane.showConfirmDialog(frame,
            "First Name: " + firstName + "\n" +
                    "Last Name: " + lastName + "\n" +
                    "Student ID: " + studentID + "\n\n" +
                    "Is this information correct?",
            "Confirm Information",
            JOptionPane.YES_NO_OPTION);

    if (option == JOptionPane.YES_OPTION) {
        // Pass student information to the next GUI
        MajorSelectionGUI majorSelectionGUI = new MajorSelectionGUI(firstName, lastName, studentID);
        majorSelectionGUI.setVisible(true);
        frame.dispose(); // Close the current frame
    }
    
    // Save student information to the database
    Student.saveStudentInfoToDatabase(studentID, firstName, lastName, "");
}


private String capitalize(String str) {
    if (str.isEmpty()) {
        return str;
    }
    return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
}

}