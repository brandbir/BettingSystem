package betting.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import betting.helper.SQLHelper;
import betting.models.Bet;
import betting.models.BettingSystem;
import betting.models.ConnectionPool;
import betting.models.User;

/**
 * Servlet implementation class ManageBets
 */
public class ManageBets extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Connection con = ConnectionPool.getConnection();
		BettingSystem bettingSystem = BettingSystem.getInstance();
		
		User user = (User)request.getSession().getAttribute("loggedUser");
		
		int level	= Integer.parseInt(request.getParameter("level"));
		int amount	= Integer.parseInt(request.getParameter("amount"));
		
		try
		{
			Bet bet = new Bet(user, level, amount);
			bettingSystem.placeBet(con, bet);
			response.sendRedirect("index.jsp?action=2");
		}
		catch(Exception e)
		{
			System.out.println("ManageBets.processRequest() - " + e.getMessage());
			response.sendRedirect("usersection.jsp");
		}
		finally
		{
			SQLHelper.close(con);
		}
	}
}
