Feature: Verify all the functionality related to Users.



  Scenario: Create user functionality
    Given The Economy is up for actions
    When I make post request to create user
    Then I should get success status as true
    And  Response should be expected as the defined JSON schema

