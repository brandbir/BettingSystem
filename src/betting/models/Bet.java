package betting.models;

import java.sql.Connection;

import betting.helper.BettingException;
import betting.helper.Misc;

public class Bet
{
	public static final int LOW 	= 1;
	public static final int MEDIUM	= 2;
	public static final int HIGH	= 3;
	
	public static final int FREE_USER_MAX_BET = 5;
	
	private User user;
	private int riskLevel;
	private String riskLevelDescription;
	private int amount;
	private BettingSystem bettingSystem = BettingSystem.getInstance();
	
	public Bet(User user, int riskLevel, int amount) throws BettingException
	{
		
		this.setUser(user);
		this.setRiskLevel(riskLevel);
		this.setAmount(amount);
	}

	public Bet()
	{
		this.user = null;
		this.riskLevel = 0;
		this.amount = 0;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(Connection con, String username) throws BettingException
	{
		if(con == null)
			this.user = bettingSystem.getUser(username);
		
		else
			this.user = BettingSystem.getUser(con, username);
		
		if(user == null)
			throw new BettingException("User is not found in database");
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

	public int getRiskLevel()
	{
		return riskLevel;
	}

	public void setRiskLevel(int riskLevel) throws BettingException
	{
		if(riskLevel != Bet.LOW && riskLevel != Bet.MEDIUM && riskLevel != Bet.HIGH)
			throw new BettingException(Misc.MSG_BETTING_INVALID_LEVEL);
		
		if(user.getAccount() == User.ACCOUNT_FREE) 
			if(riskLevel != Bet.LOW)
				throw new BettingException(Misc.MSG_BETTING_FREEUSERS_RESTRICTION);
		
		this.riskLevel = riskLevel;
	}
	
	public String getRiskLevelDescription()
	{
		return this.riskLevelDescription;
	}
	
	public void setRiskLevelDescription(String riskLevelDescription)
	{
		this.riskLevelDescription = riskLevelDescription;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount) throws BettingException
	{
		if(amount < 0)
			throw new BettingException(Misc.MSG_BETTING_INVALID_AMOUNT);

		if(user.getAccount() == User.ACCOUNT_FREE && amount > FREE_USER_MAX_BET)
			throw new BettingException(Misc.MSG_BETTING_FREEUSERS_AMOUNT_LIMIT);
		
		this.amount = amount;
	}

}
