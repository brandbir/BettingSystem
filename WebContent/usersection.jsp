<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.ArrayList,
    		java.util.Date,
            java.sql.Connection,
            betting.models.User,
            betting.models.BettingSystem,
            betting.models.ConnectionPool,
            betting.models.Login,
            betting.models.Bet,
            betting.helper.Misc,
            betting.helper.SQLHelper"
            %>
 <%         
    String loginMessage = "";
    String dateOfBirth = "";
    String expiryDate = "";
    ArrayList<Bet> userBets = null;
    Bet lastBet = null;
    BettingSystem bettingSystem = null;
    
    
    User loggedUser = (User)session.getAttribute("loggedUser");
    if(loggedUser == null)
    {
    	response.sendRedirect("index.jsp");
    	return;
    }
    String action = request.getParameter("action");
    
    if(action == null)
        action = "0";
    
    if(loggedUser != null)
    {
    	//LoginType -> if user is locked or unlocked
        loginMessage = loggedUser.getLoginType().toString(); 
        dateOfBirth = loggedUser.getDateOfBirth().toString();
        
        //There will be cases that the expiry data of a particular
        //user is null because it will be expired
        Date date = loggedUser.getExpiryDate();
        if(date != null)
        	expiryDate = loggedUser.getExpiryDate().toString();
        
        else
        	expiryDate = "Expired Date";

        Connection con = ConnectionPool.getConnection();
        bettingSystem = BettingSystem.getInstance();
        userBets = bettingSystem.getBets(con, loggedUser);
		SQLHelper.close(con);

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
    
    <!-- Custom JS -->
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
            <form id="logout" name="menuForm" action="UserLogin?action=logout" method="post" class="navbar-form navbar-right" role="search" >
                    <a class="btn btn-default" data-target="#userDetailsModal" data-toggle="modal" href="#" >Details</a>
                    <a class="btn btn-default" data-target="#tableModal" data-toggle="modal" href="#">Placed Bets</a>
                    <input id="logoutButton" type="submit" class="btn btn-default" value="Logout">
            </form>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </div>


    <div class="jumbotron masthead" style="height:100%;">
        <div class="container">
        <br>
        <br>
        <br>
        <br>
        <br>
           <div class="row">
                    <div class="col-md-12" style=" height:350px;">
                        <div class="row">
                            <div class="col-md-6">
                              <div class="thumbnail">
                                <img src="images/allinOne.png" style="width:250px;height:250px;"  alt="...">
                                <div class="caption">
                                    <div class="form-group">
                                    <br>
                                       <h3><label for="username" class="control-label">Amount:</label></h3>
                                       <input type="text" class="form-control" id="amount" placeholder="Amount in Euros" name="amount"/>
                                     </div>
                                      <h3><label>Risk Level: </label></h3>
                                          <select id="riskLevel" name="level" class="form-control">
                                            <option value="<%=Bet.LOW%>">Low</option>
                                            <option value="<%=Bet.MEDIUM%>">Medium</option>
                                            <option value="<%=Bet.HIGH%>">High</option>
                                          </select>
                                          <div class="modal-footer">
                                          <br>
                                          </div>
                                           <p><button id="betSubmitButton" class="btn btn-primary" >Place Bet</button></p>
                                           <script>
                                               $('#betSubmitButton').click(function testingfn(){
                                               
                                               var level = $('#riskLevel').val();
                                               var amount = $('#amount').val();
                                               var urlString = 'ManageBets?level='+level+'&amount='+amount;
                                               
                                              $.ajax({
                                                   url: urlString, //sending url string with parameters....sending request
                                                   cache: false,
                                                   dataType: 'application/json',
                                                   complete: function(response) //obtaining response
                                                   {
                                                	   var x = response.responseText;
                                                	   if(x == "<%=Misc.MSG_VALID_BET%>")
                                                		{
                                                			alert("<%=Misc.MSG_VALID_BET%>");
                                                     		location.reload();
                                                		}
                                                	   else
                                                	   		updatingContent(x);
                                                      
                                                   }
                                                   
                                                   });
                                              
                                               });
                                               
                                               function updatingContent(received_json)
                                               {
                                                   var json = received_json;
                                                   var json_parsed = JSON.parse(json);
                                                   $("#amount").val(json_parsed.amount).css( "color", "red" );
                                               }
                                               
                                               $( "#amount" ).keydown(function() {
                                                   $("#amount").css( "color", "black" );
                                                 });
                                               
                                               function removeTable(id)
                                               {
                                                   var tbl = document.getElementById(id);
                                                   if(tbl) tbl.parentNode.removeChild(tbl);
                                               }
                                               
                                           </script>
                                </div>
                              </div>
                            </div>
                            <div class="col-md-6">
                               <h2>Recent Bets</h2>
                                <table class="table table-bordered" id="user-bets">
                            <%
                                if(userBets != null)
                                {%>
                                    <thead>
                                           <tr>
                                              <th class="col-md-2 text-center">#</th>
                                              <th class="col-md-2 text-center">Risk Level</th>
                                              <th class="col-md-2 text-center">Amount</th>
                                           </tr>
                                       </thead>
                                    <tbody id="user-betsBody">
                                    <%
                                    	int rowCount = 0;
                                    if(!userBets.isEmpty())
                                    {
                                    	if(userBets.size() > 10)
                                    	{
                                    		for(int i = userBets.size(); i > userBets.size()-10; i--)
                                    		{%> 
                                            <tr id="user-betsRow">
                                                <td id="amount-id" class="col-md-2"><%=i-1%></td>
                                                <td id="amount-risk" class="col-md-2"><%=userBets.get(i-1).getRiskLevelDescription()%></td>
                                                <td id="amount-bet" class="col-md-2">&#8364;<%=userBets.get(i-1).getAmount()%></td>
                                            </tr>
                                          <%}
                                    	}
                                    	else
                                    	{
                                    		for(int i = userBets.size(); i > 0; i--)
                                    		{%> 
                                            <tr id="user-betsRow">
                                                <td class="col-md-2"><%=i%></td>
                                                <td class="col-md-2"><%=userBets.get(i-1).getRiskLevelDescription()%></td>
                                                <td class="col-md-2">&#8364;<%=userBets.get(i-1).getAmount()%></td>
                                            </tr>
                                    	  <%}
                                    	}
                                    		
                                    }%>
                                        </tbody>
                                <%}%>
                              </table>
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
                <form role="form" action="ManageBets" method="post" onsubmit="test();">
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
                        <input type="submit" id="betSubmitButton" class="btn btn-primary" value="Place Bet">
                      </div>
                </form>
              </div>
              
            </div>
          </div>
</div>



         


<div class="modal fade" id="userDetailsModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
<div class="row-fluid">
<!--            <div class="span2" > -->
<!--                <img src="images/userLogo.jpg" class="img-circle"> -->
<!--            </div> -->
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                        <h4 class="modal-title" id="modalLabel">User Details</h4>
                      </div>
                      <div class="modal-body">
                      <div class="span8">
                    <h3>User Name: "<%=loggedUser.getUsername()%>"</h3>
                    <h6>Name: "<%=loggedUser.getName()%>"</h6>
                    <h6>Surname: "<%=loggedUser.getSurname()%>"</h6>
                    <h6>Date of Birth: "<%=dateOfBirth%>"</h6>
                    <h6>Credit Card No.: "<%=loggedUser.getCardNumber()%>"</h6>
                    <h6>Credit Expiry Date: "<%=expiryDate%>"</h6>
                    <h6>CVV: "<%=loggedUser.getCvv()%>"</h6>
                    <h6>Amount: "<%=loggedUser.getAccountDescription()%>"</h6>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                     </div>
                </div>
        </div>
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
                                           </tr>
                                       </thead>
                                    <tbody id="betTableBody">
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
                              </table>
                        </div>
<!--                    </div> -->
              </div>
              
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              </div>
              
            </div>
          </div>
</div>
 
   
    

</body>

</html>
