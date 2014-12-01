package betting.models;

import java.sql.Connection;

public class Bet
{
	public static final int LOW 	= 1;
	public static final int MEDIUM	= 2;
	public static final int HIGH	= 3;
	
	private User user;
	private int riskLevel;
	private String riskLevelDescription;
	private int amount;
	private BettingSystem bettingSystem = BettingSystem.getInstance();
	
	public Bet(User user, int riskLevel, int amount) throws Exception
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

	public void setUser(Connection con, String username) throws Exception
	{
		if(con == null)
			this.user = bettingSystem.getUser(username);
		
		else
			this.user = BettingSystem.getUser(con, username);
		
		if(user == null)
			throw new Exception("Username not found in DB!!");
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

	public int getRiskLevel()
	{
		return riskLevel;
	}

	public void setRiskLevel(int riskLevel) throws Exception
	{
		if(user.getAccount() == User.ACCOUNT_FREE) 
			if(riskLevel != Bet.LOW)
				throw new Exception("Free users can only place low-bets");
		
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

	public void setAmount(int amount) throws Exception
	{
		if(amount < 0)
			throw new Exception("Free users are limited to place maximum bet of 5 euro");

		if(user.getAccount() == User.ACCOUNT_FREE && amount > 5)
			throw new Exception("Free users are limited to place maximum bet of 5 euro");
		
		this.amount = amount;
	}

}
