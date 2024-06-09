/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package course_application;

/**
 *
 * @author Denise
 * testing
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class CourseSelection {
    
    private static final Map<String, Integer> COURSE_SELECTION_CLASSES = new HashMap<>();
    
    static {
       // Defines location in file for classes in each major
        COURSE_SELECTION_CLASSES.put("Software Development",4);
        COURSE_SELECTION_CLASSES.put("Data Analysis", 18 );
        COURSE_SELECTION_CLASSES.put("Digital Service", 32);
        COURSE_SELECTION_CLASSES.put("Network and Cyber Security", 46);
    }
    public static void courseSelect(String selectedMajor) {
        String CourseSelectionFilePath = "Course_Application//resources//CourseList.txt";
        
        Set<Integer> selectedCourses = new HashSet<>();
        int courseClasses = 0;
        
        
        try (FileWriter writer = new FileWriter("Course_Application//resources//CourseSelection.txt")) {
            Scanner scanner = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new FileReader(CourseSelectionFilePath));
            String line;
            boolean printSelectedMajorTimetable = false;
            
            System.out.println("========================================================================================================");
            System.out.println("                                    Timetable Selection");
            System.out.println("========================================================================================================");
            
            // Print the timetable for the selected major            
            while ((line = reader.readLine()) != null) {
                if (line.contains(selectedMajor)) {
                    printSelectedMajorTimetable = true;
                } else if (line.startsWith("=") && printSelectedMajorTimetable) {
                    // Stop printing once the next major's timetable starts
                    break;
                }
                if (printSelectedMajorTimetable) {
                    System.out.println(line);
                }
            }
            
            reader.close();
            
            // Course selection loop
            while (courseClasses < 8) {
                System.out.println("========================================================================================================");
                System.out.println("Please pick 8 classes. Currently you've picked " + courseClasses +" courses out of 8 for this year");
                System.out.println("Please select a course (1-10) to enroll into your major.");
                System.out.println("========================================================================================================");  
                System.out.print("Enter your choice: ");
                
                //Check if input is an integer
                if (scanner.hasNextInt()) {  
                    
                    int choice = scanner.nextInt();
                    
                    // Check if the input is within the valid range
                    if (choice >= 1 && choice <= 10) {
                        //Check if the course has been previously selected
                        if (selectedCourses.contains(choice)) {
                            System.out.println("Course " + choice + " has already been selected. Please choose another course.");
                        } else {
                            //Select course
                            String courseName = getCourseName(CourseSelectionFilePath, choice, selectedMajor);
                            if (courseName != null) {
                                System.out.println(courseName + " selected");
                                writer.write(courseName + "\n");
                                selectedCourses.add(choice);
                                courseClasses++;
                            } else {
                                System.out.println("Course not found. Please try again.");
                            }
                        }
                    } 
                } else {
                    // If the input is not an integer, clear the scanner's buffer
                    System.out.println("Invalid selection. Please enter a number between 1 and 10.");
                    scanner.next();
                }
            }
            
            System.out.println("Course selection completed. Thank you for enrolling!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //Search for students major's classes in Course Selection File (Assistance from ChatGpt)
    private static String getCourseName(String filePath, int choice, String selectedMajor) {
        int lineOffset = COURSE_SELECTION_CLASSES.get(selectedMajor);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("=")) {
                    currentLine++;
                    if (currentLine == lineOffset + choice - 1) {
                        return line;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
}


