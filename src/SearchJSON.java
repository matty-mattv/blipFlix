import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

class MovieInfo1 {
	public String id; 
    public String title; 
    public String director; 
    public String year; 
    public ArrayList<String> star = new ArrayList<>(); 
    public ArrayList<String> genre = new ArrayList<>();
    public ArrayList<String> starYear = new ArrayList<>(); 
}

@WebServlet("/SearchJSON")
public class SearchJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchJSON() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String title = request.getParameter("title");		
		String changePage = request.getParameter("changePage"); 
		String currentPage = request.getParameter("currentPage"); 
		String jumpPage = request.getParameter("jumpPage"); 				
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type

		if(title == null) {
			title = ""; 			
		} 	
		
		if(changePage == null) {
			changePage = "0"; 
		}

		int i_currentPage = 0; 
		//CHECK IF PREVIOUS / NEXT BUTTON / JUMP IS CLICKED
		if(currentPage == null) {
			currentPage = "0"; 
			System.out.println("currentPage = 0");
			//search = search.concat("&currentPage=" + currentPage); 
		} else if (currentPage != null) {
			i_currentPage = Integer.parseInt(currentPage); 		
			
			if(changePage != null) {
				int changePageValue = Integer.parseInt(changePage); 
				
				//See if the user picked previous or next button 
				if(changePageValue == -1) {
					if(i_currentPage > 0) {
						--i_currentPage; 					
					}
				} 
				else if (changePageValue == 1) {
					++i_currentPage; 			
				} 
				else if(jumpPage != null) {
					changePageValue = Integer.parseInt(jumpPage); 
					System.out.println("Change page value:" + changePageValue); 
					i_currentPage = changePageValue; 
				}
			}
			
			currentPage = Integer.toString(i_currentPage);
			//search = search.concat("&currentPage=" + currentPage);
		}
		
		StringTokenizer st = new StringTokenizer(title);
		
		String querySt = ""; 
		while( st.hasMoreTokens() ) {
			querySt += "+" + st.nextToken() + "* "; 
		}
		
		System.out.println("The query token is: " + querySt);

		try {
			
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
	            
			  //Class.forName("org.gjt.mm.mysql.Driver");
//            Class.forName("com.mysql.jdbc.Driver").newInstance();

//            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
//            Statement statement = dbcon.createStatement();
//            Statement statement2 = dbcon.createStatement(); 
            
//            String query = "SELECT SQL_CALC_FOUND_ROWS m1.id, m1.title, m1.director, m1.year, g1.name, s1.name \n" + 
//				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
//				"WHERE g1.id = gm1.genreId \n" +
//				"AND m1.id = gm1.movieId \n" +
//				"AND s1.id = sm1.starId \n" +
//				"AND m1.id = sm1.movieId \n" +
//				"AND m1.title LIKE \"" + title + "%\" \n" +				
//				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC \n";
//				//"LIMIT " + itemPerPage + " OFFSET " + currentPage + "; \n"; 
            
            String query = "SELECT SQL_CALC_FOUND_ROWS m1.id, m1.title, m1.director, m1.year, g1.name, s1.name \n" + 
    				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
    				"WHERE g1.id = gm1.genreId \n" +
    				"AND m1.id = gm1.movieId \n" +
    				"AND s1.id = sm1.starId \n" +
    				"AND m1.id = sm1.movieId \n" +
    				"AND MATCH(m1.title) AGAINST(? IN BOOLEAN MODE) \n" +				
    				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC \n";
    				//"LIMIT " + itemPerPage + " OFFSET " + currentPage + "; \n"; 
            
            String queryCount = "SELECT count(distinct m1.title) AS mvoieCount \n" + 
    				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
    				"WHERE g1.id = gm1.genreId \n" +
    				"AND m1.id = gm1.movieId \n" +
    				"AND s1.id = sm1.starId \n" +
    				"AND m1.id = sm1.movieId \n" +  
    				"AND MATCH(m1.title) AGAINST(? IN BOOLEAN MODE) \n" +
    				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC \n";
            
            PreparedStatement queryStatement = dbcon.prepareStatement(query); 
            PreparedStatement countStatement = dbcon.prepareStatement(queryCount);
            
            dbcon.setAutoCommit(false);

            queryStatement.setString(1, querySt);
            countStatement.setString(1, querySt);
            
            dbcon.commit();            

			ResultSet rs = queryStatement.executeQuery();
			ResultSet countRs = countStatement.executeQuery(); 
		
			//How many lines on each page? 			
			int linePerPage = 10; 			
			
			//First we will get the counts of the unique title name from the search
		    countRs.next(); 
			String titleCount = countRs.getString(1); 
			int i_titleCount = Integer.parseInt(titleCount); 
			int i_totalNumPage = i_titleCount / linePerPage; 
			
			//Add a page if the the title count is odd 
			if(i_titleCount % linePerPage > 0) {
				++i_totalNumPage; 
			}
				
			String s_totalNumPage = Integer.toString(i_totalNumPage); 	  

		    //This is to keep track repeating values in the table 
		    String previousTitle = ""; 
		    String previousGenre = ""; 		    
		   
		    //Countline is to keep track of how many items are printed (based on title) 
		    int countLine = 0; 
		    int endCount = (linePerPage * (i_currentPage + 1)); 
		    int startCount = (linePerPage * i_currentPage); 

		    boolean end = false; 
		    ArrayList<MovieInfo1> movieList = new ArrayList<>(); 
		    
		    //If there are no results from the query 
		    if(!rs.next()) {
		    		end = true; 
		    }
		    
		    while(!end) {	
		    	
		    		String m_title = rs.getString("m1.title");
		    	
		    		if(countLine >= startCount) {			    		
			    		String m_id = rs.getString("m1.id"); 
			    		
			    		String m_director = rs.getString("m1.director"); 
				    String m_year = rs.getString("m1.year"); 
			        String s_name = rs.getString("s1.name"); 
			        String g_name = rs.getString("g1.name"); 
			        
			        MovieInfo1 myMovie = new MovieInfo1(); 
			    		
			        myMovie.id = m_id; 
			        myMovie.title = m_title;
			        myMovie.director = m_director;
			        myMovie.year = m_year; 
			        myMovie.genre.add(g_name); 
			        myMovie.star.add(s_name); 
			        
			        previousTitle = m_title; 
			        previousGenre = g_name; 
			        
			        boolean sameMovie = true; 
			        int genreCount = 0; 
			        
			        while(sameMovie && !end) {
			        		
			        		if(!rs.next()){
			        			end = true; 
			        		} 
			        		else {
				        		m_title = rs.getString("m1.title");
				        		
				        		if(m_title.equals(previousTitle)) {
					        	    s_name = rs.getString("s1.name"); 
					  		    g_name = rs.getString("g1.name"); 
					  		    
					  		    if(!g_name.equals(previousGenre)) {
					  		    		++genreCount; 
					  		    		myMovie.genre.add(g_name); 
					  		    		previousGenre = g_name; 
					  		    } 
					  		    	
					  		    if(genreCount == 0) {
					  		    		myMovie.star.add(s_name); 
					  		    }
				        		}
				        		else {			        						        		
				        			sameMovie = false; 
				        		}
				        		
			        		}
			        					        		
			        }
			        movieList.add(myMovie);  
		    		} else {		
		    			if(!rs.next()) {
		    				end = true; 
		    			}		    			
		    		}
              
		    		if(!previousTitle.equals(m_title)) {
		    			++countLine; 
		    			previousTitle = m_title; 
		    		}
		    }  	
		    
		     for(MovieInfo1 in : movieList) {
		    	 	System.out.println("Id: " + in.id);
				System.out.println("TItle: " + in.title);
				System.out.println("Director: " + in.director);
				System.out.println("Genre");
				for(String  inGenre : in.genre) {
					System.out.println(inGenre);
				}
				System.out.println("Stars");
				for(String instars : in.star) {
					System.out.println(instars);
				}
		     }				
		     
		     JsonArray jsonArray = new JsonArray();
		     
		     //Convert to json to be returned 
		     for(MovieInfo1 in : movieList) {
		    	 	String currentTitle = in.title; 
		    	 	String currentDirector = in.director; 
		    	 	String currentYear = in.year; 
		    	 	ArrayList<String> currentGenre = in.genre; 
		    	 	ArrayList<String> currentStars = in.star;
		    	 	
		    	 	jsonArray.add( generateJsonObject( currentTitle, currentDirector, currentYear,
		    	 			currentGenre, currentStars) );
		     }
		     
		     response.getWriter().write(jsonArray.toString());
		     return; 
			 
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
	
	JsonObject generateJsonObject(String cTitle, String cDirector, String cYear, ArrayList<String> cGenre, ArrayList<String> cStars) {
		JsonObject jsonObject = new JsonObject(); 
		jsonObject.addProperty("title", cTitle);
		jsonObject.addProperty("director", cDirector);
		jsonObject.addProperty("year", cYear);
		
		JsonArray jsonGenreArray = new JsonArray();
		
		for( String in : cGenre ) {
			jsonGenreArray.add( in );
		}
		
		jsonObject.add("genre", jsonGenreArray);
		
		//There are some repeating stars so we want to eliminate them 
		String previous = "";
		
		JsonArray jsonStarsArray = new JsonArray(); 

		for( String in : cStars ) {
			
			if( ! in.equals(previous) ) {
				jsonStarsArray.add( in );
			}
		}
		
		jsonObject.add("stars", jsonStarsArray);
		
		return jsonObject; 
	}

}
