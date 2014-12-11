package betting.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		Connection con = ConnectionPool.getConnection();
		
		try
		{
			User user = new User();
			user.setUsername(request.getParameter("username"));
			user.setPassword(request.getParameter("password"));
			user.setName(request.getParameter("user-name"));
			user.setSurname(request.getParameter("surname"));
			
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date dateOfBirth = formatter.parse(request.getParameter("dob"));
			user.setDateOfBirth(dateOfBirth);
			
			user.setAccount(Integer.parseInt(request.getParameter("premium")));
			user.setCardNumber(request.getParameter("credit-card"));
			
			Date expiryDate = formatter.parse(request.getParameter("expiry-date"));
			user.setExpiryDate(expiryDate);
			
			user.setCvv(request.getParameter("cvv"));
			BettingSystem.addUser(con, user);
		}
		catch (Exception e)
		{
			System.out.println("[UserRegistration.processRequest()] - " + e.getMessage());
			response.sendError(500);
			return;
		}
		finally
		{
			SQLHelper.close(con);
		}
		
		response.sendRedirect("indexMain.jsp");
	}
}
