package steps;
import static helpers.GetGlobalVariables.*;
import pojo.Books;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.annotations.Step;
import static org.junit.Assert.*;
import java.util.*;

public class BookApiSteps {
    private static final String BASE_URL = getBaseURL(); 
    private static final String userName= getUser();
    private static final String password = getPassword();
    private int bookId;
    private int statusCode;
    private List<Map<String, Object>> bookList;
    private Response response;

    @Step("Create a new book")
    public void createBook(Books book) {
        response = SerenityRest.given()
        		.auth().basic(userName, password)
        		.contentType(ContentType.JSON)
                .body(book)
                .post(BASE_URL);

        statusCode = response.getStatusCode();
        if(statusCode==200)
        {
        bookId = response.jsonPath().getInt("id");
        }
    }
    
    @Step("Verify POST API Successfull")
    public void verifyPostSucessResponseCode()
    {
    	assertEquals(statusCode, 200);
    }
    
    @Step("User retrievs book details")
    public void retrieveBook() {
        response = SerenityRest.given()
        		.auth().basic(userName, password)
                .get(BASE_URL + "/" + bookId)
                .then()
                .statusCode(200)
                .extract().response();
    }

	@Step("Verify book details")
    public void verifyBookDetails(Books expectedBook) {
        Books actualBook = response.as(Books.class);
        assertEquals(expectedBook.getName(), actualBook.getName());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getPublication(), actualBook.getPublication());
        assertEquals(expectedBook.getCategory(), actualBook.getCategory());
        assertEquals(expectedBook.getPages(), actualBook.getPages());
        assertTrue(actualBook.getPrice().contains(expectedBook.getPrice()));
    }

    @Step("Update the book details")
    public void updateBook(Books updatedBook) {
        updatedBook.setId(bookId);

        response = SerenityRest.given()
        		.auth().basic(userName, password)
                .contentType(ContentType.JSON)
                .body(updatedBook)
                .put(BASE_URL + "/" + bookId);

        response.then().statusCode(200);
    }

    @Step("Delete the book")
    public void deleteBook() {
        response = SerenityRest.given()
        		.auth().basic(userName, password)
                .delete(BASE_URL + "/" + bookId);
        response.then().log().all();
        assertEquals("Book deletion failed", 200, response.getStatusCode());
    }

    @Step("Verify the book is deleted")
    public void verifyBookDeleted() {
        SerenityRest.given()
        		.auth().basic(userName, password)
                .get(BASE_URL + "/" + bookId)
                .then()
                .statusCode(404);
    }
    
    @Step("the response should indicate an error")
    public void responseShouldIndicateAnError() {
        assertTrue("Expected error status, but got: " + statusCode, statusCode >= 400);
    }
    
    @Step("User retrievs all books")
    public void retrieveAllBooks() {
        response = SerenityRest.given()
        		.auth().basic(userName, password)
                .get(BASE_URL)
                .then()
                .statusCode(200)
                .extract().response();
    }
    
    @Step("User retrievs all books with Authentication")
    public void retrieveAllBooks(String userName, String password) {
        response = SerenityRest.given()
        		.auth().basic(userName, password)
                .get(BASE_URL);
        statusCode = response.getStatusCode();        
    }
    
    @Step("Verify response contains list of books")
    public void verifyResponseContainsListOfBooks() {
        assertNotNull(response);
        assertEquals(200, response.statusCode());

        bookList = response.jsonPath().getList(".");
        assertTrue("Book list should not be empty", bookList.size() > 0);
    }
    
    @Step("Verify Response Code")
    public void verifyResponseCode(Integer expectedStatusCode)
    {
    	assertEquals(expectedStatusCode.intValue(), response.statusCode());
    }
    
}
