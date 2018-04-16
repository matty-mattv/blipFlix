


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer; 

public class  AverageScript {

  
    public static void main(String[] args) {
       
        File log = new File("QUERTYTIME"); 
        
        
        try {
             String s; 
                     
            FileReader fr = new FileReader(log);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb  = new StringBuilder();

            while((s = br.readLine()) != null) {
                sb.append(s + " ");
            } 
         
             StringTokenizer st = new StringTokenizer(sb.toString()); 
           

             
             int count = 0; 
             double TJtotal = 0; 
             double TStotal = 0; 
             
      
             while( st.hasMoreTokens() ) {
		
                 if ( count % 2 == 0 ) {
                     TStotal += Long.parseLong(st.nextToken()) / 1000000000.0; 
                 
                     //System.out.println("TSTotal: " + TStotal );
                 }
                 else if ( count % 2 == 1 ) {
                     TJtotal += Long.parseLong(st.nextToken()) / 1000000000.0; 
                     //System.out.println("TJTotal: " + TJtotal );
                     
                 }   
                 
                 ++count; 
             }
          
             
             if ( count > 0 ) {
                    double TSaverage = TStotal / (count / 2); 
                    double TJaverage = TJtotal / (count / 2); 
                 System.out.println("The average of TS: " + TSaverage); 
                 System.out.println("The average of TJ: " + TJaverage); 
             }
             else {
                 System.out.println("No entry");
             }
           
        } catch(IOException e){
            System.out.println("error"); 
        }
    }
    
    
}
