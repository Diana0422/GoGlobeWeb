<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Home</title>
    
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/requests.css">
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
                    <a class="nav-link active" href="#" style="margin: 12px;">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Trips</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Log Out</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- tab panels -->
    <div class="container">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 col-xl-12">
            <br>
    
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link active" href="#incoming" data-toggle="tab">Incoming Requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#accepted" data-toggle="tab">Accepted Requests</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#declined" data-toggle="tab">Declined Requests</a>
                </li>
            </ul>
    
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="incoming">

                    <div class="request displayer">
                        <div class="request-info">
                            <h3>Trip Name</h3>
                            <div class="name">
                                <h5>Name</h5>
                                <h5>Surname</h5>
                            </div>
                            <h6>Age</h6>
                        </div>
                        <button type="submit" class="btn btn-primary" name="viewprofile">View Profile</button>
                        <button type="submit" class="btn btn-success" name="accept">Accept</button>
                        <button type="submit" class="btn btn-danger" name="decline">Decline</button>
                    </div>


                </div>
                <div role="tabpanel" class="tab-pane" id="accepted">
                    <div class="filler" style="padding: 250px"><h2>No requests.</h2></div>
                </div>
                <div role="tabpanel" class="tab-pane" id="declined">
                    <div class="filler" style="padding: 250px"><h2>No requests.</h2></div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>