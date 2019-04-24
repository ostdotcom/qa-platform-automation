Feature: Verify the functionality of Price point module.



  @sanity @pricePoint
  Scenario: Verify the price point for defined chain id
    Given The Economy is up for actions
    When I make GET request to get price point details with auxiliary chain id
    Then I should get success status as true
    And Response should be expected as the defined JSON schema



  @pricePoint
  Scenario Outline: Verify the price point for invalid chain id
    Given The Economy is up for actions
    When I make GET request to get price point detail with chain id as <chain id>
    Then I should get success status as false
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
    | chain id  | error code  |
    | 1000      | NOT_FOUND   |
    | dsvf      | BAD_REQUEST |
    | 3         | NOT_FOUND   |
