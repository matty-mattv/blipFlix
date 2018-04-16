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
        #infobox{
            padding: 10px 30px 10px 30px;
            margin-bottom: 10px;
            display: inline-block;
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
        <h3>Add Star / Add Genre Result</h3>

 <%= request.getAttribute("queryResultsOUT") %> 

            
            
   
    </div>   
    </div>
</body>
</html>