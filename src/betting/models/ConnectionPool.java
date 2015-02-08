package betting.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import betting.helper.LogFile;
import betting.helper.Misc;

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
				
				//Context context = (Context) new InitialContext().lookup("java:comp/env");
				dataSource = ((DataSource) context.lookup("jdbc/TEST"));
			}
			
			con = dataSource.getConnection();
		}
		catch(NamingException e)
		{
			LogFile.logError("ConnectionPool.getConnection() - " + e.getMessage());
		}
		catch (SQLException e)
		{
			LogFile.logError("ConnectionPool.getConnection() - " + e.getMessage());
		}
		
		return con;
	}
	
	public static Connection getStringConnection()
	{
		String connString = "jdbc:db2://" + Misc.DB_IP_ADDRESS + ":50000/TEST";
		String driver = "com.ibm.db2.jcc.DB2Driver";
		String username = "brandon";
		String password = "Brandbir018";
	
		Connection result = null;
		try 
		{
			Class.forName(driver).newInstance();
		}
		catch (Exception ex)
		{
			LogFile.logError("Check classpath. Cannot load db driver: " + driver);
		}
	
		try
		{
			result = DriverManager.getConnection(connString, username, password);
		}
		catch (SQLException e)
		{
			LogFile.logError( "Driver loaded, but cannot connect to db: " + connString);
		}
		
		return result;
	}
}
