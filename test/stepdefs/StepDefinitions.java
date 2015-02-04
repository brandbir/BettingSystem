package stepdefs;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import selenium.BetsForm;
import selenium.LoginForm;
import selenium.SignUpForm;
import betting.helper.Misc;
import betting.models.Bet;
import betting.models.BettingSystem;
import betting.models.ConnectionPool;
import betting.models.User;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions
{
	WebDriver driver;
	SignUpForm signupForm;
	BetsForm betsForm;
	
	
	String testUsername		= "Finaldemo";
	String testPassword		= "password";
	String testName			= "testName";
	String testSurname		= "testSurname";
	String testDOB			= "1980-01-01";
	String testCreditCard	= "79927398713";
	String testExpiryDate	= "5000-1-1";
	String testCVV			= "2";
	String testAccount		= Integer.toString(User.ACCOUNT_PREMIUM);
	
	@Before
	public void init()
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		BettingSystem.removeUser(testUsername, false);
		BettingSystem.removeBets(testUsername, false);
	}
	
	@After
	public void tearDown()
	{
		//removes test user if was created for testing purposes
		BettingSystem.removeUser(testUsername, false);
		BettingSystem.removeBets(testUsername, false);
		driver.quit();
	}
	
	@Given("^I am a user trying to register$")
	public void userTryingToRegister() throws Throwable 
	{
		signupForm = new SignUpForm(driver);
		signupForm.visit();
	}

	@When("^I register providing correct information$")
	public void registerByCorrectInformation() throws Throwable
	{
		//setting form with correct user information
		enterUserDetails();
		
	}

	@Then("^I should be told that the registration was successful$")
	public void performSuccessfulRegistration()
	{
		signupForm.submit();
		Misc.sleepProcess(2000);
		assertEquals(Misc.URL_BETTING_PAGE, driver.getCurrentUrl());
	}

	@When("^I fill in a form with correct data and I change the \"(.*?)\" field to have incorrect input$")
	public void fillTheFormWithCorrectDataAndChangeField(String fieldname)
	{
		enterUserDetails();
		
		//empty field name is always incorrect
		signupForm.updateField(fieldname, "");
	}

	@Then("^I should be told that the data in \"(.*?)\" is \"(.*?)\"$")
	public void getMessageOfChangedFieldName(String fieldName, String message)
	{
		signupForm.submit();
		Misc.sleepProcess(1000);
		assertEquals(message, signupForm.getFieldValue(fieldName));
	}

	@Given("^I am a user with a free account$")
	public void createFreeUser()
	{
		User userFree = null;
		//setting user with a free account
		try
		{
			userFree = new User();
			userFree.setUsername(testUsername);
			userFree.setPassword(testPassword);
			userFree.setName(testName);
			userFree.setSurname(testSurname);
			userFree.setDateOfBirth(Misc.parseDate(testDOB));
			userFree.setCardNumber(testCreditCard);
			userFree.setExpiryDate(Misc.parseDate(testExpiryDate));
			userFree.setCvv(testCVV);
			userFree.setAccount(String.valueOf(User.ACCOUNT_FREE));
			
			//add free user to db
			BettingSystem.addUser(ConnectionPool.getStringConnection(), userFree);
			
			//login with free account
			LoginForm loginForm = new LoginForm(driver);
			loginForm.visit();
			loginForm.populate(testUsername, testPassword);
			loginForm.submit();
		}
		catch(ParseException e)
		{
			System.out.println("Expiry Date/DOB of test user are invalid " + e.getMessage());
		}
	}

	@When("^I try to place a bet of (\\d+) euros$")
	public void placeBet(int amount)
	{
		betsForm = new BetsForm(driver);
		betsForm.visit();
		//Misc.sleepProcess(500);
		betsForm.populate(Bet.LOW, amount);
		betsForm.submit();
	}

	@Then("^I should be told the bet was successfully placed$")
	public void getSuccessfulBetMessage()
	{
		assertEquals(true, betsForm.isDialogPresent(driver));
		betsForm.acceptAlert();
	}

	@Then("^I should be told that I have reached the maximum number of bets$")
	public void getMaxNumberOfBetsError()
	{
		assertEquals(Misc.MSG_BETTING_FREEUSERS_LIMIT, betsForm.getFieldValue("amount"));
	}

	@Given("^I am a user with a premium account$")
	public void createPremiumUser()
	{
		//create a premium users
		User userPremium = null;
		
		//setting user with a free account
		try
		{
			userPremium = new User();
			userPremium.setUsername(testUsername);
			userPremium.setPassword(testPassword);
			userPremium.setName(testName);
			userPremium.setSurname(testSurname);
			userPremium.setDateOfBirth(Misc.parseDate(testDOB));
			userPremium.setCardNumber(testCreditCard);
			userPremium.setExpiryDate(Misc.parseDate(testExpiryDate));
			userPremium.setCvv(testCVV);
			userPremium.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
			
			//add premium user to db
			BettingSystem.addUser(ConnectionPool.getStringConnection(), userPremium);
			
			//login with premium account
			LoginForm loginForm = new LoginForm(driver);
			loginForm.visit();
			loginForm.populate(testUsername, testPassword);
			loginForm.submit();
		}
		catch(ParseException e)
		{
			System.out.println("Expiry Date/DOB of test user are invalid " + e.getMessage());
		}
	}
	
	@Then("^I should be told that I have reached the maximum cumulative betting amount$")
	public void reachedMaxiumAmount()
	{
		//Misc.sleepProcess(250);
		assertEquals(Misc.MSG_BETTING_PREMIUM_LIMIT, betsForm.getFieldValue("amount"));
	}
	
	@Given("^I am a user who has not yet logged on$")
	public void userNotYetLoggedIn()
	{
		User userPremium = null;
		
		//setting user with a free account
		try
		{
			userPremium = new User();
			userPremium.setUsername(testUsername);
			userPremium.setPassword(testPassword);
			userPremium.setName(testName);
			userPremium.setSurname(testSurname);
			userPremium.setDateOfBirth(Misc.parseDate(testDOB));
			userPremium.setCardNumber(testCreditCard);
			userPremium.setExpiryDate(Misc.parseDate(testExpiryDate));
			userPremium.setCvv(testCVV);
			userPremium.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
			
			//add premium user to db
			BettingSystem.addUser(ConnectionPool.getStringConnection(), userPremium);
			
		}
		catch(ParseException e)
		{
			System.out.println("Expiry Date/DOB of test user are invalid " + e.getMessage());
		}
	}

	@When("^I try to access the betting screen$")
	public void loginToBettingScreen()
	{
		betsForm = new BetsForm(driver);
		betsForm.visit();
	}

	@Then("^I should be refused access$")
	public void confirmNotLoggedIn()
	{
		assertEquals(Misc.URL_MAIN_PAGE, driver.getCurrentUrl());
	}
	
	@When("^I try to place a \"(.*?)\" bet of (\\d+) euros$")
	public void placeBet(String riskLevel, int amount)
	{
		betsForm = new BetsForm(driver);
		betsForm.visit();
		
		switch(riskLevel)
		{
			case "Low":
				betsForm.populate(Bet.LOW, amount);
				break;
				
			case "Medium":
				betsForm.populate(Bet.MEDIUM, amount);
				break;
			
			case "High":
				betsForm.populate(Bet.HIGH, amount);
				break;
		}
		
		betsForm.submit();
	}

	@Then("^I should be \"(.*?)\" to bet$")
	public void checkIfBetWasPlaced(String expectedResult)
	{
		if(expectedResult.equals("Allowed"))
		{
			assertEquals(true, betsForm.isDialogPresent(driver));
		}
		else if(expectedResult.equals("Not allowed"))
		{
			String errorMessage = betsForm.getFieldValue("amount");
			Misc.sleepProcess(1000);
			assertEquals(Misc.MSG_BETTING_FREEUSERS_RESTRICTION, errorMessage);
		}
	}
	
	public void enterUserDetails()
	{
		signupForm.clickSignUpButton();
		signupForm.populate(testUsername, testPassword, testName, testSurname, testDOB, testCreditCard, testExpiryDate, testCVV, testAccount);
	}
}
