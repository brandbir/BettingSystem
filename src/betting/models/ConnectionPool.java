package betting.models;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionPool
{
	private static DataSource dataSource = null;
	
	public static Connection getConnection()
	{
		Connection con = null;
		
		try
		{
			if(dataSource == null)
			{
				Context context = (Context) new InitialContext().lookup("java:comp/env");
				dataSource = ((DataSource) context.lookup("jdbc/TEST"));
			}
			
			con = dataSource.getConnection();
		}
		catch(Exception e)
		{
			System.out.println("[ConnectionPool.getConnection()] - " + e.getMessage());
		}
		
		return con;
	}
}
