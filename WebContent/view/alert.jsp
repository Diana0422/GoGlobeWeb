<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Home</title>
    
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/alert.css">
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

    <div class="alertbody displayer">
        <div class="alertcontent">
            <img id="alert-image" src="../res/images/oops.jpg">
            <h2 id="alert-message"> Some error occurred in the server. Please check server log.</h2>
        </div>
        <div class="errorlog">
            <h3 id="error-info">Error type: 404</h3>
            <h3>Error Log:</h3>
            <h6 id="log">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae lorem sed lorem blandit auctor. Aenean sit amet ante enim. Proin porttitor fermentum lorem at faucibus. Phasellus eget tortor ut metus viverra mollis a eu mi. Curabitur nunc leo, sollicitudin ac quam sed, fermentum luctus sapien. Sed viverra felis a massa ultrices, rutrum consectetur dolor gravida. Vivamus fringilla velit a justo maximus, vitae tristique tortor pellentesque. Praesent in velit metus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Mauris ac egestas lectus. Vivamus nibh nibh, tempus et justo non, semper malesuada ligula. Duis porttitor dictum turpis in laoreet. Nunc vulputate nulla sit amet pretium blandit. Proin mollis volutpat risus eu bibendum. Suspendisse vel purus et lectus egestas aliquet commodo vel sapien.</h6>
        </div>
    </div>
</body>
