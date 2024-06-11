package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MajorSelectionGUI {

    private JFrame frame;
    private JComboBox<String> majorComboBox;
    private String firstName;
    private String lastName;
    private String studentID;

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

        JLabel lblGreeting = new JLabel("Hello, " + firstName + " " + lastName + "!");
        lblGreeting.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblGreeting);

        JLabel lblInstruction = new JLabel("Please select your major:");
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblInstruction);

        majorComboBox = new JComboBox<>();
        majorComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Software Development", "Data Analysis", "Digital Service", "Network and Cyber Security"}));
        frame.getContentPane().add(majorComboBox);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to StudentInfoInputGUI
                StudentInfoInputGUI studentInfoInputGUI = new StudentInfoInputGUI();
                studentInfoInputGUI.setVisible(true);
                frame.dispose();
            }
        });
        buttonPanel.add(btnBack);

        JButton btnConfirmMajor = new JButton("Confirm Major");
        btnConfirmMajor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMajor = (String) majorComboBox.getSelectedItem();
                if (selectedMajor != null) {
                    handleMajorConfirmation(selectedMajor);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a major.");
                }
            }
        });
        buttonPanel.add(btnConfirmMajor);

        frame.getContentPane().add(buttonPanel);
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
