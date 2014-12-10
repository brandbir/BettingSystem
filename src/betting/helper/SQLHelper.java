package betting.helper;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLHelper
{
	public static void close(Connection con)
	{
		if(con != null)
		{
			try
			{
				con.close();
			}
			catch (SQLException e)
			{
				System.out.println("SQLHelper.close() - " + e.getMessage());
			}
		}
	}
}
