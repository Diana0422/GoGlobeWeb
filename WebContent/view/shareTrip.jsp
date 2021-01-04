<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <jsp:useBean id="planTripBean" scope="session" class="logic.bean.PlanTripBean"/>  
   <jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>
   <jsp:setProperty name="planTripBean" property="*"/>
   
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

    <!-- PAGE CONTENT -->
    <div id="preferences-bg">
        <!-- preferences form-->
        <div id="preferences-form">
        <h2>To share your trip insert the following info.</h2>
<%
	if (request.getParameter("share-btn") != null){
		if (planTripBean.validateSharingPref()){
			planTripBean.setSharingPreferences();
			planTripBean.saveTrip(sessionBean);
			System.out.println("VIAGGIO SALVATO COME CONDIVISO");
%>		
			
			<jsp:forward page="home.jsp"/>
<% 
		}else{
%>
			<p style="color: red"><jsp:getProperty name="planTripBean" property="errorMsg"/></p>
<%
		}
	}
%>
            <form method="POST" action="shareTrip.jsp">
            
            	   <div class="form-row">
				    <div class="form-group col-md-6">
				      <label for="inputEmail4">Min age</label>
				      <input type="text" class="form-control" name="minAge" id="inputMinAge" placeholder="Insert minimum age">
				    </div>
				    <div class="form-group col-md-6">
				      <label for="inputPassword4">Max age</label>
				      <input type="text" class="form-control" name="maxAge" id="inputMaxAge" placeholder="Insert maximum age">
				    </div>
				  </div>
            	
                  <div class="form-group">
				    <label for="exampleFormControlTextarea1">Description</label>
				    <textarea name="tripDescription" class="form-control" id="tripDescription-ta" rows="3" placeholder="Describe your trip to other users!"></textarea>
				  </div>
                
                  <div class="custom-file">
					  <input type="file" class="custom-file-input" id="customFile">
					  <label class="custom-file-label" for="customFile">Choose image</label>
				  </div>
                  
                  <div id="form-btns" class="form-btns">
                      <button type="submit" class="btn btn-secondary btn-lg" name="back-btn">Back</button>
                      <button type="submit" class="btn btn-primary btn-lg preferences-btn" name="share-btn">Share</button>
                  </div>
                
            </form>
        </div>
    </div>
</body>
</html>