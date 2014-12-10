<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.ArrayList,
            java.sql.Connection,
            betting.models.User,
            betting.models.BettingSystem,
            betting.models.ConnectionPool,
            betting.models.Login,
            betting.models.Bet"%>
 <%         
    String loginMessage = "";
    String dateOfBirth = "";
    String expiryDate = "";
    ArrayList<Bet> userBets = null;
    
    User loggedUser = (User)session.getAttribute("loggedUser");
    String action = request.getParameter("action");
    
    if(action == null)
        action = "0";
    
    if(loggedUser != null)
    {
        loginMessage = loggedUser.getLoginType().toString(); //LoginType -> if user is locked or unlocked
        dateOfBirth = loggedUser.getDateOfBirth().toString();
        expiryDate = loggedUser.getExpiryDate().toString();

        Connection con = ConnectionPool.getConnection();
        BettingSystem bettingSystem = BettingSystem.getInstance();
        userBets = bettingSystem.getBets(con, loggedUser);
        

    }
    
    if(!loginMessage.equals(Login.LOGIN_SUCCESS.toString())) //EXPLAIN....
        loggedUser = new User();
%>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Speed * Bet - Play the Game!</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/full-slider.css" rel="stylesheet">
    <link type="text/css" href="css/styling.css" rel="stylesheet">
    <link type="text/css" href="css/datepicker.css" rel="stylesheet">
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"> </script>
</script>
    
</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="indexMain.jsp">Speed * Bet</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
<!--                     <li> -->
<!--                         <a href="#">About</a> -->
<!--                     </li> -->
                </ul>
                <form name="loginForm" action="usersection.jsp" class="navbar-form navbar-right" role="search" onsubmit="return(validateLogin());">
	                 <div class="form-group">
	                     <input type="text" class="form-control" name="username" placeholder="Username">
	                 </div>
	                 <div class="form-group">
	                     <input type="password" class="form-control" name="password" placeholder="Password">
	                 </div>
	                 <button type="submit" class="btn btn-default" >Log In</button>
                </form>
            </div>
            
            
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Full Page Image Background Carousel Header -->
    <header id="myCarousel" class="carousel slide" data-interval="2000" >
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
            <li data-target="#myCarousel" data-slide-to="3"></li>
            <li data-target="#myCarousel" data-slide-to="4"></li>
        </ol>

        <!-- Wrapper for Slides -->
        <div class="carousel-inner">
            <div class="item active">
                <!-- Set the first background image using inline CSS below. -->
                <div class="fill" style="background-image: url('images/Formula1.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Formula 1</h2>
                    <p>Create a new account with a click of a button! </p>
                    <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
                </div>
            </div>
            <div class="item">
                <!-- Set the second background image using inline CSS below. -->
                <div class="fill" style="background-image:url('images/horseBetting.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Horse Racing</h2>
                    <p>Create a new account with a click of a button! </p>
                    <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
                </div>
            </div>
            <div class="item">
                <!-- Set the third background image using inline CSS below. -->
                <div class="fill" style="background-image:url('images/BARCLAYS.jpg');"></div>
                <div class="carousel-caption">
                    <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
                </div>
            </div>
            <div class="item">
                <!-- Set the third background image using inline CSS below. -->
                <div class="fill" style="background-image:url('images/SerieA.jpg');"></div>
                <div class="carousel-caption">
                    <h2>Serie A</h2>
                    <p>Create a new account with a click of a button! </p>
                    <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
                </div>
            </div>
            <div class="item">
                <!-- Set the third background image using inline CSS below. -->
                <div class="fill" style="background-image:url('images/ChampionsLeague.jpg');"></div>
                <div class="carousel-caption">
                    <p>Create a new account with a click of a button! </p>
                    <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
        
        
		<div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		        <h4 class="modal-title" id="modalLabel">Sign Up!</h4>
		      </div>
		      <div class="modal-body">
		        <form id="user-submission" action="UserSubmission" role="form" method="post">
		          <div class="form-group">
		            <label for="username" class="control-label">Username:</label>
		            <input type="text" class="form-control" id="username" placeholder="Username" value="<%=loggedUser.getUsername()%>"/>
		          </div>
		          <div class="form-group">
		            <label for="Password" class="control-label">Password:</label>
		            <input type="password" class="form-control" id="Password" placeholder="Password" value="<%=loggedUser.getPassword()%>"/>
		          </div>
		          <div class="form-group">
		            <label for="name" class="control-label">Name:</label>
		            <input type="text" class="form-control" id="name" placeholder="name" value="<%=loggedUser.getName()%>"/>
		          </div>
		          <div class="form-group">
		            <label for="surname" class="control-label">Surname:</label>
		            <input type="text" class="form-control" id="surnname" placeholder="surname" value="<%=loggedUser.getSurname()%>"/>
		          </div>
		          <div class="form-group">
		            <label for="DOB" class="control-label">Date of Birth:</label>
		            <input type="date" class="form-control datepicker" id="DOB" placeholder="Date of Birth" value="12-02-2012" value="<%=dateOfBirth%>"/> 
		          </div>
		          <div class="form-group">
		            <label for="credNo" class="control-label">Credit Card Number:</label>
		            <input type="text" class="form-control" id="credNo" placeholder="Credit Card Number" value="<%=loggedUser.getCardNumber()%>"/>
		          </div>
		           <div class="form-group">
		            <label for="credExpiry" class="control-label">Credit Card Expiry Date:</label>
		            <input type="date" class="form-control" id="credExpiry" placeholder="Credit Card Expiry Date" value="<%=expiryDate%>"/>
		          </div>
		          <div class="form-group">
		            <label for="credCVV" class="control-label">Credit Card CVV:</label>
		            <input type="text" class="form-control" id="credCVV" placeholder="Credit Card Expiry Date" value="<%=loggedUser.getCvv()%>"/>
		          </div>
		          <br>
		          <div class="btn-group" data-toggle="buttons">
		              <label class="btn btn-primary active">
		                <input type="radio" name="options" id="option1" autocomplete="off"  value="<%=User.ACCOUNT_FREE%>" checked> Free
		              </label>
		              <label class="btn btn-primary">
		                <input type="radio" name="options" id="option2" autocomplete="off" value="<%=User.ACCOUNT_PREMIUM%>"> Premium
		              </label>
		          </div>
		        </form>
		      </div>
		        
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Submit</button>
		      </div>
		    </div>
		  </div>
		  
		</div>

    </header>

    <!-- jQuery -->
    <script src="js/jquery-2.1.1.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
     <script src="js/functionality.js"></script>

</body>

</html>
