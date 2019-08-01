Feature: Verify Login and Sign up scenarios


  Background: Setting up the browser
    Given Browser is up
    And navigate to platform ost site



    @ui
    Scenario: Verify Sign up
      Given User is on sign up page for platform website
      When User registered with all details
      And User confirm the email
      Then User should signed up successfully

    @sanity @ui @test
    Scenario: Verify login functionality
      Given User is on login page
      When User login with correct email and password
      Then User should be successfully logged in


    @ui
    Scenario: Verify forgot password scenario
      Given User is on sign up page for platform website
      When User registered with all details
      And User confirm the email
      And User logout from current session
      When User click on Forgot password
      And User recovers the password
      Then Verify that user is able to login with new password
