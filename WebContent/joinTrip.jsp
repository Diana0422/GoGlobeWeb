<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- declaration of a join trip bean -->
<jsp:useBean id="joinTripBean" scope="session" class="logic.bean.JoinTripBean"/>

<%@page import="java.util.Vector"%>      <%--Importing all the dependent classes--%>
<%@page import="java.util.Iterator"%> 
<%@page import="logic.model.Trip"%> 

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe-JoinTrip</title>
    <link rel="stylesheet" href="bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="css/cards-bootstrap.css">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/jointrip.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">

    <!--nav bar-->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
        <a href="#" id="logo" class="navbar-brand">GoGlobe</a>
        <!--toggler per piccoli schermi-->
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMenu">
            <ul class="navbar-nav">  <!--aggiungere alla classe mr-auto se voglio gli elementi cliccabili a sx-->
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#" style="margin: 12px;">Trips</a>
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

    <div class="content-join-trip">

        <div class="page-header">
            <!-- page state-->    
            <div class="page-state">
                <h2>Join a trip</h2>
            </div>

            <!-- search bar-->
            <div class="search-bar">
                <form action="joinTrip.jsp" method="POST" name="search-form">
                	<input type="text" class="form-control" name="searchVal" placeholder="Search trip...">
                	<button type="submit" name="search" class="btn btn-primary">Search</button>
                	
                	<h2 id="or">or</h2>
                	<button type="submit" name="plantrip" class="btn btn-primary">Plan Trip</button>
                	<%
          				if (request.getParameter("plantrip") != null) {
          			%>
          					<jsp:forward page="planTrip.jsp"/>
          			<%
          				}
          			%>
          			</form>
            </div>
        </div>


        <!--cards for the results-->
        <div class="results">
        	
        	<%
        		if (request.getParameter("search") != null) {
        		// Search for trips that match this title TODO
        	%>
        	    	<!-- map class attributes to values of the form -->
					<jsp:setProperty name="joinTripBean" property="searchVal"/>
			<%
        			if(joinTripBean.searchTripsByValue()) {
        				System.out.println("here");
        				Vector<Trip> trips = joinTripBean.getObjects();
        				System.out.println("jsp: trips = "+trips);
        				if (trips != null) {
        					System.out.println("here2.\n");
        					Iterator<Trip> iter = trips.iterator();
        							
        					while(iter.hasNext()) {
        						System.out.println("iter has next!");
        						Trip trip = iter.next();
        						if (!trip.getTitle().equals("")) {
        							System.out.println("jsp: trip= "+trip);
        	%>
            						<p><%= trip.getTitle() %></p>
            						<p><%= trip.getPrice() %></p>
            <%
        						}
        					}
        				}
        			}
        		}
        							
        	%>
        	
            <div id=#first-row class="row">
                <!--card element #1-->
                <div class="col">
                    <div class="card"> <!--aggiungere attributo text-center per centrare il contenuto nella card-->
                        <img src="res/images/Avenue-of-the-Baobobs-Madagascar 2.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #2-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Arches-National-Park-Moab 1.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #3-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/kyoto.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>
            </div>

            <!-- row2-->
            <div id="second-row" class="row">
                <!--card element #4-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/provence.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #5-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Arches-National-Park-Moab 1.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #6-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Avenue-of-the-Baobobs-Madagascar 2.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>