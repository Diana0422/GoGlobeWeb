<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- declaration of a  register bean -->
<jsp:useBean id="registerBean" scope="request" class="logic.bean.RegistrationBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<% System.out.println(sessionBean.getName());
   System.out.println(sessionBean.getSurname());	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Home</title>
    
    <link rel="icon" href="../res/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/home.css">
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override-home">
	
	<!-- navigation bar -->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
        <div class="app">
            <img id="logo-img" src="../res/images/icons8-around-the-globe-50.png">
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
    
    <!-- Welcome tag -->
    <div class="welcome">
    	<h1>Welcome <jsp:getProperty name="sessionBean" property="name"/> <jsp:getProperty property="surname" name="sessionBean"/></h1>
    </div>
    <!-- get started -->
    <div class="get-started">
        <div class="join">
        	<form action="home.jsp" method="POST">
        		<h3>Join an organized trip with other people</h3>
            	<button type="submit" name="jointrip" class="btn btn-primary">Join a Trip</button>
          		<%
          			if (request.getParameter("jointrip") != null) {
          		%>
          				<jsp:forward page="joinTrip.jsp"/>
          		<%
          			}
          		%>
        	</form>
        </div>
        <div class="plan">
        	<form action="home.jsp" method="POST">
        		<h3>Plan your personal trip and share with the community</h3>
            	<button type="submit" name="plantrip" class="btn btn-primary">Plan a Trip</button>
            	<%
          			if (request.getParameter("plantrip") != null) {
          		%>
          				<jsp:forward page="SelectTripPreferences.jsp"/>
          		<%
          			}
          		%>
        	</form>
        </div>
    </div>
    

</body>
</html>