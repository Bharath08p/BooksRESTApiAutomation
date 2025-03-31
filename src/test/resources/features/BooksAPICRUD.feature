Feature: Books API CRUD operations

  Scenario Outline: Create and verify book details multiple times
    Given User creates a book with the required details for TestCase "<TestCaseName>"
    When User retrieves and verifies the book successfully
    Then the user deletes the book  
    And the book should be deleted successfully  

  Examples: 
      | TestCaseName                    |
      | CreateBookWithValidDataTest1    |
      | CreateBookWithValidDataTest2    |
      | CreateBookWithValidDataTest3    |
      | CreateBookWithValidDataTest4    |
      | CreateBookWithValidDataTest5    |
      | CreateBookWithValidDataTest6    |
      | CreateBookWithValidDataTest7    |
      | CreateBookWithValidDataTest8    |
      | CreateBookWithValidDataTest9    |
      | CreateBookWithValidDataTest10   |
      | CreateBookWithValidDataTest11   |
      | CreateBookWithValidDataTest12   |
      | CreateBookWithValidDataTest13   |
      | CreateBookWithValidDataTest14   |
      | CreateBookWithValidDataTest15   |
      | CreateBookWithValidDataTest16   |
      | CreateBookWithValidDataTest17   |
      | CreateBookWithValidDataTest18   |
      | CreateBookWithValidDataTest19   |
      | CreateBookWithValidDataTest20   |
      
 
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
    Then The response status code should be 400

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
    Then The response status code should be 400

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
      | TestCaseName                          | FieldName        |
      | UpdateBookDetailsNameUpdate           | name             |      
      | UpdateBookDetailsAuthorUpdate         | author           |
      | UpdateBookDetailsPublicationUpdate    | publication      |
      | UpdateBookDetailsCategoryUpdate       | category         |
      | UpdateBookDetailsPagesUpdate          | pages            |
      | UpdateBookDetailsPriceUpdate          | price            |
      | UpdateBookDetailsMultipleFieldsUpdate | multiplefields   |
      
        
 Scenario Outline: Verify invalid authentication with incorrect username or password
    Given User tries to authenticate with username "<username>" and password "<password>"
    When User sends a request to retrieve books
    Then The response status code should be 401
 
  Examples:
      | username     | password    |
      | invalidUser  | password123 |
      | testUser     | wrongPass   |
      | admin        | incorrect   |  