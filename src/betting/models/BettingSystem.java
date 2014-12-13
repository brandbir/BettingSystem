package betting.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import betting.helper.BettingException;
import betting.helper.Misc;
import betting.helper.SQLHelper;

public class BettingSystem
{
	public static final int ACCOUNT_LOCK_INTERVAL_MINS = 5;
	public  static final int MAX_BETTING_SUM 	= 5000;
	private static final int MAX_INVALID_LOGINS = 3;
	
	private HashMap<String, User> usersDB = new HashMap<String, User>();
	private HashMap<String, ArrayList<Bet>> betList = new HashMap<String, ArrayList<Bet>>();
	private static BettingCalendar calendar;
	private static BettingSystem bettingSystemInstance = null;
	
	
	/**
	 * Singleton approach to avoid the creation of multiple objects
	 * of the Betting System
	 * 
	 * @return bettingSystemInstance
	 */
	public static BettingSystem getInstance()  
	{
		if (bettingSystemInstance == null)
		{
			bettingSystemInstance = new BettingSystem();
			calendar = new BettingCalendar();
		}
		return bettingSystemInstance;	
	}
	
	/**
	 * Deallocation of instance object
	 */
	public void  clearInstance()  
	{
		bettingSystemInstance = null;
	}
	
	/**
	 * Gets a user from the Betting System
	 * @param username username to be found
	 * @return User
	 */
	public User getUser(String username)
	{
		if(usersDB.containsKey(username))
			return usersDB.get(username);
		else
			return null;
	}
	
	/**
	 * Gets a user from the Betting System
	 * @param username username to be found
	 * @return User
	 */
	public static User getUser(Connection con, String username)
	{
		User user = null;
		if (con == null)
		{
			BettingSystem b = BettingSystem.getInstance();
			user = b.getUser(username);
			return user;
		}
		else
		{
			StringBuffer callStmt = new StringBuffer();
			callStmt.append("CALL SELECT_USER('");
			callStmt.append(username);
			callStmt.append("')");
			
			try
			{
				CallableStatement cs = con.prepareCall(callStmt.toString());
				ResultSet rs = cs.executeQuery();
				if(rs.next())
				{
					user = new User();
					user.setUsername(username);
					user.setPassword(rs.getString("PASSWORD"));
					user.setName(rs.getString("NAME"));
					user.setSurname(rs.getString("SURNAME"));
					user.setDateOfBirth(rs.getDate("DOB"));
					user.setAccount(rs.getString("PREMIUM"));
					user.setCardNumber(rs.getString("CREDIT_CARD"));
					user.setExpiryDate(rs.getDate("EXPIRY_DATE"));
					user.setCvv(rs.getString("CVV"));
				}
			}
			catch (SQLException e)
			{
				System.out.println("BettingSystem.getUser(Connection, String) - " + e.getMessage());
			}
		}
		
		return user; 
	}
	
	
	/**
	 * Check if a username is already being used by another user
	 * @param con Database connection
	 * @param username username to be found
	 * @return whether the username is found in the system
	 */
	public static boolean userExists(Connection con, String username)
	{
		User user = null;
		
		user = getUser(con, username);
		
		if(user == null)
			return false;
		else
			return true;
	}
	/**
	 * Setting the class dependency of the Betting System
	 * @param calendar from where dates will be obtained
	 */
	public void setBettingCalendar(BettingCalendar calendar)
	{
		BettingSystem.calendar = calendar;
	}
	
	/**
	 * Adding user to the Betting System
	 * @param user User object with all the details
	 */
	public void addUser(User user)
	{
		usersDB.put(user.getUsername(), user);
	}
	
	/**
	 * Adding user to the Betting System
	 * @param user User object with all the details
	 */
	public static void addUser(Connection con, User user)
	{
		if(con == null)
		{
			BettingSystem system = BettingSystem.getInstance();
			system.addUser(user);
		}
		else
		{
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer args = new StringBuffer();
			
			args.append("'");
			args.append(user.getUsername());
			args.append("', '");
			args.append(user.getPassword());
			args.append("', '");
			args.append(user.getName());
			args.append("', '");
			args.append(user.getSurname());
			args.append("', '");
			args.append(df.format(user.getDateOfBirth()));
			args.append("', ");
			args.append(user.getAccount());
			args.append(", ");
			args.append(user.getCardNumber());
			args.append(", '");
			args.append(df.format(user.getExpiryDate()));
			args.append("', ");
			args.append(user.getCvv());
			
			StringBuffer callStmt = new StringBuffer();
			callStmt.append("CALL CREATE_USER(");
			callStmt.append(args);
			callStmt.append(")");
			
			System.out.println(callStmt.toString());
			
			try
			{
				CallableStatement cs = con.prepareCall(callStmt.toString());
				cs.execute();
			} 
			catch (SQLException e)
			{
				System.out.println("BettingSystem.addUser() - " + e.getMessage());
			}
			
		}
	}
	
	/**
	 * Gets the total number of users found in the Betting System
	 * @return total users
	 */
	public int getNumberofUsers()
	{
		return usersDB.size();
	}
	
	
	/**
	 * Validating user login
	 * @param username User's username
	 * @param password User's password
	 * @param User temporary user details
	 * @return User details
	 */
	public User login(Connection con, String username, String password, User user)
	{
		if(user == null)
			user = getUser(con, username);
		
		String dbPassword = user.getPassword();
		if (dbPassword.equals(password))
		{
			long lastInvalidAccess = user.getTimeofInvalidLogin();
			if(calendar.getCurrentTime() - lastInvalidAccess >= ACCOUNT_LOCK_INTERVAL_MINS *60*1000)
			{
				user.setInvalidPasswordCount(0);
				
				//correct credentials and account is not locked
				user.setLoginType(Login.LOGIN_SUCCESS);
			}
			else
			{
				//account is locked
				user.setLoginType(Login.LOGIN_LOCKED);
			}
		}
		else
		{
			int invalidCounts = user.getInvalidPasswordCount();
			user.setInvalidPasswordCount(++invalidCounts);
			
			if(invalidCounts == MAX_INVALID_LOGINS)
				user.setTimeofInvalidLogin(calendar.getCurrentTime());

			//invalid credentials
			user.setLoginType(Login.LOGIN_FAIL);
		}
		
		return user;
	}

	/**
	 * Placing a Bet to the Betting System
	 * @param bet User's bet
	 * @throws Exception
	 */
	public void placeBet(Connection con, Bet bet) throws BettingException
	{
		User user = bet.getUser();
		
		//getting the bets of the current user
		ArrayList<Bet> userBets = getBets(con, user);
		
		//allocate a new list for bets 
		if(userBets == null) 
			userBets = new ArrayList<Bet>();
		
		int currentBetSum = getTotalAmount(userBets, user.getUsername());
		
		boolean validFreeAccount = (user.getAccount() == User.ACCOUNT_FREE) && (userBets.size() < 3);
		
		if((bet.getAmount() + currentBetSum) > MAX_BETTING_SUM)
			throw new BettingException(Misc.MSG_BETTING_LIMIT);
		
		else if(validFreeAccount || user.getAccount() == User.ACCOUNT_PREMIUM )
		{
			
			//adding bet locally
			userBets.add(bet);
			
			//pushing bet to database
			pushBet(con, bet);
		}
		
		else //when we DO NOT have a validFreeAccount
			throw new BettingException(Misc.MSG_BETTING_FREEUSERS_LIMIT);

		betList.put(user.getUsername(), userBets);
	}
	
	/**
	 * Gets the total amount of bets
	 * @param username specifying the user
	 * @return total amount of bets of a particular user
	 */
	public int getTotalAmount(ArrayList<Bet> userBets, String username)
	{
		if(userBets != null)
		{
			int sum = 0;
			for(int i=0; i < userBets.size(); i++)
			{
				sum += userBets.get(i).getAmount();
			}
			
			return sum;
		}
		return 0;
	}
	
	/**
	 * Getting the total number of bets placed by all users
	 * @return total bets
	 */
	public int getNoOfBets()
	{
		return betList.size();
	}
	
	/**
	 * Getting the total number of bets placed by a particular user
	 * @return total bets
	 */
	public int getNoOfBets(String username)
	{
		ArrayList<Bet> userBets = betList.get(username);
		return userBets.size();
	}

	/**
	 * Getting the last bet placed by a specific user
	 * @param username
	 * @return last placed bet
	 */
	public Bet getLastBet(String username)
	{
		ArrayList<Bet> userBets = betList.get(username);
		
		if(userBets == null)
			return null;
		else
			return userBets.get(userBets.size() - 1);
	}
	
	public ArrayList<Bet> getBets(Connection con, User user)
	{
		if(con == null)
			return betList.get(user.getUsername());
		else
			return pullBets(con, user);
	}

	private ArrayList<Bet> pullBets(Connection con, User user)
	{
		ArrayList<Bet> bets = new ArrayList<Bet>();
		StringBuffer callStmt = new StringBuffer();
		
		callStmt.append("CALL SELECT_USER_BETS('");
		callStmt.append(user.getUsername());
		callStmt.append("')");
		
		try
		{
			CallableStatement cs = con.prepareCall(callStmt.toString());
			ResultSet rs = cs.executeQuery();
			
			while(rs.next())
			{
				Bet bet = new Bet();
				bet.setUser(user);
				bet.setRiskLevel(rs.getInt("RISK_LEVEL"));
				bet.setRiskLevelDescription(rs.getString("LEVEL_NAME"));
				bet.setAmount(rs.getInt("AMOUNT"));
				bets.add(bet);
			}
			
			//forcing not to close the current connection
		}
		catch (Exception e)
		{
			System.out.println("BettingSystem.getBetsFromDatabase(Connection, User) - " + e.getMessage());
		}
		
		return bets; 
	}
	
	private void pushBet(Connection con, Bet bet)
	{
		if(con != null)
		{
			StringBuffer callStmt = new StringBuffer();
			callStmt.append("CALL CREATE_BET(");
			callStmt.append("'");
			callStmt.append(bet.getUser().getUsername());
			callStmt.append("', ");
			callStmt.append(bet.getRiskLevel());
			callStmt.append(", ");
			callStmt.append(bet.getAmount());
			callStmt.append(")");
			
			System.out.println(callStmt.toString());
			
			try
			{
				CallableStatement cs = con.prepareCall(callStmt.toString());
				cs.execute();
			} 
			catch (SQLException e)
			{
				System.out.println("BettingSystem.pushBet(Connection, Bet) - " + e.getMessage());
			}
			finally
			{
				SQLHelper.close(con);
			}
		}
	}
}
