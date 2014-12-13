package betting.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import betting.helper.BettingException;
import betting.helper.LogFile;
import betting.helper.Misc;
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
	private JSONObject error = null;
	
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
		error = new JSONObject();
		boolean errorFlag = false;
		
		Connection con = null;
		BettingSystem bettingSystem = BettingSystem.getInstance();
		
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		
		User user = (User)request.getSession().getAttribute("loggedUser");
		
		int level  = parseBetAttribute(request.getParameter("level"), "level");
		int amount = parseBetAttribute(request.getParameter("amount"), "amount");
		
		if(level != Misc.FAIL && amount != Misc.FAIL)
		{
			try
			{
				con = ConnectionPool.getConnection();
				Bet bet = new Bet(user, level, amount);
				bettingSystem.placeBet(con, bet);
			}
			
			catch (BettingException e)
			{
				LogFile.logError("ManageBets.processRequest() - " + e.getMessage());
				errorFlag = true;
				error = Misc.addError("bet", e.getMessage(), error);
			}
			
			finally
			{
				SQLHelper.close(con);
				if(errorFlag)
				{
					writer.write(error.toString());
					writer.flush();
				}
				else
				{
					writer.write(Misc.MSG_VALID_BET);
					writer.flush();
				}
			}
		}
		else
		{
			writer.write(error.toString());
			writer.flush();
		}
	}
	
	/**
	 * Parse string to int
	 * @param attribute the string to be converted to int
	 * @param parameterName the name of the request parameter
	 * @return valid bet attribute
	 */
	public int parseBetAttribute(String attribute, String parameterName)
	{
		try
		{
			return Integer.parseInt(attribute);
		}
		
		catch (NumberFormatException e)
		{
			LogFile.logError("ManageBets.parseBetAttribute(" + attribute + ") - Invalid " + parameterName + " Format: " + e.getMessage());
			error = Misc.addError(parameterName, "Invalid " + parameterName, error);
			return Misc.FAIL;
		}
	}
}
