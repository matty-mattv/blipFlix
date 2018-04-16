import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource; 

@WebServlet("/ShowMetadata")
public class ShowMetadata extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ShowMetadata() {
        super();
    }

    
    String print(String input) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NamingException
    {
//	    	String loginUser = "mytestuser";
//			String loginPasswd = "mypassword";
//			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	    	ArrayList<ArrayList<String>> bigTable = new ArrayList<>();
	    	
//	        Class.forName("com.mysql.jdbc.Driver").newInstance();
//	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	    	
	    	
	    	 Context initCtx = new InitialContext();
	            if (initCtx == null)
	                System.out.println("initCtx is NULL");

	            Context envCtx = (Context) initCtx.lookup("java:comp/env");
	            if (envCtx == null)
	                System.out.println("envCtx is NULL");

	            // Look up our data source
	            DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	            
	            if (ds == null)
	                System.out.println("ds is null.");

	            Connection dbcon = ds.getConnection();
	            if (dbcon == null)
	                System.out.println("dbcon is null.");
	    	Statement statement = dbcon.createStatement();
	    	//*********
	        String query = input;
	 		   ResultSet rs = statement.executeQuery(query);
	 		    
	 		   ResultSetMetaData md = rs.getMetaData();
	 		   int colCount = md.getColumnCount();
	 		   	 		   
	 			  ArrayList<String> eachLine = new ArrayList<>();
	
	 		   for (int i = 1; i <= colCount; i++) //First Line is column label
	 		   {
	 			   eachLine.add(md.getColumnLabel(i));
	 		   }
	 		  bigTable.add(eachLine);
	 		   
	 		  while (rs.next()) //The rest
	 		  {
	 			  eachLine = new ArrayList<>();
	 		      for (int i = 1; i <= colCount; i++) 
	 		      {
	 		          String value = rs.getString(i);
	 		          eachLine.add(value);
	 		      }
	     		  bigTable.add(eachLine);
	 		  }
	 		 
		        String result = "<div><h4>Command: " + input + "</h4></div>";
		        
		        
		        result += "<table class = 'table table-striped table-hover sortable table-bordered'>"
		        		+ "<tr>";
	
	 		  //PRINT
		        int count = 0;
	 		  for (ArrayList<String> each : bigTable)
	 		  {
	 			  for (String e : each)
	 			  {
	 				  if (count == 0)
	 					  result += "<th>" + e + "</th>";
	 				  else
	 					  result += "<td>" + e + "</td>";
	 			  }
	 			  count++;
	 			  result += "</tr>";
	 		  }
	 		  result += "</table><br>";
	 		  
	 		  return result;
	   

	        
    }
    
    
    
    
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		
		 
		response.setContentType("text/html"); // Response mime type
		
		try
		{

			String loginUser = "mytestuser";
			String loginPasswd = "mypassword";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
	    	
	        Class.forName("com.mysql.jdbc.Driver").newInstance();
	        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
  		   		   
	           ArrayList<String> tables = null;
	   
	           // receive the Type of the object in a String array.
	           DatabaseMetaData metadata = dbcon.getMetaData();
	           ResultSet rs = metadata.getTables(null, "public", "%" ,new String[] {"TABLE"} );
	   
	           tables = new ArrayList<String>();
	   
	           while (rs.next()) {
	   
	               tables.add(rs.getString("TABLE_NAME"));
	   
	           }
	           
	    		  String result = print("SHOW tables;");

	           int tableSize = tables.size(); //11 dont print last one
	           int count = 1;
	           
	           for (String s : tables)
	           {
	        	   		if (count < tableSize)
	        	   		{
	        	   			result += print("SHOW COLUMNS FROM " + s + ";");
	        	   		}
	        	   		count++;
	        	   }
	 	        

	    request.setAttribute("queryResultsOUT", result);
		request.getRequestDispatcher("ShowMetadata.jsp").forward(request,  response);
   		   
			
		}catch (SQLException ex) 
		{
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
            //Print out error
            return;
        }

	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
