package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResetPasswordPage extends Base_UI {

    @FindBy(id = "email")
    private WebElement emailTB;

    @FindBy(xpath = "//span[@id='recaptcha-anchor' and @aria-checked='true']")
    private WebElement selectedRecaptchaCB;


    @FindBy(id = "recover-email-btn")
    private WebElement recoverEmailPB;


    @FindBy(className = "btn ost-btn-secondary")
    private WebElement returnToLoginPB;



    public ResetPasswordPage() {
        PageFactory.initElements(driver, this);
    }


    public void writeEmail(String email)
    {
        emailTB.sendKeys(email);
    }

    public void clickOnRecaptcha()
    {
        // Switching frame to Google captcha
        driver.switchTo().frame(0);
        driver.findElement(By.id("recaptcha-anchor")).click();


        //Verifying check box is marked as true
        WebDriverWait wait = new WebDriverWait(driver, 180);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='recaptcha-anchor' and @aria-checked='true']")));

        driver.switchTo().defaultContent();
    }

    public void clickOnRecoverEmailBtn()
    {
        recoverEmailPB.click();
    }


}
