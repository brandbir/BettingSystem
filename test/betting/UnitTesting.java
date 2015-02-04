package betting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import betting.helper.BettingException;
import betting.helper.Misc;
import betting.models.Bet;
import betting.models.BettingCalendar;
import betting.models.BettingSystem;
import betting.models.Login;
import betting.models.User;

public class UnitTesting
{
	User user;
	BettingSystem bettingSystem;
	
	@Before
	public void setUp()
	{
		bettingSystem = BettingSystem.getInstance();
		bettingSystem.setBettingCalendar(new BettingCalendar());
		user = new User();
	}
	
	@After
	public void tearDown()
	{
		user = null; 
		bettingSystem.clearInstance();
	}
	
	@Test
	public void testUserExists()
	{
		user.setUsername("andrew");
		assertEquals("andrew", user.getUsername());
		
		BettingSystem.addUser(null, user);
		assertEquals(true, BettingSystem.userExists(null, "andrew"));
		assertEquals(false, BettingSystem.userExists(null, "brandon"));

	}
	@Test
	public void testUserName()
	{
		User other = new User();
		user.setUsername("andrew");
		assertEquals("andrew", user.getUsername());
		other.setUsername("andrew");
		
		BettingSystem.addUser(null, user);
		BettingSystem.addUser(null, user);
		
		assertEquals(1, bettingSystem.getNumberofUsers());
	}
	
	
	@Test
	public void testPasswordSuccess()
	{
		user.setPassword("birmingham");
		assertEquals("birmingham", user.getPassword());
	}
	
	@Test
	public void testPasswordSuccess2()
	{
		user.setPassword("awerdert");
		assertEquals("awerdert", user.getPassword());
	}
	
	@Test
	public void testPasswordFail()
	{
		assertEquals(Misc.FAIL, user.setPassword("brandon"));
	}
	
	@Test
	public void testEmptyNameSurname()
	{
		assertEquals(Misc.FAIL, user.setSurname(""));
	}
	
	@Test
	public void testOnlyAlphabetical1()
	{
		user.setName("andrew");
		user.setSurname("birmingham");
		
		assertEquals("birmingham", user.getSurname());
		assertEquals("andrew", user.getName());
	}
	
	@Test
	public void testOnlyAlphabetical2()
	{
		assertEquals(Misc.FAIL, user.setName("34534543adkfjdnf"));
	}
	
	@Test
	public void testOnlyNumbers()
	{
		assertEquals(Misc.FAIL, user.setName("34534543"));
	}
	
	@Test
	public void testWhiteSpace()
	{
		assertEquals(Misc.FAIL, user.setName("dgsfng dskgjsbg "));
	}
	
	@Test
	public void testNotNullDate()
	{
		BettingCalendar calendar = new BettingCalendar();
		assertNotSame(null, calendar.getCurrentDate());
	}
	
	/**
	 * Valid DOB - 18 and over (by day)
	 */
	@Test
	public void testSuccessDOB1()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 0, 12);
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date dateOfBirth = BettingCalendar.getDate(2000,0,11); 
		user.setDateOfBirth(dateOfBirth);
		assertEquals(dateOfBirth, user.getDateOfBirth());
	} 
	
	/**
	 * Valid DOB - exactly 18 years 
	 */
	@Test
	public void testSuccessDOB2()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 0, 11);
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date d = BettingCalendar.getDate(2000,0,11);
		user.setDateOfBirth(d);
		assertEquals(d, user.getDateOfBirth());
	}
	
	/**
	 * Valid DOB - greater than 18 years old by month
	 */
	@Test
	public void testSuccessDOB3()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 2, 10);  //setting mocked curr Date
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date dateOfBirth = BettingCalendar.getDate(2000,1,11);
		user.setDateOfBirth(dateOfBirth);
		assertEquals(dateOfBirth, user.getDateOfBirth());
		
	}
	
	/**
	 * Testing DOB to be less than 18 years old (by day)
	 */
	@Test
	public void testFailDOB1()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 1, 10);  //setting mocked curr Date
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date dateOfBirth = BettingCalendar.getDate(2000,1,11);
		user.setDateOfBirth(dateOfBirth);
		
	}
	
	/**
	 * Testing DOB to be less than 18 years old (by month)
	 */
	@Test
	public void testFailDOB2()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 1, 10);  //setting mocked curr Date
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date dateOfBirth = BettingCalendar.getDate(2000,2,11);
		assertEquals(Misc.FAIL, user.setDateOfBirth(dateOfBirth));
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidDate()
	{
		BettingCalendar.getDate(2018, 12, 10); //wrong month
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidDate2()
	{
		BettingCalendar.getDate(2018, -1, 10); //wrong month
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidDate3()
	{
		BettingCalendar.getDate(-1, 11, 10); //wrong year
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidDate4()
	{
		BettingCalendar.getDate(1, 11, 0); //wrong day
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidDate5()
	{
		BettingCalendar.getDate(1, 11, 32); //wrong day
	}
	
	/**
	 * Testing the selection of a free account
	 */
	@Test
	public void testFreeUser()
	{
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		assertEquals(0, user.getAccount()); 
	}
	
	/**
	 * Testing the selection of a premium account
	 */
	@Test
	public void testPremiumUser()
	{
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		assertEquals(1, user.getAccount()); 
	}
	
	/**
	 * Testing the selection of a free account
	 */
	@Test
	public void testInvalidUser()
	{
		assertEquals(Misc.FAIL, user.setAccount(String.valueOf(3)));
	}
	
	@Test
	public void testValidCreditCard()
	{
		user.setCardNumber("4568822148985156");
		assertEquals("4568822148985156", user.getCardNumber());
	}
	
	@Test
	public void testValidCreditCard2()
	{
		user.setCardNumber("ejhjegf");
		assertEquals("", user.getCardNumber());
	}
	
	@Test
	public void testInValidCreditCard()
	{
		assertEquals(Misc.FAIL, user.setCardNumber("12345678912345678"));
	}
	
	@Test
	public void validExpiryDate() 
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		user.setCalendar(calendar); //set dependency
		
		Date mockedCurrDate = BettingCalendar.getDate(2012, 11, 25);
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrDate);
		
		Date d = BettingCalendar.getDate(2015, 11, 26); //setting expiry date
		user.setExpiryDate(d);
		assertEquals(d, user.getExpiryDate());
	}
	
	@Test
	public void validInvalidExpiryDate()
	{
		
		BettingCalendar calendar = mock(BettingCalendar.class);
		user.setCalendar(calendar); //set dependency
		
		Date mockedCurrDate = BettingCalendar.getDate(2012, 11, 25);
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrDate);
		
		
		Date expiryDate = BettingCalendar.getDate(2012, 11, 24);
		assertEquals(Misc.FAIL, user.setExpiryDate(expiryDate));
	}
	
	
	@Test
	public void validCVV()
	{
		user.setCvv("123");
		assertEquals("123", user.getCvv());
	}
	
	@Test
	public void invalidCVV1()
	{
		assertEquals(Misc.FAIL, user.setCvv("1235"));
	}
	
	@Test
	public void invalidCVV2()
	{
		assertEquals(Misc.FAIL, user.setCvv("ann"));
	}
	
	@Test
	public void invalidCVV3()
	{
		assertEquals(Misc.FAIL, user.setCvv(""));
	}
	
	@Test
	public void invalidCVV4()
	{
		assertEquals(Misc.FAIL, user.setCvv("a nn"));
	}
	
	@Test
	public void invalidCVV5()
	{
		assertEquals(Misc.FAIL, user.setCvv("123 456"));
	} 
		
	/**
	 * Login Success
	 */
	@Test
	public void loginSuccess()
	{
		user.setUsername("mousey");
		user.setPassword("masterpassword");
		BettingSystem.addUser(null, user);
		
		User user = bettingSystem.login(null, "mousey", "masterpassword", null);
		assertEquals(Login.LOGIN_SUCCESS, user.getLoginType());
	}
	
	/**
	 * Login Fail
	 */
	@Test
	public void loginFailure()
	{
		user.setUsername("mousey");
		user.setPassword("master*******");
		BettingSystem.addUser(null, user);
		
		User user = bettingSystem.login(null, "mousey", "masterpassword", null);
		assertEquals(Login.LOGIN_FAIL, user.getLoginType());
	}
	
	@Test
	public void testInvalidPasswordCounts()
	{
		user.setUsername("mousey");
		user.setPassword("master*******");
		BettingSystem.addUser(null, user);
		user = bettingSystem.login(null, "mousey", "masterPassword", null);
		user = bettingSystem.login(null, "mousey", "masterPassword", null);
		assertEquals(2, user.getInvalidPasswordCount());
	}
	
	@Test
	public void testInvalidPasswordCounts1()
	{
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		BettingSystem.addUser(null, user);
		user = bettingSystem.login(null, "mousey", "masterPassword", null);
		assertEquals(0, user.getInvalidPasswordCount());
	}
	
	/**
	 * Testing invalid Credentials
	 */
	@Test
	public void testInvalidLogin1()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(3);
		user.setTimeofInvalidLogin(time);
		BettingSystem.addUser(null, user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		
		User tempUser = bettingSystem.login(null, "mousey", "jehgfhjgrgfrhj", user);
		assertEquals(Login.LOGIN_FAIL, tempUser.getLoginType());
	}
	
	/**
	 * Testing locked account
	 */
	@Test
	public void testInvalidLogin2()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(3);
		user.setTimeofInvalidLogin(time);
		BettingSystem.addUser(null, user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		
		User tempUser = bettingSystem.login(null, "mousey", "masterPassword", user);
		assertEquals(Login.LOGIN_LOCKED, tempUser.getLoginType());
	}
	
	@Test
	public void testValidLogin3()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(3);
		user.setTimeofInvalidLogin(time);
		BettingSystem.addUser(null, user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 5*60*1000);
		
		User tempUser = bettingSystem.login(null, "mousey", "masterPassword", user);
		assertEquals(Login.LOGIN_SUCCESS, tempUser.getLoginType());
	}
	
	@Test
	public void testInvalidLogin4()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(2);
		BettingSystem.addUser(null, user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		
		User tempUser = bettingSystem.login(null, "mousey", "masterPassword2", user);
		assertEquals(Login.LOGIN_FAIL, tempUser.getLoginType());
	}
	
	@Test
	public void testValidLogin5()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		when(calendar.getCurrentTime()).thenReturn(time);
		
		user.setUsername("brandbir");
		user.setPassword("testingPassword");
		user.setInvalidPasswordCount(1);
		BettingSystem.addUser(null, user);
		
		User tempUser = bettingSystem.login(null, "brandbir", "masterPassword", null);
		tempUser = bettingSystem.login(null, "brandbir", "testingPassword", null);
		assertEquals(Login.LOGIN_SUCCESS, tempUser.getLoginType());
	}
	
	@Test
	public void testInvalidLogin6()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		when(calendar.getCurrentTime()).thenReturn(time);
		
		user.setUsername("brandbir");
		user.setPassword("testingPassword");
		user.setInvalidPasswordCount(1);
		BettingSystem.addUser(null, user);
		
		user = bettingSystem.login(null, "brandbir", "masterPassword", null);
		assertEquals(Login.LOGIN_FAIL, user.getLoginType());
		
		
		user = bettingSystem.login(null, "brandbir", "testingPassword2", null); //the timeOfInvalidLogin should be set
		assertEquals(Login.LOGIN_FAIL, user.getLoginType());
		
		assertEquals(time, user.getTimeofInvalidLogin()); //asserting that the mocked current time is being returned
		
		//Three consecutive invalid passwords encountered
		assertEquals(3, user.getInvalidPasswordCount());
		when(calendar.getCurrentTime()).thenReturn(time + 2*60*1000);
		
		//trying to login with valid credentials after 2 mins the account has been locked
		
		user = bettingSystem.login(null, "brandbir", "testingPassword", null);
		assertEquals(Login.LOGIN_LOCKED, user.getLoginType());

		//trying to login with invalid credentials after 3 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 3*60*1000);
		user = bettingSystem.login(null, "brandbir", "testingPassword", null);
		assertEquals(Login.LOGIN_LOCKED, user.getLoginType());
		
		//trying to login with valid credentials after 4 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		user = bettingSystem.login(null, "brandbir", "testingPassword", null);
		assertEquals(Login.LOGIN_LOCKED, user.getLoginType());
						
		//trying to login with valid credentials after 5 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 5*60*1000);
		user = bettingSystem.login(null, "brandbir", "testingPassword", null);
		assertEquals(Login.LOGIN_SUCCESS, user.getLoginType());
		
		//checking that the invalidPasswordCount is reset to 0 after a successful login by a user
		assertEquals(0, user.getInvalidPasswordCount());
	}
	
	@SuppressWarnings("unused")
	@Test (expected = Exception.class)
	public void testingFreeAccountFail() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		assertEquals(user, bettingSystem.getUser("Brandon"));
		assertEquals(1, bettingSystem.getNumberofUsers());
		
		Bet bet = new Bet(user, Bet.HIGH, 5);
	}
	
	@Test 
	public void testingFreeAccountSuccess() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		assertEquals(user, bettingSystem.getUser("Brandon"));
		assertEquals(1, bettingSystem.getNumberofUsers());
		
		Bet bet = new Bet(user, Bet.LOW, 5);
		bettingSystem.placeBet(null, bet);
		assertEquals(1, bettingSystem.getNoOfBets());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testingPremiumAccountSuccess() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.HIGH, 5);
	}
	
	@Test
	public void testingPremiumAccountSuccess2() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		@SuppressWarnings("unused")
		Bet bet = new Bet(user, Bet.LOW, 5);
	}
	
	@Test
	public void testingPremiumAccountSuccess3() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		@SuppressWarnings("unused")
		Bet bet = new Bet(user, Bet.MEDIUM, 5);
	}
	
	@Test
	public void placingBets() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		User user2 = new User();
		user2.setUsername("Andrew");
		user2.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user2);
		
		Bet bet = new Bet(user, Bet.MEDIUM, 5);
		Bet bet2 = new Bet(user2, Bet.MEDIUM, 5);
		Bet bet3 = new Bet(user, Bet.MEDIUM, 5);


		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet2);
		bettingSystem.placeBet(null, bet3);
		
		assertEquals(2, bettingSystem.getNoOfBets("Brandon"));
	}
	
	@Test
	public void validFreeUserBet() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.LOW, 5);
		bettingSystem.placeBet(null, bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test
	public void validFreeUserBet2() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		//placing a bet over 5
		Bet bet = new Bet(user, Bet.LOW, 0);
		bettingSystem.placeBet(null, bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test(expected = BettingException.class)
	public void invalidFreeUserBet() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		//placing a bet over 5
		Bet bet = new Bet(user, Bet.LOW, -1);
		bettingSystem.placeBet(null, bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	
	
	@Test(expected = BettingException.class)
	public void invalidFreeUserBet2() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		//placing a bet over 5
		Bet bet = new Bet(user, Bet.LOW, 6);
		bettingSystem.placeBet(null, bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test
	public void validNumberFreeUserBets() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.LOW, 3);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		
		assertEquals(3, bettingSystem.getNoOfBets(user.getUsername()));
	}
	
	@Test (expected = Exception.class)
	public void invalidNumberFreeUserBets() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_FREE));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.LOW, 3);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		
		assertEquals(3, bettingSystem.getNoOfBets(user.getUsername()));
	}
	
	@Test
	public void validNumberPremiumUserBets() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.MEDIUM, 500);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		
		ArrayList<Bet> bets = bettingSystem.getBets(null, user);
		assertEquals(2000, bettingSystem.getTotalAmount(bets, "Brandon"));
	}
	
	@Test (expected = Exception.class)
	public void invalidNumberPremiumUserBets() throws BettingException
	{
		user.setUsername("Brandon");
		user.setAccount(String.valueOf(User.ACCOUNT_PREMIUM));
		BettingSystem.addUser(null, user);
		
		Bet bet = new Bet(user, Bet.MEDIUM, 1500);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
		bettingSystem.placeBet(null, bet);
	}
	
	@Test
	public void getNotFoundUser()
	{
		assertEquals(null, bettingSystem.getUser("Mario"));
		assertEquals(null, bettingSystem.getLastBet("Mario"));
	}
}
