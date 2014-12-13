package betting.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import betting.helper.LogFile;
import betting.helper.Misc;
import betting.helper.SQLHelper;
import betting.models.BettingSystem;
import betting.models.ConnectionPool;
import betting.models.User;

/**
 * Servlet implementation class UserRegistration
 */
public class UserRegistration extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	//holds the errors encountered during user registration
	private JSONObject error = null;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processRequest(request, response);
	}
	
	protected void  processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		//get new connection
		Connection con = ConnectionPool.getConnection();
		
		//setting the response content to json object
		response.setContentType("application/json");
		
		//getting the writer for writing the response
		PrintWriter writer = response.getWriter();
		
		error = new JSONObject();
		
		User user = null;
		boolean success = true;
		
		try
		{
			user = new User();
			
			//checking for invalid date formats
			Date dateOfBirth = parseDate(request.getParameter("dob"), "dob");
			Date expiryDate = parseDate(request.getParameter("expiry-date"), "expiry-date");
			
			user.setUsername(request.getParameter("username"));
			
			//checking if username already exists
			if (BettingSystem.userExists(con, user.getUsername()))
			{
				success = false;
				error = Misc.addError("username", Misc.MSG_INVALID_USERNAME, error);
			}
			
			if((user.setPassword(request.getParameter("password")) == Misc.FAIL))
			{
				success = false;
				error = Misc.addError("password", Misc.MSG_INVALID_PASSWORD, error);
			}
			
			if (user.setName(request.getParameter("user-name")) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("user-name", Misc.MSG_INVALID_NAME, error);
			}
			
			if((user.setSurname(request.getParameter("surname"))) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("surname", Misc.MSG_INVALID_SURNNAME, error);
			}
			
			if((user.setAccount(request.getParameter("premium"))) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("premium", Misc.MSG_INVALID_ACCOUNT, error);
			}
			
			if((user.setCardNumber(request.getParameter("credit-card"))) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("credit-card", Misc.MSG_INVALID_CREDITCARD, error);
			}
			
			if ((user.setCvv(request.getParameter("cvv"))) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("cvv", Misc.MSG_INVALID_CVV, error);
			}

			if(expiryDate != null && (user.setExpiryDate(expiryDate)) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("expiry-date", Misc.MSG_INVALID_EXPIRYDATE, error);
			}

			if (dateOfBirth != null && (user.setDateOfBirth(dateOfBirth)) == Misc.FAIL)
			{
				success = false;
				error = Misc.addError("dob", Misc.MSG_INVALID_DOB, error);
			}
			
			// register user if data is all valid
			if (success)
				BettingSystem.addUser(con, user);
		}
		
		finally
		{
			SQLHelper.close(con);
		}
		
		if(success)
		{
			writer.write(Misc.MSG_VALID_REGISTRATION);
		}
		
		else
		{
			writer.write(error.toString());
		}
		
		writer.flush();
	}
	
	/**
	 * Parse string to date
	 * @param stringDate the string to be converted to date
	 * @param parameterName the name of the request parameter
	 * @return valid date or null if the date is in invalid format
	 */
	public Date parseDate(String stringDate, String parameterName)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try
		{
			return formatter.parse(stringDate);
		}
		
		catch (ParseException e)
		{
			LogFile.logError("UserRegistration.parseDate() - Invalid " + parameterName + " Format: " + e.getMessage());
			error = Misc.addError(parameterName, "Invalid " + parameterName + " Format", error);
			return null;
		}
	}
}
