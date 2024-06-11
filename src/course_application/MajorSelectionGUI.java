package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MajorSelectionGUI {

    //Declare class variables
    private JFrame frame;
    private JComboBox<String> majorComboBox;
    private String firstName;
    private String lastName;
    private String studentID;

    //Constructor for initializing the GUI with student information
    public MajorSelectionGUI(String firstName, String lastName, String studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        initialize();
    }

    //Initialize the contents of the frame
    private void initialize() {
        frame = new JFrame("Major Selection");
        frame.setBounds(100, 100, 400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(4, 1));

        //Add greeting label with student's name
        JLabel lblGreeting = new JLabel("Hello, " + firstName + " " + lastName + "!");
        lblGreeting.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblGreeting);
        
        //Add instruction label for major selection
        JLabel lblInstruction = new JLabel("Please select your major:");
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblInstruction);
        
        //Add combo box for major selection
        majorComboBox = new JComboBox<>();
        majorComboBox.setModel(new DefaultComboBoxModel<>(new String[]{
                "Software Development", "Data Analysis", "Digital Service", "Network and Cyber Security"}));
        frame.getContentPane().add(majorComboBox);

        //Add button panel for back and confirm major buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        
        //Add back button to return to StudentInfoInputGUI
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

        //Add confirm major button to proceed to course selection
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

    //Method to handle major confirmation
    private void handleMajorConfirmation(String selectedMajor) {
        JOptionPane.showMessageDialog(frame, "You have confirmed your major as: " + selectedMajor);
        frame.dispose();
        CourseSelectionGUI courseSelectionGUI = new CourseSelectionGUI(firstName, lastName, studentID, selectedMajor);
        courseSelectionGUI.setVisible(true);
    }

    //frame visibility
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
