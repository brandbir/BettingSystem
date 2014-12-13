package betting.servlets;

import javax.servlet.http.HttpServlet;

import betting.helper.LogFile;

/**
 * Servlet implementation class Initializer
 */
public class Initializer extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public void init()
	{
		System.out.println("BettingWebsite Initialization");
		LogFile.writeMode = true;
		System.out.println("Enabling LogConsole write mode...");
		System.out.println("BettingWebsite initialized successfully");
	}
}
