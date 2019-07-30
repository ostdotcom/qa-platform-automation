package com.platform.steps.ui;

import com.platform.base.Base_UI;
import com.platform.pages.PlatformWeb;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StakeAndMintSteps {

    private Base_UI base;
    

    public StakeAndMintSteps(Base_UI base) {
        this.base = base;
    }

    @Given("^User is on Stake and Mint tokens page$")
    public void navigate_to_stake_mint_page() {
        
    }

    @And("^verify default values tokens to mint and USD value of it$")
    public void verifyDefaultValuesTokensToMintAndUSDValueOfIt() {
    }

    @When("^User changes the amount of tokens to mint$")
    public void userChangesTheAmountOfTokensToMint() {
    }

    @Then("^Verify that tokens value is USD also change respectively$")
    public void verifyThatTokensValueIsUSDAlsoChangeRespectively() {
    }

    @And("^Available and used OST tokens should also changed respectively$")
    public void availableAndUsedOSTTokensShouldAlsoChangedRespectively() {
    }
}

