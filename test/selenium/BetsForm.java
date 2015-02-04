package selenium;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import betting.helper.Misc;

public class BetsForm extends Form
{

	WebDriver driver;
	
	public BetsForm(WebDriver driv)
	{
		this.driver = driv;
	}
	@Override
	public void visit()
	{
		//driver.get("http://localhost:8887/BettingWebsite/usersection.jsp");
		driver.get(Misc.URL_BETTING_PAGE);
	}

	@Override
	public void close()
	{
		driver.quit();
	}
	
	public void populate(int riskLevel, int amount)
	{
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		
		Select select = new Select(driver.findElement(By.id("riskLevel")));
		select.selectByValue(Integer.toString(riskLevel));
		
		driver.findElement(By.id("amount")).sendKeys(del + amount);
	}

	@Override
	public String getFieldValue(String fieldID)
	{
		//Misc.sleepProcess(1000);
		return driver.findElement(By.id(fieldID)).getAttribute("value");
	}

	@Override
	public String getUrl()
	{
		return driver.getCurrentUrl();
	}

	@Override
	public void submit()
	{
		driver.findElement(By.id("betSubmitButton")).click(); 
	}

	@Override
	public List<WebElement> findById(String name)
	{
		List<WebElement> paragraph = driver.findElements(By.id(name));
		return paragraph;
	}
	
	public void logout()
	{
		WebElement element = (new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(By.id("logoutButton"))));
		element.click();
	}
	
	public boolean isDialogPresent(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.alertIsPresent());
		
		boolean alertPresent = true;
		
		try 
		{ 
			driver.switchTo().alert(); 
		}
		catch (NoAlertPresentException Ex) 
		{ 
			alertPresent = false;
		}
		
		return alertPresent;
	}
	
	public void acceptAlert()
	{
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
}
