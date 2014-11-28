package betting;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserRegistrationTest
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
	public void testUserName()
	{
		User other = new User();
		user.setUsername("andrew");
		assertEquals("andrew", user.getUsername());
		other.setUsername("andrew");
		
		bettingSystem.addUser(user);
		bettingSystem.addUser(other);
		
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
	
	@Test (expected = IllegalArgumentException.class)
	public void testPasswordFail()
	{
		user.setPassword("brandon");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEmptyNameSurname()
	{
		user.setSurname("");
	}
	
	@Test
	public void testOnlyAlphabetical1()
	{
		user.setName("andrew");
		user.setSurname("birmingham");
		
		assertEquals("birmingham", user.getSurname());
		assertEquals("andrew", user.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOnlyAlphabetical2()
	{
		user.setName("34534543adkfjdnf");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testOnlyNumbers()
	{
		user.setName("34534543");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testWhiteSpace()
	{
		user.setName("dgsfng dskgjsbg ");
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
	@Test (expected = IllegalArgumentException.class)
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
	@Test (expected = IllegalArgumentException.class)
	public void testFailDOB2()
	{
		BettingCalendar mockedCalendar = mock(BettingCalendar.class);
		user.setCalendar(mockedCalendar);
		
		Date mockedCurrentDate = BettingCalendar.getDate(2018, 1, 10);  //setting mocked curr Date
		//setting mocked behaviour
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrentDate);
		
		Date dateOfBirth = BettingCalendar.getDate(2000,2,11);
		user.setDateOfBirth(dateOfBirth);
		
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
		user.setAccount("Free");
		assertEquals(0, user.getAccount()); 
	}
	
	/**
	 * Testing the selection of a premium account
	 */
	@Test
	public void testPremiumUser()
	{
		user.setAccount("Premium");
		assertEquals(1, user.getAccount()); 
	}
	
	/**
	 * Testing the selection of a free account
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidUser()
	{
		user.setAccount("Invalid");
	}
	
	@Test
	public void testValidCreditCard()
	{
		user.setCardNumber("4568822148985156");
		assertEquals("4568822148985156", user.getCardNumber());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testInValidCreditCard()
	{
		user.setCardNumber("12345678912345678");
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
	
	@Test (expected = IllegalArgumentException.class)
	public void validInvalidExpiryDate()
	{
		
		BettingCalendar calendar = mock(BettingCalendar.class);
		user.setCalendar(calendar); //set dependency
		
		Date mockedCurrDate = BettingCalendar.getDate(2012, 11, 25);
		when(user.bettingCalendar.getCurrentDate()).thenReturn(mockedCurrDate);
		
		
		Date expiryDate = BettingCalendar.getDate(2012, 11, 24);
		user.setExpiryDate(expiryDate);
	}
	
	
	@Test
	public void validCVV()
	{
		user.setCvv("123");
		assertEquals("123", user.getCvv());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCVV1()
	{
		user.setCvv("1235");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCVV2()
	{
		user.setCvv("ann");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCVV3()
	{
		user.setCvv("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCVV4()
	{
		user.setCvv("a nn");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalidCVV5()
	{
		user.setCvv("123 456");
	} 
	
	/**
	 * Login Success
	 */
	@Test
	public void loginSuccess()
	{
		user.setUsername("mousey");
		user.setPassword("masterpassword");
		bettingSystem.addUser(user);
		assertEquals(true, bettingSystem.login("mousey", "masterpassword"));
	}
	
	/**
	 * Login Fail
	 */
	@Test
	public void loginFailure()
	{
		user.setUsername("mousey");
		user.setPassword("master*******");
		bettingSystem.addUser(user);
		assertEquals(false,bettingSystem.login("mousey", "masterpassword"));
	}
	
	@Test
	public void testInvalidPasswordCounts()
	{
		user.setUsername("mousey");
		user.setPassword("master*******");
		bettingSystem.addUser(user);
		bettingSystem.login("mousey", "masterpassword");
		bettingSystem.login("mousey", "masterpassword");
		assertEquals(2, user.getInvalidPasswordCount());
	}
	
	@Test
	public void testInvalidPasswordCounts1()
	{
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		bettingSystem.addUser(user);
		bettingSystem.login("mousey", "masterPassword");
		assertEquals(0, user.getInvalidPasswordCount());
	}
	
	@Test
	public void testInvalidLogin()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(3);
		user.setTimeofInvalidLogin(time);
		bettingSystem.addUser(user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		assertEquals(false, bettingSystem.login("mousey", "jehgfhjgrgfrhj"));
	}
	
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
		bettingSystem.addUser(user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		assertEquals(false, bettingSystem.login("mousey", "masterPassword"));
	}
	
	@Test
	public void testInvalidLogin3()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		user.setUsername("mousey");
		user.setPassword("masterPassword");
		user.setInvalidPasswordCount(3);
		user.setTimeofInvalidLogin(time);
		bettingSystem.addUser(user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 5*60*1000);
		assertEquals(true, bettingSystem.login("mousey", "masterPassword"));
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
		bettingSystem.addUser(user);
		
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		assertEquals(false, bettingSystem.login("mousey", "masterPassword2"));
	}
	
	@Test
	public void testInvalidLogin5()
	{
		BettingCalendar calendar = mock(BettingCalendar.class);
		bettingSystem.setBettingCalendar(calendar); //mocking dependent calendar
		
		long time = System.currentTimeMillis();
		when(calendar.getCurrentTime()).thenReturn(time);
		
		user.setUsername("brandbir");
		user.setPassword("testingPassword");
		user.setInvalidPasswordCount(1);
		bettingSystem.addUser(user);
		bettingSystem.login("brandbir", "masterPassword");
		
		assertEquals(true, bettingSystem.login("brandbir", "testingPassword"));
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
		bettingSystem.addUser(user);
		assertEquals(false, bettingSystem.login("brandbir", "masterPassword"));
		assertEquals(false, bettingSystem.login("brandbir", "testingPassword2")); //the timeOfInvalidLogin should be set
		assertEquals(time, user.getTimeofInvalidLogin()); //asserting that the mocked current time is being returned
		
		//Three consecutive invalid passwords encountered
		assertEquals(3, user.getInvalidPasswordCount());
		when(calendar.getCurrentTime()).thenReturn(time + 2*60*1000);
		
		//trying to login with valid credentials after 2 mins the account has been locked
		assertEquals(false, bettingSystem.login("brandbir", "testingPassword"));
		
		//trying to login with invalid credentials after 3 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 3*60*1000);
		assertEquals(false, bettingSystem.login("brandbir", "tesstingPassword"));
		
		//trying to login with valid credentials after 4 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 4*60*1000);
		assertEquals(false, bettingSystem.login("brandbir", "testingPassword"));
						
		//trying to login with valid credentials after 5 mins the account has been locked
		when(calendar.getCurrentTime()).thenReturn(time + 5*60*1000);
		assertEquals(true, bettingSystem.login("brandbir", "testingPassword"));
		
		//checking that the invalidPasswordCount is reset to 0 after a successful login by a user
		assertEquals(0, user.getInvalidPasswordCount());
	}
	
	@SuppressWarnings("unused")
	@Test (expected = Exception.class)
	public void testingFreeAccountFail() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		assertEquals(user, bettingSystem.getUser("Brandon"));
		assertEquals(1, bettingSystem.getNumberofUsers());
		
		Bet bet = new Bet(user.getUsername(), Bet.HIGH, 5);
	}
	
	@Test 
	public void testingFreeAccountSuccess() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		assertEquals(user, bettingSystem.getUser("Brandon"));
		assertEquals(1, bettingSystem.getNumberofUsers());
		
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 5);
		bettingSystem.placeBet(bet);
		assertEquals(1, bettingSystem.getNoOfBets());
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testingPremiumAccountSuccess() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.HIGH, 5);
	}
	
	@Test
	public void testingPremiumAccountSuccess2() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		@SuppressWarnings("unused")
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 5);
	}
	
	@Test
	public void testingPremiumAccountSuccess3() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		@SuppressWarnings("unused")
		Bet bet = new Bet(user.getUsername(), Bet.MEDIUM, 5);
	}
	
	@Test
	public void placingBets() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		User user2 = new User();
		user2.setUsername("Andrew");
		user2.setAccount("Premium");
		bettingSystem.addUser(user2);
		
		Bet bet = new Bet(user.getUsername(), Bet.MEDIUM, 5);
		Bet bet2 = new Bet(user2.getUsername(), Bet.MEDIUM, 5);
		Bet bet3 = new Bet(user.getUsername(), Bet.MEDIUM, 5);


		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet2);
		bettingSystem.placeBet(bet3);
		
		assertEquals(2, bettingSystem.getNoOfBets("Brandon"));
	}
	
	@Test
	public void validFreeUserBet() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 5);
		bettingSystem.placeBet(bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test
	public void validFreeUserBet2() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		//placing a bet over 5
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 0);
		bettingSystem.placeBet(bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test(expected = Exception.class)
	public void invalidFreeUserBet() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		//placing a bet over 5
		Bet bet = new Bet(user.getUsername(), Bet.LOW, -1);
		bettingSystem.placeBet(bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	
	
	@Test(expected = Exception.class)
	public void invalidFreeUserBet2() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		//placing a bet over 5
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 6);
		bettingSystem.placeBet(bet);
		assertEquals(bet, bettingSystem.getLastBet(user.getUsername()));
	}
	
	@Test
	public void validNumberFreeUserBets() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 3);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		
		assertEquals(3, bettingSystem.getNoOfBets(user.getUsername()));
	}
	
	@Test (expected = Exception.class)
	public void invalidNumberFreeUserBets() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Free");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.LOW, 3);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		
		assertEquals(3, bettingSystem.getNoOfBets(user.getUsername()));
	}
	
	@Test
	public void validNumberPremiumUserBets() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.MEDIUM, 500);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		
		assertEquals(2000, bettingSystem.getTotalAmount("Brandon"));
	}
	
	@Test (expected = Exception.class)
	public void invalidNumberPremiumUserBets() throws Exception
	{
		user.setUsername("Brandon");
		user.setAccount("Premium");
		bettingSystem.addUser(user);
		
		Bet bet = new Bet(user.getUsername(), Bet.MEDIUM, 1500);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
		bettingSystem.placeBet(bet);
	}
	
	@Test
	public void getNotFoundUser()
	{
		assertEquals(null, bettingSystem.getUser("Mario"));
		assertEquals(null, bettingSystem.getLastBet("Mario"));
	}
	
}
