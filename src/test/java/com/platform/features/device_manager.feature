Feature: Verify Device manager functionality


  Scenario: Verify get Device manager functionality
    Given The Economy is up for actions
    When I make GET request to get device manager details for defined user
    Then I should get success status as true
    And Response should be expected as the defined JSON schema



  Scenario: Verify get Device manager functionality with new user
    Given The Economy is up for actions
    When I make POST request to create user
    And I make GET request to get device manager for the newly created user
    Then I should get success status as false
    And I should get error code as UNPROCESSABLE_ENTITY
    And Response should be expected as the defined JSON schema 
