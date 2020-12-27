<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <jsp:useBean id="planTripBean" scope="session" class="logic.bean.PlanTripBean"/>  
   <jsp:setProperty name="planTripBean" property="*"/>
   
<% 
   if (request.getParameter("back-btn")!= null){
%>
	   <jsp:forward page="home.jsp"/>
<% 
   }
%>

<!DOCTYPE html>

<html lang="en">
<head>
	<meta charset="UTF-8">
    <title>GoGlobe - Select trip preferences</title>
    
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/planTrip.css">
    <link rel="stylesheet" href = "../css/style.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="selectPreferences-body">
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

    <!-- PAGE CONTENT -->
    <div id="preferences-bg">
        <!-- preferences form-->
        <div id="preferences-form">
        <h2>Insert information about your trip!</h2>
<%
	if (request.getParameter("next-btn") != null){
		if (planTripBean.validateForm()){
			planTripBean.setPreferences();
%>			
			<jsp:forward page="planTrip.jsp"/>
<% 
		}else{
%>
			<p style="color: red"><jsp:getProperty name="planTripBean" property="errorMsg"/></p>
<%
		}
	}
%>
            <form method="POST" action="SelectTripPreferences.jsp">
                <div class="form-row">
                    <!-- Trip name-->
                    <div class="form-group col-md-6">
                        <label for="inputDepDate" ><h4>Trip Title</h4></label>
                        <input type="text" class="form-control" name="tripName" id="tripName"
                        placeholder="Enter trip name..." maxlength="25">
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="inputDepDate"><h4>Departure Date</h4></label>
                        <input type="text" class="form-control" name="departureDate" id="depDate"
                        placeholder="dd/mm/yyyy">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="inputRetDate"><h4>Return Date</h4></label>
                        <input type="text" class="form-control" name="returnDate" id="retDate"
                        placeholder="dd/mm/yyyy" >
                    </div>
                </div>

                <div class="form-row form-categories">              
                    <div class="form-group col-md-6">
                        <label for="selectCategory1"><h4>Trip category 1</h4></label>
        
                        <select class="custom-select" id="selectCategory1" name="category1">
                            <option selected value="none">Choose...</option>
                          	<option value="Adventure">Adventure</option>
                            <option value="Relax">Relax</option>
                            <option value="Culture">Culture</option>
                            <option value="Fun">Fun</option>
                    </select>
                    </div>  
                    <div class="form-group col-md-6">
                        <label for="selectCategory2"><h4>Trip category 2</h4></label>
        
                        <select class="custom-select" id="selectCategory2" name="category2">
                            <option selected value="none">Choose...</option>
                            <option value="Adventure">Adventure</option>
                            <option value="Relax">Relax</option>
                            <option value="Culture">Culture</option>
                            <option value="Fun">Fun</option>
                    </select>
                    </div>
                    
                    <div id="form-btns" class="form-btns">
                        <button type="submit" class="btn btn-secondary btn-lg" name="back-btn">Back</button>
                        <button type="submit" class="btn btn-primary btn-lg preferences-btn" name="next-btn">Next</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</body>
</html>