import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/Star")
public class Star extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Star() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		

		try {

//            Class.forName("com.mysql.jdbc.Driver").newInstance();

//            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
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
            Statement statement2 = dbcon.createStatement(); 
            
           
            String starQuery = "SELECT * FROM stars WHERE id = \"" + id + "\";"; 
            
            String movieQuery = "SELECT sm1.starId, m1.title, sm1.movieId FROM movies m1, stars_in_movies sm1 WHERE sm1.starId = \"" + id + "\" AND m1.id = sm1.movieId;"; 

			ResultSet starRs = statement.executeQuery(starQuery);
			ResultSet movieRs = statement2.executeQuery(movieQuery);
			
		    String table = "<table class=\"table\">"; 		    		 
		    
	         
		    while(starRs.next()) 
		    {
	    	 		String s_name = starRs.getString("name");
		        String s_birth = starRs.getString("birthyear"); 
		        
		      	String result =
      					"<tr><th>Name: </th>" + 	"<td>" + s_name + "</a></td></tr>" +
      					"<tr><th>Birth Year: </th>" + 	"<td>" + s_birth + "</a></td></tr>" +
      					"<tr><th>Movies: </th>" +
      					"<td>";
		      	table = table.concat(result); 
		    }

	      	while(movieRs.next()) 
	      	{
		        String m_title = movieRs.getString("m1.title"); 
		        String m_id = movieRs.getString("sm1.movieId"); 
		        String result2 = "<li><a href=Movie?id=" + m_id +">" + m_title + "</a></li>";
	       		System.out.println("TEST----> " + m_title);
				table = table.concat(result2); 	

		   	}
		    starRs.close();
		    movieRs.close();

		    table = table.concat("</td></tr></table>"); 
		    request.setAttribute("queryResults", table);
		    request.getRequestDispatcher("Star.jsp").forward(request,  response);	        
		    
		}  catch (SQLException ex) {
            while (ex != null) {
                System.out.println("SQL Exception:  " + ex.getMessage());
                ex = ex.getNextException();
            } // end while
        } // end catch SQLException

        catch (java.lang.Exception ex) {
           
            return;
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
