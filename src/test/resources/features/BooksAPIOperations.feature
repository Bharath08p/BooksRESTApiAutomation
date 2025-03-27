Feature: Books API CRUD operations

  Scenario Outline: Create and verify book details multiple times
    Given User creates a book with the required details for TestCase "<TestCaseName>"
    When User retrieves and verifies the book sucessfully
    Then the user deletes the book  
    And the book should be deleted successfully  

  Examples: 
      | TestCaseName                    |
      | CreateBookwithValidDataTest1    |
      | CreateBookwithValidDataTest2    |
      | CreateBookwithValidDataTest3    |
      | CreateBookwithValidDataTest4    |
      | CreateBookwithValidDataTest5    |
      | CreateBookwithValidDataTest6    |
      | CreateBookwithValidDataTest7    |
      | CreateBookwithValidDataTest8    |
      | CreateBookwithValidDataTest9    |
      | CreateBookwithValidDataTest10   |
      | CreateBookwithValidDataTest11   |
      | CreateBookwithValidDataTest12   |
      | CreateBookwithValidDataTest13   |
      | CreateBookwithValidDataTest14   |
      | CreateBookwithValidDataTest15   |
      | CreateBookwithValidDataTest16   |
      | CreateBookwithValidDataTest17   |
      | CreateBookwithValidDataTest18   |
      | CreateBookwithValidDataTest19   |
      | CreateBookwithValidDataTest20   |
      
 
  Scenario: Retrieve all books successfully
    Given Books are available in the system
    When User retrieves all books
    Then The response should contain a list of books
    And The response status code should be 200
    

  Scenario Outline: Validate book creation with invalid price values
    Given User tries to create a book with the following details:
      | name       | author      | publication      | category    | pages | price     |
      | <name>     | <author>    | <publication>    | <category>  | <pages> | <price> |
    When User submits the request to create a book
    Then the API should return an error response

  Examples:
    | name        | author       | publication      | category    | pages | price     |
    | Book A      | Author A     | Publisher A      | Fiction     | 200   | ABC       |
    | Book B      | Author B     | Publisher B      | Non-Fiction | 300   | -10       |
    | Book C      | Author C     | Publisher C      | Non-Fiction | 300   | 8,698     |
    | Book D      | Author D     | Publisher D      | Science     | 250   | $50       |
    

  Scenario Outline: Validate book creation with invalid pages values
    Given User tries to create a book with the following details:
      | name       | author      | publication      | category    | pages  | price  |
      | <name>     | <author>    | <publication>    | <category>  | <pages> | <price> |
    When User submits the request to create a book
    Then the API should return an error response

    Examples:
      | name      | author      | publication  | category    | pages   | price |
      | Book A    | Author A    | Publisher A  | Fiction     | P220    | 20.50 |
      | Book B    | Author B    | Publisher B  | Science     | ABC     | 30    |
      | Book C    | Author C    | Publisher C  | Science     | #100    | 400   |
      

  Scenario Outline: Successfully update a book's details
    Given A book already exists for test - "<TestCaseName>"
    When User updates the book details for field - "<FieldName>"
    Then The book details should be updated successfully

    Examples:
      | TestCaseName                           | FieldName        |
      | UpdateBookDetails_NameUpdate           | name             |      
      | UpdateBookDetails_AuthorUpdate         | author           |
      | UpdateBookDetails_PublicationUpdate    | publication      |
      | UpdateBookDetails_CategoryUpdate       | category         |
      | UpdateBookDetails_PagesUpdate          | pages            |
      | UpdateBookDetails_PriceUpdate          | price            |
      | UpdateBookDetails_MultipleFieldsUpdate | MultipleFields   |
      
        
 Scenario Outline: Verify invalid authentication with incorrect username or password
    Given User tries to authenticate with username "<username>" and password "<password>"
    When User sends a request to retrieve books
    Then The API should return an authentication error with status code 401
 
  Examples:
      | username     | password    |
      | invalidUser  | password123 |
      | testUser     | wrongPass   |
      | admin        | incorrect   |  