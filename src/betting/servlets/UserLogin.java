package betting.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import betting.helper.SQLHelper;
import betting.models.BettingSystem;
import betting.models.ConnectionPool;
import betting.models.Login;
import betting.models.User;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet
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
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String action = request.getParameter("action");
		
		if(action.equals("logout"))
		{
			request.getSession().removeAttribute("loggedUser");
			response.sendRedirect("index.jsp");
		}
		
		else if(action.equals("login"))
		{
			BettingSystem bettingSystem = BettingSystem.getInstance();
			Connection con = ConnectionPool.getConnection();
			HttpSession userSession = request.getSession();
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//Obtaining logged user from session : user that tried to login with invalid credentials
			
			User loggingUser = (User) userSession.getAttribute(username); 

	
			loggingUser = bettingSystem.login(con, username, password, loggingUser);
			System.out.println(loggingUser.getUsername() + " Login Type:" + loggingUser.getLoginType().toString() + 
								"\nTime of invalid Login: " + loggingUser.getTimeofInvalidLogin() + 
								"\nInvalid Passwords: " + loggingUser.getInvalidPasswordCount() + "\n");
			
			//userSession.setAttribute(username, loggingUser);
			userSession.setAttribute(username, loggingUser);
			
			SQLHelper.close(con);
			
			if(loggingUser.getLoginType() == Login.LOGIN_SUCCESS)
			{
				userSession.setAttribute("loggedUser", loggingUser);
				response.sendRedirect("usersection.jsp");
			}
			else
			{
				response.sendRedirect("index.jsp?message=" + loggingUser.getLoginType());
			}
			
		}
	}
}
