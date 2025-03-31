package testrunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)

@CucumberOptions(
		plugin = {"pretty"}, 
		features = "src/test/resources/features/BooksAPICRUD.feature", 
		glue = {"stepdefinitions"},
		tags = "not @Ignore")
public class BooksAPITestSuite {

}
