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
            background-color: #31698A;
        }
        #logo{
            float: left;
        }
        #subtitle{
        		float: left;
                color: white;

        }
        #cart{
            float: right;
            
            padding-right: 10px;
            color: white;

        }
        #hello{
            display:inline-block;
            align-content: center;
            vertical-align: middle;
        }
        .wrapper{
            padding: 10px;
        }
    </style>
    <title>Movie Site Employee</title>


</head>
<body>

<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String fullname = (String)session.getAttribute("fullname");
	
%>


<div id="header">
    <div id="logo">
    		<h1><a href="MainEmp.jsp">MovieSite</a></h1>
    </div>
    
    <div id="subtitle">    		
    		<br><span><h4>&emsp;Employee Dashboard</h4></span>
    </div>
    
    <div id="cart">    		
    		<br><span id="hello"><h5>Hello, <%=fullname %></h5></span>
    </div>
</div>
    

<div class="wrapper"  align="center">
    <div style="width: 500px">
        <h3>Add Movie</h3>

              <form action="AddMovie" method="get">

		  <div class="form-group" align="left">
		    <label for="title">Title*</label>
		    <input type="title" class="form-control" name="title" placeholder="The title of the movie" required>
		  </div>
		  
		  <div class="form-group" align="left">
		    <label for="director">Director*</label>
		    <input type="director" class="form-control" name="director" placeholder="The movie's director" required>
		  </div>
		
		  <div class="form-group" align="left">
		    <label for="year">Year (Must be 4 numbers)*</label>
 		    <input type="number" class="form-control" name="year" placeholder="The movie's release year" required> 									
		  </div>
		            
		  <div class="form-group" align="left">
		    <label for="genreName">Genre*</label>
		    <input type="genreName" class="form-control" name="genreName" placeholder="A genre of the movie" required>
		  </div>
		            
		  <div class="form-group" align="left">
		    <label for="starName">Star Name*</label>
		    <input type="starName" class="form-control" name="starName" placeholder="An actor or actress of the movie" required>
		  </div>
		  
		  <div class="form-group" align="left">
		    <label for="starYear">Star's Birth Year (Must be 4 numbers)</label>
		    <input type="number" class="form-control" name="starYear" placeholder="An actor or actress's birth year">
		  </div>
		  
		  
		  
		  <div class="text-secondary" align="left"><p>*denote required fields</p></div>
<%-- 		  <%=message %>
 --%>            
            
		  <button type="submit" value="AddMovie" class="btn btn-primary">Submit</button>
		            
		</form>
      
      
      
      
      
    </div>   
</div>
</body>
</html>