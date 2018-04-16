
public class CartItem 
{
	String custId;
	String movieId;
	String mTitle;
	int count;
	
	public CartItem(String custId, String movieId, String mTitle) {
		super();
		this.custId = custId;
		this.movieId = movieId;
		this.mTitle = mTitle;
	}
	
	public CartItem() {
	}
	
	public CartItem(String custId2, String movieId2, String movieTitle, int i) {
		super();
		this.custId = custId2;
		this.movieId = movieId2;
		this.mTitle = movieTitle;
		this.count = i;
	}


	@Override
	public String toString() {
		return "CartItem [custId=" + custId + ", movieId=" + movieId + ", mTitle=" + mTitle + ", count=" + count + "]";
	}

	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getMovieId() {
		return movieId;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
