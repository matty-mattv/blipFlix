import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 

@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Main() {
        super();
    }

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		ArrayList<CartItem> 	shoppingCart= (ArrayList<CartItem>)session.getAttribute("myCart");
		
		// if there is no shopping cart array, create it
		if (shoppingCart == null)
		{
			shoppingCart = new ArrayList<CartItem>();
			session.setAttribute("myCart", shoppingCart);
		}
	    // Get total count
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
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
