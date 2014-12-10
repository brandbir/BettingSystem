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
    <link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

    <!-- Custom CSS -->
    <link href="css/full-slider.css" rel="stylesheet">
    <link type="text/css" href="css/styling.css" rel="stylesheet">
<!--     <link type="text/css" href="css/docs.css" rel="stylesheet"> -->
    <link type="text/css" href="css/slider.css" rel="stylesheet">
    
    <!-- jQuery -->
    <script src="js/jquery.js"></script>
      
    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-datepicker.js"></script>
    <script src="js/bootstrap-slider.js"></script>
     <script src="js/functionality.js"></script>
    
</head>

<body>

    <!-- Navigation -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand">Speed * Bet</a>
            </div>
            <form name="loginForm" action="indexMain.jsp" class="navbar-form navbar-right" role="search" >
                    <a class="btn btn-default" data-target="#tableModal" data-toggle="modal" href="#">Placed Bets</a>
                    <button type="submit" class="btn btn-default">Log Out</button>
            </form>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </div>


    <div class="jumbotron masthead" style=" height:800px;">
	    <div class="container">
	    <h3>Which game do you want to play?!</h3>
	    <br>
	    <br>
	       <div class="row">
	                <div class="col-md-12" style=" height:350px;">
	                    <div class="row">
	                        <div class="col-md-3">
	                          <div class="thumbnail">
	                            <img src="images/F1Logo.jpg" style="width:150px;height:150px;"  alt="...">
	                            <div class="caption">
	                              <h2>Formula 1</h2>
	                              <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#betModal" data-whatever="@mdo">Bet Now!</button></p>
	                            </div>
	                          </div>
	                        </div>
	                        <div class="col-md-3">
	                          <div class="thumbnail">
	                            <img src="images/horseRacingLogo.jpg" style="width:150px;height:150px;"  alt="...">
	                            <div class="caption">
	                              <h2>Horse Racing</h2>
	                              <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#betModal" data-whatever="@mdo">Bet Now!</button></p>
	                            </div>
	                          </div>
	                        </div>
	                        <div class="col-md-3">
	                          <div class="thumbnail">
	                            <img src="images/BarclaysLogo.gif" style="width:150px;height:150px;"  alt="...">
	                            <div class="caption">
	                               <h2>Premier League</h2>
	                              <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#betModal" data-whatever="@mdo">Bet Now!</button></p>
	                            </div>
	                          </div>
	                        </div>                     
	                        <div class="col-md-3">
	                          <div class="thumbnail">
	                            <img src="images/SerieALogo.jpeg" style="width:150px;height:150px;"  alt="...">
	                            <div class="caption">
	                              <h2>Serie A</h2>
	                              <p><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#betModal" data-whatever="@mdo">Bet Now!</button></p>
	                            </div>
	                          </div>
	                        </div>
	                  </div>
                </div>
         </div>

       </div>
       
<!--        <div class="price-box"> -->

<!--         <form class="form-horizontal form-pricing" role="form"> -->

<!--           <div class="price-slider"> -->
<!--             <h4 class="great">Amount</h4> -->
<!--             <span>Minimum $10 is required</span> -->
<!--             <div class="col-sm-12"> -->
<!--               <div id="slider"></div> -->
<!--             </div> -->
<!--           </div> -->
<!--           <div class="price-slider"> -->
<!--             <h4 class="great">Duration</h4> -->
<!--             <span>Minimum 1 day is required</span> -->
<!--             <div class="col-sm-12"> -->
<!--               <div id="slider2"></div> -->
<!--             </div> -->
<!--           </div> -->

<!--           <div class="price-form"> -->

<!--             <div class="form-group"> -->
<!--               <label for="amount" class="col-sm-6 control-label">Amount ($): </label> -->
<!--               <span class="help-text">Please choose your amount</span> -->
<!--               <div class="col-sm-6"> -->
<!--                 <input type="hidden" id="amount" class="form-control"> -->
<!--                 <p class="price lead" id="amount-label"></p> -->
<!--                 <span class="price">.00</span> -->
<!--               </div> -->
<!--             </div> -->
<!--             <div class="form-group"> -->
<!--               <label for="duration" class="col-sm-6 control-label">Duration: </label> -->
<!--               <span class="help-text">Choose your commitment</span> -->
<!--               <div class="col-sm-6"> -->
<!--                 <input type="hidden" id="duration" class="form-control"> -->
<!--                 <p class="price lead" id="duration-label"></p> -->
<!--                 <span class="price">days</span> -->
<!--               </div> -->
<!--             </div> -->
<!--             <hr class="style"> -->
<!--             <div class="form-group total"> -->
<!--               <label for="total" class="col-sm-6 control-label"><strong>Total: </strong></label> -->
<!--               <span class="help-text">(Amount * Days)</span> -->
<!--               <div class="col-sm-6"> -->
<!--                 <input type="hidden" id="total" class="form-control"> -->
<!--                 <p class="price lead" id="total-label"></p> -->
<!--                 <span class="price">.00</span> -->
<!--               </div> -->
<!--             </div> -->

<!--           </div> -->

<!--           <div class="form-group"> -->
<!--             <div class="col-sm-12"> -->
<!--               <button type="submit" class="btn btn-primary btn-lg btn-block">Proceed <span class="glyphicon glyphicon-chevron-right pull-right" style="padding-right: 10px;"></span></button> -->
<!--             </div> -->
<!--           </div> -->
<!--           <div class="form-group"> -->
<!--             <div class="col-sm-12"> -->
<!--               <img src="http://amirolahmad.github.io/bootstrap-pricing-slider/images/payment.png" class="img-responsive payment" /> -->
<!--             </div> -->
<!--           </div> -->

<!--         </form> -->

<!--         <p class="text-center" style="padding-top:10px;font-size:12px;color:#2c3e50;font-style:italic;">Created by <a href="https://twitter.com/AmirolAhmad" target="_blank">AmirolAhmad</a></p> -->

<!--       </div> -->
      
</div>


<div class="modal fade" id="betModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalLabel">Betting</h4>
              </div>
              <div class="modal-body">
                <form role="form">
                  <label>Risk Level: </label>
                      <select class="form-control">
				        <option value="<%=Bet.LOW%>">Low</option>
                        <option value="<%=Bet.MEDIUM%>">Medium</option>
                        <option value="<%=Bet.HIGH%>">High</option>
				      </select>
                </form>
              </div>
              
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Place Bet</button>
              </div>
              
            </div>
          </div>
</div>

<div class="modal fade" id="tableModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalLabel">Placed Bets</h4>
              </div>
              <div class="modal-body">
<!--                 <div class="row"> -->
		            <div class="table-responsive table-hover">
		                    <table class="table" id="user-bets">
		                    <%
		                        if(userBets != null)
		                        {%>
		                            <thead>
                                           <tr>
                                              <th>#</th>
                                              <th>Type</th>
                                              <th>Risk Level</th>
                                              <th>Amount</th>
                                              <th>Date</th>
                                           </tr>
                                       </thead>
		                            <tbody>
		                            <%
		                                for(int i = 0; i < userBets.size(); i++)
		                                {%>
		                                    <tr class="active">
		                                        <td><%=i%></td>
		                                        <td><%=userBets.get(i).getRiskLevelDescription()%></td>
		                                        <td><%=userBets.get(i).getAmount()%></td>
		                                    </tr>
		                                <%}%>
		                                </tbody>
		                                <% 
		                        }
		                    %>
<!-- 				                        <thead> -->
<!-- 				                            <tr> -->
<!-- 				                               <th>#</th> -->
<!-- 				                               <th>Type</th> -->
<!-- 				                               <th>Risk Level</th> -->
<!-- 				                               <th>Amount</th> -->
<!-- 				                               <th>Date</th> -->
<!-- 				                            </tr> -->
<!-- 				                        </thead> -->
<!-- 				                        <tbody> -->
<!-- 				                          <tr class="active"> -->
<!-- 				                            <td>1</td> -->
<!-- 				                            <td>Horse Racing</td> -->
<!-- 				                            <td>Medium</td> -->
<!-- 				                            <td>$50</td> -->
<!-- 				                            <td>7/12/14</td> -->
<!-- 				                          </tr> -->
<!-- 				                        </tbody> -->
		                        
		                      </table>
		                </div>
<!-- 		            </div> -->
              </div>
              
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
              
            </div>
          </div>
</div>
 
   
    

</body>

</html>
