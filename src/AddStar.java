import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/AddStar")
public class AddStar extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddStar() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String starName = request.getParameter("starName"); 
		String starYear = request.getParameter("starYear");
		String genreName = request.getParameter("genreName"); 
		
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		
		
	    String result = null;

		boolean errorFlag = false;
		if (starName == null && genreName == null)
		{
			errorFlag = true;
		}
				
		try 
		{
			if (errorFlag)
			{
				System.out.println("ERRRRRRRORRRRRRRRR");
				result = "Star, Genre are required fields.";
			}
			
			
			boolean starFlag = false;
			boolean genreFlag = false;
			if (!errorFlag)
			{				

	            //Class.forName("org.gjt.mm.mysql.Driver");
//	            Class.forName("com.mysql.jdbc.Driver").newInstance();

//	            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	            // Declare our statement
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
	            //String query = "call addMovie('NEW MOVIE10000', 2018, 'New Director1', 'Best Star1', 1980, 'OK', @output);";
	            String query = null;
	            System.out.println("Star>" + starName);
	            System.out.println("Genre>" + genreName);
	            
	            if (starName != null)
	            {
	            		starFlag = true;
	            		System.out.println("star only");
	            }
	            
	            if (genreName != null) 
	            {
            			genreFlag = true;
	            		System.out.println("genre only");

	            }
	            
	            if (starFlag)
	            {
		            	System.out.println("LALLAALALALAL");
		            	if (starYear == "")
			            {
			            		query = "call addMovie(null, null, null, '" + starName + "', null, null, @output);" ;
			            		System.out.println(query);
			            }
		            	else
		            	{
		            		query = "call addMovie(null, null, null, '" + starName + "', '" + starYear +"', null, @output);" ;
		            		System.out.println(query);
		            	}
		            	
		            	
		            	
	            }
	            
	            if (genreFlag)
	            {
		            	System.out.println("YOOYYOYOYOYOOOYO");
	        			query = "call addMovie(null, null, null, null, null, '" + genreName + "', @output);" ;
	        			System.out.println(query);
	            }
	            
	            
	            
			    ResultSet rs = statement.executeQuery(query); 
			    
			    query = "SELECT @output;";
			    rs = statement.executeQuery(query); 
			    int output = -1;
			    while (rs.next())
			    {
			    		output = rs.getInt("@output");
			    }
			    System.out.println("WHAT I HAVE IS : " + output);
			    
			    
			    if (starFlag)
			    {
			    		String starYearOut = starYear;
			    		if (starYearOut == "")
			    			starYearOut = "null";
			    		
				    	result = "<div id=\"infobox\" class=\"border border-secondary\"> You've entered: <br>"+
			    				"Star Name: <strong>" + starName + "</strong> | Birth Year: <strong>" + starYearOut + "</strong><br></div>" + 
			    				"<br>";
			    	
			    		if (output == 8) // STAR
				    {
				    		
				    		result += "<div class='p-3 mb-2 text-white bg-success'> [ " + starName + " ] was added successfully!"
			    					+ " <br> Here are the last 5 entries of the stars table.</div>";
			    			
			    			
				    		result += "<table class = 'table table-striped table-hover sortable table-bordered'><tr><th>ID</th><th>Star Name</th><th>Birth Year</th></tr>";
				    		
				    		
				    		query = "SELECT * FROM (SELECT * FROM stars ORDER BY id DESC LIMIT 5) AS T ORDER BY id;";
				    		
				    		rs = statement.executeQuery(query);
				    		while (rs.next())
				    		{
				    			String s_id = rs.getString("id");
				    			String s_name = rs.getString("name");
				    			String s_year = rs.getString("birthYear");
				    			result += "<tr><td>" + s_id + "</td><td><a href='Star?id=" + s_id + "&'>" + s_name + 
				    					"</a></td><td>" + s_year + "</td></tr>";
				    		}
				    		result += "</table>";
				    		
				    }
			    		else
			    		{
			    			result += "<div class='p-3 mb-2 text-white bg-warning'> [ " + starName + " ] was NOT added, it already exist in the stars table."
			    					+ " <br> Here is the matching entry.</div>";
			    			
				    		result += "<table class = 'table table-striped table-hover sortable table-bordered'><tr><th>ID</th><th>Star Name</th><th>Birth Year</th></tr>";
				    		

			    			query = "SELECT * FROM stars where name = '" + starName + "';";
				    		
				    		rs = statement.executeQuery(query);
				    		while (rs.next())
				    		{
				    			String s_id = rs.getString("id");
				    			String s_name = rs.getString("name");
				    			String s_year = rs.getString("birthYear");
				    			result += "<tr><td>" + s_id + "</td><td><a href='Star?id=" + s_id + "&'>" + s_name + 
				    					"</a></td><td>" + s_year + "</td></tr>";
				    		}
				    		result += "</table>";
				    		
				    						    		
			    		}
			    	
			    }
			    
			    
			    if (genreFlag)
			    {
			    		result = "<div id=\"infobox\" class=\"border border-secondary\"> You've entered: <br>"+
			    				"Genre: <strong>" + genreName + "</strong><br></div>" +
			    				"<br>";
			 
			    			
			    		if (output == 9) // genre
				    {
			    			result += "<div class='p-3 mb-2 text-white bg-success'> [ " + genreName + " ] was added successfully!"
			    					+ " <br> Here are the last 5 entries of the  genre table.</div> ";
			    			
			    			
				    		result += "<table class = 'table table-striped table-hover sortable table-bordered'><tr><th>ID</th><th>Genre</th></tr>";
				    		
				    		
				    		query = "SELECT * FROM (SELECT * FROM genres ORDER BY id DESC LIMIT 5) AS T ORDER BY id;";
				    		
				    		rs = statement.executeQuery(query);
				    		while (rs.next())
				    		{
				    			String g_id = rs.getString("id");
				    			String g_name = rs.getString("name");
				    			System.out.println("TEST -> " + g_id + " " + g_name);
				    			result += "<tr><td>" + g_id + "</td><td><a href='Search?&title=&year=&director=&starName=&genre=" + g_name + "&'>" + g_name + "</a></td></tr>";
				    		}
				    		result += "</table>";
				    }
			    		else
			    		{
			    			result += "<div class='p-3 mb-2 text-white bg-warning'> [ " + genreName + " ] was NOT added, it already exist in the genres table."
			    					+ " <br> Here is the matching entry.</div>";
			    			
			    			result += "<table class = 'table table-striped table-hover sortable table-bordered'><tr><th>ID</th><th>Genre</th></tr>";
				    		

			    			query = "SELECT * FROM genres where name = '" + genreName + "';";
				    		
				    		rs = statement.executeQuery(query);
				    		while (rs.next())
				    		{
				    			String g_id = rs.getString("id");
				    			String g_name = rs.getString("name");
				    			System.out.println("TEST -> " + g_id + " " + g_name);
				    			result += "<tr><td>" + g_id + "</td><td><a href='Search?&title=&year=&director=&starName=&genre=" + g_name + "&'>" + g_name + "</a></td></tr>";
				    		}
				    		result += "</table>";
				    		

			    		}
			    		System.out.println(result);
			    		
			    }
			    
			    request.setAttribute("queryResultsOUT", result);
    				request.getRequestDispatcher("AddStarResult.jsp").forward(request,  response);

			}
		}
		catch (SQLException ex) 
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

	
	// ------------
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
