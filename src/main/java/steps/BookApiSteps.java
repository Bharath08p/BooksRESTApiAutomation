package steps;

import pojo.Books;
import static helpers.GetGlobalVariables.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

public class BookAPISteps {
    private static final String BASE_URL = getBaseURL(); 
    private static final String USERNAME = getUser();
    private static final String PASSWORD = getPassword();
    
    private static final int SUCCESS_STATUS = 200;
    private static final int NOT_FOUND_STATUS = 404;
    

    @Step("Create a new book")
    public int createBook(Books book) {
    	 Response response = SerenityRest.given()
    	            .auth().basic(USERNAME, PASSWORD)
    	            .contentType(ContentType.JSON)
    	            .body(book)
    	            .post(BASE_URL);

    	    int statusCode = response.getStatusCode();
    	    if (statusCode != SUCCESS_STATUS) {
    	        throw new AssertionError("Failed to create book! Expected 200 but got " + statusCode + 
    	                                 ". Response: " + response.getBody().asString());
    	    }

    	    return response.jsonPath().getInt("id");
    }

    @Step("Retrieve book details")
    public Response retrieveBook(int bookId) {
    	Response response = SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .get(BASE_URL + "/" + bookId);

        int statusCode = response.getStatusCode();
        if (statusCode == NOT_FOUND_STATUS) {
            return null;
        } else if (statusCode != SUCCESS_STATUS) {
            throw new AssertionError("Failed to retrieve book! Expected 200 but got " + statusCode);
        }
        
        return response;
    }

    @Step("Verify book details")
    public void verifyBookDetails(Books actualBook, Books expectedBook) {
        assertNotNull("Book retrieval failed", actualBook);
        assertEquals("Book name mismatch", expectedBook.getName(), actualBook.getName());
        assertEquals("Author mismatch", expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals("Publication mismatch", expectedBook.getPublication(), actualBook.getPublication());
        assertEquals("Category mismatch", expectedBook.getCategory(), actualBook.getCategory());
        assertEquals("Pages mismatch", expectedBook.getPages(), actualBook.getPages());
        assertTrue("Price mismatch", actualBook.getPrice().contains(expectedBook.getPrice()));
    }

    @Step("Update book details")
    public void updateBook(Books updatedBook, int bookId) {
    	Response response = SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(updatedBook)
                .put(BASE_URL + "/" + bookId);

        int statusCode = response.getStatusCode();
        if (statusCode != SUCCESS_STATUS) {
            throw new AssertionError("Failed to update book! Expected 200 but got " + statusCode +
                                     ". Response: " + response.getBody().asString());
        }
    }

    @Step("Delete the book")
    public void deleteBook(int bookId) {
    	Response response = SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .delete(BASE_URL + "/" + bookId);

        int statusCode = response.getStatusCode();
        if (statusCode != SUCCESS_STATUS) {
            throw new AssertionError("Failed to delete book! Expected 200 but got " + statusCode);
        }
    }

    @Step("Verify the book is deleted")
    public void verifyBookDeleted(int bookId) {
        SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .get(BASE_URL + "/" + bookId)
                .then()
                .statusCode(NOT_FOUND_STATUS);
    }

    @Step("Retrieve all books")
    public Response retrieveAllBooks() {
        return SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .get(BASE_URL)
                .then()
                .statusCode(SUCCESS_STATUS)
                .extract().response();
    }

    @Step("Retrieve all books with authentication")
    public int retrieveAllBooks(String username, String password) {
        return SerenityRest.given()
                .auth().basic(username, password)
                .get(BASE_URL)
                .getStatusCode();
    }

    @Step("Verify response contains a list of books")
    public void verifyResponseContainsListOfBooks(Response response) {
        assertNotNull("Response is null", response);
        assertEquals("Unexpected response status", SUCCESS_STATUS, response.statusCode());

        List<Map<String, Object>> bookList = response.jsonPath().getList(".");
        assertFalse("Book list should not be empty", bookList.isEmpty());
    }

    @Step("Verify response code")
    public void verifyResponseCode(int actualStatusCode, int expectedStatusCode) {
        assertEquals("Response code mismatch", expectedStatusCode, actualStatusCode);
    }

    @Step("Create a new book with invalid book details")
    public int createBookWithInvalidBookDetails(Books book) {
        return SerenityRest.given()
                .auth().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(book)
                .post(BASE_URL)
                .getStatusCode();
    }
}