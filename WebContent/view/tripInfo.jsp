<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- declaration of a join trip bean -->
<jsp:useBean id="joinTripBean" scope="session" class="logic.bean.JoinTripBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<%@page import="java.util.List"%>      <%--Importing all the dependent classes--%> 
<%@page import="logic.bean.DayBean"%>
<%@page import="logic.bean.ActivityBean"%>
<%@page import="logic.control.JoinTripController"%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe-JoinTrip</title>
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
    		</ul>
    	
    	<div class="tab-content">
    		<div class="tab-pane active" role="tabpanel" id="general">
    			<div class="main-content">
    				<div class="general-info">
            			<div class="trip-title">
      
                			<h1><%= joinTripBean.getTrip().getTitle() %></h1>
            			</div>
            			<div class="price-tag" style="margin-top:10%; margin-bottom: 5%;">
                			<h4>Starting price:</h4>
                			<h5><%= joinTripBean.getTrip().getPrice() %></h5>
            			</div>
            			<h3>Description:</h3>
            			<div class="trip-description">
                			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam tincidunt ex enim, ac laoreet justo rhoncus eget. Suspendisse potenti. Vivamus eleifend dui ut turpis aliquet tempor. Phasellus consequat tincidunt varius. Aenean rhoncus nunc ut tristique porttitor. Curabitur quis vulputate sem. Nunc eu nunc at urna blandit ornare at sed dolor. Nunc blandit in sem a eleifend. Nulla at odio vulputate nisl fringilla vehicula. In vel est vel dui volutpat placerat sit amet id sem. Suspendisse elit mi vel. </p>
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
                    			<h5>Departure:</h5>
                    			<h5><%= joinTripBean.getTrip().getDepartureDate() %></h5>
                			</div>
                			<div class="return">
                    			<h5>Return:</h5>
                    			<h5><%= joinTripBean.getTrip().getReturnDate() %></h5>
                			</div>
            			</div>
            			<h3>Categories:</h3>
            			<ul class="category-list">
                			<li class="category1">
                    			<img src="../res/images/icons8-cocktail-50.png">
                    			<h5><%= joinTripBean.getTrip().getCategory1() %></h5>
                			</li>
                			<li class="category2">
                    			<img src="../res/images/icons8-greek-pillar-capital-50.png">
                    			<h5><%= joinTripBean.getTrip().getCategory2() %></h5>
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
    					<h2 id="panel-title">Location: <%= dayBean.getLocation() %></h2>
    					<h2 id="panel-title">Activities of the day:</h2>
    					<div class="scrollable">
    					
    					<%
        					for (int j=0; j<dayBean.getActivities().size(); j++) {
        						ActivityBean activityBean = dayBean.getActivities().get(j);
        						%>
        							<div class="activity">		
		            					<h2 id="activity-title"><%= activityBean.getTitle() %></h2>
		            					<h3><%= activityBean.getTime() %></h3>
		            					<div class="description">
		            						<p><%= activityBean.getDescription() %></p>
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
    		
    	</div>
    </div>
</div>
</body>
</html>