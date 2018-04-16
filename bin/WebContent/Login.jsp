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
        #logo{
            float: left;
        }

        .wrapper{
            padding: 10px;
        }
    </style>
    <title>Movie Site - Login</title>
    
<!--    GOOGLE RECAPTCHA, UNCOMMENT TO ACTIVATE
 <script src="https://www.google.com/recaptcha/api.js"></script>
 -->    
 
</head>
<body>
<div id="header">
    <div id="logo">
        <h1>MovieSite</h1>
    </div>
</div>
    

<div class="wrapper"  align="center">
    <div style="width: 500px">
        <h3>Login</h3>
        
  <form action="Login" method="post">
            
  <div class="form-group" align="left">
    <label>Email address</label>
    <input type="email" class="form-control" placeholder="Enter email" name="username">
  </div>
  <div class="form-group" align="left">
    <label>Password</label>
    <input type="password" class="form-control" placeholder="Password" name="password">
  </div>
      
<div class="g-recaptcha"
			data-sitekey="6LcSo0cUAAAAAChJQk1xy2VySbRpfhiEc5cxk6m9"></div>
      <p></p>
  <button type="submit" class="btn btn-primary" name="Login">Submit</button>
            
            
</form>

<% 
	
	String wrongInformation = (String) request.getAttribute("wrongInformation");
	String wrongRecaptcha = (String) request.getAttribute("wrongRecaptcha");
	
	if(wrongInformation != null) {
		
		if(wrongInformation.equals("0")) {
			
			out.println("<div class='p-3 mb-2 text-white bg-danger'><strong>Your Email or Password is incorrect.</strong></div>"); 
		}
	}
	
	if(wrongRecaptcha != null) {
		
		if(wrongRecaptcha.equals("0")) {
			
			out.println("<div class='p-3 mb-2 bg-warning'><strong>Recaptcha Fail. Please click the checkbox.</strong></div>"); 
		}
	}
	
	
%>
   
    </div>  
    </div> 
</body>
</html>