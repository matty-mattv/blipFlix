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
        <h3>this is employee main page</h3>

	<h4>Database Access</h4>
<!-- 	<p>Database schema and adding new movie/star entry </p>
 -->	
      <form action="ShowMetadata">
		<button type="metaData" class="btn btn-primary btn-lg btn-block" value="Show Metadata">Show Metadata</button>
      </form>  
        
      <form action="AddMovie.jsp">
		<button type="addMovie" class="btn btn-primary btn-lg btn-block" value="Add Movie">Add Movie</button>
      </form>   

      <form action="AddStar.jsp">
		<button type="addStar" class="btn btn-primary btn-lg btn-block" value="Add Star">Add Star / Add Genre</button>
      </form>          
        
      <p></p>
      <h4>Preview Interface</h4>
<!--       <p>Note: shopping cart features are disabled on Employee Dashbaord</p>
 -->      
      <form action="Search.jsp">
		<button type="search" class="btn btn-secondary btn-lg btn-block" value="Search">Search</button>
      </form>   
      
      <form action="Browse">
		<button type="browse" class="btn btn-secondary btn-lg btn-block" value="Browse XXXX">Browse</button>
      </form>   
            
            
   
    </div>   
    </div>
</body>
</html>