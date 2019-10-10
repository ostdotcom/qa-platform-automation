Feature: Verify execute Transactions and Get transaction functionality


  @sanity   @transactions
  Scenario: Verify Execute Transaction DIRECT-TRANSFERS: company to user transaction
    Given The Economy is up for actions
    When I make POST request of Company transfers 1000000000000000000 UBT in wei to user via direct transfer method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
    And Company's balance should be debited
    And User's balance should be credited

  @sanity @transactions
  Scenario: Verify Execute Transaction PAY: company to user transaction USD
    Given The Economy is up for actions
    When I make POST request of Company transfers 100000000000000000 USD in wei to user via pay method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
    And Company's balance should be debited
    And User's balance should be credited


  @sanity @transactions
  Scenario: Verify Execute Transaction PAY: company to user transaction GBP
    Given The Economy is up for actions
    When I make POST request of Company transfers 100000000000000000 GBP in wei to user via pay method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
    And Company's balance should be debited
    And User's balance should be credited



  @sanity @transactions
  Scenario: Verify Execute Transaction PAY: company to user transaction EUR
    Given The Economy is up for actions
    When I make POST request of Company transfers 100000000000000000 EUR in wei to user via pay method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
    And Company's balance should be debited
    And User's balance should be credited

  @transactions
  Scenario: Verify Get Transaction details functionality
    Given The Economy is up for actions
    When I make GET request to get transaction details with defined user id and transaction id
    Then I should get success status as true

  @sanity @transactions
  Scenario: Verify Get User Transactions functionality
    Given The Economy is up for actions
    When I make GET request to get Transaction list for defined user
    Then I should get success status as true

  @sanity @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with multiple transfers: company to user transaction
    Given The Economy is up for actions
    When I make POST request of Company transfers 10 UBT in wei to same user <No. of Transfers> times via direct transfer method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
    And Company's balance should be debited
    And User's balance should be credited

    Examples:
    | No. of Transfers|
    | 1               |
#    | 5               |
    | 10              |


  @sanity @transactions
  Scenario Outline: Verify Execute Transaction PAY with multiple transfers: company to user transaction
    Given The Economy is up for actions
    When I make POST request of Company transfers 10 USD in wei to same user <No. of Transfers> times via pay method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS
#    And Company's balance should be debited
#    And User's balance should be credited
    Examples:
      | No. of Transfers|
      | 1               |
#      | 5               |
      | 10              |



  @transactions
  Scenario Outline: Verify Execute Transaction PAY with multiple transfers with invalid time: company to user transaction
    Given The Economy is up for actions
    When I make POST request of Company transfers 10 USD in wei to same user <No. of Transfers> times via pay method
    Then I should get success status as false
    And I should get error code as <error code>
    Examples:
      | No. of Transfers| error code    |
      | 11              | BAD_REQUEST   |




  #Session is not allowed to get this big transaction
  @transactions
  Scenario: Verify Execute Transaction PAY for insufficient balance: company to user transaction
    Given The Economy is up for actions
    When I make POST request of Company transfer more than its balance to user via direct transfer method
    Then I should get success status as false


#  Scenario: Verify Execute Transaction DIRECT-TRANSFERS for insufficient balance: company to user transaction
  @sanity @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with valid meta property: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with <meta_name> and <meta_type> and <meta_details> via direct transfer method
    Then I should get success status as true
    Examples:
    | meta_name | meta_type         | meta_details  |
    | test      | user_to_user      | memo field to add additional info about the transaction_Max length 120 characters numbers alphabets spaces _-_-_ allowed  |
    | _- a1     | user_to_company   | test details  |
    | Max length 25 characters | user_to_company   | test_details- 12  |

    # invalid cases
  @transactions
  Scenario: Verify Execute Transaction PAY will not work with direct transfer's rule: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with direct transfer's rule via pay method
    Then I should get success status as false

  @transactions
  Scenario: Verify Execute Transaction DIRECT-TRANSFERS will not work with pay's rule: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with pay's rule via Direct transfer method
    Then I should get success status as false

  @transactions
  Scenario: Verify that Execute Transaction DIRECT-TRANSFERS will fail if from is userId in place of Company: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction from user's id or company to user transaction via direct transfer method
    Then I should get success status as false

  @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with invalid to rule address: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with rule address as <Rule Address> via direct method
    Then I should get success status as false

    Examples:
    | Rule Address                                |
    | 0xfae609af29acc68a291b8e63a442c93c2adc05d8  |
    | abcd123                                     |
    | d47af60e-c29e-484f-b7c1-32c637028f33        |

  @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with invalid to token holder address: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with token holder address as <Token Holder> via direct method
    Then I should get success status as false

    Examples:
      | Token Holder                                |
      | 0xb06a267c9c13295ca81b94394f3328cae46d60dd  |
      | abcd123                                     |
      | d47af60e-c29e-484f-b7c1-32c637028f33        |

  @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with invalid method name: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with method name as <Method name> via direct method
    Then I should get success status as false

    Examples:
      | Method name                        |
      | pay                                |
      | Direct Trasnfer                    |
      | test $ % ^                         |
      | d47af60e-c29e-484f-b7c1            |


  @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with invalid amount: company to user transaction
    Given The Economy is up for actions
    When I make POST request of execute transaction with amount as <amount> via direct transfer method
    Then I should get success status as false

    Examples:
      | amount          |
      | kemkv           |
      | 1e10            |
      | $#%#$           |

    # This is same as Direct transfer so no need for duplicating it

#  Scenario: Verify Execute Transaction PAY with invalid from user id: company to user transaction
#  Scenario: Verify Execute Transaction PAY with invalid to rule address: company to user transaction
#  Scenario: Verify Execute Transaction PAY with invalid to token holder address: company to user transaction


  @transactions
  Scenario: Verify Execute Transaction PAY with direct transfer's method name: company to user transaction
    Given The Economy is up for actions
    When I make POST request of execute transaction with method name directTransfers via pay method
    Then I should get success status as false

  @transactions
  Scenario Outline: Verify Execute Transaction PAY with invalid pricer: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with pricer as <pricer> via pay method
    Then I should get success status as false

    Examples:
    | pricer           |
    | 0                |
    | efjvoiejr        |
    | $@#RegeR%$       |

  @transactions
  Scenario Outline: Verify Execute Transaction PAY with invalid currency: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with currency as <currency> via pay method
    Then I should get success status as false

    Examples:
     | currency   |
     | 123        |
     | INR        |
     | OST        |
     | BTC        |


    # remaining meta property test cases

  @transactions
  Scenario Outline: Verify Execute Transaction PAY with invalid meta_name: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with <meta_name> and company_to_user and meta details via direct transfer method
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | meta_name                               | error code    |
      | More than Max length 25 characters      | BAD_REQUEST   |
      | !@##$%^^&*(*&                           | BAD_REQUEST   |

  @transactions
  Scenario Outline: Verify Execute Transaction DIRECT-TRANSFERS with invalid meta type: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with test name and <meta_type> and meta details via direct transfer method
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | meta_type                               | error code    |
      | company_to_company                      | BAD_REQUEST   |
      | companytouser                           | BAD_REQUEST   |
      | 1234company                             | BAD_REQUEST   |


  @transactions
  Scenario Outline: Verify Execute Transaction PAY with invalid meta details: company to user transaction
    Given The Economy is up for actions
    When I make POST request to execute transaction with test name and company_to_user and <meta_details> via direct transfer method
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | meta_details                            | error code    |
      | company^$%to^company                    | BAD_REQUEST   |
      | More than memo field to add additional info about the transaction_Max length 120 characters numbers alphabets spaces _-_-_ allowed   |  BAD_REQUEST   |

    # Get transaction details

  @transactions
  Scenario Outline: Verify Get Transaction Details with invalid transaction ID
    Given The Economy is up for actions
    When I make GET request to get transaction details with defined user and transaction id as <transaction id>.
    Then I should get success status as false
    And Response should be expected as the defined JSON schema

    Examples:
    | transaction id                              |
    | d47af60e-c29e-484f-b7c1-32c637028f33        |
    | 0x557e631a3d556f7ad62382fe079ed76397f02133  |
    | fvenrjfner                                  |



    # Get User Transactions
  @sanity @transactions
  Scenario: Verify all transactions count should be same with filter of all statuses
    Given The Economy is up for actions
    When I make GET request to get Transaction list for defined user
    Then I should get success status as true
    And I should get total transaction count
    When I make GET request to get transactions with filter as all the statuses
    Then I should get success status as true
    And I should get the same transaction count as early

  @sanity @transactions
  Scenario Outline: Verify Get User Transactions functionality with limit
    Given The Economy is up for actions
    When I make GET request to get transaction list with limit as <limit>
    Then I should get success status as true
    And I should get the Transaction list with number of transaction as <limit>
    And Response should be expected as the defined JSON schema

    Examples:
      | limit |
      | 1     |
      | 10    |
      | 25    |


  @transactions
  Scenario Outline: Verify Get User Transactions functionality with invalid limit
    Given The Economy is up for actions
    When I make GET request to get transaction list with limit as <limit>
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

  @transactions
  Scenario Outline: Verify Get User Transactions functionality with invalid pagination identifier
    Given The Economy is up for actions
    When I make GET request to get Transaction list with pagination identifier as <pagination identifier>
    Then I should get success status as false
    And I should get error code as <error code>

    Examples:
      | pagination identifier   | error code  |
      | tdst123435vev           | BAD_REQUEST|
      | eyJmcm9tIjozLCJsaW1pdCI6MSwibWV0YV9wcm9wZXJ0eSI6W10sIn23YXR1cyI6W119 | BAD_REQUEST|
  # Need to add different economy's pagination identifier. as of now just added wrong syntext


  # This is not verifying the result with filter of meta property. Just verifying API is giving error or not
  @sanity @transactions
  Scenario: Verify Get User Transactions functionality with multiple meta properties
    Given The Economy is up for actions
    When I make GET request to get transaction list with multiple meta properties
      | meta_name   | meta_type         | meta_details      |
      | test        | user_to_user      | memo field to ad  |
      | _- a1       | user_to_company   | test details      |
      | acters      | user_to_company   | test_details- 12  |
    Then I should get success status as true

#  @transactions
#  Scenario: Verify pagination identifier for user
#    Given The Economy is up for actions
#    When I make GET request to get transactions list details with defined user id
#    Then I should get full list of transaction with pagination identifier


  
  @usertouser
  Scenario: Verify user to user transaction
    Given The Economy is up for actions
    And User is in activated state
    When I make POST request of user transfers 10 UBT in wei to another user vie direct transfer method
    Then I should get success status as true
    And I should get Transaction status as SUCCESS


