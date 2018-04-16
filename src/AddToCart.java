import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

@WebServlet("/AddToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddToCart() {
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		//shoppingCart; 
		ArrayList<CartItem> shoppingCart= (ArrayList<CartItem>)session.getAttribute("myCart");
		
		// if there is no shopping cart array, create it
		if (shoppingCart == null)
		{
			shoppingCart = new ArrayList<CartItem>();
			session.setAttribute("myCart", shoppingCart);
		}
		
		// Get cust ID and movie ID from session
		String custId = (String)session.getAttribute("custId");
		String movieId = (String)session.getAttribute("movieId");
		String movieTitle = (String)session.getAttribute("movieTitle");

		System.out.println("CUST ID: " + custId);
		System.out.println("Captured MOVIE ID: " + movieId);
		System.out.println("Captured MOVIE Title: " + movieTitle);
				
		
		// does the item already exist in cart?
		// if the item in cart, get the count of the item, then set the count to be count++
		// if the item is NOT in cart, create a new CartItem, then add to shopping cart array
	    boolean duplicate = false; 
	    
	    //***GO THROUGH THE LIST TO SEE IF THIS IS THE DUPLCIATE 	    
	    for(CartItem in : shoppingCart) {
	    		if(in.mTitle.equals((movieTitle))) {
	    			++in.count; 
	    			duplicate = true; 
	    		}
	    }
	    
	    if(!duplicate) {
	    	// ADD TO CART
		    CartItem each = new CartItem(custId, movieId, movieTitle, 1);
			shoppingCart.add(each);
		    session.setAttribute("myCart", shoppingCart);
	    }
	    
	    // Get total count of the Cart
	    int totalCartCount = 0;
	    for(CartItem each : shoppingCart)
	    {
	    		totalCartCount +=each.count;
	    }
	    System.out.println("TOTAL: " + totalCartCount);
	    
        // put the count to session
	    String cartTotal = String.valueOf(totalCartCount);
		session.setAttribute("cartTotal", cartTotal);
		String total = (String) session.getAttribute("cartTotal");
		System.out.println("TEST->THIS Cart total: " + total);	
		cartTotal = null;
	    
	    // OUPUT the movie title
	    String result = movieTitle;	    

	    custId = null;
	    movieId = null;
	    movieTitle = null;
   
	    
	    
	    request.setAttribute("queryResults", result);
	    request.getRequestDispatcher("AddToCart.jsp").forward(request,  response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
