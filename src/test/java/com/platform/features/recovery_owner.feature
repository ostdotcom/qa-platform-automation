Feature: Verify Recovery owner functionality

  @sanity @recovery_owner
  Scenario: Verify recovery owner functionality for defined user
    Given The Economy is up for actions
    When I make GET request to get recovery owner details for defined user
    Then I should get success status as true
    And Response should be expected as the defined JSON schema



  @recovery_owner
  Scenario Outline: Verify recovery owner functionality for defined user
    Given The Economy is up for actions
    When I make GET request to get recovery owner details for user with recovery owner address as <recovery owner address>
    Then I should get success status as false
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
    | recovery owner address                       | error code   |
    | 0xfae609af29acc68a291b8e63a442c93c2adc05d8   | NOT_FOUND    |
    | d47af60e-c29e-484f-b7c1-32c637028f33         | BAD_REQUEST  |
#    | jbvervjrvijeverviorjvierjj r3e3 fwef         | BAD_REQUEST  |
