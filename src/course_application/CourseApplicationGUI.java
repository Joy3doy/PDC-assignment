/*package course_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class CourseApplicationGUI {

    private JFrame frame;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField studentIDField;
    private JComboBox<String> majorComboBox;
    private JTextArea displayArea;
    private JList<String> courseList;
    private DefaultListModel<String> courseListModel;
    private Set<String> selectedCourses;
    private JLabel selectedCourseCountLabel;

    private static final int MAX_COURSES = 8;
    private static Map<String, String[]> studentInfoMap = new HashMap<>();
    private static final Map<String, Integer> COURSE_SELECTION_CLASSES = new HashMap<>();

    static {
        COURSE_SELECTION_CLASSES.put("Software Development", 4);
        COURSE_SELECTION_CLASSES.put("Data Analysis", 18);
        COURSE_SELECTION_CLASSES.put("Digital Service", 32);
        COURSE_SELECTION_CLASSES.put("Network and Cyber Security", 46);
    }

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
        selectedCourses = new HashSet<>();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("BCIS Course Registration System");
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(9, 2));

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

        JLabel lblCourses = new JLabel("Available Courses:");
        frame.getContentPane().add(lblCourses);

        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(courseList);
        frame.getContentPane().add(listScrollPane);

        selectedCourseCountLabel = new JLabel("Selected Courses: 0/" + MAX_COURSES);
        frame.getContentPane().add(selectedCourseCountLabel);

        JButton btnLoadCourses = new JButton("Load Courses");
        btnLoadCourses.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadCourses();
            }
        });
        frame.getContentPane().add(btnLoadCourses);

        JButton btnSelectCourse = new JButton("Select Course");
        btnSelectCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCourse();
            }
        });
        frame.getContentPane().add(btnSelectCourse);

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

    private void loadCourses() {
        String selectedMajor = (String) majorComboBox.getSelectedItem();
        if (selectedMajor != null) {
            courseListModel.clear();
            selectedCourses.clear();
            List<String> courses = getCoursesForMajor(selectedMajor);
            for (String course : courses) {
                courseListModel.addElement(course);
            }
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
                    courseListModel.removeElement(course);
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
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String studentID = studentIDField.getText().trim();
        String selectedMajor = (String) majorComboBox.getSelectedItem();

        if (!firstName.matches("[a-zA-Z]+")) {
            displayArea.setText("First name should contain only alphabetical characters.");
            return;
        }

        if (!lastName.matches("[a-zA-Z]+")) {
            displayArea.setText("Last name should contain only alphabetical characters.");
            return;
        }

        if (!studentID.matches("\\d+")) {
            displayArea.setText("Student ID should contain only numeric characters.");
            return;
        }

        if (firstName.isEmpty() || lastName.isEmpty() || studentID.isEmpty() || selectedMajor == null) {
            displayArea.setText("Please fill in all fields.");
            return;
        }

        if (selectedCourses.size() != MAX_COURSES) {
            displayArea.setText("Please select exactly " + MAX_COURSES + " courses.");
            return;
        }

        firstName = capitalize(firstName);
        lastName = capitalize(lastName);

        saveStudentInfoToFile(studentID, firstName, lastName, selectedMajor, new ArrayList<>(selectedCourses));
        studentInfoMap = loadStudentInfoFromFile();

        studentInfoMap.forEach((id, info) -> {
            if (id.equals(studentID)) {
                info[2] = selectedMajor;
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(studentID).append("\nName: ").append(firstName).append(" ").append(lastName)
                .append("\nMajor: ").append(selectedMajor).append("\nCourses:\n");
        for (String course : selectedCourses) {
            sb.append(course).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void saveStudentInfoToFile(String studentID, String firstName, String lastName, String major, List<String> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Course_Application//resources//StudentInfo.txt", true))) {
            writer.print(studentID + "," + firstName + "," + lastName + "," + major);
            for (String course : courses) {
                writer.print("," + course);
            }
            writer.println();
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

    public List<String> loadCoursesForMajor(String major) {
        return getCoursesForMajor(major);
    }
}
*/