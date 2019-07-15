package com.platform.steps.ui;

import com.platform.base.Base_API;
import com.platform.base.Base_UI;
import com.platform.managers.TestDataManager;
import com.platform.pages.PlatformWeb;
import com.platform.pages.SignupPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginSignupSteps {


    private Base_UI base;
    PlatformWeb platformWeb = new PlatformWeb();

    public LoginSignupSteps(Base_UI base) {
        this.base = base;
    }


    @Given("^User is on sign up page for platform website$")
    public void navigate_to_signup_page() {


        platformWeb.clickOnRegisterNow();

    }

    @When("^User registered with all details$")
    public void user_registration() {

        SignupPage signupPage = new SignupPage();
        signupPage.registerUser("bhavik", "shah", "bhavik+121212323@ost.com","12345678");

    }


    @Then("^User should get confirmation mail$")
    public void userShouldGetConfirmationMail() {
    }
}
