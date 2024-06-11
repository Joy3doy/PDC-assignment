/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package course_application;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;


/**
 *
 * @author GGPC
 */
public class ReadFile {

     public static void readFile(String semClass) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(semClass))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
}
