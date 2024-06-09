/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course_application;


import static course_application.ReadFile.readFile;

/**
 *
 * @author Denise
 * 
 *
 */

public class Course_Application {

    public static void main(String[] args) {
        
        System.out.println("========================================================================================================");
        System.out.println("           Welcome to the BCIS Course Registration system, please enter your first name.");
        System.out.println("========================================================================================================");
        
        // Setting information for lecturers
        Lecturer softwareDevelopmentLecturer = new SoftwareLect("Minh", "Nguyen", "Minh.Nguyen@autuni.ac.nz");
        Lecturer dataAnalysisLecturer = new DataLect("Akbar", "Ghobakhlou", "Akbar.Ghobakhlou@john.doe@autuni.ac.nz");
        Lecturer networksAndCyberSecurityLecturer = new NetworkLect("Raymond", "Lutui", "john.doe@autuni.ac.nz");
        Lecturer digitalServicesLecturer = new DigiLect("Farhaan", "Mirza", "Farhaan.Mirza@autuni.ac.nz");

        
        // Student information method
        String studentID = Student.studentInformation();
        
        String majorFilePath = "Course_Application//resources//MajorSelection.txt";
        System.out.println("========================================================================================================");
        System.out.println("                                    Time to pick your Major!");
        System.out.println("========================================================================================================");
        readFile(majorFilePath);
        
        //Method to select Major
        String selectedMajor = majorSelection.majorSelect();

        //Adding major to students information
        Student.addMajorToStudent(studentID, selectedMajor);
        
        
        //Lecturer information based on students selected major
        System.out.println("========================================================================================================");
        System.out.println("                                    Teaching team");
        System.out.println("========================================================================================================");
        
        switch (selectedMajor) {
            case "Software Development":
                softwareDevelopmentLecturer.teach();
                break;
            case "Data Analysis":
                dataAnalysisLecturer.teach();
                break;
            case "Network and Cyber Security":
                networksAndCyberSecurityLecturer.teach();
                break;
            case "Digital Service":
                digitalServicesLecturer.teach();
                break;
            default:
                break;
        }
        CourseSelection.courseSelect(selectedMajor);
        
        //Final output for student information and their 
        System.out.println("========================================================================================================");
        System.out.println("                                    Student Information");
        System.out.println("========================================================================================================");
        
        Student.printStudentDetails(studentID);
        
        
        System.out.println("========================================================================================================");
        System.out.println("                                         Timetable");
        System.out.println("========================================================================================================");
        
        
        String timetableFilePath = "Course_Application//resources//CourseSelection.txt";
        readFile(timetableFilePath);
    } 
    
}

