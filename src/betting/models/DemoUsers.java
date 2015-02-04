package betting.models;

import java.text.ParseException;

import betting.helper.Misc;
import betting.helper.SQLHelper;

import com.ibm.db2.jcc.am.Connection;

public class DemoUsers
{
	public static void main (String args[]) throws ParseException
	{
		Connection con = (Connection) ConnectionPool.getStringConnection();
		
		for(int i = 900; i < 1001; i++)
		{
			String username = "demo" + i;
			User user = new User();
			user.setUsername(username);
			user.setPassword("password");
			user.setName("DemoName");
			user.setSurname("DemoSurname");
			user.setDateOfBirth(Misc.parseDate("1980-01-01"));
			user.setCardNumber("79927398713");
			user.setExpiryDate(Misc.parseDate("2020-01-01"));
			user.setCvv("2");
			user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
			BettingSystem.addUser(con, user);
		}
		
		SQLHelper.close(con);
	}
}
