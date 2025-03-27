package TestRunnerAPI;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)

@CucumberOptions(
		plugin = {"pretty"}, 
		features = "src/test/resources/features/BooksAPIOperations.feature", 
		glue = {"stepDefinition"},
		tags = "not @Ignore")
public class BooksAPITestSuite {

}
