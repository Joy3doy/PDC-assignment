/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package course_application;

/**
 *
 * @author bolosvadanga
 */
public class DataLect extends Lecturer{
    
    //Defines Lecturer for Data Analysis
    public DataLect(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
    }

    @Override
    public void teach() {
        System.out.println(getFirstName() + " " + getLastName() + " will be your lecturer for Data Analysis! \nFor any inquiries email them at " +  getEmail());
    }
}
