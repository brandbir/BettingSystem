package betting;

import java.util.ArrayList;
import java.util.HashMap;

public class BettingSystem
{
	private static final int MAX_BETTING_SUM 	= 5000;
	private static final int MAX_INVALID_LOGINS = 3;
	
	private HashMap<String, User> usersDB = new HashMap<String, User>();
	private HashMap<String, ArrayList<Bet>> betList = new HashMap<String, ArrayList<Bet>>();
	private BettingCalendar calendar;
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
			bettingSystemInstance = new BettingSystem();
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
	 * Setting the class dependency of the Betting System
	 * @param calendar from where dates will be obtained
	 */
	public void setBettingCalendar(BettingCalendar calendar)
	{
		this.calendar = calendar;
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
	 * @return validity of the login
	 */
	public boolean login(String username, String password)
	{
		User user = usersDB.get(username);
		String dbPassword = user.getPassword();
		if (dbPassword.equals(password))
		{
			long lastInvalidAccess = user.getTimeofInvalidLogin();
			if(calendar.getCurrentTime() - lastInvalidAccess >= 5*60*1000)
			{
				user.setInvalidPasswordCount(0);
				return true;
			}
			return false;
		}
		else
		{
			int invalidCounts = user.getInvalidPasswordCount();
			user.setInvalidPasswordCount(++invalidCounts);
			
			if(invalidCounts == MAX_INVALID_LOGINS)
				user.setTimeofInvalidLogin(calendar.getCurrentTime());
						
			return false;
		}
		
	}

	/**
	 * Placing a Bet to the Betting System
	 * @param bet User's bet
	 * @throws Exception
	 */
	public void placeBet(Bet bet) throws Exception
	{
		User user = bet.getUser();
		
		//getting the bets of the current user
		ArrayList<Bet> userBets = betList.get(user.getUsername());
		
		//allocate a new list for bets 
		if(userBets == null) 
			userBets = new ArrayList<Bet>();
		
		int currentBetSum = getTotalAmount(user.getUsername());
		
		boolean validFreeAccount = (user.getAccount() == User.ACCOUNT_FREE) && (userBets.size() < 3);
		
		if((bet.getAmount() + currentBetSum) > MAX_BETTING_SUM)
			throw new Exception("Users cannot have a sum of bets of more than 5000");
		
		else if(validFreeAccount)
			userBets.add(bet);
		
		else if(user.getAccount() == User.ACCOUNT_PREMIUM)
			userBets.add(bet);
		
		else //when we DO NOT have a validFreeAccount
			throw new Exception("Free users can place a maximum of 3 bets");
		

		betList.put(user.getUsername(), userBets);
	}
	
	/**
	 * Gets the total amount of bets
	 * @param username specifying the user
	 * @return total amount of bets of a particular user
	 */
	public int getTotalAmount(String username)
	{
		ArrayList<Bet> userBets = betList.get(username);
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
}
