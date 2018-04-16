 import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import java.io.IOException;

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

@WebServlet("/AutoSearch")
public class AutoSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AutoSearch() {
        super();      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		String fullname = (String) session.getAttribute("fullname");
		String firstname = (String) session.getAttribute("firstname");
		
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		response.setContentType("text/html"); // Response mime type			
		
		try {
			// setup the response json arrray
			JsonArray jsonArray = new JsonArray();
			
			// get the query string from parameter
			String query = request.getParameter("query");
			
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			
			//Token query to enable full text search 
			StringTokenizer st = new StringTokenizer(query); 
			
			String querySt = ""; 
			while( st.hasMoreTokens() ) {
				querySt += "+" + st.nextToken() + "* ";                        
			}
			
			System.out.println("The query token is: " + querySt);
			
			try {
				 //Class.forName("org.gjt.mm.mysql.Driver");
//	            Class.forName("com.mysql.jdbc.Driver").newInstance();
//	            
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
		            	           
	            
	            //WHERE MATCH (name) AGAINST ('+Man* +A*' IN BOOLEAN MODE);

	            int mistakenLength = 5;
	            
	            if( query.length() < 5 ) {
	            		mistakenLength = 2; 
	            }
	            else if (query.length() < 10) {
	            		mistakenLength = 4;
	            }
	            
	            String movieQuery = "SELECT m1.title, m1.id \n" + 
	            		"FROM movies m1 \n" + 
	            		"WHERE MATCH (m1.title) AGAINST ( ? IN BOOLEAN MODE)  OR ed( m1.title, ?) <= ? \n" + 
	            		"ORDER BY m1.title ASC \n" +
	            		"LIMIT 5 \n"; 
	            
	            System.out.println(movieQuery);
	            
	            String starsQuery = "SELECT s1.name, s1.id \n" +
	            		"FROM stars s1 \n" +
	            		"WHERE MATCH (s1.name) AGAINST (? IN BOOLEAN MODE) \n" +
	            		"ORDER BY s1.name ASC \n" +
	            		"LIMIT 5 \n"; 
	            
	            System.out.println(starsQuery);
	            
	            PreparedStatement movieStatement = dbcon.prepareStatement(movieQuery); 
	            PreparedStatement starsStatement = dbcon.prepareStatement(starsQuery);
	            
	            dbcon.setAutoCommit(false);
	            
	            movieStatement.setString(1, querySt);
	            movieStatement.setString(2, query);
	            movieStatement.setInt(3, mistakenLength)
	            ;
	            starsStatement.setString(1, querySt);
	            dbcon.commit();            

	            ResultSet movieRs = movieStatement.executeQuery();
	            ResultSet starsRs = starsStatement.executeQuery(); 
	            
	            while( movieRs.next() ) {
	            		String title = movieRs.getString( "m1.title" ); 
	            		String mId = movieRs.getString( "m1.id" ); 
	            		
	            		jsonArray.add( generateJsonObject(mId, title, "Movie") ); 	            		
	            }
	            
	            while( starsRs.next() ) {
	            		String name = starsRs.getString( "s1.name" ); 
	            		String sId = starsRs.getString( "s1.id" );
	            		
	            		jsonArray.add( generateJsonObject(sId, name, "Star") );
	            		System.out.println( name );
	            }
	            
	            movieRs.close(); 
	            response.getWriter().write(jsonArray.toString());
				return;
	
			} catch (SQLException ex) {
	            while (ex != null) {
	                System.out.println("SQL Exception:  " + ex.getMessage());
	                ex = ex.getNextException();
	            } // end while
	        } catch (java.lang.Exception ex) {	           
	            return;
	        }
			
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "Movie", "id": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String id, String titleOrName, String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", titleOrName);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("id", id);
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}

}
