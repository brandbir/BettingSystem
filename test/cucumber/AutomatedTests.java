package cucumber;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

	@SuppressWarnings("deprecation")
	@RunWith(Cucumber.class)
	@Cucumber.Options(format={"pretty", "html:target/cucumber"}, glue = {"stepdefs"}, features = { "test/resources/features" }, monochrome = true)
	public class AutomatedTests{
	}
