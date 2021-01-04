<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe - Home</title>
    
    <link rel="stylesheet" type="text/css" href="../bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="../css/profile.css">
    <link rel="stylesheet" href="../css/review.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>

<body id="bootstrap-override">
	
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
                    <img src="../res/images/falessi.jpg" alt="profile image" class="rounded-circle border border-dark">
                </div>

                <div class="info">
                    <div class="name profile-element">
                        <h2>Davide</h2>
                        <h2>Falessi</h2>
                    </div>

                    <div class="age profile-element">
                        <h4>Age: <span class="text-val">33</span></h4>
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
                        <p class="bio-text">
                            My name is Yoshikage Kira. I'm 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married.
                            I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest. 
                            I don't smoke, but I occasionally drink. I'm in bed by 11 PM, and make sure I get eight hours of sleep, no matter what. 
                            After having a glass of warm milk and doing about twenty minutes of stretches before going to bed, I usually have no problems sleeping until morning. 
                            Just like a baby, I wake up without any fatigue or stress in the morning. I was told there were no issues at my last check-up. 
                            I'm trying to explain that I'm a person who wishes to live a very quiet life. I take care not to trouble myself with any enemies, like winning and losing, that would cause me to lose sleep at night. 
                            That is how I deal with society, and I know that is what brings me happiness. 
                            Although, if I were to fight I wouldn't lose to anyone.
                        </p>


                    </div>

                    <div class="tab-pane" role="tabpanel" id="reviews">
                        <!--<div class="filler center"><h4>No reviews.</h4></div>-->

                        <!-- review form-->
                        <div class="review-form card">
                            <h3>Post a review</h3>
                            <form action="postReview.jsp" method="POST">
            
                                <div class="post-card">
                                    <!-- type of review input-->
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="type-radio" id="traveler" value="traveler" checked>
                                        <label class="form-check-label" for="traveler">As traveler</label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="type-radio" id="organizer" value="organizer">
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
                
                                    <!-- text area for comment-->
                                    <div class="input-group">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text">Comment</span>
                                        </div>
                                        <textarea class="form-control" aria-label="Comment"></textarea>
                                    </div>
                                </div>
                                
                                <button type="submit" class="btn btn-primary" name="reviewbtn">Post</button>
                            </form>
                        </div>

                        <!-- review list-->
                        <div class="reviews scrollable">

                            <!--SINGLE REVIEW CARD (repeatable)-->
                            <div class="review card">
                                <h4 id="user">Pippo Baudo</h4>
                                <div class="stars">
                                    <label class="star star-5"></label>
                                    <label class="star star-4"></label>
                                    <label class="star star-3"></label>
                                    <label class="star star-2"></label>
                                    <label class="star star-1"></label>
                                </div>
                                <div class="review-text">
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc volutpat eget eros quis dignissim. Vivamus condimentum vulputate eros, vitae congue sapien dapibus eget. Suspendisse potenti. Donec eget sodales nisl. Praesent vel accumsan urna. Fusce tincidunt ut orci eu aliquet. 
                                    Donec quis orci erat. Vestibulum ullamcorper mattis ante, sit amet lobortis lectus ornare et.
                                    Pellentesque id nisi non lacus finibus sagittis eu eget enim. Praesent tellus velit, molestie vitae nunc a, pulvinar iaculis urna. Ut tempor porttitor metus, quis gravida urna cursus vel. Donec eleifend varius ultrices. 
                                    Aenean bibendum consequat nisl in posuere. Aenean facilisis mauris a bibendum dapibus. 
                                    In eget turpis ornare mauris bibendum pulvinar ut at augue. Nam volutpat enim at turpis fermentum lobortis sit amet eget nisl.</p>
                                </div>
                            </div>


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