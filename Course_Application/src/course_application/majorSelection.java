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
public class majorSelection {
    public static void majorSelect(){
        String filePath = "C:\\Users\\Denise\\Downloads\\Course_Application\\Course_Application\\files\\MajorSelection.txt";
        readFile(filePath);
        
        Scanner scanner = new Scanner(System.in);
        boolean selection = true;
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Denise\\Downloads\\Course_Application\\Course_Application\\files\\StudentInfo.txt");
            while (selection) {
                System.out.println("====================================================");
                System.out.println("Please select 1,2 ,3 , or 4 to enroll into your major.");
                System.out.println("====================================================");
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.println("Software Development");
                        writer.write("Software Development\n");
                        break;
                    case 2:
                        System.out.println("Data Analysis");
                        writer.write("Data Analysis\n");
                        break;
                    case 3:
                        System.out.println("Networks and Cyber Security");
                        writer.write("Networks and Cyber Security\n");
                        break;
                    case 4:
                        System.out.println("Digital Services");
                        writer.write("Digital Services\n");
                        
                        break;
                    default:
                        System.out.println("Invalid character");
                }
                if (choice >= 1 && choice <= 4) {
                    System.out.println("Are you sure you want this as your major? Please input Y for yes or N for no");
                    String majorConfirmation;
                    boolean majorConfirmed = false;
                    while (!majorConfirmed) {
                        majorConfirmation = scanner.next().toLowerCase();
                        if (majorConfirmation.equals("y")) {
                            System.out.println("Major confirmed");
                            majorConfirmed = true;
                            selection = false; 
                        } else if (majorConfirmation.equals("n")) {
                            System.out.println("Choose another major");
                            break; 
                        } else {
                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
}
