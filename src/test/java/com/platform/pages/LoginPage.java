package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends Base_UI {

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


    public LoginPage() {
        PageFactory.initElements(driver, this);
    }


    public void login(String email, String password)
    {
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
    }

    public void clickOnForgotPassword()
    {
        forgotPasswordLink.click();
    }

}

