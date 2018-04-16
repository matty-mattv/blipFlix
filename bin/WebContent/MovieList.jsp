<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="sorttable.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootswatch/4.0.0-beta.3/flatly/bootstrap.min.css" rel="stylesheet" integrity="sha384-+lmTKXkS+c9d34U9obDdGOZT7zqFicJDkhckYYsW7oenXR37T2OEV4uqfUO45M87" crossorigin="anonymous">
    <style>
        #header{
            height: 80px;
            padding: 10px;
            padding-left:20px;
            background-color: aliceblue;
        }
        #headerEmp{
            height: 80px;
            padding: 10px;
            padding-left:20px;
            background-color: #31698A;
        }
        #logo{
            float: left;
        }
        #subtitle{
        		float: left;
            color: white;

        }
        #cartEmp{
            float: right;
            padding-right: 10px;
            color: white;

        }
        #hello{
            display:inline-block;
            align-content: center;
            vertical-align: middle;
        }
        #cart{
            float: right;
            font-size: 20px;
            padding-right: 10px
        }
        .wrapper{
            padding: 10px;
        }
        #infobox{
            padding: 10px 30px 10px 30px;
            display: inline-block;
        }
    </style>
    <title>Movie List</title>
</head>
<body>

<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String firstName = (String)session.getAttribute("firstName");
	String cartTotal = (String)session.getAttribute("cartTotal");

	String fullname = (String)session.getAttribute("fullname");

	String title = request.getParameter("title");
	String year = request.getParameter("year"); 
	String director = request.getParameter("director");
	String starName = request.getParameter("starName");
	String genre = request.getParameter("genre");
	
	String output = "";
	
	if (genre != null)
	{
		output += "Genre: " + genre;
	}
	
	if (title != "")
	{
		output += "Title: " + title;
	}
	if (year != "")
	{
		output += " | Year: " + year;
	}
	if (director != "")
	{
		output += " | Director: " + director;
	}
	if (starName != "")
	{
		output += " | Star: " + starName;
	}	

%>
<%if (fullname != null) {%>
<div id="headerEmp">
    <div id="logo">
    		<h1><a href="MainEmp.jsp">MovieSite</a></h1>
    </div>
    
    <div id="subtitle">    		
    		<br><span><h4>&emsp;Employee Dashboard</h4></span>
    </div>
    
    <div id="cartEmp">    		
    		<br><span id="hello"><h5>Hello, <%=fullname %></h5></span>
    </div>
</div>
<% }  else { %>
<div id="header">
    <div id="logo">
    <h1><a href="Main.jsp">MovieSite</a></h1>
    </div>
    <div id="cart">
    <span>Hello, <%=firstName%></span><br>
    <span><a href="Cart">Cart: <%=cartTotal%></a></span>
    </div>
</div>
<%} %>
<div class="wrapper"  align="center">
    
        <div style="width: 1000px">
        <h3>Movie List</h3>
        
        <div id="infobox" class="border border-secondary">
        Your search result<br>
        <%= output %>
        </div>
        
        <p></p>
        
        Items per page 
	<% String searchOptions = (String)request.getAttribute("searchOptions"); 
		out.println("<a href=\"Search?" + searchOptions +"&viewNum=" + 10 + "\">10</a>");
		out.println("<a href=\"Search?" + searchOptions +"&viewNum=" + 20 + "\">20</a>");
		out.println("<a href=\"Search?" + searchOptions +"&viewNum=" + 50 + "\">50</a>");
		out.println("<a href=\"Search?" + searchOptions +"&viewNum=" + 100 + "\">100</a>");
	%>
	
	<%= request.getAttribute("queryResults") %>
	 
	<%  
		out.println("<a href=\"Search?" + searchOptions +"&changePage=-1\">Previous</a>");
		out.println("&nbsp;");
		out.println("<a href=\"Search?" + searchOptions +"&changePage=1\">Next</a>");
		out.println("&nbsp; Pages: ");
		
		 String totalNumPage = (String) (request.getAttribute("totalNumPage"));
		int i_totalNumPage = Integer.parseInt(totalNumPage); 
		for(int i = 0; i < i_totalNumPage; ++i) {
			out.println("<a href=\"Search?" + searchOptions +"&jumpPage=" + i + "\">" + i + "</a>");
		} 
	%>
        
        
        
        </div>   
    </div>
    </body>
</html>