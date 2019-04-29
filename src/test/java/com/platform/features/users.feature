Feature: Verify all the functionality related to Users.


  @sanity @users
  Scenario: Create New user
    Given The Economy is up for actions
    When I make POST request to create user
    Then I should get success status as true
    And I should get the unique id of the user
    And User's status is CREATED
    And Token holder address should be null
    And Device manager address should be null
    And Recovery address should be null
    And Recovery Owner address should be null
    And Response should be expected as the defined JSON schema

  @sanity @users
  Scenario: Get all users list
    Given The Economy is up for actions
    When I make GET request to get users list
    Then I should get success status as true
    And  Response should be expected as the defined JSON schema

  @sanity @users
  Scenario: Get user's details with defined user ID
    Given The Economy is up for actions
    When I make GET request to get user details
    Then I should get success status as true
    And  type should be user
    And Response should be expected as the defined JSON schema

  @sanity @users
  Scenario: Get company user's details with defined company ID
    Given The Economy is up for actions
    When I make GET request to get company user details
    Then I should get success status as true
    And  type should be company
    And Response should be expected as the defined JSON schema

  @users
  Scenario Outline: Verify the get user details API with wrong or invalid user ID
    Given The Economy is up for actions
    When I make GET request to get user details with <user ID>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
    | user ID                                 | success status  | error code  |
    | 2d971b59-1cda-4fb4-a022-8b2fa65c7622    | false           | NOT_FOUND   |
#    | test abdsd                              | false           | NOT_FOUND   |
#    | 12345.12345_12345                       | false           | NOT_FOUND   |
#    |    2d971b59-1cda-4fb4-a022-8b2fa65c7622 | false           | NOT_FOUND   |     //not working properly as gherkin do not consider spaces
#    | 2d971b59-1cda-4fb4-a022-8b2fa65c7622    | false           | NOT_FOUND   |    // same as above
#    | avfbdf  ^!@$$@$#%&*                     | false           | NOT_FOUND   |
#    |                                         | false           | NOT_FOUND   |


  @sanity @users
  Scenario Outline: Verify the valid limit for user list
    Given The Economy is up for actions
    When I make GET request to get users list with limit as <limit>
    Then I should get success status as true
    And I should get the user list with <limit> members
    And Response should be expected as the defined JSON schema

    Examples:
    | limit |
    | 1     |
    | 10    |
    | 25    |

  @users
  Scenario Outline: Verify the invalid limit for user list
    Given The Economy is up for actions
    When I make GET request to get users list with limit as <limit>
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


    # This scenario will fetch all the members with bunch of 10-10 users. This will consume lot of time, so try to exclude from regular run

#  Scenario: Verify valid pagination identifier functionality
#    Given The Economy is up for actions
#    When I make GET request to get users list
#    Then I should get all the users list till last page with pagination identifier

  @users
  Scenario Outline: Verify invalid pagination identifier
    Given The Economy is up for actions
    When I make GET request to get users list with pagination identifier as <pagination identifier>
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
    | pagination identifier   | error code  |
    | tdst123435vev | BAD_REQUEST|
    | eyJsYXN0RXZhbHVhdGVkS2V5Ijp7InRpZCI6eyJOIjoiMTAwMSJ9LCJ1aWQiOnsiUyI6Ijk5ZWQ3MDg5LTI4YTYtNGJjNy05MTAzLTgzYjlmMGNjMThiNiJ9fSwicGFnZSI6MiwibGltaXQiOjF9 | UNPROCESSABLE_ENTITY |
  #above pagination identifier is from different economy or from different request
