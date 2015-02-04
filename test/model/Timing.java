package model;

import java.util.ArrayList;

public class Timing
{
	String actionType;
	ArrayList<Long> timings;
	
	public Timing()
	{
		this.actionType = "";
		timings = new ArrayList<Long>();
	}
	public void setAction(String action)
	{
		this.actionType = action;
	}
	
	public String getAction()
	{
		return actionType;
	}
	
	public void insertTime(long timeDuration)
	{
		timings.add(timeDuration);
	}
	
	public ArrayList<Long> getTimings()
	{
		return timings;
	}
	
}
