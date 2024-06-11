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

    private JFrame frame;
    private JList<String> courseList;
    private JButton btnSubmit;
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

    static {
        COURSE_SELECTION_CLASSES.put("Software Development", 4);
        COURSE_SELECTION_CLASSES.put("Data Analysis", 18);
        COURSE_SELECTION_CLASSES.put("Digital Service", 32);
        COURSE_SELECTION_CLASSES.put("Network and Cyber Security", 46);
    }

    public CourseSelectionGUI(String firstName, String lastName, String studentID, String selectedMajor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.selectedMajor = selectedMajor;
        selectedCourses = new HashSet<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Course Selection - " + selectedMajor);
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(9, 1));

        JLabel lblInstruction = new JLabel("Please select your courses (up to 8):");
        lblInstruction.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lblInstruction);

        listModel = new DefaultListModel<>();
        courseList = new JList<>(listModel);
        courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        frame.getContentPane().add(new JScrollPane(courseList));

        selectedCourseCountLabel = new JLabel("Selected Courses: 0/" + MAX_COURSES);
        frame.getContentPane().add(selectedCourseCountLabel);

        JButton btnLoadCourses = new JButton("Load Courses");
        btnLoadCourses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCourses();
            }
        });
        frame.getContentPane().add(btnLoadCourses);

        JButton btnSelectCourse = new JButton("Select Course");
        btnSelectCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectCourse();
            }
        });
        frame.getContentPane().add(btnSelectCourse);

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmitButton();
            }
        });
        frame.getContentPane().add(btnSubmit);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        frame.getContentPane().add(new JScrollPane(displayArea));
    }

    private void loadCourses() {
        listModel.clear();
        selectedCourses.clear();
        List<String> courses = getCoursesForMajor(selectedMajor);
        for (String course : courses) {
            listModel.addElement(course);
        }
        updateSelectedCourseCount();
    }

    private List<String> getCoursesForMajor(String selectedMajor) {
        List<String> courses = new ArrayList<>();
        String filePath = "Course_Application//resources//CourseList.txt";
        int lineOffset = COURSE_SELECTION_CLASSES.get(selectedMajor);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            boolean readCourses = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(selectedMajor)) {
                    readCourses = true;
                } else if (line.startsWith("=") && readCourses) {
                    break;
                }
                if (readCourses && !line.startsWith("=")) {
                    currentLine++;
                    if (currentLine >= lineOffset && currentLine < lineOffset + 10) {
                        courses.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

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

    private void updateSelectedCourseCount() {
        selectedCourseCountLabel.setText("Selected Courses: " + selectedCourses.size() + "/" + MAX_COURSES);
    }

    private void displaySelectedCourses() {
        StringBuilder sb = new StringBuilder();
        sb.append("Selected Courses:\n");
        for (String course : selectedCourses) {
            sb.append(course).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void handleSubmitButton() {
        if (selectedCourses.size() != MAX_COURSES) {
            displayArea.setText("Please select exactly " + MAX_COURSES + " courses.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("You have selected the following courses:\n");
        for (String course : selectedCourses) {
            sb.append(course).append("\n");
        }
        displayArea.setText(sb.toString());
        frame.dispose();
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CourseSelectionGUI window = new CourseSelectionGUI("John", "Doe", "12345678", "Software Development");
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
