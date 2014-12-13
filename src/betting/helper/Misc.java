package betting.helper;

import org.json.JSONException;
import org.json.JSONObject;

import betting.models.Bet;
import betting.models.BettingSystem;

public class Misc
{
	public static final int SUCCESS = 1;
	public static final int FAIL	= -1;
	
	public static final String MSG_VALID_REGISTRATION	= "User was successfully registered";
	public static final String MSG_VALID_BET			= "Bet was placed successfully";
	
	public static final String MSG_LOGIN_SUCCESSFUL		= "Successful Login";
	public static final String MSG_LOGIN_INVALID		= "Invalid Credentials";
	public static final String MSG_LOGIN_LOCKED			= "Account was locked for 5 mins, please try again later.";

	

	
	public static final String MSG_INVALID_USERNAME		= "Username is already being used";
	public static final String MSG_INVALID_PASSWORD		= "Invalid Password";
	public static final String MSG_INVALID_NAME			= "Invalid name";
	public static final String MSG_INVALID_SURNNAME		= "Invalid surname";
	public static final String MSG_INVALID_DOB			= "Age should be over 18";
	public static final String MSG_INVALID_ACCOUNT		= "Please select one of the available Account types";
	public static final String MSG_INVALID_CREDITCARD	= "Invalid Credit Card Number";
	public static final String MSG_INVALID_EXPIRYDATE	= "Invalid Expiry Date";
	public static final String MSG_INVALID_CVV			= "Invalid CVV";
	
	public static final String MSG_BETTING_INVALID_LEVEL			= "Please select one of the available Bet Level types";
	public static final String MSG_BETTING_LIMIT					= "Users cannot have a sum of bets of more than " + BettingSystem.MAX_BETTING_SUM;
	public static final String MSG_BETTING_FREEUSERS_LIMIT			= "Free users can place a maximum of 3 bets";
	public static final String MSG_BETTING_FREEUSERS_RESTRICTION	= "Free users can only place low-bets";
	public static final String MSG_BETTING_INVALID_AMOUNT			= "Invalid bet amount";
	public static final String MSG_BETTING_FREEUSERS_AMOUNT_LIMIT	= "Free users are limited to place maximum bet of " + Bet.FREE_USER_MAX_BET + " euro";
	
	
	/**
	 * Keep track of errors encountered while trying to register a user
	 * @param key parameter name
	 * @param value reason of error
	 */
	public static JSONObject addError(String key, String value, JSONObject error)
	{
		try
		{
			error.put(key, value);
		}
		catch (JSONException e)
		{
			LogFile.logError("UserRegistration.addError() - " + e.getMessage());
			return error;
		}
		
		return error;
		
	}
	
}
