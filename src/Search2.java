import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.sql.PreparedStatement;


class MovieInfo3 {
	public String id; 
    public String title; 
    public String director; 
    public String year; 
    public ArrayList<String> star = new ArrayList<>(); 
    public ArrayList<String> genre = new ArrayList<>();
    public ArrayList<String> starYear = new ArrayList<>(); 
}

@WebServlet("/Search2")
public class Search2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Search2() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		long TStimeStart = System.nanoTime(); 
		
		String contextPath = getServletContext().getRealPath("/");
		System.out.println("@@@: " + contextPath);
		String xmlFilePath=contextPath+"boobies";

		System.out.println(xmlFilePath);
		
		File myfile = new File(xmlFilePath);
		
		
		myfile.createNewFile();
		
		String something = System.getProperty("user.dir");
		System.out.println("TESTESTESTESTESTESTSE" + something);
		
		HttpSession session = request.getSession();
		String fullname = (String) session.getAttribute("fullname");
		String firstname = (String) session.getAttribute("firstname");
		System.out.println("TEST-- FULLNAME-->" + fullname);
		System.out.println("TEST-- First Name-->" + firstname);
		
		String title = request.getParameter("title");
		String year = request.getParameter("year"); 
		String director = request.getParameter("director");
		String starName = request.getParameter("starName"); 
		String letter = request.getParameter("letter");
		String genre = request.getParameter("genre"); 
		String changePage = request.getParameter("changePage"); 
		String currentPage = request.getParameter("currentPage"); 
		String jumpPage = request.getParameter("jumpPage"); 
		String viewNum = request.getParameter("viewNum"); 
		
		System.out.println("Print the number of views:" + viewNum);
		System.out.println("print the changePage: " + changePage); 
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		
		String search = ""; 
		String titleSt = ""; // For string token if needed
		boolean likeFunction = true; 
		if(title == null) {
			title = ""; 
		} else {
			search = search.concat("&title=" + title); 

			//Tokenize the title in order to do full text search in query
			
//			if ( title.length() > 1 ) {
//				likeFunction = false; 
//				StringTokenizer st = new StringTokenizer(title); 
//							 
//				while( st.hasMoreTokens() ) {
//					titleSt += "+" + st.nextToken() + "* ";                        
//				}
				
//				System.out.println("The query token is: " + titleSt);
//			} 
//			else {
//				title = ""; 
//		
//			}
		}
		if(year == null) {
			year = ""; 
		} else {
			search = search.concat("&year=" + year); 
		}
		if(director == null) {
			director = ""; 
		}else {
			search = search.concat("&director=" + director); 
		}
		if(starName == null) {
			starName = ""; 
		}else {
			search = search.concat("&starName=" + starName); 
		}
		if(genre == null) {
			genre = "";
		}else {
			search = search.concat("&genre=" + genre); 
		}
		
		if(changePage == null) {
			changePage = "0"; 
		}

		int i_currentPage = 0; 
		//CHECK IF PREVIOUS / NEXT BUTTON / JUMP IS CLICKED
		if(currentPage == null) {
			currentPage = "0"; 
			System.out.println("currentPage = 0");
			search = search.concat("&currentPage=" + currentPage); 
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
			search = search.concat("&currentPage=" + currentPage);
		}
	
		request.setAttribute("searchOptions", search);
		
		System.out.println("SEARCH OPTIONS:"  + search);
		try {
			  //Class.forName("org.gjt.mm.mysql.Driver");
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//
//            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			long TJtimeStart = System.nanoTime(); 
			
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
	            System.out.println( "Connection: " + dbcon);
	            if (dbcon == null)
	                System.out.println("dbcon is null.");
            // Declare our statement
//            Statement statement = dbcon.createStatement();
//            Statement statement2 = dbcon.createStatement(); 
            
            String query = ""; 
//            if ( ! likeFunction ) {
//            
//            	 query = "SELECT SQL_CALC_FOUND_ROWS m1.id, m1.title, m1.director, m1.year, g1.name, s1.name \n" + 
//         				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
//         				"WHERE g1.id = gm1.genreId \n" +
//         				"AND m1.id = gm1.movieId \n" +
//         				"AND s1.id = sm1.starId \n" +
//         				"AND m1.id = sm1.movieId \n" +
//         				"AND MATCH (m1.title) AGAINST ( ? IN BOOLEAN MODE) \n" +          				//
//         				"AND m1.director LIKE ? \n" + 
//         				"AND m1.year LIKE ? \n" +
//         				"AND s1.name LIKE ? \n" +
//         				"AND g1.name LIKE ? \n" +
//         				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC; \n";         	
//            }
//            else {
            	 query = "SELECT SQL_CALC_FOUND_ROWS m1.id, m1.title, m1.director, m1.year, g1.name, s1.name \n" + 
         				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
         				"WHERE g1.id = gm1.genreId \n" +
         				"AND m1.id = gm1.movieId \n" +
         				"AND s1.id = sm1.starId \n" +
         				"AND m1.id = sm1.movieId \n" +         				
         				"AND m1.title LIKE ? \n" +
         				"AND m1.director LIKE ? \n" + 
         				"AND m1.year LIKE ? \n" +
         				"AND s1.name LIKE ? \n" +
         				"AND g1.name LIKE ? \n" +
         				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC; \n";  
         				
//            }
            
            String queryCount = "SELECT count(distinct m1.title) AS mvoieCount \n" + 
    				"FROM genres g1, movies m1, stars s1, genres_in_movies gm1, stars_in_movies sm1 \n" +
    				"WHERE g1.id = gm1.genreId \n" +
    				"AND m1.id = gm1.movieId \n" +
    				"AND s1.id = sm1.starId \n" +
    				"AND m1.id = sm1.movieId \n" +
    				"AND m1.title LIKE ? \n" +
     				"AND m1.director LIKE ? \n" + 
     				"AND m1.year LIKE ? \n" +
     				"AND s1.name LIKE ? \n" +
     				"AND g1.name LIKE ? \n" +
     				"ORDER BY m1.title ASC, g1.name ASC, s1.name ASC; \n";
            
            System.out.println(query);
            System.out.println("--------"); 
            System.out.println(queryCount);
            
            System.out.println("First"); 
            //Assign and execute the prepared statements        
            PreparedStatement queryStatement = (PreparedStatement) dbcon.prepareStatement(query); 
            PreparedStatement countStatement = (PreparedStatement) dbcon.prepareStatement(queryCount);
            System.out.println("Second"); 
//            if ( ! likeFunction ) {
//            		queryStatement.setString(1, title);
//            } 
//            else {
//            		
//            }
            dbcon.setAutoCommit(false);
            queryStatement.setString(1, title + "%");
            queryStatement.setString(2, director+ "%");
            queryStatement.setString(3, year+ "%");
            queryStatement.setString(4, starName+ "%");
            queryStatement.setString(5, genre+ "%");
            System.out.println("Third"); 
            countStatement.setString(1, title+ "%"); 
            countStatement.setString(2, director+ "%");
            countStatement.setString(3, year+ "%");
            countStatement.setString(4, starName+ "%");
            countStatement.setString(5, genre+ "%");
            System.out.println("Fourth"); 
//           int one = queryStatement.executeUpdate(); 
//           int two = countStatement.executeUpdate();
           dbcon.commit();
           
           
//            System.out.println("Reached here, first: "+ one + " Second: " + two );
            
            //System.out.println(query);
			ResultSet rs = queryStatement.executeQuery();
			ResultSet countRs = countStatement.executeQuery(); 
			
			
		
			//How many lines on each page? 
			
			int linePerPage = 20; 
			if(viewNum != null) {			
				int i_viewNum = Integer.parseInt(viewNum); 
				linePerPage = i_viewNum;				
			}
			
			//First we will get the counts of the unique title name from the search
		    countRs.next(); 
			String titleCount = countRs.getString(1); 
			System.out.println("The count of the query is" + titleCount)  ;
			int i_titleCount = Integer.parseInt(titleCount); 
			int i_totalNumPage = i_titleCount / linePerPage; 
			
			if(i_titleCount % linePerPage > 0) {
				++i_totalNumPage; 
			}
				
			String s_totalNumPage = Integer.toString(i_totalNumPage); 
			
			//Send the totalNumPage back to JSP to print out 
			request.setAttribute("totalNumPage", s_totalNumPage);
			
			//String out will contain the html to be printed out in movieList.jsp
			String out = "<table class=\"table table-striped table-hover sortable table-bordered\"><tr><th>ID</th><th>Title</th><th>Director</th>" +
		    "<th>Year</th><th>Genre</th><th>Star Name</th></tr>"; 		  

		    //This is to keep track repeating values in the table 
		    String previousTitle = ""; 
		    String previousGenre = ""; 		    
		   
		    //Countline is to keep track of how many items are printed (based on title) 
		    int countLine = 0; 
		    int endCount = (linePerPage * (i_currentPage + 1)); 
		    int startCount = (linePerPage * i_currentPage); 

		    boolean end = false; 
		    ArrayList<MovieInfo3> movieList = new ArrayList<>(); 
		    
		    if(!rs.next()) {
		    		end = true; 
		    		System.out.println("*******"); 
		    }
		    
//		    System.out.println("endCount= " + endCount);
//		    System.out.println("countLine= " + countLine );
//		    System.out.println("startCount=" + startCount);
		    while(!end && countLine < endCount) {	
		    	
		    		String m_title = rs.getString("m1.title");
		    	
		    		if(countLine >= startCount) {			    		
			    		String m_id = rs.getString("m1.id"); 
			    		
			    		String m_director = rs.getString("m1.director"); 
				    String m_year = rs.getString("m1.year"); 
			        String s_name = rs.getString("s1.name"); 
			        String g_name = rs.getString("g1.name"); 
			        
			        MovieInfo3 myMovie = new MovieInfo3(); 
			    		
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
		    
		    dbcon.close(); 
		    rs.close(); 
		    countRs.close(); 
		    
			long TJtimeEnd = System.nanoTime(); 

		    
		     for(MovieInfo3 in : movieList) {
//		    	 	System.out.println("Id: " + in.id);
//				System.out.println("TItle: " + in.title);
//				System.out.println("Director: " + in.director);
//				System.out.println("Genre: ");
				
				String htmlFormat = "<tr><td>" + in.id + "</td>" +
      					"<td><a href=Movie?id=" + in.id +">" + in.title + "</a></td>" +
						"<td>" + in.director + "</td>" +
						 "<td>" + in.year + "</td>" +
						 "<td>"; 
						
				out = out.concat(htmlFormat); 
				
				for(String inString : in.genre) {					
					out = out.concat(inString + "<br>"); 
				}
				
				out = out.concat("</td><td>"); 			
				for(String inString : in.star) {					
					out = out.concat(inString + "<br>"); 
				}
				
				out = out.concat("</td></tr>"); 
			}
		     
		     out = out.concat("</table>"); 

		     System.out.println( out );
		     //System.out.println(out);
		     
		     long TStimeEnd = System.nanoTime(); 
		     
		     long TStime = TStimeEnd - TStimeStart;
		     long TJtime = TJtimeEnd - TJtimeStart; 
		     
		     System.out.println("TJ:" + TJtime + " TS:" + TStime + "\n");
		     request.setAttribute("queryResults", out);
			 request.getRequestDispatcher("MovieList.jsp").forward(request,  response);
			 			 
			 
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
