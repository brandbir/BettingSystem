package betting.models;

import java.util.Calendar;
import java.util.Date;

public class User
{
	//Dependency Class 'CalendarYear' - This means that User Class depends on
	//CalendarYear Class which can be mocked
	
	public static final int ACCOUNT_FREE	= 0;
	public static final int ACCOUNT_PREMIUM = 1;
	public static final int ACCOUNT_UNKNOWN	= -1;
	
	public BettingCalendar bettingCalendar = null;
	
	private String username;
	private String password;
	private String name;
	private String surname;
	private Date dateOfBirth;
	private int premium;
	private String cardNumber;
	private Date expiryDate;
	private String cvv;
	private int invalidPasswordCount;
	private long timeOfInvalidLogin;
	private Login loginType;
	
	public User()
	{
		this.bettingCalendar = new BettingCalendar();
		this.invalidPasswordCount = 0;
		this.username = "";
		this.password = "";
		this.name = "";
		this.surname = "";
		this.dateOfBirth = null;
		this.premium = ACCOUNT_UNKNOWN;
		this.cardNumber = "";
		this.expiryDate = null;
		this.cvv = "";
	}
	
	public void setCalendar(BettingCalendar calendar)
	{
		this.bettingCalendar = calendar;
	}
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * @param username the username to set for the user
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	
	/**
	 * @param password the password to set for the user
	 */
	public void setPassword(String password)
	{
		if(validatePassword(password))
			this.password = password;
		else throw new IllegalArgumentException("Invalid Password");
	}
	
	/**
	 * @return the name of the user
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * @param name the name to set for the user
	 */
	public void setName(String name)
	{
		if(validateNameSurname(name))
			this.name = name;
		else throw new IllegalArgumentException("Invalid name");
	}
	
	/**
	 * @return surname of user
	 */
	public String getSurname()
	{
		return surname;
	}
	
	/**
	 * @param surname  surname to set for the user
	 */
	public void setSurname(String surname)
	{
		if(validateNameSurname(surname))
			this.surname = surname;
		else throw new IllegalArgumentException("Invalid Surname");
	}
	
	/**
	 * @return dateOfBirth of user
	 */
	public Date getDateOfBirth()
	{
		return dateOfBirth;
	}
	
	/**
	 * @param dateOfBirth dateOfBirth to be set for the user
	 */
	public void setDateOfBirth(Date dateOfBirth)
	{
		if(validateDOB(dateOfBirth))
			this.dateOfBirth = dateOfBirth;
		
		else throw new IllegalArgumentException("Invalid Date");
	} 
	
	/**
	 * @return True if premium account or false if free account
	 */
	public int getAccount()
	{
		return premium;
	}
	
	public String getAccountDescription()
	{
		if(premium == User.ACCOUNT_PREMIUM)
			return "Premium";
		else
			return "Free";
	}
	
	/**
	 * @param type of user account
	 */
	public void setAccount(int premium)
	{
		if(premium == ACCOUNT_FREE || premium == ACCOUNT_PREMIUM)
			this.premium = premium;
			
		else
			throw new IllegalArgumentException("Account can be Free or Premium");
	}
	
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber()
	{
		return cardNumber;
	}
	
	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber)
	{
		if(luhnsalgorithm(cardNumber))
			this.cardNumber = cardNumber;
		else
			throw new IllegalArgumentException("Invalid Credit Card Number"); 
	}
	
	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate()
	{
		return expiryDate;
	}
	
	/**
	 * 
	 * @return the number of incorrect password counts
	 */
	public int getInvalidPasswordCount()
	{
		return this.invalidPasswordCount;
	}
	
	public void setInvalidPasswordCount(int count)
	{
		this.invalidPasswordCount = count;
	}
	
	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate)
	{
		if(validateExpiryDate(expiryDate))
			this.expiryDate = expiryDate;
		else throw new IllegalArgumentException("Invalid Expiry Date");
	}
	
	
	/**
	 * @return the cvv
	 */
	public String getCvv()
	{
		return cvv;
	}
	
	/**
	 * @param cvv the cvv to set
	 */
	public void setCvv(String cvv)
	{
		if(validCVV(cvv))
			this.cvv = cvv;
		else throw new IllegalArgumentException("Invalid CVV");
	}
	
	public void setTimeofInvalidLogin(long time)
	{
		this.timeOfInvalidLogin = time;
	}
	
	public long getTimeofInvalidLogin()
	{
		return this.timeOfInvalidLogin;
	}
	
	public Login getLoginType()
	{
		return loginType;
	}

	public void setLoginType(Login loginType)
	{
		this.loginType = loginType;
	}
	
	public boolean validatePassword(String password)
	{
		if(password.length() >= 8)
			return true;
		else
			return false;
	}
	
	public boolean validateNameSurname(String name)
	{
		if(name == null || name.isEmpty())
			return false;
		else 
		{
		if(!name.contains(" ") && isAlphabetical(name))
			return true;
		}
		return false;	
	}
	
	public boolean validateDOB(Date dateOfBirth)
	{
		Calendar a = Calendar.getInstance();
		a.setTime(dateOfBirth);

		Calendar b = Calendar.getInstance();
		b.setTime(bettingCalendar.getCurrentDate());

		int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
		if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH)
				|| (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a
						.get(Calendar.DATE) > b.get(Calendar.DATE)))
		{
			diff--;
		}
		
		if(diff >= 18)
			return true;
		else 
			return false;
		
	}
	
	public boolean validateExpiryDate(Date expiryDate) 
	{
		Date currentDate = bettingCalendar.getCurrentDate();
		if (expiryDate.compareTo(currentDate) > 0)
			return true;
		else
			return false;
	}
	
	public boolean validCVV(String cvv)
	{ 
		if(!isAlphabetical(cvv) && !cvv.contains(" "))
		{
			if(cvv.length() <= 3 && cvv.length() > 0)
				return true;
		}
		return false;
	}
	
	public boolean isAlphabetical(String s)
	{
		return s.matches("[a-zA-Z]+");
	}
	
	public static boolean luhnsalgorithm(String creditNumberArray) {
			
		int[] digits = new int[creditNumberArray.length()];
		
		for(int i = 0; i < creditNumberArray.length(); i++)
		{
			digits[i] = Integer.parseInt(creditNumberArray.substring(i, i + 1));
		}

		int sum = 0;
		int length = digits.length;
		for (int i = 0; i < length; i++)
		{

			// get digits in reverse order
			int digit = digits[length - i - 1];

			// every 2nd number multiply with 2
			if (i % 2 == 1)
			{
				digit *= 2;
			}
			sum += digit > 9 ? digit - 9 : digit;
		}
		return sum % 10 == 0;
	}
}
