package stepDefinition;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;
import pojo.Books;
import steps.BookApiSteps;
import helpers.TestData;
import java.util.*;

public class BookStepDefinitions {
    BookApiSteps bookApiSteps = new BookApiSteps();
    private static Books book;
    private String invalidUsername;
    private String invalidPassword;
    
    @DataTableType
    public Map<String, String> convert(Map<String, String> entry) {
        return entry;
    }
    
    @Given("User creates a book with the required details for TestCase {}")
    public void createBook(String TestCaseName) {
    	TestCaseName = TestCaseName.replace("\"", "");
    	book = new Books(TestData.getName(TestCaseName),
    			TestData.getAuthor(TestCaseName),
    			TestData.getPublication(TestCaseName),
    			TestData.getCategory(TestCaseName),
    			TestData.getPages(TestCaseName),
    			TestData.getPrice(TestCaseName));
    	bookApiSteps.createBook(book);
    	bookApiSteps.verifyPostSucessResponseCode();

    }

    @When("User retrieves and verifies the book sucessfully")
    public void retrieveTheBook() {
        bookApiSteps.retrieveBook();
        bookApiSteps.verifyBookDetails(book);
    }
    
    @Then("the user deletes the book")
    public void deleteTheBook() {
        bookApiSteps.deleteBook();
    }
    
    @And("the book should be deleted successfully")
    public void theBookShouldBeDeletedSuccessfully() {
        bookApiSteps.verifyBookDeleted();
    }
    
    @Given("User tries to create a book with the following details:")
    public void userTriesToCreateABook(DataTable dataTable) {
    	List<Map<String, String>> booksList = dataTable.asMaps(String.class, String.class);
    	for (Map<String, String> bookDetails : booksList) {
            book = new Books(
                    bookDetails.get("name"),
                    bookDetails.get("author"),
                    bookDetails.get("publication"),
                    bookDetails.get("category"),
                    bookDetails.get("pages"),
                    bookDetails.get("price")
            );
    }
    }
    
    @When("User submits the request to create a book")
    public void userSubmitsRequest() {
    	bookApiSteps.createBook(book);
    }
    
    @Then("the API should return an error response")
    public void responseShouldReturnErrorMessage() {
        bookApiSteps.responseShouldIndicateAnError();
    }
    
    @Given("Books are available in the system")
    public void booksAreAvailableInTheSystem() {
        // This step assumes that books exist in the system.
    }
    
    @When("User retrieves all books")
    public void userRetrievesAllBooks() {
        bookApiSteps.retrieveAllBooks();
    }
    
    @Then("The response should contain a list of books")
    public void theResponseShouldContainAListOfBooks() {
        bookApiSteps.verifyResponseContainsListOfBooks();
    }
    
    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(Integer expectedStatusCode) {
        bookApiSteps.verifyResponseCode(expectedStatusCode);
    }
    
    @Given("A book already exists for test - {}")
    public void aBookExistsWithTheFollowingDetails(String TestCaseName)
    {
    	TestCaseName = TestCaseName.replace("\"", "");
    	book = new Books(TestData.getName(TestCaseName),
    			TestData.getAuthor(TestCaseName),
    			TestData.getPublication(TestCaseName),
    			TestData.getCategory(TestCaseName),
    			TestData.getPages(TestCaseName),
    			TestData.getPrice(TestCaseName));
    	bookApiSteps.createBook(book);
    	bookApiSteps.verifyPostSucessResponseCode();
    }
    
    @When("User updates the book details for field - {}")
    public void userUpdatesTheBookDetails(String FieldName) {
    	FieldName = FieldName.replace("\"", "");
    	switch(FieldName.toLowerCase())
    	{
    	case "name":
    		book.setName("Updated Name");
    		break;
    	case "author":
    		book.setAuthor("Updated Author");
    		break;
    	case "publication":
    		book.setPublication("Updated Publication");
    		break;
    	case "category":
    		book.setCategory("Updated Category");
    		break;
    	case "pages":
    		book.setPages("100");
    		break;
    	case "price":
    		book.setPrice("2090");
    		break;
    	case "multiplefields":
    		book.setName("Updated Name");
    		book.setAuthor("Updated Author");
    		book.setPublication("Updated Publication");
    		book.setCategory("Updated Category");
    		book.setPages("100");
    		book.setPrice("2090");
    		break;
    	default:
            throw new IllegalArgumentException("Invalid field name: " + FieldName);
    	
    	}
    	bookApiSteps.updateBook(book);
    }
    
    @Then("The book details should be updated successfully")
    public void theBookDetailsShouldBeUpdatedSuccessfully() {
    	bookApiSteps.retrieveBook();
        bookApiSteps.verifyBookDetails(book);
        
    }
    
    @Given("User tries to authenticate with username {string} and password {string}")
    public void userTriesToAuthenticate(String username, String password) {
        this.invalidUsername = username;
        this.invalidPassword = password;
    }
    
    @When("User sends a request to retrieve books")
    public void userSendsRequestToRetrieveBooks() {
       bookApiSteps.retrieveAllBooks(invalidUsername, invalidPassword);
    }
    
    @Then("The API should return an authentication error with status code {int}")
    public void theApiShouldReturnAnAuthenticationError(int expectedStatusCode) {
        bookApiSteps.verifyResponseCode(expectedStatusCode);
    }
    
    }
    
