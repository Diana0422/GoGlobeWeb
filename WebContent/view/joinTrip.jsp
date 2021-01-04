<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- declaration of a join trip bean -->
<jsp:useBean id="joinTripBean" scope="session" class="logic.bean.JoinTripBean"/>

<%@page import="java.util.List"%>      <%--Importing all the dependent classes--%>
<%@page import="java.util.Iterator"%> 
<%@page import="logic.bean.TripBean"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe-JoinTrip</title>
    
    <link rel="icon" href="../res/images/favicon.ico">
    <link rel="stylesheet" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/cards-bootstrap.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/jointrip.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">

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

    <div class="content-join-trip">

        <div class="page-header">
            <!-- page state-->    
            <div class="page-state">
                <h2>Join a trip</h2>
            </div>

            <!-- search bar-->
            <div class="search-bar">
                <form class="form-inline" action="joinTrip.jsp" method="POST" name="search-form">
                	<input type="text" class="form-control" name="searchVal" placeholder="Search trip...">
                	<button type="submit" name="search" class="btn btn-primary">Search</button>
                	
                	<h2 id="or">or</h2>
                	<button type="submit" name="plantrip" class="btn btn-primary">Plan Trip</button>
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


        <!--cards for the results-->
        <form method="POST">
        <div class="results">
        	
        	<%
        		if (request.getParameter("search") != null) {
        		// Search for trips that match this title TODO
        	%>
        	    	<!-- map class attributes to values of the form -->
					<jsp:setProperty name="joinTripBean" property="searchVal"/>
			<%
        			if(joinTripBean.searchTripsByValue()) {
        				List<TripBean> trips = joinTripBean.getObjects();
        				System.out.println("jsp: trips = "+trips);
        				if (trips != null) {
        					Iterator<TripBean> iter = trips.iterator();
        		
        					int elemsInRow=0;
        					Integer idx = 0;
        					while(iter.hasNext()) {
        						System.out.println("iter has next!");
        						TripBean trip = iter.next();
        						idx++;
        						
        						if (elemsInRow == 0) {
        	%>
        		        			<div class="row" style="height: fit-content">
        	<%
        						}
        						if (elemsInRow%3 == 0 && elemsInRow != 0) {
        	%>
        							</div>
        							<div class="row" style="height: fit-content">
        	<%
        						}
        						if (!trip.getTitle().equals("")) {
        							elemsInRow++;
        							System.out.println("elemsInRow: "+elemsInRow);
        							System.out.println("jsp: trip= "+trip);
        	%>
            						<!--card element-->
            							<div class="col" style="margin-bottom: 30px; max-width: 33%">
                    						<div class="card"> <!-- add attribute text-center to center content in card-->
                        						<img src="../res/images/Avenue-of-the-Baobobs-Madagascar 2.png" class="card-img-top">
                        						<div class="card-body">
                            						<h3 class="card-title"><%=trip.getTitle() %></h3>
                            						<div class="price-tag">
                                						<h5>Starting price: </h5>
                                						<h5><%=trip.getPrice() %></h5   >
                            						</div>
                            						<img src="res/images/icons8-mountain-50.png" alt="">
                            						<img src="res/images/icons8-holiday-50.png" alt="">
                            						<img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            						<img src="res/images/icons8-cocktail-50.png" alt="">
                            						<button type="submit" name="viewinfo" class="btn btn-primary" value=<%= idx%>>More Info...</button>
                        						</div>
                    						</div>
                    					</div>
               
                    				
            <%
        						}
        					}
        				}
        			}
        		} else {
        	%>
        			<div class="filler" style="padding: 250px"><h2>No trips found.</h2></div>
        			
        	<%
        		}
        							
        	%>
        	</div>
        </div>
        
        <%
             if(request.getParameter("viewinfo") != null) {
            	int tripNum = Integer.parseInt(request.getParameter("viewinfo"));
             	System.out.println("Button pressed: "+request.getParameter("viewinfo"));
             	joinTripBean.setTrip(joinTripBean.getObjects().get(tripNum-1));
             	System.out.println(joinTripBean.getObjects().get(tripNum-1));
                %>
                	<jsp:forward page="tripInfo.jsp"/>
                <%
             }
        %>
        </form>
      </div>
</body>
</html>