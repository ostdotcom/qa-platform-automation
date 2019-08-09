package com.platform.steps.ui;

import com.platform.base.Base_UI;
import com.platform.pages.*;
import com.platform.utils.CommonUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class End2EndSteps  {

    private static final String companyName = "Test Company";
    private static final String initialtokenSetuptext = "Initialization";
    private static final String initialStakeAndMintText = "Accepting stake request";
    private final String ost = "OST";
    private final String usdc = "USDC";
    public double conversionFactor;
    public String tokenSymbol;
    public double toMintBts;


    private Base_UI base;


    public End2EndSteps(Base_UI base) {
        this.base = base;
    }


    @And("^User enter details for token economy with conversion factor as (.+) BT for 1 (.+)$")
    public void token_details(double cf, String stakeCurrency) {

        conversionFactor = cf;
        CommonUtils commonUtils = new CommonUtils();


        tokenSymbol = "A"+commonUtils.getAlphaNumericString(3);

        TokenSetupPage tokenSetupPage = new TokenSetupPage(base.driver);
        tokenSetupPage.writeTokenName(tokenSymbol);
        tokenSetupPage.writeTokenSymbol(tokenSymbol);
        if(stakeCurrency.equals(usdc))
        {
            tokenSetupPage.clickOnUseUSDC();
        }
        tokenSetupPage.clickOnEditBtn();
        tokenSetupPage.numberOfBTs(conversionFactor);
        tokenSetupPage.clickOnProceed();
    }

    @Then("^Token setup should be started$")
    public void verify_token_setup_started() {

        TokenDeployPage tokenDeployPage = new TokenDeployPage(base.driver);
        tokenDeployPage.verifyCurrentText(initialtokenSetuptext);

        System.out.println("Current Percentage: "+tokenDeployPage.getCurrentPercentage());
    }

    @And("^Token setup should complete successfully$")
    public void tokenSetupShouldCompleteSuccessfully() {

        TokenDeployPage tokenDeployPage = new TokenDeployPage(base.driver);
        tokenDeployPage.waitTillTokenSetupCompleted();
        tokenDeployPage.clickOnMintTokenPB();
    }

    @When("^User enters stake and mint details to min (.+) BT$")
    public void enter_stake_and_mint(double mintedBts) {

        toMintBts = mintedBts;
        MintTokensPage mintTokensPage = new MintTokensPage(base.driver);
        mintTokensPage.clickOnSetupMinting();
        mintTokensPage.writeMintedBts(mintedBts);
        mintTokensPage.clickOnMintTokenBtn();
    }

    @Then("^Stake and mint started successfully$")
    public void stakeAndMintStartedSuccessfully() {

        MintProgressPage mintProgressPage = new MintProgressPage(base.driver);
        mintProgressPage.verifyCurrentText(initialStakeAndMintText);

        System.out.println("Current Percentage: "+mintProgressPage.getCurrentPercentage());
        
    }

    @And("^Stake and mint should complete successfully$")
    public void verify_successful_stake_mint() {

        MintProgressPage mintProgressPage = new MintProgressPage(base.driver);
        mintProgressPage.waitTillStakeAndMintCompleted();

        //Assert.assertEquals(mintProgressPage.getTotalTokenMinted(),toMintBts);

    }

    @And("^user enters the company details$")
    public void userEntersTheCompanyDetails() {

        CompanyInformationPage companyInformationPage = new CompanyInformationPage(base.driver);
        companyInformationPage.writeCompanyName(companyName);

        companyInformationPage.clickOnConfirmBtn();
    }

    @And("^User selects the OST managed address option$")
    public void select_ost_managed_address() {

        TokenSetupPage tokenSetupPage = new TokenSetupPage(base.driver);
        tokenSetupPage.clickOnProceedWithOst();
    }
}
