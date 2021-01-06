<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- declaration of a join trip bean -->
<jsp:useBean id="joinTripBean" scope="session" class="logic.bean.JoinTripBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>
<jsp:useBean id="profileBean" scope="request" class="logic.bean.ProfileBean"/>

<%@page import="java.util.List"%>      <%--Importing all the dependent classes--%> 
<%@page import="logic.bean.DayBean"%>
<%@page import="logic.bean.ActivityBean"%>
<%@page import="logic.bean.UserBean"%>
<%@page import="logic.control.JoinTripController"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - JoinTrip</title>
    
    <link rel="icon" href="../res/images/favicon.ico">
    <link rel="stylesheet" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/cards-bootstrap.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/tripinfo.css">
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

    <div class="container-md fixed">
    	<div class="trip-info displayer fixed">
    	
    		<ul class="nav nav-tabs">
    			<li class="nav-item">
    				<a class="nav-link active" href="#general" data-toggle="tab">General Info</a>
    			</li>
    			
    			<!--  one tab for each day of the trip -->
    			<%
    				List<DayBean> days = joinTripBean.getTrip().getDays();
					System.out.println(days);
    					
    				for (int i=0; i<joinTripBean.getTrip().getTripLength(); i++) {
    					DayBean dayBean = days.get(i);
    					System.out.println(dayBean);
    			%>
    			
    				<li class="nav-item">
    					<a class="nav-link" href=<%= "#day"+i+1 %> data-toggle="tab">Day <%= i+1 %></a>
    				</li>
    			
    			<%
    				}    			
    			%>
    			<li class="nav-item">
    				<a class="nav-link" href="#users" data-toggle="tab">Participants</a>
    			</li>
    		</ul>
    	
    		<div class="tab-content">
    			<div class="tab-pane active" role="tabpanel" id="general">
    				<div class="main-content">
    				
	    				<div class="general-info">
	            			<div class="trip-title">
	                			<h1 style="font-family: BarlowBold;"><%= joinTripBean.getTrip().getTitle() %></h1>
	            			</div>
	            			
	            			<!-- Trip Organizer -->
	            			<form action="tripInfo.jsp" method="POST">
	            				<div class="organizer user-tag">
	            					<h3>Organizer:</h3>
	            					<h4 style="margin-right: 10px;margin-bottom: 0px;margin-top: 5px;"><%= joinTripBean.getTrip().getOrganizer().getName() %></h4>
	            					<h4 style="margin-right: 10px;margin-bottom: 0px;margin-top: 5px;"><%= joinTripBean.getTrip().getOrganizer().getSurname() %></h4>
	            					<button type="submit" class="btn btn-primary" name="viewprofile">profile</button>
	            				</div>
	            				
	            				<%
	            					if (request.getParameter("viewprofile") != null) {
	            						System.out.println(joinTripBean.getTrip().getOrganizer().getName());
	            						profileBean.setUser(joinTripBean.getTrip().getOrganizer());
	            						%>
	            						<jsp:forward page="profile.jsp"/>
	            						<% 
	            					}
	            				%>
	            			</form>
	            			
	            			<div class="price-tag" style="margin-top:20%; margin-bottom: 20%;">
	                			<h3>Starting price:</h3>
	                			<h4 style="margin-top: 5px; margin-left:10px;"><%= joinTripBean.getTrip().getPrice() %></h4>
	            			</div>
	            			
	            			<h3>Description:</h3>
	            			<div class="trip-description">
	                			<p><%= joinTripBean.getTrip().getDescription() %></p>
	            			</div>
	            			
	            			<form action="tripInfo.jsp" method="POST">
	            				<button type="submit" name="jointrip" class="btn btn-primary">Join Trip</button>
	            			<%
	            				if (request.getParameter("jointrip") != null ){
	            					JoinTripController.getInstance().joinTrip(joinTripBean.getTrip(), sessionBean);
	            				}
	            			%>
	            			</form>
	        			</div>
	        			
	        			<div class="infos-right">
	            			<div class="trip-img">
	                			<img class="resize" src="../res/images/MtFuji.jpg">
	            			</div>
	            			
	            			<h3>Dates:</h3>
	            			<div class="dates">
	                			<div class="departure">
	                    			<h4>Departure:</h4>
	                    			<h4><%= joinTripBean.getTrip().getDepartureDate() %></h4>
	                			</div>
	                			<div class="return">
	                    			<h4>Return:</h4>
	                    			<h4><%= joinTripBean.getTrip().getReturnDate() %></h4>
	                			</div>
	            			</div>
	            			
	            			
	            			<h3>Categories:</h3>
	            			<ul class="category-list">
	                			<li class="category1">
	                    			<img src="../res/images/icons8-cocktail-50.png">
	                    			<h4><%= joinTripBean.getTrip().getCategory1() %></h4>
	                			</li>
	                			<li class="category2">
	                    			<img src="../res/images/icons8-greek-pillar-capital-50.png">
	                    			<h4><%= joinTripBean.getTrip().getCategory2() %></h4>
	                			</li>
	            			</ul>
	            			
	            			
	        			</div>
    				</div>
    			</div>
    			
    			<%
    		
    			for (int i=0; i<joinTripBean.getTrip().getTripLength(); i++) {
    				DayBean dayBean = days.get(i);
    				System.out.println(dayBean);
    			%>
    				<div class="tab-pane" role="tabpanel" id=<%= "day"+i+1 %>>
    					<h3 id="panel-title">Location:</h3>
    					<h4><%= dayBean.getLocation() %></h4>
    					<h3 id="panel-title">Activities of the day:</h3>
    					<div class="scrollable">
    					
    					<%
        					for (int j=0; j<dayBean.getActivities().size(); j++) {
        						ActivityBean activityBean = dayBean.getActivities().get(j);
        						%>
        							<div class="activity">		
		            					<div class="activity-info">
			            					<h3 id="activity-title"><%= activityBean.getTitle() %></h3>
			            					<h3><%= activityBean.getTime() %></h3>
			            					<div class="description">
			            						<h5><%= activityBean.getDescription() %></h5>
			            					</div>
		            					</div>
		            					<div class="activity-price">
		            						<h4>200 €</h4>
		            					</div>		            			
	         						</div> 
        						<% 
        					}
        				%> 
    					
    					</div>
        			</div>
        		<% 
	    			}
    		
    			%>
    			
    			<!-- separator to inferior part -->
        			<div class="tab-pane" role="tabpanel" id="users">
	        			<div class="participants">
	        				<h3 id="panel-title">Current participants:</h3>
	        				<%
	        					List<UserBean> travelers = joinTripBean.getTrip().getParticipants();
	        					for (UserBean bean: travelers) {
	        						%>
	        							<form action="tripInfo.jsp" method="POST">
	            							<div class="organizer user-tag">
	            								<h4 style="margin-right: 10px;"><%= bean.getName() %></h4>
	            								<h4 style="margin-right: 10px;"><%= bean.getSurname() %></h4>
	            								<button type="submit" class="btn btn-primary" name="viewprofile">profile</button>
	            							</div>
	            				
	            							<%
	            								if (request.getParameter("viewprofile") != null) {
	            									System.out.println(joinTripBean.getTrip().getOrganizer().getName());
	            									profileBean.setUser(joinTripBean.getTrip().getOrganizer());
	            							%>
	            									<jsp:forward page="profile.jsp"/>
	            							<% 
	            								}
	            							%>
	            						</form>
	        						<%
	        					}
	        				%>
	        			</div>
    				</div>
    		</div>    		
    </div>
</div>
</body>
</html>