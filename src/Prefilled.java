import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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


@WebServlet("/Prefilled")
public class Prefilled extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Prefilled() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		
		HttpSession session = request.getSession();
		
		ArrayList<CartItem> shoppingCart = (ArrayList<CartItem>)session.getAttribute("myCart");
		String custId = (String)session.getAttribute("custId"); 
		int i_custId = Integer.parseInt(custId);
		System.out.println("this is the custId: " + custId);
		
		String ccId = (String) session.getAttribute("ccId"); 
		//ADD IF THE CART IS EMPTY, CANNOT PRUCHASE 
		
		
		
//		String firstName = "Will"; 
//		String lastName = "Hwang"; 
//		String expiration =  "2006-12-10";  
//		String creditCard = "259098"; 
		
		try {
				  //Class.forName("org.gjt.mm.mysql.Driver");
//	          Class.forName("com.mysql.jdbc.Driver").newInstance();
//	
//	          Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
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
	          
	          String query = "SELECT cr1.firstName, cr1.lastName, cr1.expiration, count(*) as resultCount \n" +
	  				"FROM creditcards cr1, customers cu2 \n" + 
	  				"WHERE cr1.id = cu2.ccId \n" + 
	  				"AND cr1.id = '" + ccId + "'; "; 
	          
	          //OLD QUERY 
//			String query = "SELECT count(*) as resultCount \n" + 
//					"FROM creditcards c1 \n" +
//					"WHERE c1.id = \""+ creditCard + "\" \n" + 
//					"AND c1.firstName = \"" + firstName + "\" \n" + 
//					"AND c1.lastName = \"" + lastName + "\" \n" + 
//					"AND c1.expiration = \"" + expiration + "\"; \n"; 
			
			System.out.println(query);
			ResultSet rs = statement.executeQuery(query);
	
			rs.next(); 
			
			String firstName = rs.getString("cr1.firstName"); 
			String lastName = rs.getString("cr1.lastName"); 
			String expiration = rs.getString("cr1.expiration"); 
			String resultCount = rs.getString("resultCount"); 
			
			//ADD ORDER INFORMATION AND SSEND IT TO THE CONFIRMTAION PAGE
			if(resultCount.equals("1")) {

			     
			     java.util.Date today = new java.util.Date();
			     java.sql.Date startDate= new java.sql.Date(today.getTime());
			     
			     String result = "First Name:" + firstName + 
 			    		 		"\nLast Name: " + lastName + 
 			    		 		"\nExpiration Date: " + expiration + 
 			    		 		"\nCredit Card Number: " + custId;
			     
			     String htmlResult = "First Name:" + firstName + "<br>" +
		    		 		"\nLast Name: " + lastName + "<br>" + 
		    		 		"\nExpiration Date: " + expiration + "<br>"+
		    		 		"\nCredit Card Number: " + custId;
			     System.out.println(result);
			     System.out.println(htmlResult);
			     //INSERT STATEMENT
			     String salesQuery = " INSERT INTO sales (customerID, movieID, saleDate) "
			    	        + " values (?, ?, ?)";
			     
			     // create the mysql insert preparedstatement
			     
			  
			     for(CartItem in : shoppingCart) {
			    	 	  String mId = in.movieId;  
				      PreparedStatement preparedStmt = dbcon.prepareStatement(salesQuery);
				      
				      System.out.println("customer id: " + i_custId + " movieId: " + mId + " date: " + startDate );
				      preparedStmt.setInt (1, i_custId);
				      preparedStmt.setString (2, mId);
				      preparedStmt.setDate(3, startDate);
				      
				      preparedStmt.execute();
			     }
				
			     request.setAttribute("queryResults", htmlResult);
				 request.getRequestDispatcher("Prefilled.jsp").forward(request,  response);
				 
				//response.sendRedirect("Confirmation.jsp");
							
			}
			else if (resultCount.equals("0")) {
				//Could not find any data stored
				String out = "0"; 
				request.setAttribute("wrongInformation", out);
				request.getRequestDispatcher("Checkout.jsp").forward(request,  response);
			}
			
			rs.close(); 

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