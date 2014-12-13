package betting.helper;

public class BettingException extends Exception
{
	private static final long serialVersionUID = 1L;
	private String reason;
	
	public BettingException(String reason)
	{
		this.reason = reason;
		LogFile.logError(reason);
	}

	public String getMessage()
	{
		return reason;
	}
}
