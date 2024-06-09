package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//123

public class CourseApplicationGUI {

    private JFrame frame;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentIDField;
    private JComboBox<String> majorComboBox;
    private JTextArea displayArea;

    private static Map<String, String[]> studentInfoMap = new HashMap<>();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CourseApplicationGUI window = new CourseApplicationGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CourseApplicationGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("BCIS Course Registration System");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(6, 2));

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

        JLabel lblMajor = new JLabel("Select Major:");
        frame.getContentPane().add(lblMajor);

        majorComboBox = new JComboBox<>();
        majorComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
            "Software Development", "Data Analysis", "Digital Service", "Network and Cyber Security"}));
        frame.getContentPane().add(majorComboBox);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmitButton();
            }
        });
        frame.getContentPane().add(btnSubmit);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        frame.getContentPane().add(scrollPane);
    }

    private void handleSubmitButton() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String studentID = studentIDField.getText().trim();
        String selectedMajor = (String) majorComboBox.getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty() || studentID.isEmpty() || selectedMajor == null) {
            displayArea.setText("Please fill in all fields.");
            return;
        }

        firstName = capitalize(firstName);
        lastName = capitalize(lastName);

        saveStudentInfoToFile(studentID, firstName, lastName, selectedMajor);
        studentInfoMap = loadStudentInfoFromFile();

        studentInfoMap.forEach((id, info) -> {
            if (id.equals(studentID)) {
                info[2] = selectedMajor;
            }
        });

        displayArea.setText("Student ID: " + studentID + "\nName: " + firstName + " " + lastName + "\nMajor: " + selectedMajor);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void saveStudentInfoToFile(String studentID, String firstName, String lastName, String major) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Course_Application//resources//StudentInfo.txt", true))) {
            writer.println(studentID + "," + firstName + "," + lastName + "," + major);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String[]> loadStudentInfoFromFile() {
        Map<String, String[]> studentInfoMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Course_Application//resources//StudentInfo.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    studentInfoMap.put(parts[0], new String[]{parts[1], parts[2], parts[3]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentInfoMap;
    }
}
