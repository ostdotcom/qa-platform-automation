Feature: Verify Login and Sign up scenarios


  Background: Setting up the browser
    Given Browser is up
    And navigate to platform ost site


  @ui @sanity @usdcE2e
  Scenario: Perform Token setup and Stake & mint with USDC staked currency
    Given User is on sign up page for platform website
    When User registered with all details
    And User confirm the email
    And user enters the company details
    And User enter details for token economy with conversion factor as 100 BT for 1 USDC
    And User selects the OST managed address option
    Then Token setup should be started
    And Token setup should complete successfully
    When User navigates to Developers page
    And Get the API & Secret key through mail confirmation
    Then Navigate to stake and mint page
    When User enters stake and mint details to min 100 BT
    Then Stake and mint started successfully
    And Stake and mint should complete successfully


    @ui @signup
    Scenario: Verify Sign up
      Given User is on sign up page for platform website
      When User registered with all details
      And User confirm the email
      Then User should signed up successfully

    @sanity @ui @login
    Scenario: Verify login functionality
      Given User is on login page
      When User login with correct email and password
      Then User should be successfully logged in


    @ui @forgotPassword
    Scenario: Verify forgot password scenario
      Given User is on sign up page for platform website
      When User registered with all details
      And User confirm the email
      And User logout from current session
      When User click on Forgot password
      And User recovers the password
      Then Verify that user is able to login with new password
