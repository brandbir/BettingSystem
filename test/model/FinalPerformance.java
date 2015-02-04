package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FinalPerformance
{
	private static final int MYTHREADS = 8;
	private static final int users = 1;
	
	static HashMap<String,ArrayList<Long>> totalTimings = new HashMap<String,ArrayList<Long>>();
	
	public static void main(String args[]) throws Exception
	{
		ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
		
		long beginTime = System.currentTimeMillis();
		
		for(int i = 0; i < users; i++)
		{
			Runnable worker = new BettingModel(true);
			executor.execute(worker);
		}
		
		executor.shutdown();
		System.out.println("Shutting down...");
		
		while (!executor.isTerminated())
		{
			//waiting 
		}
		
		Set<String> keys = totalTimings.keySet();
		
		for(String i: keys)
		{
			long totalTime = 0;
			
			ArrayList<Long> timeDurations = totalTimings.get(i);
			
			for(int j=0; j < timeDurations.size(); j++)
				totalTime += timeDurations.get(j);
			
			System.out.println("Key " + i + "Total time " + totalTime + "(" + timeDurations.size() + ") Average time : " + totalTime/timeDurations.size());
		}
		
		System.out.println("\nFinished all threads");
		long endTime = System.currentTimeMillis();
		long timeDiff = endTime - beginTime ;
		
		System.out.println("Time taken --> " + timeDiff/1000 + "s");
	}
}
