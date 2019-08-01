package com.platform.steps.ui;

import com.platform.base.Base_API;
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
    public static final String resetPwsEmailSubject = "Reset your OST Platform Account Password";     //did not complete the subject because it has minor difference between staging and prod environment
    Date date= new Date();
    long time = date. getTime();
    String newEmailId = "qa.automation+"+ time+"@ost.com";
    private static final String dashboardPageTitle = "OST Platform | Dashboard";
    String newPassword = "ostkit@"+time;

    private Base_UI base;
    PlatformWeb platformWeb;

    public LoginSignupSteps(Base_UI base) {
        this.base = base;
    }


    @Given("^User is on sign up page for platform website$")
    public void navigate_to_signup_page() {
        platformWeb = new PlatformWeb();
        platformWeb.clickOnRegisterNow();
    }

    @When("^User registered with all details$")
    public void user_registration() {

        SignupPage signupPage = new SignupPage();
        signupPage.registerUser("bhavik", "shah", newEmailId,"ostkit@1234");
    }

    @And("^User confirm the email$")
    public void userConfirmTheEmail() throws InterruptedException {

        //Waiting for email to receive
        Thread.sleep(10000);
        SignupPage signupPage = new SignupPage();
        String confirmationLink  = signupPage.getActivateAccountLink(newEmailId,signupEmailSubject);

        base.driver.get(confirmationLink);

    }

    @Then("^User should signed up successfully$")
    public void verify_user_signup() {
        CompanyInformationPage companyInformationPage = new CompanyInformationPage();
        companyInformationPage.verifyCompanyNameTB();
    }

    @Given("^User is on login page$")
    public void navigate_to_login_page() {
        platformWeb = new PlatformWeb();
        platformWeb.clickOnLogIn();
    }

    @When("^User login with correct email and password$")
    public void user_login() {

        LoginPage loginPage = new LoginPage();
        loginPage.login(TestDataManager.economy1.email_Id,TestDataManager.economy1.password);
    }

    @Then("^User should be successfully logged in$")
    public void verify_successful_login() {
        AssertionUtils.repeatWhenFailedForSeconds(50, ()->
        {
            Assert.assertEquals(base.driver.getTitle(),dashboardPageTitle);
        });

    }

    @When("^User click on Forgot password$")
    public void click_on_forgot_password() {

        LoginPage loginPage = new LoginPage();
        loginPage.clickOnForgotPassword();

    }

    @And("^User recovers the password$")
    public void recover_password_from_mail() {
        ResetPasswordPage resetPasswordPage = new ResetPasswordPage();
        resetPasswordPage.writeEmail(newEmailId);
        resetPasswordPage.clickOnRecaptcha();
        resetPasswordPage.clickOnRecoverEmailBtn();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SignupPage signupPage = new SignupPage();
        String confirmationLink  = signupPage.getActivateAccountLink(newEmailId,resetPwsEmailSubject);

        base.driver.get(confirmationLink);

    }

    @Then("^Verify that user is able to login with new password$")
    public void login_with_new_password() {

        UpdatePasswordPage updatePasswordPage = new UpdatePasswordPage();
        updatePasswordPage.writePassword(newPassword);
        updatePasswordPage.writeConfirmPassword(newPassword);
        updatePasswordPage.clickOnUpdatePasswordBtn();

        updatePasswordPage.verifyUpdatePwdSuccessTxt();
        updatePasswordPage.clickOnLoginBtn();

        LoginPage loginPage = new LoginPage();
        loginPage.login(newEmailId,newPassword);



    }

    @And("^User logout from current session$")
    public void user_logout() {

        LHSMenu lhsMenu = new LHSMenu();
        lhsMenu.clickOnUserSetting();
        lhsMenu.clickOnLogout();
    }
}
