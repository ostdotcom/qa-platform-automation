Feature: Verify all the functionality related to Devices

  Scenario: Create new device and get device details for defined user
    Given The Economy is up for actions
    When  I make POST request to create device with defined user
    Then I should get success status as true
    And I should get newly created device details
    And Device status should be REGISTERED
    And Response should be expected as the defined JSON schema


  Scenario Outline: Create new device with invalid user ID
    Given The Economy is up for actions
    When I make POST request to create device with user as <user ID>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | user ID                                 | success status  | error code  |
      | 2d971b59-1cda-4fb4-a022-8b2fa65c7622    | false           | NOT_FOUND   |
      | test abdsd                              | false           | NOT_FOUND   |
      | 12345.12345_12345                       | false           | NOT_FOUND   |
      | avfbdf  ^!@$$@$#%&*                     | false           | NOT_FOUND   |
#      |                                         | false           | NOT_FOUND   |


  Scenario Outline: Create new device with invalid device address
    Given The Economy is up for actions
    When I make POST request to create device with device address as <device address>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | device address                              | success status  | error code  |
      | 1Ea365269A3e6c8fa492eca9A531BFaC8bA1649W    | false           | BAD_REQUEST |
      | d971b59-1cda-4fb4-a022-8b2fa65c7622         | false           | BAD_REQUEST |
      | 12345.12345_12345                           | false           | BAD_REQUEST |
      | avfbdf  ^!@$$@$#%&*                         | false           | BAD_REQUEST |


  Scenario Outline: Create new device with invalid api signer address
    Given The Economy is up for actions
    When I make POST request to create device with api signer address as <api signer address>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | api signer address                          | success status  | error code  |
      | 1Ea365269A3e6c8fa492eca9A531BFaC8bA1649W    | false           | BAD_REQUEST |
      | d971b59-1cda-4fb4-a022-8b2fa65c7622         | false           | BAD_REQUEST |
      | 12345.12345_12345                           | false           | BAD_REQUEST |
      | avfbdf  ^!@$$@$#%&*                         | false           | BAD_REQUEST |



  Scenario: Get user device details for a defined user and device id
    Given The Economy is up for actions
    When I make GET request to get user device details for defined user and device address
    Then I should get success status as true
    And Response should be expected as the defined JSON schema



  Scenario Outline: Get user device details with invalid user ID
    Given The Economy is up for actions
    When I make GET request to get device details with user id as <user ID>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | user ID                                 | success status  | error code  |
      | 2d971b59-1cda-4fb4-a022-8b2fa65c7622    | false           | NOT_FOUND   |
      | test abdsd                              | false           | NOT_FOUND   |
      | 12345.12345_12345                       | false           | NOT_FOUND   |
      | avfbdf  ^!@$$@$#%&*                     | false           | NOT_FOUND   |


  Scenario Outline: : Get user device details with invalid device address
    Given The Economy is up for actions
    When I make GET request to get device with device address as <device address>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | device address                              | success status  | error code  |
      | 1Ea365269A3e6c8fa492eca9A531BFaC8bA1649W    | false           | BAD_REQUEST |
      | d971b59-1cda-4fb4-a022-8b2fa65c7622         | false           | BAD_REQUEST |
      | 12345.12345_12345                           | false           | BAD_REQUEST |
      | avfbdf  ^!@$$@$#%&*                         | false           | BAD_REQUEST |



  Scenario: Get user device list for a defined user
    Given The Economy is up for actions
    When I make GET request to get device lists for a defined user
    Then I should get success status as true
    And Response should be expected as the defined JSON schema



  Scenario Outline: Get user device list with invalid user ID
    Given The Economy is up for actions
    When I make GET request to get device lists with user id as <user ID>
    Then I should get success status as <success status>
    And I should get error code as <error code>
    And Response should be expected as the defined JSON schema

    Examples:
      | user ID                                 | success status  | error code  |
      | 2d971b59-1cda-4fb4-a022-8b2fa65c7622    | false           | NOT_FOUND   |
      | test abdsd                              | false           | NOT_FOUND   |
      | 12345.12345_12345                       | false           | NOT_FOUND   |
      | avfbdf  ^!@$$@$#%&*                     | false           | NOT_FOUND   |



  Scenario Outline: Get user device list with limit
    Given The Economy is up for actions
    When I make GET request to get device list with limit as <limit>
    Then I should get success status as true
    And I should get the device list with <limit> members
    And Response should be expected as the defined JSON schema

    Examples:
      | limit |
      | 1     |
      | 10    |
      | 25    |



  Scenario Outline: Get user device list with invalid limit
    Given The Economy is up for actions
    When I make GET request to get device list with limit as <limit>
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



  Scenario: Get user device list with valid pagination identifier
    Given The Economy is up for actions
    When I make GET request to get device lists for a defined user
    Then I should get all the devices list till last page with pagination identifier



  Scenario Outline: Get user device list with invalid pagination identifier
    Given The Economy is up for actions
    When I make GET request to get device list with pagination identifier as <pagination identifier>
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | pagination identifier   | error code  |
      | tdst123435vev           | BAD_REQUEST |
      | eyJsYXN0RXZhbHVhdGVkS2V5Ijp7InRpZCI6eyJOIjoiMTAwMSJ9LCJ1aWQiOnsiUyI6Ijk5ZWQ3MDg5LTI4YTYtNGJjNy05MTAzLTgzYjlmMGNjMThiNiJ9fSwicGFnZSI6MiwibGltaXQiOjF9 | UNPROCESSABLE_ENTITY |
  #above pagination identifier is from different economy or from different request



  Scenario: Get user device list with Device Address array
    Given The Economy is up for actions
    When I make GET request to get devices list with defined devices address array
    And I should get error code as true
    And Response should be expected as the defined JSON schema

