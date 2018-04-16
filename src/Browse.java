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

@WebServlet("/Browse")
public class Browse extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Browse() {
        super();
    }
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
	            
            Statement statement = dbcon.createStatement();
            
            String allGenre = "SELECT * FROM genres ORDER BY name;"; 
            
			ResultSet allGenreRs = statement.executeQuery(allGenre);
			
			String result = "<h4>By Genre</h4>";
			while(allGenreRs.next())
			{
				//String g_id = allGenreRs.getString("id");
				String g_name = allGenreRs.getString("name");
				result += "<a href='Search?&title=&year=&director=&starName=&genre=" + g_name + "&'><div id=\"gbox\"><div id=\"gtext\"><h5>" + g_name + "</h5></div></div></a>";

			}
			
			result += "<p></p><h4>By Initial Character of the Title</h4>";
		    
		    for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) 
		    {
				result += "<a href='Search?title=" + alphabet + "&year=&director=&starName='><div id=\"gbox2\"><div id=\"gtext\"><h5>" + alphabet + "</h5></div></div></a>";
		    }
		    for (int num = 0; num < 10; num++) 
		    {
				result += "<a href='Search?title=" + num + "&year=&director=&starName='><div id=\"gbox2\"><div id=\"gtext\"><h5>" + num + "</h5></div></div></a>";
		    }
		    
		    request.setAttribute("queryResults", result);
		    request.getRequestDispatcher("Browse.jsp").forward(request,  response);	 

		    
		    
		    
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
