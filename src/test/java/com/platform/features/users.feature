Feature: Verify all the functionality related to Users.



  Scenario: Create New user
    Given The Economy is up for actions
    When I make post request to create user
    Then I should get success status as true
    And  Response should be expected as the defined JSON schema


  Scenario: Get all users list
    Given The Economy is up for actions
    When I make GET request to get users list
    Then I should get success status as true
    And  Response should be expected as the defined JSON schema