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
		loginMessage = loggedUser.getLoginType().toString();
		dateOfBirth = loggedUser.getDateOfBirth().toString();
		expiryDate = loggedUser.getExpiryDate().toString();

		Connection con = ConnectionPool.getConnection();
		BettingSystem bettingSystem = BettingSystem.getInstance();
		userBets = bettingSystem.getBets(con, loggedUser);

	}
	
	if(!loginMessage.equals(Login.LOGIN_SUCCESS.toString()))
		loggedUser = new User();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
		<link rel="stylesheet" href="css/style.css">
		<script src="scripts/jquery-2.1.1.js"></script>
		<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Betting System</title>
	</head>
	<body>
		<h1 style="font-family:helvetica">Welcome to Speed Bet*</h1>
		
		<div id="tabs">
			<ul>
				<li><a href="#login-tab">Login</a></li>
				<li><a href="#user-details-tab">Add/Edit User Details</a></li>
				<li><a href="#bets-tab">Bets</a></li>
				<li><a href="#logout-tab">Logout</a></li>
			</ul>
			<div id="login-tab">
				<form id="login" action="UserLogin?action=login" method="post">
					<label>Username:</label>
					<input class="fields" type="text" name="username" value="<%=loggedUser.getUsername()%>"/>
					<span><%=loginMessage%></span>
					<br/>
					<label>Password:</label>
					<input class="fields" type="password" name="password" value="<%=loggedUser.getPassword()%>"/>
					<div style="width:100%; text-align:center">
						<input type="submit" value="Login">
					</div>
				</form>
			</div>
			<div id="user-details-tab">
				<form id="user-submission" action="UserSubmission" method="post">
					<label>Username:</label>
					<input class="fields" type="text" name="username" value="<%=loggedUser.getUsername()%>"/>
					<br/>
					<label>Password:</label>
					<input class="fields" type="password" name="password" value="<%=loggedUser.getPassword()%>"/>
					<br/>
					<label>Name: </label>
					<input class="fields" type="text" name="user-name" value="<%=loggedUser.getName()%>"/>
					<br/>
					<label>Surname:</label>
					<input class="fields" type="text" name="surname" value="<%=loggedUser.getSurname()%>"/>
					<br/>
					<label>Date of Birth:</label>
					<input class="fields" type="text" name="dob" value="<%=dateOfBirth%>"/>
					<br/>
					<label>Premium:</label>
					
					<select name="premium">
						<option value="<%=User.ACCOUNT_PREMIUM%>">Premium</option>
						<option value="<%=User.ACCOUNT_FREE%>">Free</option>
					</select>
					<br/>
					<label>Credit Card:</label>
					<input class="fields" type="text" name="credit-card" value="<%=loggedUser.getCardNumber()%>"/>
					<br/>
					<label>Expiry Date:</label>
					<input class="fields" type="text" name="expiry-date" value="<%=expiryDate%>"/>
					<br/>
					<label>CVV:</label>
					<input class="fields" type="text" name="cvv" value="<%=loggedUser.getCvv()%>"/>
					<br/>
					<div style="width:100%; text-align:center">
						<input type="submit" value="Submit Details">
					</div>
				</form>
			</div>
			<div id="bets-tab">
				<div style="width:100%">
					<form id="bets" action="ManageBets" method="post">
						<label>Amount:</label>
						<input class="fields" type="text" style="width:35px" name="amount"/>
						<label> |  Risk Level:</label>
						<select name="level">
							<option value="<%=Bet.LOW%>">Low</option>
							<option value="<%=Bet.MEDIUM%>">Medium</option>
							<option value="<%=Bet.HIGH%>">High</option>
						</select>
						<input type="submit" value="Place Bet">
					</form>
				</div>
				<hr>
				<div>
					<table id="user-bets">
					<%
						if(userBets != null)
						{%>
							<tr>
								<td>Risk Level</td>
								<td>Amount</td>
							</tr>
							<%
								for(int i = 0; i < userBets.size(); i++)
								{%>
									<tr>
										<td><%=userBets.get(i).getRiskLevelDescription()%></td>
										<td><%=userBets.get(i).getAmount()%></td>
									</tr>
								<%}
						}
					%>
					</table>
				</div>
			</div>
			<div id="logout-tab">
			<label>Are you sure you want to logout? </label>
				<form id="logout" action="UserLogin?action=logout" method="post">
					<input id="logout-btn" type="submit" value="Yes"/>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript">
		$('#tabs').tabs();
		$("#tabs").tabs('option', 'active', <%=action%>);

	</script>
</html>
