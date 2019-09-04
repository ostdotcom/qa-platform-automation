package com.platform.steps.ui;

import com.ost.services.OSTAPIService;
import com.platform.base.Base_UI;
import com.platform.constants.Constant;
import com.platform.managers.TestDataManager;
import com.platform.pages.LHSMenu;
import com.platform.utils.BrowserFactory;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CommonSteps {


    private Base_UI base;

    public CommonSteps(Base_UI base) {
        this.base = base;
    }
    @Given("^Browser is up$")
    public void setupBrowser() {

        System.out.println("In Browser up function");
        // Get browser name from command line or user default as per Constant.java
        String browserName = Constant.BROWSER;

        switch (browserName.toLowerCase())
        {
            case Constant.BROWSER_SPECIFICATION.CHROME:
                base.driver = BrowserFactory.initChromeBrowser();
                break;

            case Constant.BROWSER_SPECIFICATION.FIREFOX:
                base.driver = BrowserFactory.initFirefoxBrowser();
                break;

            case Constant.BROWSER_SPECIFICATION.IE:
                base.driver = BrowserFactory.initIEBrowser();
                break;

            case Constant.BROWSER_SPECIFICATION.SAFARI:
                base.driver = BrowserFactory.initSafariBrowser();
                break;

            default:
                throw new WebDriverException("Given browser type is wrong. Please use one of these browser name. "+Constant.BROWSER_SPECIFICATION.CHROME+ " or "+Constant.BROWSER_SPECIFICATION.FIREFOX+" or "+Constant.BROWSER_SPECIFICATION.IE);
        }

        System.out.println("Browser is up");
        base.driver.manage().window().setSize(new Dimension(1280, 1024));
        base.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS) ;
    }


    @And("^navigate to platform ost site$")
    public void navigateToPlatformOstSite() {
        base.driver.get(TestDataManager.data.url);
    }


    @When("User navigates to Developers page")
    public void navigate_to_developers_page() {
        LHSMenu lhsMenu = new LHSMenu(base.driver);
        lhsMenu.clickOnDevelopersLink();
    }


    @Before ("@ui")
    public void before(Scenario scenario) {
       base.scenario = scenario;
    }



    @After ("@ui")
    public void tearDown(Scenario scenario)
    {

        if(scenario.isFailed())
        {
            scenario.embed(((TakesScreenshot)base.driver).getScreenshotAs(OutputType.BYTES),"image/png");
        }
        base.driver.close();
        base.driver.quit();
    }

    @Then("Navigate to stake and mint page")
    public void navigate_to_stake_and_mint() {
        LHSMenu lhsMenu = new LHSMenu(base.driver);
        lhsMenu.clickOnMintTokensLink();
    }
}
