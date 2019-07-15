package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class SignupPage extends Base_UI {

    @FindBy(id = "first-name")
    private WebElement firstNameTB;


    @FindBy(id = "last-name")
    private WebElement lastNameTB;

    @FindBy(id = "user-email")
    private WebElement userEmailTB;

    @FindBy(id = "password")
    private WebElement passwordTB;

    @FindBy(xpath = "//div[@id='tosFormGrp']//label[@class='checkbox-custom-label form-check-label']")
    private WebElement termsConditionCB;

    @FindBy(xpath = "//div[@id='marcommFormGrp']//label[@class='checkbox-custom-label form-check-label']")
    private WebElement agreeOstCB;

    @FindBy(id = "recaptcha-anchor")
    private WebElement recaptchaCB;

    @FindBy(id = "register-btn")
    private WebElement registerPB;

    @FindBy(xpath = "//span[@id='recaptcha-anchor' and @aria-checked='true']")
    private WebElement selectedRecaptchaCB;


    public SignupPage() {
        PageFactory.initElements(driver, this);
    }


    public void registerUser(String firstName, String lastName, String email, String password)
    {
        firstNameTB.sendKeys(firstName);
        lastNameTB.sendKeys(lastName);
        userEmailTB.sendKeys(email);
        passwordTB.sendKeys(password);

        termsConditionCB.click();
        agreeOstCB.click();

        //driver.switchTo().frame("a-1escgy40j6yj");
        driver.switchTo().frame(0);
        driver.findElement(By.id("recaptcha-anchor")).click();
        //recaptchaCB.click();




        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='recaptcha-anchor' and @aria-checked='true']")));

        driver.switchTo().defaultContent();
        registerPB.click();
    }
}
