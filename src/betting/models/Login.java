package betting.models;

import betting.helper.Misc;


public enum Login
{
	LOGIN_SUCCESS (1),
	LOGIN_FAIL (2),
	LOGIN_LOCKED (3),
	LOGIN_DOES_NOT_EXIST(4);
	
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
				
			case 4:
				this.description = Misc.MSG_LOGIN_NOT_EXIST;
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
