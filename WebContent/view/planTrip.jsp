<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="planTripBean" scope="session" class="logic.bean.PlanTripBean"/>
<%@page import="java.util.List"%>      
<%@page import="java.util.Iterator"%> 
<%@page import="logic.model.Trip"%>
<%@page import="logic.model.Day"%>



<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>GoGlobe - Plan Trip</title>
    
    <link rel="stylesheet" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/planTrip.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">
    
	<!-- navigation bar -->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top" aria-label="navbar">
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
    
    
    <div class="sb-wrapper">
        <!-- SIDEBAR -->
        <nav id="sidebar" aria-label="sidebar">
            <form  action="planTrip.jsp" method="POST">	
            	<div class="sb-header">
            	
<% 
			if (planTripBean.getTripDays() != null){
				int i = 0;
				while(i < planTripBean.getTripDays().size()){
%>				
	                <button type="submit" class="btn btn-colors btn-lg btn-block" name="daybtn" value=<%= i%> >Day <%= i + 1 %></button>	               	    
<%
					i++;
				}
			}
%>
<%
if (request.getParameter("daybtn") != null){
	System.out.println(request.getParameter("daybtn")); 
}
%>  
				
           		</div> 
           </form>    
       </nav>
	   
        <!-- PAGE CONTENT -->
        <div class="plantrip-content">
            <!-- DAY DESCRIPTION-->
            <div class="day" id="day">
	            <div class="dayName"> 
	            	<h1>Day:</h1>  
	            	<input type="text" name="dayName" id="dayName-input"
                        placeholder="Enter day name..." maxlength="25">
	            </div>
                <div class="stop">
                    <h4 class="location">Kyoto</h4>
                    <div class="day-plan">
                        <div class="form-group">
                            <label for="Day plan">Day plan (max 2000 characters)</label>
                            <textarea class="form-control" id="day-plan" rows="3" style="resize: none;"></textarea>
                            <form method="POST">                        
                                <button id="save-btn" type="submit" class="btn btn-primary" name="save-btn" >Save Day</button>  
                                <%  
                                if (request.getParameter("save-btn") != null){
                                	System.out.println("mammalamamamamam");
                                }
                                %>
                            </form>
                        </div>   
                    </div>       
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>