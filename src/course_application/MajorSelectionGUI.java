package course_application;

import javax.swing.*;
import java.awt.*;

public class MajorSelectionGUI {

    private JFrame frame;
    private JComboBox<String> majorComboBox;

    private String firstName;
    private String lastName;
    private String studentID;
    //String url = "jdbc:derby://localhost:1527/StudentInformation;create=true";

    public MajorSelectionGUI(String firstName, String lastName, String studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Major Selection");
        frame.setBounds(100, 100, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 1));

        // Greeting message
        JLabel lblGreeting = new JLabel("Hello, " + firstName + " " + lastName + "!");
        lblGreeting.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblGreeting);

        // Instruction label
        JLabel lblInstruction = new JLabel("Please select your major:");
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblInstruction);

        // Major selection combo box
        majorComboBox = new JComboBox<>();
        majorComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
            "Software Development", "Data Analysis", "Digital Service", "Network and Cyber Security"}));
        frame.getContentPane().add(majorComboBox);

        // Confirm Major button
        JButton btnConfirmMajor = new JButton("Confirm Major");
        frame.getContentPane().add(btnConfirmMajor);

        // Event listener for the confirm major button
        btnConfirmMajor.addActionListener(e -> {
            String selectedMajor = (String) majorComboBox.getSelectedItem();
            if (selectedMajor != null) {
                handleMajorConfirmation(selectedMajor);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a major.");
            }
        });
    }

    private void handleMajorConfirmation(String selectedMajor) {
        JOptionPane.showMessageDialog(frame, "You have confirmed your major as: " + selectedMajor);
        frame.dispose();
        CourseSelectionGUI courseSelectionGUI = new CourseSelectionGUI(firstName, lastName, studentID, selectedMajor);
        courseSelectionGUI.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MajorSelectionGUI window = new MajorSelectionGUI("John", "Doe", "12345678");
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
