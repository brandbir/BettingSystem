package selenium;

import java.util.List;

import org.openqa.selenium.WebElement;

public abstract class Form {

	public abstract void visit();
	
	public abstract void close();
	
	public abstract String getFieldValue(String fieldID);
	
	public abstract String getUrl();
	
	public abstract void submit();
	
	public abstract List<WebElement> findById(String name);
	
	
	
}
