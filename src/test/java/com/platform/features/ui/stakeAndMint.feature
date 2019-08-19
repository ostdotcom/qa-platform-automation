Feature: Verify Stake And Mint functionality


  Background: Setting up the browser
    Given Browser is up
    And navigate to platform ost site
    And User is on login page
    When User login with correct email and password


  Scenario: Verify calculations of BTs to mint, OST and USD values
    Given User is on Stake and Mint tokens page
    And verify default values tokens to mint and USD value of it
    When User changes the amount of tokens to mint
    Then Verify that tokens value is USD also change respectively
    And Available and used OST tokens should also changed respectively


