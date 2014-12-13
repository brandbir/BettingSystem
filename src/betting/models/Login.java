package betting.models;

import betting.helper.Misc;


public enum Login
{
	LOGIN_SUCCESS (1),
	LOGIN_FAIL (2),
	LOGIN_LOCKED (3);
	
	private final String description;
	
	private Login(int x)
	{
		switch (x)
		{
			case 1 : 
				this.description = Misc.MSG_LOGIN_SUCCESSFUL;
				break;
				
			case 2:
				this.description = Misc.MSG_LOGIN_INVALID;
				break;
				
			case 3:
				this.description = Misc.MSG_LOGIN_LOCKED;
				break;
				
			default:
				this.description = "Unknown login";
		}
	}
	
	public String toString()
	{
		return this.description;
	}
}
