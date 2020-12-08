<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!-- declaration and initialization of a register bean -->
<jsp:useBean id="registerBean" scope="request" class="bean.RegistrationBean" />

<!-- map attributes of the bean from the form fields -->
<jsp:setProperty name="registerBean" property="*"/>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GoGlobe-Register</title>
    
    <link rel="stylesheet" type="text/css" href="bootstrap-css/bootstrap.css">
    <link rel="stylesheet" href="css/register-style.css">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"></script>
</head>
<body id="bootstrap-override">

    <!-- navbar -->
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
                    <a class="nav-link" href="#" style="margin: 12px;">Trips</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#" style="margin: 12px;">Log In</a>
                </li>
            </ul>
        </div>
    </nav>
    
    	 <%
    if (request.getParameter("signin") != null) {
        if (registerBean.validate()) {
%>
        	<jsp:forward page="home.jsp"/>
<%
        } else {
        	System.out.println("No data.\n");
%>
			<p style="color: red">wrong data</p>
<%
        }
    }
%>

    <!-- content -->
    <div class="content-register">
        <div class="container">

            <!-- registration form -->
            <div class="registration-form">
                <form  action="register.jsp" name="myform" method="POST">
                    <div class="form-row">
                        <h1>Register</h1>
                    </div>
                    <!-- email and password -->
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputEmail4">Email</label>
                            <input type="email" name="email" class="form-control" id="inputEmail4" aria-describedby="emailHelp" placeholder="Email">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputPassword4">Password</label>
                            <input type="password" name="password" class="form-control" id="inputPassword4" placeholder="Password">
                        </div>
                    </div>
                    <!-- name and surname -->
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="inputName2">Name</label>
                            <input type="text" name="name" class="form-control" id="inputName2" placeholder="Name">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="inputSurname2">Surname</label>
                            <input type="text" name="surname" class="form-control" id="inputSurname2" placeholder="Surname">
                        </div>
                    </div>
                    <!-- date of birth and password confirmation -->
                    <div class="form-row">
                    	<div class="form-group col-md-6">
                    		<label for="date">Birthday</label>
                    		<input class="form-control" id="date" name="birthday" placeholder="mm/dd/yyyy" type="text"/>
                    	</div>
                    	<div class="form-group col-md-6">
                    		<label for="password-confirm">Confirm Password</label>
                    		<input type="password" name="passwordconf" class="form-control" id="password-confirm" placeholder="Confirm Password">
                    	</div>
                    </div>
                    <hr>
                    <button type="submit" name="signin" class="btn btn-primary">Sign in</button>
                </form>
            </div>

            <!-- Page description -->
            <div class="description">
                <h2>plan your trip, share it with the community and you are ready to go.</h2>
            </div>
        </div>
    </div>
</body>
</html>