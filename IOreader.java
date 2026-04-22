/*
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: Feb.2nd, 2015
 * For Assignment 2, CS6301.001
 * This class contains the file I/O methods
 */
package uiproject1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wendi
 */
public class IOreader {
    
/**
 * Read in the record file line by line
 * and call the readinfo method
 * to save the information to a list of contacts
 * @param in The bufferedReader of the record file 
 * @return A list of all contact informations
 * @throws IOException 
 */
	private static  List<User> readdocument( BufferedReader in) throws IOException
	{
            String oneLine;
            List<String> lst = new ArrayList<>( );
        
            while( ( oneLine = in.readLine( ) ) != null )
                lst.add( oneLine );
        
            List<User> usr = readinfo(lst);
            return usr;
	}
        /**
         * Gets the List of lines of information 
         * and save them to a list of users
         * @param lst The List of each line of the read file  
         * @return A list of all contact informations
         */
        private static List<User> readinfo (List<String> lst)
        {
            List<User> usr = new ArrayList<>();
            
            for (String x : lst) //use "\t" to split all the informaiton fields
		{
		String[] c = x.split("\t");
                User u = new User(c[0],c[1],c[2],c[3],c[4],c[5],c[6],c[7],c[8],c[9]);
                usr.add(u);
                }
            
            return usr;
        }
        
        /**
         * read the record file
         * @return a List of all contacts informations
         * @throws FileNotFoundException
         * @throws IOException 
         */
        public static List<User> readin() throws FileNotFoundException, IOException
        {
            FileReader fin = new FileReader( "record.txt" );
            BufferedReader bin = new BufferedReader( fin );
            List<User> usr = readdocument( bin );
            return usr;
        }
        
        /**
         * Write all contacts informations into a txt file
         * @param usr The List of contacts
         * @throws IOException 
         */
        public static void wrt(List<User> usr) throws IOException
        {
            File writename = new File("record.txt");  
            writename.createNewFile();  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            for( User u:usr)
            {
                out.write(u.firstName+"\t" + u.lastName+"\t" + u.mid+"\t" + u.addLine1+"\t" + u.addLine2+"\t" + u.city+"\t" + u.state+"\t" + u.zip+"\t" + u.phoneNum+"\t" + u.gender+"\t" + "\n"); 
            }
            
            out.close(); 
        }
}
