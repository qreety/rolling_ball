/*
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: Feb.2nd, 2015
 * For Assignment 2, CS6301.001
 * This program is to maintain a file of data
 * This class contains the main method
 */
package uiproject1;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author Wendi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        final List<User> user = IOreader.readin();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProjectGUI(user).setVisible(true);
                
            }
        });
    }
    
}
