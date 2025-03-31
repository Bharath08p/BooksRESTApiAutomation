package stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import pojo.Books;
import steps.BookAPISteps;
import helpers.TestDataReader;
import helpers.BookFieldUpdater;
import helpers.ScenarioContext;
import java.util.*;

public class BookStepDefinitions {
    private final BookAPISteps bookApiSteps = new BookAPISteps();
    private Books book;
    private String invalidUsername, invalidPassword;

    @DataTableType
    public Map<String, String> convert(Map<String, String> entry) {
        return entry;
    }

    private Books getBookFromTestData(String testCaseName) {
        return TestDataReader.loadTestData().stream()
                .filter(b -> b.getTestCaseName().equalsIgnoreCase(testCaseName.replace("\"", "")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test case not found: " + testCaseName));
    }

    @Given("User creates a book with the required details for TestCase {}")
    public void createBook(String testCaseName) {
        book = getBookFromTestData(testCaseName);
        int bookId = bookApiSteps.createBook(book);
        ScenarioContext.set("bookId", bookId);
        ScenarioContext.set("book", book);
        
    }

    @When("User retrieves and verifies the book successfully")
    public void retrieveTheBook() {
    	 int bookId = ScenarioContext.get("bookId", Integer.class);
    	    Response response = bookApiSteps.retrieveBook(bookId);

    	    if (response == null) {
    	        throw new AssertionError("Book with ID " + bookId + " not found! Test cannot proceed.");
    	    }

    	    bookApiSteps.verifyBookDetails(response.as(Books.class), ScenarioContext.get("book", Books.class));
    }

    @Then("the user deletes the book")
    public void deleteTheBook() {
        bookApiSteps.deleteBook(ScenarioContext.get("bookId", Integer.class));
    }

    @And("the book should be deleted successfully")
    public void theBookShouldBeDeletedSuccessfully() {
        bookApiSteps.verifyBookDeleted(ScenarioContext.get("bookId", Integer.class));
    }

    @Given("User tries to create a book with the following details:")
    public void userTriesToCreateABook(DataTable dataTable) {
        List<Map<String, String>> booksList = dataTable.asMaps(String.class, String.class);
        booksList.forEach(bookDetails -> {
            book = Books.builder()
                    .name(bookDetails.get("name"))
                    .author(bookDetails.get("author"))
                    .publication(bookDetails.get("publication"))
                    .category(bookDetails.get("category"))
                    .pages(bookDetails.get("pages"))
                    .price(bookDetails.get("price"))
                    .build();
            ScenarioContext.set("book", book);
        });
    }

    @When("User submits the request to create a book")
    public void userSubmitsRequest() {
    	int statusCode = bookApiSteps.createBookWithInvalidBookDetails(ScenarioContext.get("book", Books.class));
        ScenarioContext.set("statusCode", statusCode);
    }

    @Given("Books are available in the system")
    public void booksAreAvailableInTheSystem() {
        // Assume books exist
    }

    @When("User retrieves all books")
    public void userRetrievesAllBooks() {
        ScenarioContext.set("response", bookApiSteps.retrieveAllBooks());
    }

    @Then("The response should contain a list of books")
    public void theResponseShouldContainAListOfBooks() {
    	Response response = ScenarioContext.get("response", Response.class);
        bookApiSteps.verifyResponseContainsListOfBooks(response);
        ScenarioContext.set("statusCode", response.getStatusCode());
    }

    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        bookApiSteps.verifyResponseCode(ScenarioContext.get("statusCode", Integer.class), expectedStatusCode);
    }

    @Given("A book already exists for test - {}")
    public void aBookExistsWithTheFollowingDetails(String testCaseName) {
        book = getBookFromTestData(testCaseName);
        int bookId = bookApiSteps.createBook(book);
        ScenarioContext.set("bookId", bookId);
        ScenarioContext.set("book", book);
    }

    @When("User updates the book details for field - {}")
    public void userUpdatesTheBookDetails(String fieldName) {
        int bookId = ScenarioContext.get("bookId", Integer.class);
        Books updatedBook = ScenarioContext.get("book", Books.class);
        updatedBook.setId(bookId);

        BookFieldUpdater.updateField(updatedBook, fieldName);
        bookApiSteps.updateBook(updatedBook, bookId);
        ScenarioContext.set("book", updatedBook);
    }

    @Then("The book details should be updated successfully")
    public void theBookDetailsShouldBeUpdatedSuccessfully() {
        int bookId = ScenarioContext.get("bookId", Integer.class);
        Response response = bookApiSteps.retrieveBook(bookId);
        bookApiSteps.verifyBookDetails(response.as(Books.class), ScenarioContext.get("book", Books.class));
    }

    @Given("User tries to authenticate with username {string} and password {string}")
    public void userTriesToAuthenticate(String username, String password) {
        this.invalidUsername = username;
        this.invalidPassword = password;
    }

    @When("User sends a request to retrieve books")
    public void userSendsRequestToRetrieveBooks() {
        ScenarioContext.set("statusCode", bookApiSteps.retrieveAllBooks(invalidUsername, invalidPassword));
    }
}