Feature: Verify get Chain details functionality


  @sanity @chain
  Scenario: Verify auxiliary chain data
    Given The Economy is up for actions
    When I make GET request to get chain details of auxiliary chain
    Then I should get success status as true
    And Response should be expected as the defined JSON schema


  @sanity @chain
  Scenario: Verify origin chain data
    Given The Economy is up for actions
    When I make GET request to get chain details of origin chain
    Then I should get success status as true
    And Response should be expected as the defined JSON schema


  @chain
  Scenario Outline: Verify chain data with invalid chain id
    Given The Economy is up for actions
    When I make GET request to get chain details with chain as <chain id>
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
    | chain id  | error code    |
    | 1000      | NOT_FOUND     |
    | diewd     | BAD_REQUEST   |
#    | -#656     | BAD_REQUEST   |
