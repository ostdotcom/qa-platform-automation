Feature: Verify API key and secret key and endpoint scenarios


  @sanity @apiCred
  Scenario: Verify any API with wrong API Key
    Given The economy with wrong API key is setup
    When I make POST request to create user
    Then I should get success status as false
    And I should get error code as UNAUTHORIZED
    And Response should be expected as the defined JSON schema


  @sanity @apiCred
  Scenario: Verify any API with wrong Secret key
    Given The economy with wrong Secret key is setup
    When I make GET request to get users list
    Then I should get success status as false
    And I should get error code as UNAUTHORIZED
    And Response should be expected as the defined JSON schema