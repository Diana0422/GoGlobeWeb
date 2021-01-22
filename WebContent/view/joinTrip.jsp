<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- declaration of a join trip bean -->
<jsp:useBean id="joinTripBean" scope="session" class="logic.bean.JoinTripBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<%@page import="java.util.List"%>      <%--Importing all the dependent classes--%>
<%@page import="java.util.Iterator"%> 
<%@page import="logic.bean.TripBean"%>
<%@page import="logic.control.JoinTripController"%>

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

<%
	if (sessionBean.getSessionEmail() != null) {
		%>
		  <%@ include file="html/loggedNavbar.html" %>
		<%
	} else {
		%>
		  <%@ include file="html/unloggedNavbar.html" %>
		<%
	}
%>

    <div class="content-join-trip">

			<%@ include file="html/joinTripHeader.html" %>
        
            <%
          		if (request.getParameter("plantrip") != null) {
          	%>
          			<jsp:forward page="SelectTripPreferences.jsp"/>
          	<%
          		}
          	%>


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
					System.out.println(joinTripBean.getSearchVal());
					joinTripBean.setObjects(JoinTripController.getInstance().searchTrips(joinTripBean.getSearchVal()));
        			if(!joinTripBean.getObjects().isEmpty()) {
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
        							request.setAttribute("title", trip.getTitle());
        							request.setAttribute("price", trip.getPrice());
        							request.setAttribute("btninfoid", idx);
        	%>
        							<%@ include file="html/tripCard.html" %>  			
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