package com.platform.steps.ui;


import com.platform.base.Base_UI;
import com.platform.managers.TestDataManager;
import com.platform.pages.*;
import com.platform.utils.AssertionUtils;
import com.platform.utils.WebInteractionsUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.security.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LoginSignupSteps {



    public static final String signupEmailSubject = "Confirm Your Email Address";
    public static final String resetPwsEmailSubject = "Reset your Password";     //did not complete the subject because it has minor difference between staging and prod environment

    private static final String dashboardPageTitle = "OST Platform | Dashboard";
    String newPassword = "ostkit@1234";

    private static final String automationTokens = "?automation_test_token=lkashfiouqheinsdioqinsoidfhiondoi09239hnw903n903";

    private Base_UI base;
    PlatformWeb platformWeb;

    public LoginSignupSteps(Base_UI base) {
        this.base = base;
    }


    @Given("^User is on sign up page for platform website$")
    public void navigate_to_signup_page() {

        platformWeb = new PlatformWeb(base.driver);
        platformWeb.clickOnRegisterNow();
        String currentUrl = base.driver.getCurrentUrl();
        base.driver.get(currentUrl + automationTokens);
        base.takeScreenshot();
    }

    @When("^User registered with all details$")
    public void user_registration() {

        SignupPage signupPage = new SignupPage(base.driver);
        signupPage.registerUser("bhavik", "shah", base.newEmailId,"ostkit@1234");
        base.takeScreenshot();
    }

    @And("^User confirm the email$")
    public void userConfirmTheEmail() throws InterruptedException {

        //Waiting for email to receive
        Thread.sleep(5000);
        SignupPage signupPage = new SignupPage(base.driver);
        String confirmationLink  = signupPage.getActivateAccountLink(base.newEmailId,signupEmailSubject);
        base.takeScreenshot();

        base.driver.get(confirmationLink);

    }

    @Then("^User should signed up successfully$")
    public void verify_user_signup() {
        CompanyInformationPage companyInformationPage = new CompanyInformationPage(base.driver);
        companyInformationPage.verifyCompanyNameTB();
        base.takeScreenshot();
    }

    @Given("^User is on login page$")
    public void navigate_to_login_page() {
        platformWeb = new PlatformWeb(base.driver);
        platformWeb.clickOnLogIn();

        String currentUrl = base.driver.getCurrentUrl();
        base.driver.get(currentUrl + automationTokens);
        base.takeScreenshot();
    }

    @When("^User login with correct email and password$")
    public void user_login() {

        LoginPage loginPage = new LoginPage(base.driver);
        loginPage.login(TestDataManager.economy1.email_Id,TestDataManager.economy1.password);
        base.takeScreenshot();
    }

    @Then("^User should be successfully logged in$")
    public void verify_successful_login() {
        AssertionUtils.repeatWhenFailedForSeconds(50, ()->
        {
            Assert.assertEquals(dashboardPageTitle,base.driver.getTitle());
        });
        //base.scenario.embed(((TakesScreenshot)base.driver).getScreenshotAs(OutputType.BYTES),"image/png");
        base.takeScreenshot();
    }

    @When("^User click on Forgot password$")
    public void click_on_forgot_password() {

        LoginPage loginPage = new LoginPage(base.driver);
        loginPage.clickOnForgotPassword();

        String currentUrl = base.driver.getCurrentUrl();
        base.driver.get(currentUrl + automationTokens);
        base.takeScreenshot();

    }

    @And("^User recovers the password$")
    public void recover_password_from_mail() {
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage(base.driver);
        resetPasswordPage.writeEmail(base.newEmailId);
        resetPasswordPage.clickOnRecaptcha();
        base.takeScreenshot();
        resetPasswordPage.clickOnRecoverEmailBtn();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SignupPage signupPage = new SignupPage(base.driver);
        String confirmationLink  = signupPage.getActivateAccountLink(base.newEmailId,resetPwsEmailSubject);

        base.driver.get(confirmationLink);
        base.takeScreenshot();

    }

    @Then("^Verify that user is able to login with new password$")
    public void login_with_new_password() {

        UpdatePasswordPage updatePasswordPage = new UpdatePasswordPage(base.driver);
        updatePasswordPage.writePassword(newPassword);
        updatePasswordPage.writeConfirmPassword(newPassword);
        base.takeScreenshot();
        updatePasswordPage.clickOnUpdatePasswordBtn();

        updatePasswordPage.verifyUpdatePwdSuccessTxt();
        updatePasswordPage.clickOnLoginBtn();


        LoginPage loginPage = new LoginPage(base.driver);
        loginPage.login(base.newEmailId,newPassword);
        base.takeScreenshot();
    }

    @And("^User logout from current session$")
    public void user_logout() {

        LHSMenu lhsMenu = new LHSMenu(base.driver);
        lhsMenu.clickOnUserSetting();
        lhsMenu.clickOnLogout();
        base.takeScreenshot();
    }
}
