<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="https://maxcdn.bootstrapcdn.com/bootswatch/4.0.0-beta.3/flatly/bootstrap.min.css" rel="stylesheet" integrity="sha384-+lmTKXkS+c9d34U9obDdGOZT7zqFicJDkhckYYsW7oenXR37T2OEV4uqfUO45M87" crossorigin="anonymous">
    <style>
        #header{
            height: 80px;
            padding: 10px;
            padding-left:20px;
            background-color: aliceblue;
        }
        #logo{
            float: left;
        }
        #cart{
            float: right;
            font-size: 20px;
            padding-right: 10px
        }
        .wrapper{
            padding: 10px;

        }

        #info{
            font-size: 20px;
            color: #ff5800;
        }

    </style>
    <title>Movie Site</title>
</head>
<body>

<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String firstName = (String)session.getAttribute("firstName");
	String cartTotal = (String)session.getAttribute("cartTotal");
%>


<div id="header">
    <div id="logo">
    		<h1><a href="Main.jsp">MovieSite</a></h1>
    </div>
    <div id="cart">
    <span>Hello, <%=firstName%></span><br>
    <span><a href="Cart">Cart: <%=cartTotal%></a></span>
    </div>
</div>
    

<div class="wrapper"  align="center">
    <div style="width: 500px">
        <h3>added to cart</h3>
        
        Your movie: <br>
        <div id="info"><%=request.getAttribute("queryResults") %></div>
        has been added to the cart! <br>
            	
    <p></p>
    
    <div id="infobox" class="border border-secondary" style="width:200px;">
    You can... 
	<form>
	  <input type="button" value="Go back" class="btn btn-secondary" onclick="history.back()">
	</form>   
	      <form action="Search.jsp">
		<button type="search" class="btn btn-primary" value="Search">Search</button>
      </form>   
      
      <form action="Browse">
		<button type="browse" class="btn btn-primary" value="Browse">Browse</button>
      </form>  
      </div>
	
	

            
   
    </div>   
    </div>
</body>
</html>