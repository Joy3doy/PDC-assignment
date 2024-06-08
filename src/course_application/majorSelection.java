/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course_application;

import java.util.Scanner;
import java.io.IOException; 
import java.io.FileWriter; 
import java.util.Map;

/**
 *
 * @author Denise
 */

public class majorSelection {
    public static String majorSelect(){
 
        Scanner scanner = new Scanner(System.in);
        boolean selection = true;
        String selectedMajor = "";
        
        //Major Selection 
        try {
            
            //Record major to course selection file
            FileWriter writer = new FileWriter("Course_Application//resources//CourseSelection.txt");
            while (selection) {
                
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.println("Please select 1, 2, 3,or 4 to enroll into your major.");
                System.out.println("--------------------------------------------------------------------------------------------------------");
        
               //Choose major
               int choice;
               try{
                   //Ensure that this is an integer (Assistance: ChatGPT)
                   String majorchoice = scanner.nextLine().trim();
                   if(majorchoice.isEmpty()){
                       System.out.println("Please select 1,2,3, or 4, for your major");
                       continue;
                   }
                    choice = Integer.parseInt(majorchoice); // Parse the input as an integer
               }catch (NumberFormatException e) {
                    System.out.println("Please select 1,2,3, or 4, for your major");
                    continue;
                }
            
               //Write major into course selection file
                switch (choice) {
                     case 1:
                    selectedMajor = "Software Development";
                    writer.write("Software Development\n");
                    break;
                case 2:
                    selectedMajor = "Data Analysis";
                    writer.write("Data Analysis\n");
                    break;
                case 3:
                    selectedMajor = "Digital Service";
                    writer.write("Digital Service\n");
                    break;
                case 4:
                    selectedMajor = "Network and Cyber Security";
                    writer.write("Network and Cyber Security\n");
                    break;
                    default:
                        System.out.println("Please select 1, 2, 3, or 4.");
                        break;
                }
                
                //Confirm major 
                
                String majorConfirmation; 
               
                System.out.println("--------------------------------------------------------------------------------------------------------");            
                System.out.println("Are you sure you want this as your major? Please input Y for yes or N for no");
                System.out.println("--------------------------------------------------------------------------------------------------------"); 
                
                majorConfirmation = scanner.next().toLowerCase();
                if (majorConfirmation.equals("y")) {
                        System.out.println("Major confirmed!");
                        selection = false;
                        
                        //Print out student information
                        System.out.println("--------------------------------------------------------------------------------------------------------");            
                        System.out.println("                                        Student information");
                        System.out.println("--------------------------------------------------------------------------------------------------------"); 
                        for (Map.Entry<String, String[]> entry : Student.studentInfoMap.entrySet()) {
                        System.out.println("Student ID: " + entry.getKey());
                        System.out.println("Name: " + entry.getValue()[0] + " " + entry.getValue()[1]);
                        System.out.println("Major: "+ selectedMajor);
                }
                } else if (majorConfirmation.equals("n")){
                        //Reset Major
                        System.out.println("Choose another major");
                        selectedMajor = ""; 
                } else if (!selectedMajor.isEmpty()){
                        System.out.println("Your choice is invalid, please select another major or enter y to confirm or n to change major");
                }
            
          }
            //Close file writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   
        return selectedMajor;
    }
}
