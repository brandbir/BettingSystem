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
    
    <!-- jQuery -->
    <script src="js/jquery-2.1.1.js"></script>
    
    
    <!-- Bootstrap Core JavaScript Placed here inkella ma jahdimx habba ordering!!-->
    <script src="js/bootstrap.min.js"></script>
    
    
    
    
</head>

<body>

    <!-- Navigation -->
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <a class="navbar-brand">Speed * Bet</a>
            </div>
            <form id="logout" name="menuForm" action="UserLogin?action=logout" method="post" class="navbar-form navbar-right" role="search" >
                    <a class="btn btn-default" data-target="#userDetailsModal" data-toggle="modal" href="#">Details</a>
                    <a class="btn btn-default" data-target="#tableModal" data-toggle="modal" href="#">Placed Bets</a>
                    <input type="submit" class="btn btn-default" value="Logout">
            </form>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </div>


    <div class="jumbotron masthead" style=" height:800px;">
	    <div class="container">
	    <br>
	    <br>
	    <br>
	    <br>
	    <br>
	    <br>
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
    </div>
       

<div class="modal fade" id="betModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalLabel">Betting</h4>
              </div>
              <div class="modal-body">
                <form role="form" action="ManageBets" method="post">
                <div class="form-group">
                   <label for="username" class="control-label">Amount:</label>
                   <input type="text" class="form-control" id="amount" placeholder="Amount in Euros" name="amount"/>
                 </div>
                  <label>Risk Level: </label>
                      <select name="level" class="form-control">
                        <option value="<%=Bet.LOW%>">Low</option>
                        <option value="<%=Bet.MEDIUM%>">Medium</option>
                        <option value="<%=Bet.HIGH%>">High</option>
                      </select>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-primary" value="Place Bet">
                      </div>
                </form>
              </div>
              
            </div>
          </div>
</div>

<div class="modal fade" id="userDetailsModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalLabel">Betting</h4>
              </div>
              <div class="modal-body">
                <form role="form" action="ManageBets" method="post">
                      <div class="form-group">
	                   <label for="username" class="control-label">Username:</label>
	                   <input type="text" class="form-control" id="username" name="username" value="<%=loggedUser.getUsername()%>"/>
	                 </div>
	                 <div class="form-group">
	                   <label for="Password" class="control-label">Password:</label>
	                   <input type="password" class="form-control" id="Password" name="password" value="<%=loggedUser.getPassword()%>"/>
	                 </div>
	                 <div class="form-group">
	                   <label for="name" class="control-label">Name:</label>
	                   <input type="text" class="form-control" id="name" name="user-name"  value="<%=loggedUser.getName()%>"/>
	                 </div>
	                 <div class="form-group">
	                   <label for="surname" class="control-label">Surname:</label>
	                   <input type="text" class="form-control" id="surnname" name="surname" value="<%=loggedUser.getSurname()%>"/>
	                 </div>
	                 <div class="form-group">
	                   <label for="DOB" class="control-label">Date of Birth:</label>
	                   <input type="text" id="DOB" class="form-control" value="yyyy-mm-dd" name="dob" value="<%=dateOfBirth%>">
	                 </div>
	                 <div class="form-group">
	                   <label for="credNo" class="control-label">Credit Card Number:</label>
	                   <input type="text" class="form-control" id="credNo" name="credit-card" value="<%=loggedUser.getCardNumber()%>"/>
	                 </div>
	                  <div class="form-group">
	                   <label for="credExpiry" class="control-label">Credit Card Expiry Date:</label>
	                   <input type="text" id="expiry-date" class="form-control" value="yyyy-mm-dd" name="expiry-date" value="<%=expiryDate%>">
	                 </div>
	                 <div class="form-group">
	                   <label for="credCVV" class="control-label">Credit Card CVV:</label>
	                   <input type="text" class="form-control" id="credCVV" name="cvv" value="<%=loggedUser.getCvv()%>"/>
	                 </div>
	                 <br>
	                 <div class="form-group">
                       <label for="accountDescr" class="control-label">Account:</label>
                       <input type="text" class="form-control" id="accountDescr" name="accountDescr" value="<%=loggedUser.getAccountDescription()%>"/>
                     </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                      </div>
                </form>
              </div>
              
            </div>
          </div>
</div>


<div class="modal fade" id="betModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="modalLabel">Betting</h4>
              </div>
              <div class="modal-body">
                <form role="form" action="ManageBets" method="post">
                <div class="form-group">
                   <label for="username" class="control-label">Amount:</label>
                   <input type="text" class="form-control" id="amount" placeholder="Amount in Euros" name="amount"/>
                 </div>
                  <label>Risk Level: </label>
                      <select name="level" class="form-control">
				        <option value="<%=Bet.LOW%>">Low</option>
                        <option value="<%=Bet.MEDIUM%>">Medium</option>
                        <option value="<%=Bet.HIGH%>">High</option>
				      </select>
				      <div class="modal-footer">
		                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		                <input type="submit" class="btn btn-primary" value="Place Bet">
		              </div>
                </form>
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
                                              <th>Risk Level</th>
                                              <th>Amount</th>
                                              <th>Type</th>
                                              <th>Date</th>
                                           </tr>
                                       </thead>
		                            <tbody>
		                            <%
		                                for(int i = 0; i < userBets.size(); i++)
		                                {%>
		                                    <tr class="active">
		                                        <td><%=i+1%></td>
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
