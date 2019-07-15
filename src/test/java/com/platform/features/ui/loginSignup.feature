Feature: Verify Login and Sign up scenarios


  Background: Setting up the browser
    Given Browser is up
    And navigate to platform ost site




    Scenario: Verify Sign up
      Given User is on sign up page for platform website
      When User registered with all details
      Then User should get confirmation mail
