package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import net.sourceforge.czt.modeljunit.Action;
import net.sourceforge.czt.modeljunit.AllRoundTester;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;
import net.sourceforge.czt.modeljunit.coverage.CoverageMetric;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.BetsForm;
import selenium.LoginForm;
import selenium.SignUpForm;
import betting.helper.Misc;
import betting.helper.SQLHelper;
import betting.models.Bet;
import betting.models.BettingSystem;
import betting.models.ConnectionPool;
import betting.models.User;

import com.ibm.db2.jcc.am.Connection;

public class BettingModel implements FsmModel, Runnable
{
	WebDriver driver;
	LoginForm loginform;
	SignUpForm signupform;
	BetsForm betsform;
	Connection con;
	BettingSystem betting;
	
	//action and its corresponding timing durations
	 HashMap<String, ArrayList<Long>> timings;
	
	String premiumType = Integer.toString(User.ACCOUNT_PREMIUM);
	String freeType = Integer.toString(User.ACCOUNT_FREE);
	
	//setting default userType to premium user
	String userType = premiumType;
	//String userName = "demo1";
	
	String userName;

	
	int invalidCount = 0;
	long lastInvalidLoginTime;
	
	double initialProb = Math.random();

	public BettingModel(boolean flag)
	{
		//ensuring constructor is not invoked twice per user while threading...
	}
	
	public BettingModel()
	{
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loginform = new LoginForm(driver);
		signupform = new SignUpForm(driver);
		betsform = new BetsForm(driver);
		con = (Connection) ConnectionPool.getStringConnection();
		betting = new BettingSystem();
		timings = new HashMap<String, ArrayList<Long>>();
	}

	ModelState state = ModelState.MainPage;
	
	@Override
	public Object getState()
	{
		return state;
	}

	@Override
	public void reset(boolean arg0)
	{
		if(state == ModelState.SuccessfulBet)
			acceptAlert();
		
		visitMainPage();
		
		userType = premiumType;
		state = ModelState.MainPage;
		//userName = "demo1";
		//userName = getUsername();
		initialProb = Math.random();
	}

	public boolean invalidLoginGuard()
	{
		return state == ModelState.MainPage && initialProb <= 0.125;
		
	}
	
	public @Action void invalidLogin()
	{
		long start = System.currentTimeMillis();
		
		initialProb = Math.random();
		inputInvalidLoginDetails();
		invalidCount++;
		lastInvalidLoginTime = System.currentTimeMillis();
		assertEquals(Misc.MSG_LOGIN_INVALID, loginform.getLoginMessage());
		state = ModelState.MainPage;
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("invalidLogin", duration);
	}
	
	public boolean validLoginGuard()
	{
		return state == ModelState.MainPage && (initialProb > 0.125 && initialProb <= 0.5); 
	}
	
	public @Action void validLogin()
	{
		long start = System.currentTimeMillis();
		initialProb = Math.random();
		
		userName = getUsername();
		loginform.populate(userName, "password");
		loginform.submit();
		
		long currentTime = System.currentTimeMillis();
		long timeDiff = currentTime - lastInvalidLoginTime;
		
		if(timeDiff >= BettingSystem.ACCOUNT_LOCK_INTERVAL_MINS *60*1000)
		{
			invalidCount = 0;
		}
		if(invalidCount == 3)
		{
			assertEquals(Misc.MSG_LOGIN_LOCKED, loginform.getLoginMessage());
		}
		else
		{
			assertEquals(Misc.URL_BETTING_PAGE, driver.getCurrentUrl());
			invalidCount = 0;
			lastInvalidLoginTime = 0;
			state = ModelState.Betting;
		}
		
		long duration = System.currentTimeMillis() - start;
		
		insertTiming("validLogin", duration);
	}
	
	public boolean signupGuard()
	{
		return state == ModelState.MainPage && initialProb > 0.5;
	}
	
	//Going In Registration state
	public @Action void signup()
	{
		initialProb = Math.random();
		signupform.clickSignUpButton();
		state = ModelState.Register;
	}
	
	public boolean invalidRegistrationGuard()
	{
		double r = Math.random();
		return state == ModelState.Register && r < 0.2;
	}
	
	public @Action void invalidRegistration()
	{
		long start = System.currentTimeMillis();
		
		signupform.populate("invalid user", "password", "name", "surname", "dob", "credCard", "expiryDate", "cvv", "1");
		assertEquals(Misc.URL_MAIN_PAGE, driver.getCurrentUrl());
		state = ModelState.Register;
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("invalidRegistration", duration);
	}
	
	public boolean validRegistrationGuard()
	{
		double r = Math.random();
		return state == ModelState.Register && r < 0.6;
	}
	
	public @Action void validRegistration()
	{
		long start = System.currentTimeMillis();
		int index = BettingSystem.randInt(1, 10000);
		long currentTime = System.currentTimeMillis() + index;
		String username = "user" + Long.toString(currentTime) + "_" + index;
		
		//setting global usernname
		userName = username;
		
		double r = Math.random();
				
		//25% of users will register for a premium account whilst 75% of users will register with a free account
		if(r < 0.25)
			userType = premiumType;
		else
			userType = freeType;

		signupform.populate(username, "password", "modName", "modSurname", "1980-01-24", "79927398713", "3000-01-01", "2", userType);
		signupform.submit();
		
		/*Misc.sleepProcess(2000);
		assertEquals(Misc.URL_BETTING_PAGE, driver.getCurrentUrl());*/
		
		state = ModelState.Betting;
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("validRegistration", duration);
	}
	
	public boolean backToMainPageGuard()
	{
		double r = Math.random();
		return state == ModelState.Register && r < 0.2;
	}
	
	public @Action void backToMainPage()
	{
		visitMainPage();
		state = ModelState.MainPage;
	}
	
	public boolean logoutGuard()
	{
		double r = Math.random();
		return state == ModelState.Betting && r < 0.5;
	}
	
	public @Action void logout()
	{
		long start = System.currentTimeMillis();
		
		betsform.logout();
		assertEquals(Misc.URL_MAIN_PAGE, driver.getCurrentUrl());
		
		state = ModelState.MainPage;
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("logout", duration);
	}
	
	public boolean invalidBetGuard()
	{
		double r = Math.random();
		return state == ModelState.Betting && r < 0.1;
	}
	
	public @Action void invalidBet()
	{
		long start = System.currentTimeMillis();
		
		betsform.populate(Bet.LOW, -10);
		betsform.submit();
		
		assertEquals(Misc.MSG_BETTING_INVALID_AMOUNT, betsform.getFieldValue("amount"));
		state = ModelState.Betting;
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("invalidBet", duration);
		
	}
	
	public boolean validBetGuard()
	{
		double r = Math.random();
		return state == ModelState.Betting && r < 0.4;
	}
	
	public @Action void validBet() 
	{
		long start = System.currentTimeMillis();
		//number of bets and total amount
		int[] betResult = betting.getTotalBetsAmount(con, userName);
	
		int numberOfBets = betResult[0];
		int totalBetAmount = betResult[1];

		int amount;
		int betLevel = Bet.LOW;
		
		//free user will bet between 1 and 6 euros
		if(userType.equals(freeType))
			amount = BettingSystem.randInt(1, 6);
		
		//premium user will bet between 100 and 2000 and 30% chance that place a High Level Bet
		else
		{
			amount = BettingSystem.randInt(100, 2000);
			
			double betProb = Math.random();
			if(betProb > 0.8)
				betLevel = Bet.HIGH;
			else if(betProb < 0.3)
				betLevel = Bet.MEDIUM;
		}
		
		betsform.populate(betLevel, amount);
		betsform.submit();
				
		if(userType.equals(premiumType))
		{
			if(totalBetAmount + amount > 5000)
			{
				assertEquals(Misc.MSG_BETTING_PREMIUM_LIMIT, betsform.getFieldValue("amount"));
			}
			else
			{
				assertEquals(true, betsform.isDialogPresent(driver));
				state = ModelState.SuccessfulBet;
			}
		}
		//free account
		else
		{
			if(amount == 6)
			{
				assertEquals(Misc.MSG_BETTING_FREEUSERS_AMOUNT_LIMIT, betsform.getFieldValue("amount"));
			}
			
			else if(numberOfBets == 3)
				assertEquals(Misc.MSG_BETTING_FREEUSERS_LIMIT, betsform.getFieldValue("amount"));
			
			else
			{
				assertEquals(true, betsform.isDialogPresent(driver));
				state = ModelState.SuccessfulBet;
			}
		}
		
		long duration = System.currentTimeMillis() - start;
		insertTiming("validBet", duration);
	}

	public boolean returnToBetScreenGuard()
	{
		return state == ModelState.SuccessfulBet;
	}
	
	/**
	 * Pressing OK Button from successful alert
	 */
	public @Action void returnToBetScreen()
	{
		acceptAlert();
		assertEquals(Misc.URL_BETTING_PAGE, driver.getCurrentUrl());
		state = ModelState.Betting;
	}

	/**
	 * Visiting the betting screen welcome page
	 */
	private void visitMainPage()
	{
		loginform.visit();
	}
	
	private void inputInvalidLoginDetails()
	{
		//inputting invalid login details
		loginform.populate(getUsername(), "my_____password");
		loginform.submit();
	}
	
	private void acceptAlert()
	{
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	private void insertTiming(String actionType, long duration)
	{
		//ArrayList<Long> timeDurations = timings.get(actionType);
		ArrayList<Long> timeDurations = FinalPerformance.totalTimings.get(actionType);
		
		if(timeDurations == null)
		{
			timeDurations = new ArrayList<Long>();
			timeDurations.add(duration);
		}
		else
		{
			timeDurations.add(duration);
		}
		
		//timings.put(actionType, timeDurations);
		FinalPerformance.totalTimings.put(actionType, timeDurations);
	} 
	
	public void printTimings()
	{
		Set<String> keys = timings.keySet();
		
		for(String i: keys)
		{
			ArrayList<Long> timeDurations = timings.get(i);
			for(int j=0; j < timeDurations.size(); j++)
			{
				System.out.println("Key " + i + " time : " + timeDurations.get(j));
			}
		}
	}
	
	public HashMap<String, ArrayList<Long>> getTimings()
	{
		return timings;
	}
	
	public void waitForPageLoaded(WebDriver driver)
	{
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver driver)
			{
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver,30);
		
		try
		{
			wait.until(expectation);
		}
		catch (Throwable error)
		{
			assertFalse("Timeout waiting for Page Load Request to complete.",true);
		}
	} 
	
	/*
	 * Get a random registered user
	 */
	public String getUsername()
	{
		int randomUser = BettingSystem.randInt(1, 1000);
		return "demo" + randomUser;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		Tester tester = new AllRoundTester(new BettingModel());
		tester.buildGraph();
		
		CoverageMetric coverage = new TransitionCoverage();
		tester.addCoverageMetric(coverage);
		
		tester.addListener("verbose", new VerboseListener(tester.getModel()));
		
		tester.generate(10);
		tester.getModel().printMessage(coverage.getName() + " was " + coverage.toString());
		SQLHelper.close(con);
	}

}
