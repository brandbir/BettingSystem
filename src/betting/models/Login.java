package betting.models;

public enum Login
{
	LOGIN_SUCCESS ("Successful Login"),
	LOGIN_FAIL ("Invalid Credentials"),
	LOGIN_LOCKED ("Account was locked, please try again later.");
	
	private final String description;
	
	private Login(String description)
	{
		this.description = description;
	}
	
	public String toString()
	{
		return this.description;
	}
}
