package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UpdatePasswordPage extends Base_UI {


    @FindBy(id = "password")
    private WebElement passwordTB;

    @FindBy(id = "confirm_password")
    private WebElement confirmPasswordTB;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement updatePasswordPB;

    @FindBy(xpath = "//h3[contains(text(),'Your Password was Updated Successfully')]")
    private WebElement updatePwdSuccessTxt;

    @FindBy(xpath = "//a[@class='btn btn-gold mt-3']")
    private WebElement loginPB;



    public UpdatePasswordPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void writePassword(String password)
    {
        passwordTB.sendKeys(password);
    }

    public void writeConfirmPassword(String password)
    {
        confirmPasswordTB.sendKeys(password);
    }

    public void clickOnUpdatePasswordBtn()
    {
        updatePasswordPB.click();
    }

    public void verifyUpdatePwdSuccessTxt()
    {
        updatePasswordPB.getText();
    }

    public void clickOnLoginBtn()
    {
        loginPB.click();
    }
}
