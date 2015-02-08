<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="betting.models.User,
    		betting.helper.Misc"
  %>
<%
	String loginMessage = request.getParameter("message");
	
	if(loginMessage == null)
		loginMessage = "";
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
    <link href="css/bootstrap-select.css" rel="stylesheet">
    <link type="text/css" href="css/styling.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="js/jquery-2.1.1.js"></script>
    
    
    <!-- Bootstrap Core JavaScript Placed here inkella ma jahdimx habba ordering!!-->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/functionality.js"></script>
    <script src="js/bootstrap-select.js"></script>
    
    <!-- JQuery-UI -->
    <link rel="stylesheet" href="css/jquery-ui.css">
  <script src="js/jquery-1.10.2.js"></script>
  <script src="js/jquery-ui.js"></script>
  <script>
  
  function clear()
  {
	  $('#username').text('');
	  $('#password').text('');
	  $('#user-name').text('');
	  $('#surname').text('');
	  $('#dob').text('');
	  $('#credit-card').text('');
	  $('#expiry-date').text('');
	  $('#cvv').text('');
  }
  
  $(function() {
    $( "#dob" ).datepicker({dateFormat:'yy-mm-dd', defaultDate: '1980-01-01'});
    $("#expiry-date").datepicker({dateFormat:'yy-mm-dd'});
    clear();
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
                <a class="navbar-brand" href="index.jsp">Speed * Bet</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <form id="login" name="loginForm" action="UserLogin?action=login" method="post" class="navbar-form navbar-right" role="search" onsubmit="return(validateLogin());">
                     <div id="loginMessage" class="form-group" style="color:white; padding:10px">
                        <%=loginMessage%>
                     </div>
                     <div class="form-group">
                         <input id="usernameLogin" type="text" class="form-control" name="username" placeholder="Username">
                     </div>
                     <div class="form-group">
                         <input id="passwordLogin" type="password" class="form-control" name="password" placeholder="Password">
                     </div>
                     <button id="loginButton" type="submit" class="btn btn-default" >Log In</button>
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
                    <p><button id="signUpButton" type="button" class="btn btn-primary" data-toggle="modal" data-target="#signupModal" data-whatever="@mdo">Sign Up Now</button></p>
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
                 <div class="form-group">
                   <label for="username" class="control-label">Username:</label> 
                   <input type="text" class="form-control" id="username" placeholder="Username" name="username" />
                 </div>
                 <div class="form-group">
                   <label for="Password" class="control-label">Password:</label>
                   <input type="password" class="form-control" id="password" placeholder="Password" name="password" />
                 </div>
                 <div class="form-group">
                   <label for="name" class="control-label">Name:</label> 
                   <input type="text" class="form-control" id="user-name" placeholder="name" name="user-name" />
                 </div>
                 <div class="form-group">
                   <label for="surname" class="control-label">Surname:</label> 
                   <input type="text" class="form-control" id="surname" placeholder="surname" name="surname" />
                 </div>
                 <div class="form-group">
                   <label for="dob" class="control-label">Date of Birth:</label> 
                   <input type="text" id="dob" class="form-control"  placeholder="yyyy-mm-dd" name="dob" />
                 </div>
                 <div class="form-group">
                   <label for="credit-card" class="control-label">Credit Card Number:</label> 
                   <input type="text" class="form-control" id="credit-card" placeholder="Credit Card Number" name="credit-card" />
                 </div>
                  <div class="form-group">
                   <label for="credExpiry" class="control-label">Credit Card Expiry Date:</label>
                   <input type="text" id="expiry-date" class="form-control" placeholder="yyyy-mm-dd" name="expiry-date" />
                 </div>
                 <div class="form-group">
                   <label for="cvv" class="control-label">Credit Card CVV:</label> 
                   <input type="text" class="form-control" id="cvv" placeholder="Credit Card Expiry Date" name="cvv"/>
                 </div>
                 <br>
                 <div class="form-group">
                    <select id="premium" name="premium" data-style="btn-primary" style="width:100%">
                        <option value="<%=User.ACCOUNT_PREMIUM%>">Premium</option>
                        <option value="<%=User.ACCOUNT_FREE%>">Free</option>
                      </select>
                 </div>
                 <script>
                 $('.selectpicker').selectpicker();
                 </script>
                 <div class="modal-footer">
                 <div id="successfulMessage"> </div>
                   <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                   <button id="submitButton" class="btn btn-primary" >Submit</button>
                 </div>
                 
                 <script>
                 $( "#submitButton" ).click(function() {
                 var username = $('#username').val();
                 var password = $('#password').val();
                 var name = $('#user-name').val();
                 var surname = $('#surname').val();
                 var dob = $('#dob').val();
                 var creditCard = $('#credit-card').val();
                 var expiryDate = $('#expiry-date').val();
                 var cvv = $('#cvv').val();
                 var premium = $('#premium').val();
                 
                 var urlString = 'UserSubmission?username='+username+'&password='+password+'&user-name='+name+'&surname='+surname
                		 +'&dob='+dob+'&credit-card='+creditCard+'&expiry-date='+expiryDate+'&cvv='+cvv+'&premium='+premium;
                 
                $.ajax({
                     url: urlString, //sending url string with parameters....sending request
                     cache: false,
                     dataType: 'application/json',
                     complete: function(response) 
                     {
                         var x = response.responseText;
                         updatingContent(x);
                     }
                     });
                 });
                 
                 function updatingContent(received_json)
                 {
                     var json = received_json;
                     
					if(json == "<%=Misc.MSG_VALID_REGISTRATION%>")
					{
						$('#successfulMessage').css('display', '');
                		$('#successfulMessage').html("<p id='successRegistration'>Successful Registration. Please Login.</p>");
                		
                		//sending request to user login
                		$.ajax({
                            url: 'UserLogin?action=login&username=' + $('#username').val() + '&password=' + $('#password').val(),
                            cache: false,
                            complete: function(response) 
                            {
                            	console.log('access user section');
                            	location.replace("usersection.jsp");
                            }
                            
                        });
					}
					else
					{
					
                		$('#successfulMessage').css('display', 'none');

                     	var errors = JSON.parse(json);
                     	var key = "";

	                    for (key in errors)
	                    {
	                    	if (errors.hasOwnProperty(key))
	                     	{
	                    	 
	                         console.log(key + " = " + errors[key]);
	                         var id = "#" + key;
	                         $(id).val(errors[key]).css( "color", "#ff9494" ).keydown(function() {
	                             $(id).css( "color", "black" ); //TODO
	                           });
	                     	}
	                    } 
                     
                     //alert('JSON received '+ json + 'json_name : ' + json_name + 'json_value : ' + json_value);
                     
                 	}
                 }
                 
                 
                 </script>
             </div>
             
           </div>
         </div>
          
       </div>

    </header>

</body>

</html>
