import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 




@WebServlet("/Cart")
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Cart() {
        super();
    }


	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		String result = "HELLO";
		
		// get my cart
		ArrayList<CartItem> shoppingCart= (ArrayList<CartItem>)session.getAttribute("myCart");
		// if there is no shopping cart array, create it
		if (shoppingCart == null)
		{
			shoppingCart = new ArrayList<CartItem>();
			session.setAttribute("myCart", shoppingCart);
		}
		
		if (shoppingCart.isEmpty())
		{
			result = "Your cart is empty";
		}
		else
		{
			result = "<table class=\"table table-striped table-hover sortable table-bordered\"><tr><th>Movie</th><th>Quantity</th></tr>";
			
			
			for (CartItem eachOne : shoppingCart)
			{
					System.out.println(eachOne);
					
					String movieId = eachOne.getMovieId();
					String title = eachOne.getmTitle();
					int movieCount = eachOne.getCount();
					
					// CART EDIT PAGE IS HERE
					result += "<tr><td>" + title + "</td><td>" + "<a href='CartEdit.jsp?/&movieId=" + movieId +"&title=" + title + "&movieCount=" + movieCount + "'>" + movieCount + "</a></td></tr>";
			}
			
			
			// CHECK OUT BUTTON IS HERE
			result += "</table><p></p><form action=\"Checkout.jsp\">\n" + 
					"			<button type=\"submit\" class=\"btn btn-primary\">Check Out</button> \n" + 
					"		</form>";
			
		}

		request.setAttribute("queryResults", result);
		request.getRequestDispatcher("Cart.jsp").forward(request,  response);
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}
	
}

