/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course_application;

import java.io.File;
import java.util.Scanner;
import java.io.IOException; 
import java.io.FileWriter; 
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author Denise
 */


public class Student {
    public static Map<String, String[]> studentInfoMap = new HashMap<>(); // HashMap to store student information (ID -> [FirstName, LastName, Major])


    public static String studentInformation() {
        Scanner scanner = new Scanner(System.in);
        String studentID = "";
        String firstName = "";
        String lastName = "";
        
        while (true) {
            
            //Ensure that first name is alphabetical character even if a space or 'enter' is inputted (Assistance: ChatGPT)
            boolean validFirstName = false;
            do{
                System.out.println("--------------------------------------------------------------------------------------------------------");            
                System.out.println("Please enter your first name:");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                firstName = scanner.nextLine();
            
                if (!isAlphabetical(firstName) || firstName.isEmpty()) {
                System.out.println("Invalid input, please enter alphabetical characters");
                } else{
                    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
                    validFirstName = true;
                }
            }while (!validFirstName);
            
            //Ensure that last name is alphabetical character even if a space or 'enter' is inputted (Assistance: ChatGPT)
            boolean validLastName = false;
            do{
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println(firstName + "! Please enter your last name:");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                lastName = scanner.nextLine().trim();
            
                if (!isAlphabetical(lastName) || lastName.isEmpty()) {
                System.out.println("Invalid input, please enter alphabetical characters");
                } else{
                    lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
                    validLastName = true;
                }
            }while (!validLastName);

            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println("Hi " + firstName + " " + lastName + "! What is your student ID?");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            studentID = scanner.nextLine();
            
            
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.println(studentID + ": " + firstName + " " + lastName + ", can you confirm this is your student id and full name?");
            System.out.println("y for yes, n for no");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            
            String confirm = scanner.nextLine().toLowerCase();
            
            while (!confirm.equals("y") && !confirm.equals("n")) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                confirm = scanner.nextLine().toLowerCase();
            }

            if (confirm.equals("y")) {
                // Save student information to file and display 
                saveStudentInfoToFile(studentID, firstName, lastName);
                studentInfoMap = loadStudentInfoFromFile();                
                break;
            } else {
                System.out.println("Please re-enter your details.");
            }
        }
        return studentID;
    }
    
    //Method check for alphabetical characters
    private static boolean isAlphabetical(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    //Method write student information to file
    private static void saveStudentInfoToFile(String studentID, String firstName, String lastName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Course_Application//resources//StudentInfo.txt"))) {
            writer.println(studentID + "," + firstName + "," +lastName);
            writer.flush();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    //Method load student information into studentInfoMap
    private static Map<String, String[]> loadStudentInfoFromFile() {
        Map<String, String[]> studentInfoMap = new HashMap<>();
        try (Scanner scanner = new Scanner(new File("Course_Application//resources//StudentInfo.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                studentInfoMap.put(parts[0], new String[]{parts[1], parts[2], ""}); 
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the file: " + e.getMessage());
        }
        return studentInfoMap;
    }

    //Link major to student (Assistance from ChatGpt)
    public static void addMajorToStudent(String studentID, String major) {
        String[] info = studentInfoMap.get(studentID);
        if (info != null) {
            info[2] = major;
            studentInfoMap.put(studentID, info);
            System.out.println("Thank you for enrolling into " + major);
        } else {
            System.out.println("Student with ID '" + studentID + "' not found.");
        }
    }
    
    //Print student details from Hashmap
    public static void printStudentDetails(String studentID) {
    String[] info = studentInfoMap.get(studentID);
    if (info != null) {
        System.out.println("Student ID: " + studentID);
        System.out.println("Name: " + info[0] + " " + info[1]);
        System.out.println("Major: " + info[2]);
    } else {
        System.out.println("Student with ID '" + studentID + "' not found.");
    }
    }
    
}

