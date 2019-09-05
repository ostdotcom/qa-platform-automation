package com.platform.steps.ui;

import com.platform.base.Base_API;
import com.platform.base.Base_UI;
import com.platform.drivers.PricePointDriver;
import com.platform.drivers.TokenDriver;
import com.platform.managers.TestDataManager;
import com.platform.pages.*;
import com.platform.steps.api.CommonSteps;
import com.platform.steps.api.PricePointSteps;
import com.platform.steps.api.TokensSteps;
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
    private static final String getApiKeyMail = "Access Your API and WebHook Keys";
    private final String ost = "OST";
    private final String usdc = "USDC";
    public double conversionFactor;
    public String tokenSymbol;
    public double toMintBts;


    private Base_UI base;

    public End2EndSteps(Base_UI base) {
        this.base = base;
    }


    //Store default stage
    double valueInUSD;
    double stakeAvailable;
    double stakeAmount;
    double remainingStake;
    String apiKey;
    String privateKey;


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


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        valueInUSD = Double.parseDouble(mintTokensPage.getUsdValue().replace(",",""));
        stakeAvailable = Double.parseDouble(mintTokensPage.getStakeAvailable().replace(",",""));
        stakeAmount = Double.parseDouble(mintTokensPage.getStakeAmount().replace(",",""));
        remainingStake = Double.parseDouble(mintTokensPage.getRemainingStake().replace(",",""));


        // Write tokens to be Minted
        mintTokensPage.writeMintedBts(mintedBts);

        if(apiKey != null && privateKey != null)
        {
            // Get the current pricer and expected amount for USD and OST
            Base_API base_api = new Base_API();
            CommonSteps commonSteps = new CommonSteps(base_api);
            commonSteps.initialize_economy(TestDataManager.data.apiEndpoint,apiKey,privateKey);


            TokensSteps tokensSteps = new TokensSteps(base_api);
            tokensSteps.get_tokens();

            TokenDriver tokenDriver = new TokenDriver();
            double conversion_factor = Double.parseDouble(tokenDriver.get_conversion_factor(base_api.response));
            String base_token = tokenDriver.get_base_token(base_api.response);

            PricePointSteps pricePointSteps = new PricePointSteps(base_api);
            pricePointSteps.get_price_point_with_aux_chain_id();

            PricePointDriver pricePointDriver = new PricePointDriver();
            double usd_price = Double.parseDouble(pricePointDriver.get_price(base_api.response, base_token,"USD"));

            //get Expected base tokens to mint
            double expected_stakeAmount = mintedBts/conversion_factor;

            double expected_usd_value = expected_stakeAmount * usd_price;


            double expected_stakeAvailable = stakeAvailable;
            double expected_remainingStake = stakeAvailable - expected_stakeAmount;

            Assert.assertEquals(Double.parseDouble(mintTokensPage.getStakeAmount().replace(",","")),expected_stakeAmount, 3);
            Assert.assertEquals(Double.parseDouble(mintTokensPage.getRemainingStake().replace(",","")),expected_remainingStake, 3);
            Assert.assertEquals(Double.parseDouble(mintTokensPage.getUsdValue().replace(",","")),expected_usd_value,3);
            Assert.assertEquals(Double.parseDouble(mintTokensPage.getStakeAvailable().replace(",","")),expected_stakeAvailable,3);

        }

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

    @And("Get the API & Secret key through mail confirmation")
    public void get_keys_through_confirmation_mail()
    {
        DevelopersPage developersPage = new DevelopersPage(base.driver);
        developersPage.clickOnGetKeysBtn();

        if(developersPage.verifyEmailSentTextVisible())
        {
            System.out.println("Get API Key mail sent");
        }
        else
        {
            Assert.fail("Email sent text not visible");
        }

        //Confirm the mail and browse the URL in the same browser
        SignupPage signupPage = new SignupPage(base.driver);
        String confirmationLink  = signupPage.getActivateAccountLink(base.newEmailId,getApiKeyMail);

        base.driver.get(confirmationLink);

        apiKey = developersPage.getApiKey();
        privateKey = developersPage.getPrivateKey();
    }


    @And("Verify default USD value and OST value")
    public void default_usd_and_ost() {

        MintTokensPage mintTokensPage = new MintTokensPage(base.driver);
        mintTokensPage.clickOnSetupMinting();

        //Store default stage
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        valueInUSD = Double.parseDouble(mintTokensPage.getUsdValue().replace(",",""));
         stakeAvailable = Double.parseDouble(mintTokensPage.getStakeAvailable().replace(",",""));
         stakeAmount = Double.parseDouble(mintTokensPage.getStakeAmount().replace(",",""));
         remainingStake = Double.parseDouble(mintTokensPage.getRemainingStake().replace(",",""));
    }
}
