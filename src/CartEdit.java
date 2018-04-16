import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 




@WebServlet("/CartEdit")
public class CartEdit extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CartEdit() {
        super();
    }


	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		response.setContentType("text/html");
		HttpSession session = request.getSession();
		ArrayList<CartItem> shoppingCart= (ArrayList<CartItem>)session.getAttribute("myCart");

		String movieId = request.getParameter("movieId");
		String movieCount = request.getParameter("movieCount");
		int count = Integer.parseInt(movieCount);
		System.out.println("WHAT IS COUNT" + count);
		System.out.println("WHAT IS ID" + movieId);
				
						
		if (count > 0)
		{
			for (CartItem eachItem : shoppingCart)
			{
				if (eachItem.movieId.equals(movieId) )
				{
					eachItem.count = count;
				}
			}
		}
		else
		{
			for (CartItem eachItem : shoppingCart)
			{
				if (eachItem.movieId.equals(movieId) )
				{
					shoppingCart.remove(eachItem);
				}
			}
		}
	    session.setAttribute("myCart", shoppingCart);

	    
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
		
		//test
	    for (CartItem eachItem : shoppingCart)
	    {
	    		System.out.println(eachItem);
	    }
	    
	    
		request.getRequestDispatcher("Cart").forward(request,  response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
	
	
	
	
}





