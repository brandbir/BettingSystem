package betting.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFile
{
	public static boolean writeMode = true;
	public static void logError(String msg)
	{
		if(writeMode)
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			System.out.println(dateFormat.format(date) + " - " + msg);
		}
	}
}
