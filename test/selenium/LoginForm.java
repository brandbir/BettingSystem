package selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import betting.helper.Misc;

public class LoginForm extends Form
{

	WebDriver driver;
	
	public LoginForm(WebDriver driver)
	{
		this.driver = driver;
	}
	
	@Override
	public void visit()
	{
		//driver.get("http://localhost:8887/BettingWebsite/index.jsp");
		driver.get(Misc.URL_MAIN_PAGE);
	}

	@Override
	public void close()
	{
		driver.quit();
	}
	
	public void populate(String username, String password)
	{
		String del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
		
		driver.findElement(By.id("usernameLogin")).sendKeys(del + username);
		driver.findElement(By.id("passwordLogin")).sendKeys(del + password);
	}
	
	public String getLoginMessage()
	{
		return driver.findElement(By.id("loginMessage")).getText();
	}

	@Override
	public String getFieldValue(String fieldID)
	{
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
		driver.findElement(By.id("loginButton")).submit();
	}
	
	@Override
	public List<WebElement> findById(String name)
	{
		List<WebElement> paragraph = driver.findElements(By.id(name));
		return paragraph;
	}
}
