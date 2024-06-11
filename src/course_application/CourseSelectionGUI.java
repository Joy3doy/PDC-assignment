package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseSelectionGUI {

    //Declare class variables
    private JFrame frame;
    private JList<String> courseList;
    private JButton btnSubmit;
    private JButton btnSelectCourse;
    private JButton btnLoadCourses;
    private DefaultListModel<String> listModel;
    private JLabel selectedCourseCountLabel;
    private Set<String> selectedCourses;
    private JTextArea displayArea;
    private static final int MAX_COURSES = 8;

    private String firstName;
    private String lastName;
    private String studentID;
    private String selectedMajor;

    private static final Map<String, Integer> COURSE_SELECTION_CLASSES = new HashMap<>();

    //Static initializer block to populate course selection classes using haashmap
    static {
        COURSE_SELECTION_CLASSES.put("Software Development", 3);
        COURSE_SELECTION_CLASSES.put("Data Analysis", 17);
        COURSE_SELECTION_CLASSES.put("Digital Service", 32);
        COURSE_SELECTION_CLASSES.put("Network and Cyber Security", 46);
    }

    //Constructor for initializing the GUI with student information and selected major
    public CourseSelectionGUI(String firstName, String lastName, String studentID, String selectedMajor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.selectedMajor = selectedMajor;
        selectedCourses = new HashSet<>();
        initialize();
    }

    //Initialize the contents of the frame
    private void initialize() {
    frame = new JFrame("Course Selection - " + selectedMajor);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    frame.getContentPane().add(mainPanel);

    // Greeting label
    JLabel greetingLabel = new JLabel(firstName + "'s major: " + selectedMajor + ", to load available classes click load courses, to pick classes click the classes click select course. When finished, click submit.");
    greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
    greetingLabel.setFont(new Font("Arial", Font.PLAIN, 14)); 
    mainPanel.add(greetingLabel, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel(new BorderLayout());
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    listModel = new DefaultListModel<>();
    courseList = new JList<>(listModel);
    courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    centerPanel.add(new JScrollPane(courseList), BorderLayout.CENTER);

    JPanel rightPanel = new JPanel(new BorderLayout());
    mainPanel.add(rightPanel, BorderLayout.EAST);

    JPanel selectedCoursesPanel = new JPanel(new BorderLayout());
    selectedCoursesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    rightPanel.add(selectedCoursesPanel, BorderLayout.CENTER);

    selectedCourseCountLabel = new JLabel("Selected Courses: 0/" + MAX_COURSES);
    selectedCoursesPanel.add(selectedCourseCountLabel, BorderLayout.NORTH);

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    displayArea.setLineWrap(true);
    displayArea.setWrapStyleWord(true);
    JScrollPane displayScrollPane = new JScrollPane(displayArea);
    displayScrollPane.setPreferredSize(new Dimension(500, 300));
    selectedCoursesPanel.add(displayScrollPane, BorderLayout.CENTER);

    JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    rightPanel.add(submitPanel, BorderLayout.SOUTH);

    // Load courses button
    btnLoadCourses = new JButton("Load Courses");
    btnLoadCourses.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadCourses();
        }
    });
    submitPanel.add(btnLoadCourses);

    // Select course button
    btnSelectCourse = new JButton("Select Course");
    btnSelectCourse.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            selectCourse();
        }
    });
    submitPanel.add(btnSelectCourse);

    // Submit button
    btnSubmit = new JButton("Submit");
    btnSubmit.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleSubmitButton();
        }
    });
    submitPanel.add(btnSubmit);
    
    // Change major button
    JButton backButton = new JButton("Change Major");
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to change your major?", "Change Major", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                frame.dispose();
                MajorSelectionGUI majorSelectionGUI = new MajorSelectionGUI(firstName, lastName, studentID);
                majorSelectionGUI.setVisible(true);
            }
        }
    });
    submitPanel.add(backButton);

    frame.setSize(1200, 400); //size
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

    //load available courses for the selected major
    private void loadCourses() {
        listModel.clear();
        selectedCourses.clear();
        List<String> courses = getCoursesForMajor(selectedMajor);
        for (String course : courses) {
            listModel.addElement(course);
        }
        updateSelectedCourseCount();
    }

    //read course list from file fore the selected major
    private List<String> getCoursesForMajor(String selectedMajor) {
    List<String> courses = new ArrayList<>();
    String filePath = "Course_Application/resources/CourseList.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean startReading = false;

        while ((line = reader.readLine()) != null) {
            if (startReading && !line.startsWith("=")) {
                courses.add(line);
            } else if (line.contains(selectedMajor)) {
                startReading = true;
            } else if (startReading && line.startsWith("=")) {
                break; 
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return courses;
}

    //select courses from list
    private void selectCourse() {
        List<String> selectedValuesList = courseList.getSelectedValuesList();
        if (selectedValuesList.size() + selectedCourses.size() > MAX_COURSES) {
            displayArea.setText("You can only select up to " + MAX_COURSES + " courses.");
        } else {
            for (String course : selectedValuesList) {
                if (!selectedCourses.contains(course)) {
                    selectedCourses.add(course);
                    listModel.removeElement(course);
                }
            }
            updateSelectedCourseCount();
            displaySelectedCourses();
        }
    }

    //update selected course count label
    private void updateSelectedCourseCount() {
        selectedCourseCountLabel.setText("Selected Courses: " + selectedCourses.size() + "/" + MAX_COURSES);
    }

    //display selected courses in the text area
    private void displaySelectedCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("Selected Courses:\n");
        for (String course : selectedCourses) {
            sb.append(course).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    //submit button click event
    private void handleSubmitButton() {
    if (selectedCourses.size() != MAX_COURSES) {
        displayArea.setText("Please select exactly " + MAX_COURSES + " courses.");
        return;
    }

    // Pass the necessary information to the StudentInfoDisplayGUI constructor
    StudentInfoDisplayGUI studentInfoDisplayGUI = new StudentInfoDisplayGUI(firstName, lastName, selectedMajor, selectedCourses.toArray(new String[0]));
    studentInfoDisplayGUI.setVisible(true);

    // Close the current CourseSelectionGUI window
    frame.dispose();
}


    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

}
