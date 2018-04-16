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
        label{
    display:inline-block;
    width:100%;
}
#infobox{
padding:10px;
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
        <h3>Checkout</h3>
<%
System.out.println("once"); 
%>

	<form action="Checkout"> 
	

	<p></p>
	If you are a new user, please enter your information below. <p></p>
	<div class="form-group">
	<label>First name:</label>
		<input type="text" name="firstName"></div>
		
			<div class="form-group">
			<label>Last name:  </label>
		<input type="text" name="lastName">
		</div>
		
					<div class="form-group">
		
		<label>Credit card number: </label>
		<input type="text" name="creditCard">
		</div>
		
					<div class="form-group">
		<label>Expiration date: </label>
		<input type="text" name="expiration">
		</div>
		<button type="submit" class="btn btn-secondary">Submit</button>
	</form>
	

	

	<% 
	
	String wrongInformation = (String) request.getAttribute("wrongInformation");

	if(wrongInformation != null) {
		
		if(wrongInformation.equals("0")) {
			
			out.println("<div class='p-3 mb-2 bg-warning'><strong>Please re-enter your info.</strong></div>"); 
		}
	}
	
	%>
	
		<p></p>
	
		<div id="infobox" class="border border-secondary" style="width:100%;">
	If your information is on file, please click this button.<p></p>
		<form action="Prefilled">
		<button type="submit" class="btn btn-primary">Pre-filled Information</button>
	</form>
	</div>
	
   
    </div>   
    </div>
</body>
</html>