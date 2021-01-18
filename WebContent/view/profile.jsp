<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="profileBean" scope="session" class="logic.bean.ProfileBean"/>
<jsp:useBean id="userBean" scope="session" class="logic.bean.UserBean"/>
<jsp:useBean id="sessionBean" scope="session" class="logic.bean.SessionBean"/>

<%@page import="logic.bean.ReviewBean"%>
<%@page import="logic.control.ReviewUserController"%>

<jsp:setProperty name="profileBean" property="comment"/>
<jsp:setProperty name="profileBean" property="title"/>

<%
	userBean = profileBean.getUser();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Profile</title>
    
    <link rel="icon" href="../res/images/favicon.ico">
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/profile.css">
    <link rel="stylesheet" href="../css/review.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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

    <div class="displayer">
        <div class="wrapper">

            <!-- left profile info-->
            <div class="box-profile-info rounded-circle">
                <div class="profile-pic">
                    <img src="../res/images/default-profile.png" alt="profile image" class="rounded-circle border border-dark">
                </div>

                <div class="info">
                    <div class="name profile-element">
                        <h2><%= profileBean.getUser().getName() %></h2>
                        <h2><%= profileBean.getUser().getSurname() %></h2>
                    </div>

                    <div class="age profile-element">
                        <h4>Age: <span class="text-val"><%= profileBean.getUser().getAge() %></span></h4>
                    </div>

                    <div class="travel-attitude profile-element">
                        <h4>Traveler Attitude</h4>
                        <ul>
                            <li class="adventure">
                                <label for="adv-img">x%</label>
                                <img src="../res/images/icons8-mountain-50.png" id="adv-img">
                                <label for="adv-img">Adventure</label>
                            </li>
                            <li class="relax">
                                <label for="rel-img">x%</label>
                                <img src="../res/images/icons8-holiday-50.png" id="rel-img">
                                <label for="rel-img">Relax</label>
                            </li>
                            <li class="fun">
                                <label for="fun-img">x%</label>
                                <img src="../res/images/icons8-cocktail-50.png" id="fun-img">
                                <label for="fun-img">Fun</label>
                            </li>
                            <li class="culture">
                                <label for="cult-img">x%</label>
                                <img src="../res/images/icons8-greek-pillar-capital-50.png" id="cult-img">
                                <label for="cult-img">Culture</label>
                            </li>
                        </ul>
                    </div>
                    
                    <div class="organizerRating">
                    	<h4>Organizer Rating:</h4>
                    	<%
                    		int count = 5;
                    		for (double d=0; d<profileBean.getUser().getOrgRating(); d++) {
                    			count--;
                    			%>
                    			<span class="fa fa-star checked"></span>
                    			<% 
                    		}
                    		
                    		for (int i=0; i<count; i++) {
                    			%>
                    			<span class="fa fa-star"></span>
                    			<%
                    		}
                    	%>
                    </div>
                    
                    <div class="travelerRating">
                    	<h4>Traveler Rating:</h4>
                    	<%
                    		int count2 = 5;
                    		for (double d=0; d<profileBean.getUser().getTravRating(); d++) {
                    			count2--;
                    			%>
                    			<span class="fa fa-star checked"></span>
                    			<% 
                    		}
                    		
                    		for (int i=0; i<count2; i++) {
                    			%>
                    			<span class="fa fa-star"></span>
                    			<%
                    		}
                    	%>
                    </div>

                    <form method="POST" action="profile.jsp">
                        <button type="submit" name="change-btn" class="btn btn-light">
                            <img src="../res/images/icons8-edit-50.png" id="change-icon" style="width: 20px; height: 20px; margin-right: 1%;">
                            <label for="change-icon">Change Info</label>
                        </button>
                    </form>
                </div>
            </div>


            <div class="content-tabs">
                <ul class="nav nav-tabs">
                    <li class="nav-item">
                        <a class="nav-link active" href="#bio" data-toggle="tab">Bio</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#reviews" data-toggle="tab">Reviews</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#prev-trips" data-toggle="tab">Previous Trips</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#up-trips" data-toggle="tab">Upcoming Trips</a>
                    </li>
                    <li class="nav-item">
                    	<a class="nav-link" href="#my-trips" data-toggle="tab">My Trips</a>
                    </li>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane active" role="tabpanel" id="bio">
                        <!--<div class="filler center"><h4>No bio.</h4></div>-->

                        <!-- biography -->
                        <p class="bio-text"><%= profileBean.getUser().getBio() %></p>
                    </div>

                    <div class="tab-pane" role="tabpanel" id="reviews">
                        <!--<div class="filler center"><h4>No reviews.</h4></div>-->

                        <!-- review form-->
                        <div class="review-form card">
                            <h3>Post a review</h3>
                            <form action="profile.jsp" method="POST">
            
                                <div class="post-card">
                                    <div class="review-part1">
                                    	<!-- type of review input-->
                                    	<div class="form-check">
                                        	<input class="form-check-input" type="radio" name="type-radio" id="traveler" value="TRAVELER" checked>
                                        	<label class="form-check-label" for="traveler">As traveler</label>
                                    	</div>
                                    	<div class="form-check">
                                        	<input class="form-check-input" type="radio" name="type-radio" id="organizer" value="ORGANIZER">
                                        	<label class="form-check-label" for="organizer">As organizer</label>
                                    	</div>
                
                                    	<!-- star rating input -->
                                    	<div class="stars">
                                            <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
                                            <label class="star star-5" for="star-5"></label>
                                            <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
                                            <label class="star star-4" for="star-4"></label>
                                            <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
                                            <label class="star star-3" for="star-3"></label>
                                            <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
                                            <label class="star star-2" for="star-2"></label>
                                            <input class="star star-1" id="star-1" type="radio" name="star" value="1"/>
                                            <label class="star star-1" for="star-1"></label>
                                    	</div>
                                    </div>
                
                                    <div class="review-part2">
                                    	<!-- input for review title -->
                                    	<input name="title" type="text" class="form-control" id="reviewTitle" placeholder="Add a title for the review...">
                                    
                                    	<textarea name="comment" class="form-control" id="reviewComment" placeholder="Add a comment..."></textarea>     
                                    </div>
                                </div>
                                
                                <button type="submit" class="btn btn-primary" name="reviewbtn">Post</button>
                                
                                <%
                                	if (request.getParameter("reviewbtn") != null) {
                                		profileBean.setVote(Double.parseDouble(request.getParameter("star")));
                                		// Date
                                		
                                		ReviewBean review = new ReviewBean();
                                		review.setTitle(profileBean.getTitle());
                                		review.setComment(profileBean.getComment());
                                		review.setDate(profileBean.getDate());
                                		review.setReviewerName(sessionBean.getName());
                                		review.setReviewerSurname(sessionBean.getSurname());
                                		review.setVote(profileBean.getVote());
                                		profileBean.getUser().getReviews().add(review);
                           
                                		ReviewUserController.getInstance().postReview(request.getParameter("type-radio"), profileBean.getVote(), profileBean.getComment(), profileBean.getTitle(), sessionBean.getEmail(), userBean.getEmail(), null);
                                		
                                	}
                                
                                %>
                            </form>
                        </div>

                        <!-- review list-->
                        <div class="reviews scrollable">

                            <%
                            	for (ReviewBean bean: profileBean.getUser().getReviews()) {
                            		%>
                            		<!--SINGLE REVIEW CARD (repeatable)-->
                            		<div class="review card">
                                		<h4 id="user"><%= bean.getReviewerName()+" "+bean.getReviewerSurname() %></h4>
                               			<div class="stars">
                                   			<%
                    							int max = 5;
                    							for (double d=0; d<bean.getVote(); d++) {
                    								max--;
                    						%>
                    							<span class="fa fa-star checked"></span>
                    						<% 
                    							}
                    		
                    							for (int i=0; i<max; i++) {
                    							%>
                    								<span class="fa fa-star"></span>
                    							<%
                    							}
                    						%>
                                		</div>
                                		<div class="review-text">
                                    		<p><%= bean.getComment() %></p>
                                		</div>
                            		</div>
                            		
                            		
                            		<% 
                            	}
                            
                            %>

                        </div>

                    </div>

                    <div class="tab-pane" role="tabpanel" id="prev-trips">

                        <!-- previous trips list-->
                        <div class="previous-trips scrollable">
                        </div>

                        <div class="filler center"><h4>No previous trips.</h4></div>
                    </div>

                    <div class="tab-pane" role="tabpanel" id="up-trips">

                        <!-- upcoming trips list-->
                        <div class="upcoming-trips scrollable">
                        </div>

                        <div class="filler center"><h4>No upcoming trips.</h4></div>
                    </div>
                    
                    <div class="tab-pane" role="tabpanel" id="my-trips">
                    	<div class="filler center"><h4>No trips planned.</h4></div>
                    </div>
                </div>
            </div>


        </div>
    </div>
</body>