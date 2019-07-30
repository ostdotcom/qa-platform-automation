Feature: Verify Token setup & Stake and Mint functionality



  Background: Setting up the browser
    Given Browser is up
    And navigate to platform ost site

  @sanity @ui
  Scenario: Perform Token setup and Stake & mint with OST staked currency
    Given User is on sign up page for platform website
    When User registered with all details
    And User confirm the email
    And user enters the company details
    And User enter details for token economy with conversion factor as 100 BT for 1 OST
    And User selects the OST managed address option
    Then Token setup should be started
    And Token setup should complete successfully
    When User enters stake and mint details to min 100 BT
    Then Stake and mint started successfully
    And Stake and mint should complete successfully



  Scenario: Perform Token setup and Stake & mint with USDC staked currency
    Given User is on sign up page for platform website
    When User registered with all details
    And User confirm the email
    And user enters the company details
    And User enter details for token economy with conversion factor as 100 BT for 1 USDC
    And User selects the OST managed address option
    Then Token setup should be started
    And Token setup should complete successfully
    When User enters stake and mint details to min 100 BT
    Then Stake and mint started successfully
    And Stake and mint should complete successfully

