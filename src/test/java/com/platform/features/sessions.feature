Feature: Verify Session related functionality.




  Scenario: Verify GET Session detail for a defined user
    Given The Economy is up for actions
    When I make GET request to get session for defined session address
    Then I should get success status as true
    And Response should be expected as the defined JSON schema
    

  Scenario Outline: Verify GET Session detail for a invalid session address
    Given The Economy is up for actions
    When I make GET request to get session details with session address as <session address>
    Then I should get success status as false
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
    | session address                             | error code    |
    | d47af60e-c29e-484f-b7c1-32c637028f33        | BAD_REQUEST   |
    | 0x557e631a3d556f7ad62382fe079ed76397f02133  | NOT_FOUND     |
    | fvenrjfner                                  | BAD_REQUEST   |


    Scenario: Verify GET session list for company and verify session details also
      Given The Economy is up for actions
      When I make GET request to get transactions list for a company
      Then I should get success status as true
      And Response should be expected as the defined JSON schema
      When I make GET request to get session details for any of the company's session
      Then I should get success status as true
      And Response should be expected as the defined JSON schema


    Scenario Outline: Verify GET Session list with limit
      Given The Economy is up for actions
      When I make GET request to get Session list with limit as <limit>
      Then I should get success status as true
      And I should get session list with <limit> sessions
      And Response should be expected as the defined JSON schema

      Examples:
        | limit |
        | 1     |
        | 10    |
        | 25    |


  Scenario Outline: Verify GET Session list with invalid limit
    Given The Economy is up for actions
    When I make GET request to get Session list with limit as <limit>
    Then I should get success status as false
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
    | limit   | error code  |
    | 0       | BAD_REQUEST |
    | 26      | BAD_REQUEST |
    | abc     | BAD_REQUEST |
    | #%$^    | BAD_REQUEST |
    | -25     | BAD_REQUEST |
    | 12_12   | BAD_REQUEST |
    | 12-12   | BAD_REQUEST |
    | 25.42   | BAD_REQUEST |



  Scenario: Verify valid pagination identifier functionality
    Given The Economy is up for actions
    When I make GET request to get transactions list for a company
    Then I should get all the sessions list till last page with pagination identifier


  Scenario Outline: Verify invalid pagination identifier
    Given The Economy is up for actions
    When I make GET request to get session list with pagination identifier as <pagination identifier>
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | pagination identifier   | error code  |
      | tdst123435vev | BAD_REQUEST|
      | eyJsYXN0RXZhbHVhdGVkS2V5Ijp7InRpZCI6eyJOIjoiMTAwMSJ9LCJ1aWQiOnsiUyI6Ijk5ZWQ3MDg5LTI4YTYtNGJjNy05MTAzLTgzYjlmMGNjMThiNiJ9fSwicGFnZSI6MiwibGltaXQiOjF9 | UNPROCESSABLE_ENTITY |
  #above pagination identifier is from different economy or from different request


