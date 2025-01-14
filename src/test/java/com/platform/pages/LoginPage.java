package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Base_UI {

    String authorizeDeviceMail = "Authorize New Device Or Browser";

    @FindBy(id = "email")
    private WebElement emailTB;

    @FindBy(id = "password")
    private WebElement passwordTB;

    @FindBy(id = "login-btn")
    private WebElement loginPB;

    @FindBy(xpath = "//*[@class='btn-reject-cookies my-auto']")
    private WebElement privacyCancelBtn;

    @FindBy(xpath = "//a[@class='form-text mt-0']")
    private WebElement forgotPasswordLink;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public void login(String email, String password)  {
        try
        {
            privacyCancelBtn.click();
        }
        catch (Exception e)
        {
            System.out.println("Privacy cookie is already cancelled");
        }
        emailTB.sendKeys(email);
        passwordTB.sendKeys(password);


        // Switching frame to Google captcha
        driver.switchTo().frame(0);
        driver.findElement(By.id("recaptcha-anchor")).click();


        //Verifying check box is marked as true
        WebDriverWait wait = new WebDriverWait(driver, 180);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='recaptcha-anchor' and @aria-checked='true']")));

        driver.switchTo().defaultContent();


        loginPB.click();
        System.out.println("Clicked on Login Button");
        System.out.println("Page title: "+driver.getTitle());

        // Verify if user's device is authorised or not.
        try
        {
            wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("send-verify-device-link")));

            System.out.println("Your Browser is not authorised.");

            SignupPage signupPage = new SignupPage(driver);
            String confirmationLink  = signupPage.getActivateAccountLink(email,authorizeDeviceMail);
            driver.get(confirmationLink);
        }
        catch (Exception e)
        {
            System.out.println("Your Browser is already authorised.");
        }
    }

    public void clickOnForgotPassword()
    {
        forgotPasswordLink.click();
    }
}

