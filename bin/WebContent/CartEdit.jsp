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
    </style>
    <title>Movie Site</title>
</head>
<body>

<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String firstName = (String)session.getAttribute("firstName");
	String cartTotal = (String)session.getAttribute("cartTotal");
	
  
	// GET INFO FROM URL
	String movieId = (String)request.getParameter("movieId");
	String title = (String)request.getParameter("title");
	String movieCount = (String)request.getParameter("movieCount");

	System.out.println("Movie Id=" + movieId + "title= " + title + "Moive Count=" + movieCount);	
 
 
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
        <h3>Edit Movie Quantity</h3>
        
            <form action="CartEdit"> 
        
<table class="table table-striped table-hover sortable table-bordered">
  <tr>
    <th>ID</th>
    <th>Movie</th>
    <th>Quantity</th> 
  </tr>
  
  <tr>
      <td>
        <input type="text" value="<%=movieId%>" name="movieId" readonly>  
    </td> 
    <td>
        <input type="text" value="<%=title%>" name="title" readonly>  
    </td> 
    <td>
        <input type="text" name="movieCount">   
    </td>
  </tr>
</table>


  <button type="submit" class="btn btn-primary">Submit Changes</button>

  </form>

</div>


</div>
</body>
</html>