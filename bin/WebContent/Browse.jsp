<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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
		#gbox {
			position: relative;
	        width: 100px;
	        height: 100px;
	        display: inline-block;
	        margin: 5px;
	        border-radius: 50px;
	        
	        

background: #2BC0E4;  /* fallback for old browsers */
background: -webkit-linear-gradient(to top, #EAECC6, #2BC0E4);  /* Chrome 10-25, Safari 5.1-6 */
background: linear-gradient(to top, #EAECC6, #2BC0E4); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */



	        }   
	        #gbox:hover{
background: #ff6e7f;  /* fallback for old browsers */
background: -webkit-linear-gradient(to top, #bfe9ff, #ff6e7f);  /* Chrome 10-25, Safari 5.1-6 */
background: linear-gradient(to top, #bfe9ff, #ff6e7f); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	        }
	        
	        
	        	#gbox2 {
			position: relative;
	        width: 40px;
	        height: 40px;
	        display: inline-block;
	        margin: 5px;
	        border-radius: 50%;    

			
	        } 
	        
	        #gtext{
		  position: relative;
		  float: left;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%, -50%);
		  
	        } 
   	        #gtext a:hover{
   	            text-decoration: underline;
	        
	        }
	        a:link{
	        color: black;
	        }
	        .wrapper a:hover{
	            text-decoration: underline;
	        color: ffd800;
	        }

    </style>
    <title>Browse</title>
</head>
<body>

<% if(session.getAttribute("username")==null){
	response.sendRedirect("Login.jsp"); 
	}
	String firstName = (String)session.getAttribute("firstName");
	String cartTotal = (String)session.getAttribute("cartTotal");
	
	String fullname = (String)session.getAttribute("fullname");

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

        <div style="width: 600px">
        <h3>Browse</h3>

     	<%= request.getAttribute("queryResults") %> 
     	<p></p>
    </div>
    </div>
</body>
</html>