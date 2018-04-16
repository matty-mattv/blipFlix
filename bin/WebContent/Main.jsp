<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<!-- Using jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- include jquery autocomplete JS  -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.devbridge-autocomplete/1.4.7/jquery.autocomplete.min.js"></script>
    
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
        #p4{
			padding-top: 20px;
        }
        
.autocomplete-group { font-weight: bold;font-size: 1.2em; background:	#3498DB; color:#fff }
.autocomplete-suggestions {padding: 4px; border: 1px solid #999; background: #fff; cursor: default; overflow: auto; }
.autocomplete-suggestion { padding: 10px 5px; font-size: 1.2em; white-space: nowrap; overflow: hidden; }
.autocomplete-selected { background: #f0f0f0; }
.autocomplete-suggestions strong { font-weight: bold; color: #18BC9C; }

        #p2{
			padding-top: 50px;
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
        <h3>this is main page</h3>
      
 <div id="p4"> 
 		<h4><i>Start here</i></h4>
    
	 <input type="text" class="form-control" name="hero" id="autocomplete" placeholder="Type somehting...">
    		<script src="AutoSearch.js"></script>
 </div>     
      
<div id="p2">
 		<h4><i>or here</i></h4>

      <form action="Search.jsp" method="post">
		<button type="search" class="btn btn-primary btn-lg btn-block" value="Search">Advance Search</button>
      </form>   
      
      <form action="Browse" method="post">
		<button type="browse" class="btn btn-secondary btn-lg btn-block" value="Browse">Browse</button>
      </form>   
</div>      

   
    </div>   
    </div>
</body>
</html>