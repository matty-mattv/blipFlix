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

@WebServlet("/AddMovie")
public class AddMovie extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddMovie() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		String title = request.getParameter("title");
		String year = request.getParameter("year"); 
		String director = request.getParameter("director");
		String starName = request.getParameter("starName"); 
		String starYear = request.getParameter("starYear");
		String genreName = request.getParameter("genreName"); 
		
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		
		
	    String result = null;

		boolean errorFlag = false;
		if (title == null || year == null || starName == null || genreName == null)
		{
			errorFlag = true;
		}
				
		try 
		{
			if (errorFlag)
			{
				System.out.println("ERRRRRRRORRRRRRRRR");
				result = "Title, Year, Star, Genre are required fields.";
			}
			
			
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
	            System.out.println("<MOVIE->" + title +" " + year +" "+ director);
	            System.out.println("<Star->" + starName +" " + starYear);
	            System.out.println("<Genre->" + genreName);
	            		
	            	// Assumption, because of the required text field, all required text field and variable WILL have values
	            	
	            if (starYear == "")
	            {
	            		System.out.println("LALLAALALALAL");
	            		query = "call addMovie('" + title + "', '" +  year + "', '" + director + "', '" + starName + "', null, '" + genreName + "', @output);" ;
	            		System.out.println(query);
	            }
	            else 
	            {
	            		query = "call addMovie('" + title + "', '" +  year + "', '" + director + "', '" + starName + "', '" + starYear + "', '" + genreName + "', @output);" ;
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

			    /*Conditions:
			     *  # output = 10 --> ONLY MOVIE
			        # output = 20 --> ONLY STAR
			        # output = 40 --> ONLY GENRE
			        
			        # output = 30 --> MOVIE + STAR
			        # output = 50 --> MOVIE + GENRE
			        # output = 60 --> STAR + GENRE
			        # output = 70 --> MOVIE + STAR + GENRE
			     */
			    String starYearOut = starYear;
		    		if (starYearOut == "")
		    			starYearOut = "null";
		    		
			    	result = "<div id=\"infobox\" class=\"border border-secondary\"> You've entered: <br>"+
		    				"Movie Title: <strong>" + title + "</strong> | Year: <strong>" + year + "</strong> | Director: <strong>" + director + "</strong><br>" + 
		    				"Star Name: <strong>" + starName + "</strong> | Birth Year: <strong>" + starYearOut + "</strong> | Genre: <strong>" + genreName + "</strong></div>";
					boolean movieAdded = false;
					boolean starAdded = false;
					boolean genreAdded = false;
					boolean movieNotAdded = false;					
					boolean starNotAdded = false;
					boolean genreNotAdded = false;

			    if (output == 10) // ONLY MOVIE
			    {
			    		movieAdded = true; // determine printing the table
						
			    		starNotAdded = true;
					genreNotAdded = true;

			    }
			    
			    if (output == 20) // ONLY STAR
			    {
			    		starAdded = true;
			    		
			    		movieNotAdded = true;					
					genreNotAdded = true;

			    }
			    
			    if (output == 40) // ONLY GENRE
			    {
			    		genreAdded = true;
			    		
			    		movieNotAdded = true;					
					starNotAdded = true;

			    }
			    
			    if (output == 30) // Movie & Star
			    {
			    		movieAdded = true;
			    		starAdded = true;
			    				
					genreNotAdded = true;

			    }
			    
			    if (output == 50) // Movie & Genre
			    {
				    	movieAdded = true;
				    	genreAdded = true;
				    	
					starNotAdded = true;

			    }
			    
			    if (output == 60) // Star & Genre
			    {
				    	starAdded = true;
				    	genreAdded = true;
				    			
			    		movieNotAdded = true;					

			    }
			    
			    if (output == 70) // Movie + Star + Genre ALL added
			    {
				    	movieAdded = true;
			    		starAdded = true;
			    		genreAdded = true;
			    }
			    
			    if (output == 0) // Nothing was added
			    {
			    		movieNotAdded = true;					
					starNotAdded = true;
					genreNotAdded = true;

			    }
			    
			    if (movieAdded)
			    {
			    		result += "<div class='p-3 mb-2 text-white bg-success'> Movie [ " + title + " ] was added.</div>";
			    		
			    		String queryMovie = "SELECT * FROM movies where title = '" + title + "';";
				    	String queryStar = "SELECT m1.title, s1.name, s1.birthYear\n" + 
				    			"FROM movies m1, stars s1, stars_in_movies sm1,\n" + 
				    			"(SELECT *\n" + 
				    			"FROM movies m2\n" + 
				    			")t1\n" + 
				    			"WHERE m1.title = '" + title + "'\n" +
				    			"AND m1.id = sm1.movieId\n" + 
				    			"AND s1.id = sm1.starId\n" + 
				    			"AND t1.id = m1.id;";
				    	String queryGenre = "SELECT g1.name\n" + 
				    			"FROM movies m1, genres g1, genres_in_movies gm1,\n" + 
				    			"(SELECT * FROM movies m2\n" + 
				    			"WHERE m2.title = '" + title + "'\n" +
				    			"LIMIT 20)t1\n" + 
				    			"WHERE t1.id = m1.id\n" + 
				    			"AND g1.id = gm1.genreId\n" + 
				    			"AND m1.id = gm1.movieId\n" + 
				    			";";
				    	
				    	MovieInfo myMovie = new MovieInfo();
				    	
				    	// Get movie stuff
				    	ResultSet rsMovie = statement.executeQuery(queryMovie);
				    	while (rsMovie.next())
				    	{
				    		String m_id = rsMovie.getString("id");
			    			String m_title = rsMovie.getString("title");
			    			String m_year = rsMovie.getString("year");
			    			String m_director = rsMovie.getString("director");
			    			myMovie.id = m_id; 
						myMovie.title = m_title;
						myMovie.year = m_year;
						myMovie.director = m_director;
				    	}
				        
				    	// get genre
				    	ResultSet rsGenre = statement.executeQuery(queryGenre);
				    while(rsGenre.next())
				    {
				    		String g_name = rsGenre.getString("g1.name");
				    		myMovie.genre.add(g_name);
				    }
				    	
				    // get star
				    ResultSet rsStar = statement.executeQuery(queryStar);
				    while(rsStar.next())
				    {
				    		String s_name = rsStar.getString("s1.name");
				    		String s_year = rsStar.getString("s1.birthYear");
				    		myMovie.star.add(s_name);
				    		myMovie.starYear.add(s_year);
				    }
				    
				    result += "<table class = 'table table-striped table-hover sortable table-bordered'>"
			    			+ "<tr><th>ID</th><th>Movie Title</th><th>Director</th><th>Year</th><th>Genre</th><th>Star Name</th><th>Star Year</th></tr>"
				    		+ "<tr>";

				    result += "<td>" + myMovie.id + "</td>" + 
				    		"<td><a href=Movie?id=" + myMovie.id +">" + myMovie.title + "</a></td>" +
				    		"<td>" + myMovie.director+ "</td>" +
				    		"<td>" + myMovie.year + "</td>";

				    result += "<td>";
				    for (String eachGenre : myMovie.genre)
				    {
				    		result += eachGenre + "<br>";
				    }
				    result += "</td>";
				    
				    result += "<td>";
				    for (String eachStar : myMovie.star)
				    {
				    		result += eachStar + "<br>";
				    }
				    result += "</td>";
				    
				    result += "<td>";
				    for (String eachStarYr : myMovie.starYear)
				    {
				    		result += eachStarYr + "<br>";
				    }
				    result += "</td>";
				    
				    result += "</tr></table>";			    		
			    	
			    }
			    
			    if (starAdded)
			    {
				    	result += "<div class='p-3 mb-2 text-white bg-success'> Star [ " + starName + " ] was added.</div>";
		    			
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
			    
			    if (genreAdded)
			    {
				    	result += "<div class='p-3 mb-2 text-white bg-success'> Genre [ " + genreName + " ] was added.</div>";
		    			
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
			    
			    
			    if (movieNotAdded)
			    {
				    	result += "<div class='p-3 mb-2 text-white bg-warning'> Movie [ " + title + " ] was NOT added, it already exist in the movies table. "
				    			+ "Here is the matching entry.</div>";

				    	String queryMovie = "SELECT * FROM movies where title = '" + title + "';";
				    	String queryStar = "SELECT m1.title, s1.name, s1.birthYear\n" + 
				    			"FROM movies m1, stars s1, stars_in_movies sm1,\n" + 
				    			"(SELECT *\n" + 
				    			"FROM movies m2\n" + 
				    			")t1\n" + 
				    			"WHERE m1.title = '" + title + "'\n" +
				    			"AND m1.id = sm1.movieId\n" + 
				    			"AND s1.id = sm1.starId\n" + 
				    			"AND t1.id = m1.id;";
				    	String queryGenre = "SELECT g1.name\n" + 
				    			"FROM movies m1, genres g1, genres_in_movies gm1,\n" + 
				    			"(SELECT * FROM movies m2\n" + 
				    			"WHERE m2.title = '" + title + "'\n" +
				    			"LIMIT 20)t1\n" + 
				    			"WHERE t1.id = m1.id\n" + 
				    			"AND g1.id = gm1.genreId\n" + 
				    			"AND m1.id = gm1.movieId\n" + 
				    			";";
				    	
				    	MovieInfo myMovie = new MovieInfo();
				    	
				    	// Get movie stuff
				    	ResultSet rsMovie = statement.executeQuery(queryMovie);
				    	while (rsMovie.next())
				    	{
				    		String m_id = rsMovie.getString("id");
			    			String m_title = rsMovie.getString("title");
			    			String m_year = rsMovie.getString("year");
			    			String m_director = rsMovie.getString("director");
			    			myMovie.id = m_id; 
						myMovie.title = m_title;
						myMovie.year = m_year;
						myMovie.director = m_director;
				    	}
				        
				    	// get genre
				    	ResultSet rsGenre = statement.executeQuery(queryGenre);
				    while(rsGenre.next())
				    {
				    		String g_name = rsGenre.getString("g1.name");
				    		myMovie.genre.add(g_name);
				    }
				    	
				    // get star
				    ResultSet rsStar = statement.executeQuery(queryStar);
				    while(rsStar.next())
				    {
				    		String s_name = rsStar.getString("s1.name");
				    		String s_year = rsStar.getString("s1.birthYear");
				    		myMovie.star.add(s_name);
				    		myMovie.starYear.add(s_year);
				    }
				    
				    result += "<table class = 'table table-striped table-hover sortable table-bordered'>"
			    			+ "<tr><th>ID</th><th>Movie Title</th><th>Director</th><th>Year</th><th>Genre</th><th>Star Name</th><th>Star Year</th></tr>"
				    		+ "<tr>";

				    result += "<td>" + myMovie.id + "</td>" + 
				    		"<td><a href=Movie?id=" + myMovie.id +">" + myMovie.title + "</a></td>" +
				    		"<td>" + myMovie.director+ "</td>" +
				    		"<td>" + myMovie.year + "</td>";

				    result += "<td>";
				    for (String eachGenre : myMovie.genre)
				    {
				    		result += eachGenre + "<br>";
				    }
				    result += "</td>";
				    
				    result += "<td>";
				    for (String eachStar : myMovie.star)
				    {
				    		result += eachStar + "<br>";
				    }
				    result += "</td>";
				    
				    result += "<td>";
				    for (String eachStarYr : myMovie.starYear)
				    {
				    		result += eachStarYr + "<br>";
				    }
				    result += "</td>";
				    
				    result += "</tr></table>";
			    }
			    
			    if (starNotAdded)
			    {
				    	result += "<div class='p-3 mb-2 text-white bg-warning'> Star [ " + starName + " ] was NOT added, it already exist in the stars table. "
				    			+ "Here is the matching entry.</div>";
		    			
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
			    
			    if (genreNotAdded)
			    {
				    	result += "<div class='p-3 mb-2 text-white bg-warning'> Genre [ " + genreName + " ] was NOT added, it already exist in the genres table. "
				    			+ "Here is the matching entry.</div>";
		    			
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
			    
			    
			    request.setAttribute("queryResultsOUT", result);
				request.getRequestDispatcher("AddMovieResult.jsp").forward(request,  response);

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
