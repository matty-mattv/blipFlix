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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password"); 
		String android = request.getParameter("android"); 
				
//		String loginUser = "mytestuser";
//		String loginPasswd = "mypassword";
//		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		 
		response.setContentType("text/html"); // Response mime type
		
		try {
            //Class.forName("org.gjt.mm.mysql.Driver");
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

            String query = "SELECT * FROM customers ;"; 
            
		    ResultSet rs = statement.executeQuery(query); 

		    boolean userFound = false; 
		    boolean passwordMatch = false;
		    boolean empFound = false;
		    boolean empPasswordMatch = false;
		
		    while(!userFound &&  rs.next()) {
		    		String c_email = rs.getString("email"); 
		
			    	if(c_email.equals(username)){
			    		userFound = true; 
			    	}
		    }
		
		    rs = statement.executeQuery(query);
		
		    while(!passwordMatch && rs.next()) {
			    	String c_password = rs.getString("password"); 
			    	String c_email = rs.getString("email"); 
		
			    	if(c_password.equals(password) && c_email.equals(username)) {
			    		passwordMatch = true; 
			    	}
		    }
		    
		    //*********************************************************************************
		    if (!passwordMatch && !userFound)
		    {
			    query = "SELECT * FROM employees ;"; 
			    rs = statement.executeQuery(query);
			    while(!empFound &&  rs.next()) {
		    		String c_email = rs.getString("email"); 
		
				    	if(c_email.equals(username)){
				    		empFound = true; 
				    	}
			    }
			
			    rs = statement.executeQuery(query);
			
			    while(!empPasswordMatch && rs.next()) {
				    	String c_password = rs.getString("password"); 
				    	String c_email = rs.getString("email"); 
			
				    	if(c_password.equals(password) && c_email.equals(username)) {
				    		empPasswordMatch = true; 
				    	}
			    }
		    }
		    //*********************************************************************************
		    
		    
		    
		    // get reCAPTCHA request param *****************************
		    // GOOGLE RECAPTCHA - Uncomment to activate
//			String gRecaptchaResponse = request
//					.getParameter("g-recaptcha-response");
//			System.out.println(gRecaptchaResponse);
//			boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
			
			boolean verify = true;

			
			// logging example
			System.out.println("User=" + username + "::password=" + password + "::Captcha Verify"+verify);
		
			
			HttpSession session;
		
			//If android is trying to access login, send back JSON 
			if( android != null ) {								
				JsonObject jsonObject = new JsonObject(); 
				System.out.println("this was an android request");				
				
				if(userFound && passwordMatch) {
					jsonObject.addProperty("login", "success");
				}
				else {
					jsonObject.addProperty("login", "fail");					
				}
				
				response.getWriter().write(jsonObject.toString()); ;
				return; 
			}
			
		    if(userFound && passwordMatch && verify) 
		    {
			    	// login success:
		    				    		 		    	

		    		// CAPUTE THIS CUSTOMER's INFO for SESSION
		    		session = request.getSession(); 
		    		
		    		// username is the emailaddress, this is used for authenticating login for the whole site
		    		session.setAttribute("username", username);
		    		
		    		// firstname is the customer's name, this is used for the "Hello, first name"
		    		String firstName = rs.getString("firstName");
		    		session.setAttribute("firstName", firstName);
		    		
		    		// ccId --> credit card number
		    		String ccId = rs.getString("ccId");
		    		session.setAttribute("ccId", ccId);
		    		
		    		// custId is the customer's id, this is used for Cart
		    		String custId = rs.getString("id");
		    		session.setAttribute("custId", custId);
		    		session.setAttribute("cartTotal", "0");
		    		
		    		// SET FULL NAME TO NULL
		    		String fullname = null;
		    		session.setAttribute("fullname", fullname);

		    		
//		    		System.out.println("TEST--> first name AND the customer ID is: " + firstName + "-> " + custId);
		    		response.sendRedirect("Main.jsp"); 
		    }
		    else if (empFound && empPasswordMatch && verify) // Employee Login Success
		    {
			    	// Caputre THIS Emp's INFO for SESSION
		    		session = request.getSession(); 
		    		
		    		// username is the emailaddress, this is used for authenticating login for the whole site
		    		session.setAttribute("username", username);
		    		
		    		// fullname is the emp's name, this is used for the "Hello, fullname"
		    		String fullname = rs.getString("fullname");
		    		session.setAttribute("fullname", fullname);
		    		
		    		// SET Firstname TO NULL
		    		String firstname = null;
		    		session.setAttribute("firstname", firstname);
	    		
		    		System.out.println("TEST--> full name is: " + fullname);
		    		// Go To Employee's Main Page
		    		response.sendRedirect("MainEmp.jsp"); 	
		    }
		    else {
		    	// login fail			    	
		    	String out = "0"; 
		    	
		    	// Error message for after clicking the recaptcha
		    	if (verify)
		    	{
			    	request.setAttribute("wrongInformation", out);
				request.getRequestDispatcher("Login.jsp").forward(request,  response);
		    	}
		    	else // fail recaptcha
		    	{
		    		request.setAttribute("wrongRecaptcha", out);
				request.getRequestDispatcher("Login.jsp").forward(request,  response);
		    	}

	    }
            
		   } catch (SQLException ex) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
