/*
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: Feb.2nd, 2015
 * For Assignment 2, CS6301.001
 * This program is to maintain a file of data
 * This class contains the User class to store information
 */
package uiproject1;

/**
 *
 * @author Wendi
 */
public class User {
    
    String firstName;
    String lastName;
    String mid;
    String addLine1;
    String addLine2;
    String city;
    String state;
    String zip;
    String phoneNum;
    String gender;
    
    public User(String fn, String ln, String m, String ad1, String ad2, String c, String s, String z, String pn, String g)
    {
        firstName = fn;
        lastName = ln;
        mid = m;
        addLine1 = ad1;
        addLine2 = ad2;
        city = c;
        state = s;
        zip = z;
        phoneNum = pn;
        gender = g;
    }
   
}

