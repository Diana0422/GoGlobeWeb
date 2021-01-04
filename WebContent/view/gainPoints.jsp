<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Home</title>
    
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/gainpoints.css">
    <link rel="stylesheet" href="../css/style.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">
	
	<!-- navigation bar -->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
        <a href="#" id="logo" class="navbar-brand">GoGlobe</a>
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

    <div class="content">
        <!-- Welcome tag -->
        <div class="header">
            <h1>Gain Trip Points</h1>
            <h3>Verify your participation to a trip, and gain points to redeem exclusive prizes.</h3>
        </div>

        <div class="current-trip">
            <div class="left-side">
                <h4>Today you should be at...</h4>
                <form method="POST">
                    <div class="long-card displayer">
                        <h1>Trip Title</h1>
                        <div class="departure-date">
                            <h3>Departure:</h3>
                            <h5>dd/mm/yyyy</h5>
                        </div>
                        <div class="return-date">
                            <h3>Return:</h3>
                            <h5>dd/mm/yyyy</h5>
                        </div>
                        <div class="location">
                            <h3>Today's locations:</h3>
                            <ul>
                                <li>location 1</li>
                                <li>location 2</li>
                            </ul>
                        </div>
                        <button type="submit" name="gainpoints" class="btn btn-primary">Gain Points</button>
                    </div>
                </form>
            </div>

            <div class="gain-result center">
                <h3>Gain points result message.</h3>
            </div>

            <form method="POST" class="center">
                <div class="points-status">
                    <h4>You have ?? points.</h4>
                    <button type="submit" name="redeemprize" class="btn btn-primary">Redeem Prizes</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>