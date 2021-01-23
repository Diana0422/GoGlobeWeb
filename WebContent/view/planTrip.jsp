<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:useBean id="planTripBean" scope="session" class="logic.bean.PlanTripBean"/>
<jsp:useBean id="activityBean" scope="request" class="logic.bean.ActivityBean"/> 
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<jsp:setProperty name="activityBean" property="*" />
<jsp:setProperty name="planTripBean" property="location" />

<%@page import="java.util.List"%>      
<%@page import="java.util.Iterator"%> 
<%@page import="logic.model.Trip"%>
<%@page import="logic.model.Day"%>
<%@page import="logic.model.Place"%>
<%@page import="logic.control.PlanTripController"%>
<%@page import="logic.persistence.exceptions.DatabaseException"%>



<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>GoGlobe - Plan Trip</title>
    
    <link rel="icon" href="../res/images/favicon.ico">
    <link rel="stylesheet" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/planTrip.css">
    <link rel="stylesheet" href="../css/style.css">
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">
    
	<%@ include file="html/loggedNavbar.html" %>
    
    
    <div class="sb-wrapper">
        <!-- SIDEBAR -->
        <nav id="sidebar" aria-label="sidebar">
            <form  action="planTrip.jsp" method="POST">	
            	<div class="sidebar-header">
            	
            	<button type="submit" name="save-trip-btn" class="btn btn-primary btn-lg btn-block">Save Trip</button>
            	
            	<div class="separator"> OR </div>
            	
            	<button type="submit" name="share-trip-btn" class="btn btn-primary btn-lg btn-block">Share Trip</button>
            	
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
	System.out.println("button pressed: " + request.getParameter("daybtn")); 
	planTripBean.setPlanningDay(Integer.parseInt(request.getParameter("daybtn")));
}
%>  
				
           		</div> 
           </form>    
       </nav>
	   
        <!-- PAGE CONTENT -->
        <div class="plantrip-content">
            <!-- DAY DESCRIPTION-->
            
            <div class="day" id="day">
            	<div class="form">
	            <h1 id="trip-title"><%= planTripBean.getTripName() %></h1>
	           
	           <!--  IF SAVE TRIP IS CLICKED -->  
				<%
				if (request.getParameter("save-trip-btn") != null){
					try {
						if (planTripBean.validateTrip()){
				    		PlanTripController.getInstance().saveTrip(planTripBean.getTripBean(), sessionBean); 
							%>
							<jsp:forward page="home.jsp"/>
							<% 
						}else{
							%>
							<!-- VALIDATE TRIP ERROR -->
							<p style="color: red">ERRORE</p>	
							<%				
						}
					} catch (DatabaseException e) {
						request.setAttribute("errType", e.getMessage());
						if (e.getCause()!=null) request.setAttribute("errLog", e.getCause().toString());
						%>
						 <jsp:forward page="error.jsp"/>
						<%
					}				
				}
				%>
			<!--  IF SHARE TRIP IS CLICKED -->
<%
				if (request.getParameter("share-trip-btn") != null){
					
					if (planTripBean.validateTrip()){
%>
						<jsp:forward page="shareTrip.jsp"/>
<% 
					}else{
%>
						<!-- VALIDATE TRIP ERROR -->
						<p style="color: red">ERRORE</p>	
<%				
					}				
				}
%>

	            <h2>Day <%= planTripBean.getPlanningDay() + 1 %></h2>   
  		
 <%		
		 if (request.getParameter("save-location-btn") != null){
			 if (planTripBean.validateLocation()){
				planTripBean.saveLocation();
			 }
			}
 
 		if (planTripBean.checkDay()){
 			
 
 %>             
               	<!--  Location Form -->
			   <form method="POST" action="planTrip.jsp">
			   	 <div class="location-form form">
				   	  <div class="form-group row">
					    <label for="inputPassword" class="col-sm-2 col-form-label"><h6>Location</h6></label>
					    <div class="col-sm-10">
					      <input  type="text" name="location" class="form-control" id="inputLocation" placeholder="Insert Location...">
					    </div>
					  </div>
                	<button type="submit" class="btn btn-colors btn-lg btn-block" name="save-location-btn"  >Save Location</button>	               	                           	
                   </div> 
               </form>
               
 <%
 		}else{
 %>            
               
             	<h3>Location: <%=planTripBean.getDayLocation() %></h3>
             </div>
                <div class="day-plan">
                	<!-- NEW ACTIVITY FORM -->
                	<form action="planTrip.jsp" method="POST">
                  		<div class="activity-form">
<%
		if (request.getParameter("save-activity-btn") != null){
			if (activityBean.validateActivity()){
				//planTripBean.addActivity(activityBean);	
				PlanTripController.getInstance().addActivity(planTripBean, activityBean);
			}else{
%>
				<p style="color: red">ERRORE</p>
<% 
			}
		}		                    
%>
                        	<div class = "flex-form-row">
                        		<h4>Title</h4>
                        		<input type="text" name="title" id="act-title"
                        		placeholder="Enter activity name" maxlength="15">
                        	</div>
                        	<div class = "flex-form-row">
                        		<h4>Time</h4>
                        		<input type="text" name="time" id="act-time"
                        		placeholder="hh:mm" maxlength="5">
                        	</div>
                        	<div class = "flex-form-row">
                        		<h4>Cost</h4>
                        		<input type="text" name="estimatedCost" id="act-cost"
                        		placeholder="Insert activity cost">
                        	</div>
                        	<div class = "activity-description">
                        		<h4>Plan</h4>
                               <textarea class="activity-plan" name="description" style="resize:none;"></textarea>
                        		
                        	</div>
                        	<button type="submit" class="btn btn-colors btn-lg btn-block" name="save-activity-btn"  >Add activity</button>	               	                           	
                        </div> 
                    </form>
                    <div>
                   	 <!-- ACTIVITIES -->
 <%
						int activitiesNum = planTripBean.getActivitiesNum();
					 	for ( int j = 0; j < activitiesNum; j++){ 
					 		System.out.println("Activity Found!\n");
	            			System.out.println("title: " +planTripBean.displayActivityTitle(j) + "\n");
	            			System.out.println("time: "+ planTripBean.displayActivityTime(j) + "\n");
	            			System.out.println("time: "+ planTripBean.displayActivityTime(j) + "\n");
	            			System.out.println("cost: "+ planTripBean.displayActivityCost(j) + "\n");
%>		
	            			  
	            			<div class="activity form">		
		            			<h2><%=planTripBean.displayActivityTitle(j) %></h2>
		            			<h3><%=planTripBean.displayActivityTime(j) %></h3>
		            			<h3><%=planTripBean.displayActivityCost(j) + "€" %></h3>
		            			<div class="description">
		            				<p><%=planTripBean.displayActivityDescription(j)%></p>
		            			</div>		            			
	         				</div>                        
 <%
					 	}
 	}
 %>                  
 					</div>        
	            </div>	            
		     </div>
		  </div>
		  <div class="place-suggestions scrollable">
<%
		if (!(planTripBean.checkDay())){
					
			List<Place> suggestions = PlanTripController.getInstance().getNearbyPlaces(planTripBean.getDayLocation(), planTripBean.getCategory1());
			for (int i = 0; i < suggestions.size(); i++){
%>
				
				<!--  INSERIRE LAYOUT SUGGESTION QUI -->
				
		        <div class = "suggestion-card">
		            <div class="ic-container">
		                <img class="suggestion-icon" src=<%= suggestions.get(i).getIcCategoryRef() %> alt=""> 
		            </div>
		            <div class="suggestion-info">
		                <p><%= suggestions.get(i).getName() %></p>
		                <p><%= suggestions.get(i).getAddress() %></p>
		                <p><%= suggestions.get(i).getOpeningHours() %></p>
		            </div>
		       </div>
<% 			
			}
%>		  		  
		  </div>
<%
		}
%>
	   </div>
	  
    
</body>
</html>