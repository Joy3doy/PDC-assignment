/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course_application;

import static course_application.ReadFile.readFile;
import java.io.File;
import java.util.Scanner;
import java.io.IOException; 
import java.io.FileNotFoundException; 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter; 
import java.io.PrintWriter;

/**
 *
 * @author Denise
 */
public class Student {
    public static void studentInformation(){
        
        while(true){
        Scanner scanner = new Scanner(System.in);
        
        FileWriter writer = new FileWriter("C:\\Users\\Denise\\Downloads\\Course_Application\\Course_Application\\files\\StudentInfo.txt");

        System.out.println("============================================================================");
        System.out.println("Welcome to the BCIS Course Registration system, please enter your first name.");
        System.out.println("============================================================================");
        String firstName = scanner.nextLine();
        
        System.out.println("============================================================================");
        System.out.println("Thank you " + firstName + ". Please enter your last name");
        System.out.println("============================================================================");
        String lastName = scanner.nextLine();
        
        System.out.println("============================================================================");
        System.out.println("Hi "+ firstName + " " + lastName + "! What is your student ID?");
        System.out.println("============================================================================");
        String studentID = scanner.nextLine();
        
        System.out.println("============================================================================");
        System.out.println(firstName + " " + lastName + " can you confirm this is your full name and student id? " + studentID);
        System.out.println("============================================================================");
        
         String confirm = scanner.nextLine().toLowerCase();
            while (!confirm.equals("y") && !confirm.equals("n")) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                confirm = scanner.nextLine().toLowerCase();
            }

            if (confirm.equals("y")) {
                System.out.println("============================================================================");
                System.out.println("Thank you! You may select a major from the following");
                System.out.println("============================================================================");
                break;
            } else {
                System.out.println("Please re-enter your details.");
            }
    }
  }       
}
