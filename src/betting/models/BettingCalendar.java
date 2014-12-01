package betting.models;

import java.util.Date;
import java.util.GregorianCalendar;

//Class cannot be accessed in static mode because of mocking
public class BettingCalendar
{
		
	/**
	 * Returns a current date
	 * explain that this cannot be covered due to mocking
	 */
	public Date getCurrentDate()
	{
		return new GregorianCalendar().getTime();
	}
	
	/**
	 * Returns a valid GregorianCalendar Date Object
	 * @param year
	 * @param month
	 * @param day
	 * @return Date
	 */
	public static Date getDate(int year, int month, int day)
	{
		if(year > 0 && (month >= 0 && month < 12) && (day > 0 && day < 32))
			return  new GregorianCalendar(year, month,day).getTime();
		else
			throw new IllegalArgumentException("Invalid date arguments");
	}
	
	public long getCurrentTime()
	{
		return System.currentTimeMillis();
	}
	
}
