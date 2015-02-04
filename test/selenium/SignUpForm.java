package selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import betting.helper.Misc;

public class SignUpForm extends Form
{
	
	WebDriver driver;
	
	public SignUpForm(WebDriver driv)
	{
		driver = driv;
	}
	
	public void visit()
	{
		//driver.get("http://localhost:8887/BettingWebsite/index.jsp");
		driver.get(Misc.URL_MAIN_PAGE);
	}
	
	public void clickSignUpButton()
	{
		driver.findElement(By.id("signUpButton")).click();
		waitForElement("submitButton");
	}
	
	protected void waitForElement(final String elementId)
	{
		new FluentWait<WebDriver>(driver)
		.withTimeout(20,TimeUnit.SECONDS).pollingEvery(1,TimeUnit.SECONDS).until(new ExpectedCondition<Boolean>()
		{
			public Boolean apply(WebDriver wd)
			{
				boolean found = false;
				WebElement element;
				element = wd.findElement(By.id(elementId));
				
				if(element != null)
					found = true;
				
				return found;
			
			}
		});
	}

	public void close() {
		driver.quit();
	}
	
	public void populate(String username, String password, String name, String surname,
			String dob, String credCard, String expiryDate, String cvv, String type)
	{
		
		//driver.findElement(By.id("signUpButton")).click();
		//Misc.sleepProcess(1000);
		
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		
		driver.findElement(By.id("username")).sendKeys(del + username);
		driver.findElement(By.id("password")).sendKeys(del + password);
		driver.findElement(By.id("user-name")).sendKeys(del + name);
		driver.findElement(By.id("surname")).sendKeys(del + surname);
		driver.findElement(By.id("dob")).sendKeys(del + dob);
		driver.findElement(By.id("credit-card")).sendKeys(del + credCard);
		driver.findElement(By.id("expiry-date")).sendKeys(del + expiryDate);
		driver.findElement(By.id("cvv")).sendKeys(del + cvv);
		Select select = new Select(driver.findElement(By.id("premium")));
		select.selectByValue(type);
	}

	public String getFieldValue(String fieldID)
	{
		System.out.println(driver.findElement(By.id(fieldID)).getAttribute("value"));
		return driver.findElement(By.id(fieldID)).getAttribute("value");
	}
	
	public String getUrl()
	{
		return driver.getCurrentUrl();
	}
	
	public void updateField(String key, String value)
	{
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE; 
		
		if(key.equals("username"))
		{
			driver.findElement(By.id("username")).sendKeys(del + value);
		}
		else if(key.equals("password"))
		{
			driver.findElement(By.id("password")).sendKeys(del + value);
		}
		else if(key.equals("user-name"))
		{
			driver.findElement(By.id("user-name")).sendKeys(del + value);
		}
		else if(key.equals("surname"))
		{
			driver.findElement(By.id("surname")).sendKeys(del + value);
		}
		else if(key.equals("dob"))
		{
			driver.findElement(By.id("dob")).sendKeys(del + value);
		}
		else if(key.equals("credit-card"))
		{
			driver.findElement(By.id("credit-card")).sendKeys(del + value);
		}
		else if(key.equals("expiry-date"))
		{
			driver.findElement(By.id("expiry-date")).sendKeys(del + value);
		}
		else if(key.equals("cvv"))
		{
			driver.findElement(By.id("cvv")).sendKeys(del + value);
		}
		else if(key.equals("premium"))
		{
			Select select = new Select(driver.findElement(By.id(key)));
			select.deselectAll();
			select.selectByVisibleText(value); 
		}
	}
	
	public void submit()
	{
		driver.findElement(By.id("submitButton")).click();
		//Misc.sleepProcess(1000);
	}
	
	public List<WebElement> findById(String name){
		List<WebElement> paragraph = driver.findElements(By.id(name));
		return paragraph;
	}

	public boolean isSuccess()
	{
		boolean foundSuccessMessage = false;
		//Misc.sleepProcess(100);
	
		String display = driver.findElement(By.id("successfulMessage")).getCssValue("display");
		if(!display.equals("none"))
			foundSuccessMessage = true;
		
		return foundSuccessMessage;
	}

}