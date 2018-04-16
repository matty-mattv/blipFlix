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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet("/Movie")
public class Movie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Movie() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// this is this movie page's movie's id
		String id = request.getParameter("id");
		System.out.println("TEST->THIS IS MOVIE ID: " + id);		
		
		// capture this movie id into session, to be used for cart
		HttpSession session = request.getSession(); 

        // this is for session
		session.setAttribute("movieId", id);
		String movieId = (String)session.getAttribute("movieId");
		System.out.println("TEST->THIS IS MOVIE ID: " + movieId);		

				
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		

		try {

//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
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
            // Declare our statement
            Statement statement = dbcon.createStatement();
            Statement statement2 = dbcon.createStatement(); 
            Statement statement3 = dbcon.createStatement(); 
            
           
            String movieQuery = "SELECT * FROM movies WHERE id = \"" + id + "\";"; 
            String genreQuery = "SELECT gm1.genreId, g1.name, gm1.movieId FROM genres g1, genres_in_movies gm1 WHERE gm1.movieId = \"" + id + "\" AND g1.id = gm1.genreId;";
            String starQuery = "SELECT sm1.starId, s1.name, sm1.movieId FROM stars s1, stars_in_movies sm1 WHERE sm1.movieId = \"" + id + "\" AND s1.id = sm1.starId ORDER BY s1.name;"; 

			ResultSet movieRs = statement.executeQuery(movieQuery);
			ResultSet genreRs = statement2.executeQuery(genreQuery);
			ResultSet starRs = statement3.executeQuery(starQuery);

		    String table = "<table class=\"table\">"; 		    		 
		    
		    // this is for session
		    String m_title = "";
		    
		    while(movieRs.next()) 
		    {
	    	 		m_title = movieRs.getString("title");
		        String m_director = movieRs.getString("director"); 
		        String m_year = movieRs.getString("year");

		        
		      	String result =
      					"<tr><th>Title: </th><td>" + m_title + "</a></td></tr>" +
      					"<tr><th>Year: </th><td>" + m_year + "</a></td></tr>" +
      					"<tr><th>Director: </th><td>" + m_director + "</a></td></tr>" +
      					"<tr><th>Genres: </th><td>";
		      	table = table.concat(result); 
		    }
		    // for session
			session.setAttribute("movieTitle", m_title);
			String movieTitle = (String)session.getAttribute("movieTitle");
			System.out.println("TEST->THIS IS MOVIE TITLE: " + movieTitle);		


	      	while(genreRs.next()) 
	      	{
		        String g_name = genreRs.getString("g1.name"); 
//		        String g_id = genreRs.getString("gm1.genreId"); 
		        String result2 = "<li><a href='Search?&title=&year=&director=&starName=&genre=" + g_name + "&'>" + g_name + "</a></li>";

		        
		        

		        
	       		System.out.println("TEST----> " + g_name);
				table = table.concat(result2); 	

		   	}
		    table = table.concat("</td></tr>"); 
		    
		    
		    table = table.concat("<tr><th>Stars: </th><td>"); 
	      	while(starRs.next()) 
	      	{
		        String s_name = starRs.getString("s1.name"); 
		        String s_id = starRs.getString("sm1.starId"); 
		        String result3 = "<li><a href=Star?id=" + s_id +">" + s_name + "</a></li>";
	       		System.out.println("TEST----> " + s_name);
				table = table.concat(result3); 	

		   	}
		    
		    
		    movieRs.close();
		    genreRs.close();
		    starRs.close();
		    
		    table = table.concat("</td></tr></table>"); 
		    request.setAttribute("queryResults", table);
		    request.getRequestDispatcher("Movie.jsp").forward(request,  response);	        
		    System.out.println("IT HAS REACHED THE END");
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
