package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentInfoDisplayGUI {

    private JFrame frame;

    public StudentInfoDisplayGUI(String firstName, String lastName, String major, String[] selectedCourses) {
        initialize(firstName, lastName, major, selectedCourses);
    }

    private void initialize(String firstName, String lastName, String major, String[] selectedCourses) {
        frame = new JFrame("Student Information");
        frame.setBounds(100, 100, 600, 230); // Adjusted frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel infoPanel = new JPanel(new GridLayout(5, 1));
        frame.getContentPane().add(infoPanel, BorderLayout.WEST);

        JLabel lblFirstName = new JLabel("First Name: " + firstName);
        lblFirstName.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name: " + lastName);
        lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblLastName);

        JLabel lblMajor = new JLabel("Major: " + major);
        lblMajor.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblMajor);

        JLabel lblSelectedCourses = new JLabel("Selected Courses:");
        lblSelectedCourses.setHorizontalAlignment(SwingConstants.CENTER);
        infoPanel.add(lblSelectedCourses);

        JPanel coursesPanel = new JPanel(new BorderLayout());
        frame.getContentPane().add(coursesPanel, BorderLayout.CENTER);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        StringBuilder coursesText = new StringBuilder();
        for (String course : selectedCourses) {
            coursesText.append(course).append("\n");
        }
        textArea.setText(coursesText.toString());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(580, 150)); // Adjusted preferred size
        coursesPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Apply for new course");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current StudentInfoDisplayGUI window
                frame.dispose();

                // Open the StudentInfoInputGUI window
                StudentInfoInputGUI studentInfoInputGUI = new StudentInfoInputGUI();
                studentInfoInputGUI.setVisible(true);
            }
        });
        buttonPanel.add(backButton);

        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
