Feature: Verify Rule API

  @sanity @rules
  Scenario: Verify the GET Rule information
    Given The Economy is up for actions
    When I make GET request to get list of rules
    Then I should get success status as true
    And Response should be expected as the defined JSON schema
    #veriy pricer and DT rule