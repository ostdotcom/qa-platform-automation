Feature: Verify token information.

  @sanity @token
  Scenario: Read token information: Get Token
    Given The Economy is up for actions
    When I make GET request to get token information
    Then I should get success status as true
    And Verify the origin and auxiliary chain id
    And  Response should be expected as the defined JSON schema
