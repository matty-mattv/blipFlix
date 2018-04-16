<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    </style>
<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String firstName = (String)session.getAttribute("firstName");
	String cartTotal = (String)session.getAttribute("cartTotal");
	
	String fullname = (String)session.getAttribute("fullname");
	System.out.println("FULLNAME: " + fullname);
%>
    <title>Movie Site - Search</title>
</head>
<body>



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

<div class=wrapper  align="center">
    <div style="width: 500px">
        <h3>Search</h3>
        <p>Please enter your search criteria, one of the following fields is required.</p>
        
        <form action="Search" method="get">

		  <div class="form-group" align="left">
		    <label for="title">Title</label>
		    <input type="title" class="form-control" name="title" placeholder="The title of the movie">
		  </div>
		
		  <div class="form-group" align="left">
		    <label for="year">Year</label>
		    <input type="year" class="form-control" name="year" placeholder="The movie's release year">
		  </div>
		            
		  <div class="form-group" align="left">
		    <label for="director">Director</label>
		    <input type="director" class="form-control" name="director" placeholder="The movie's director">
		  </div>
		            
		  <div class="form-group" align="left">
		    <label for="starName">Star</label>
		    <input type="starName" class="form-control" name="starName" placeholder="An actor or actress of the movie">
		  </div>
<%-- 		  <%=message %>
 --%>            
            
		  <button type="submit" value="Search" class="btn btn-primary">Submit</button>
		            
		            
		</form>
    </div>
</div>
    
</body>
</html>