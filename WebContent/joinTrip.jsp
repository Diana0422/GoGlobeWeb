<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe-JoinTrip</title>
    <link rel="stylesheet" href="bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="css/cards-bootstrap.css">
    <link rel="stylesheet" href="css/jointrip.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">

    <!--nav bar-->
    <nav class="navbar navbar-expand-sm navbar-light bg-light sticky-top">
        <a href="#" id="logo" class="navbar-brand">GoGlobe</a>
        <!--toggler per piccoli schermi-->
        <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarMenu">
            <ul class="navbar-nav">  <!--aggiungere alla classe mr-auto se voglio gli elementi cliccabili a sx-->
                <li class="nav-item">
                    <a class="nav-link" href="#" style="margin: 12px;">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#" style="margin: 12px;">Trips</a>
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

    <div class="content-join-trip">

        <div class="page-header">
            <!-- page state-->    
            <div class="page-state">
                <h2>Join a trip</h2>
            </div>

            <!-- search bar-->
            <div class="search-bar">
                <input type="text" class="form-control" name="search" value="Search trip...">
                <input id="custom-btn" type="button" class="btn btn-primary" value="Search">
                <h2 id="or">or</h2>
                <input id="custom-btn" type="button" class="btn btn-primary" value="Plan trip">
            </div>
        </div>


        <!--cards for the results-->
        <div class="results">
            <div id=#first-row class="row">
                <!--card element #1-->
                <div class="col">
                    <div class="card"> <!--aggiungere attributo text-center per centrare il contenuto nella card-->
                        <img src="res/images/Avenue-of-the-Baobobs-Madagascar 2.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #2-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Arches-National-Park-Moab 1.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #3-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/kyoto.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>
            </div>

            <!-- row2-->
            <div id="second-row" class="row">
                <!--card element #4-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/provence.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #5-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Arches-National-Park-Moab 1.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>

                <!--card element #6-->
                <div class="col">
                    <div class="card">
                        <img src="res/images/Avenue-of-the-Baobobs-Madagascar 2.png" class="card-img-top">
                        <div class="card-body">
                            <h3 class="card-title">Trip Title</h3>
                            <div class="price-tag">
                                <h5>Starting price: </h5>
                                <h5>2000€</h5   >
                            </div>
                            <img src="res/images/icons8-mountain-50.png" alt="">
                            <img src="res/images/icons8-holiday-50.png" alt="">
                            <img src="res/images/icons8-greek-pillar-capital-50.png" alt="">
                            <img src="res/images/icons8-cocktail-50.png" alt="">
                            <input id="custom-btn" type="button" class="btn btn-primary" value="More Info">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>