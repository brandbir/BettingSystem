<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="betting.models.User"
  %>
<%
String loginMessage = "";
User loggedUser = (User)session.getAttribute("loggedUser");
if(loggedUser != null)
{
   loginMessage = loggedUser.getLoginType().toString(); 
}

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
    
    <!-- jQuery -->
    <script src="js/jquery-2.1.1.js"></script>
    
    
    <!-- Bootstrap Core JavaScript Placed here inkella ma jahdimx habba ordering!!-->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/functionality.js"></script>
    
    <!-- JQuery-UI -->
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  <script>
  $(function() {
    $( "#DOB" ).datepicker({dateFormat:'yy-mm-dd'});
    $("#expiry-date").datepicker({dateFormat:'yy-mm-dd'});
  });
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
                <form id="login" action="UserLogin?action=login" method="post" class="navbar-form navbar-right" role="search" onsubmit="return(validateLogin());">
                     <div class="form-group">
                         <input type="text" class="form-control" name="username" placeholder="Username">
                     </div>
                     <div class="form-group">
                         <input type="password" class="form-control" name="password" placeholder="Password">
                     </div>
                     <input type="submit" class="btn btn-default" value="Login">
                     <div class="alert alert-danger"><%=loginMessage%></div>
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
                   <input type="text" class="form-control" id="username" placeholder="Username" name="username"/>
                 </div>
                 <div class="form-group">
                   <label for="Password" class="control-label">Password:</label>
                   <input type="password" class="form-control" id="Password" placeholder="Password" name="password"/>
                 </div>
                 <div class="form-group">
                   <label for="name" class="control-label">Name:</label>
                   <input type="text" class="form-control" id="name" placeholder="name" name="user-name" />
                 </div>
                 <div class="form-group">
                   <label for="surname" class="control-label">Surname:</label>
                   <input type="text" class="form-control" id="surnname" placeholder="surname" name="surname"/>
                 </div>
                 <div class="form-group">
                   <label for="DOB" class="control-label">Date of Birth:</label>
                   <input type="text" id="DOB" class="form-control" value="yyyy-mm-dd" name="dob">
                 </div>
                 <div class="form-group">
                   <label for="credNo" class="control-label">Credit Card Number:</label>
                   <input type="text" class="form-control" id="credNo" placeholder="Credit Card Number" name="credit-card"/>
                 </div>
                  <div class="form-group">
                   <label for="credExpiry" class="control-label">Credit Card Expiry Date:</label>
                   <input type="text" id="expiry-date" class="form-control" value="yyyy-mm-dd" name="expiry-date">
                 </div>
                 <div class="form-group">
                   <label for="credCVV" class="control-label">Credit Card CVV:</label>
                   <input type="text" class="form-control" id="credCVV" placeholder="Credit Card Expiry Date" name="cvv"/>
                 </div>
                 <br>
                 <div class="btn-group" data-toggle="buttons">
<!--                      <label class="btn btn-primary active"> -->
<!--                        <input type="radio" name="premium" id="option1" autocomplete="off"   checked> Free -->
<!--                      </label> -->
<!--                      <label class="btn btn-primary"> -->
<!--                        <input type="radio" name="premium" id="option2" autocomplete="off" > Premium -->
<!--                      </label> -->
					<select name="premium">
					    <option value="<%=1%>">Premium</option>
                        <option value="<%=0%>">Free</option>
					  </select>
                 </div>
                 <div class="modal-footer">
	               <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	               <input type="submit" class="btn btn-default" value="Submit Details">
	             </div>
               </form>
             </div>
             
           </div>
         </div>
          
       </div>

    </header>

</body>

</html>
