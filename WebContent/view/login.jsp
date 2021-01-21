<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Dichiarazione e istanziazione di un loginBean -->
<!--  id è il nome della variabile che andiamo ad istanziare -->

<jsp:useBean id="loginBean" scope="request" class="logic.bean.LoginBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<!-- Mappare gli attributi di un oggetto sui campi della form -->
<jsp:setProperty name="loginBean" property="*"/>

<%@page import="logic.control.LoginController"%>

<%
	System.out.println(sessionBean.getName());
	System.out.println(sessionBean.getSurname());
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>GoGlobe - Login</title>
	
	<link rel="icon" href="../res/images/favicon.ico">
	<link href="../bootstrap-css/bootstrap.css" rel="stylesheet">
	<link href="../css/login.css" rel="stylesheet">
	<link href="../css/style.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="js/bootstrap.min.js"></script> 
	
</head>
<body id="login-body">
		<!-- navigation bar -->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
        <div class="app">
            <img id="logo-img" src="../res/images/icons8-around-the-globe-50.png" alt="">
            <a href="#" id="logo" class="navbar-brand">GoGlobe</a>
        </div>
        <!--toggler for shorter screens -->
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMenu">
            <ul class="navbar-nav">  <!--aggiungere alla classe mr-auto se voglio gli elementi cliccabili a sx-->
                <li class="nav-item">
                    <a class="nav-link active" href="home.jsp" style="margin: 12px;">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="joinTrip.jsp" style="margin: 12px;">Trips</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="profile.jsp" style="margin: 12px;">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="manageRequests.jsp" style="margin: 12px;">Requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Log Out</a>
                </li>
            </ul>
        </div>
    </nav>
    
        <div class="login-wrapper">
            <div class="login-box">
                <h1 id="Login-text">Login</h1>
                
<%
	if (request.getParameter("signin") != null){
		if (loginBean.validate()){
			if ((loginBean = LoginController.getInstance().login(loginBean.getUsername(), loginBean.getPassword())) != null) {
				sessionBean.setName(loginBean.getNome());
				sessionBean.setSurname(loginBean.getCognome());
				sessionBean.setEmail(loginBean.getUsername());
			}
%>
	<jsp:forward page="home.jsp"/>
<%	
		}else{
%>
<p style="color: red"> Dati Errati</p>
<% 
		}
	}
%>
                <form action="login.jsp" name="login-form" method="POST">
                
               		<!-- username -->
                    <div class="form-group">
                    
                      <label for="username">Email address</label>
                      <input type="email" name="username" class="form-control" id="username" 
                       aria-describedby="emailHelp" placeholder="Enter email">
                      <small id="emailHelp" class="form-text text-muted">
                      We'll never share your email with anyone else.</small>
                    </div>
                    
                    <!-- password -->
                    <div class="form-group">
                      <label for="password">Password</label>
                      <input type="password" name="password" class="form-control" 
                      id=password placeholder="Password">
                    </div>
                    <!-- SIGN IN  button -->
                    <div class="signin-btn-div">
                        <input type="submit" class="btn btn-primary btn-lg" name="signin" value="Sign In"></input>
                        
                        <!-- No account, sign up -->
                        <p>Don't have an account? 
                            <a href="register.jsp" id="signup">Sign Up</a>
                        </p>
                    </div>
                  </form>
                </div> 
            </div>
</body>
</html>