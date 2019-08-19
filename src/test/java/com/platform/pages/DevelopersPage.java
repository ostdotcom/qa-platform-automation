package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DevelopersPage extends Base_UI {

    @FindBy(xpath = "//button[text()='Get Keys']")
    private WebElement getKeysPB;

    @FindBy(className = "email-sent-wrapper")
    private WebElement emailSentText;

    @FindBy(xpath = "//div[@class='mb-5 keys-wrapper']//h4[contains(text(),'API Key:')]/following-sibling::p")
    private WebElement apiKeyText;

    @FindBy(xpath = "//div[@class='mb-5 keys-wrapper']//h4[contains(text(),'API Secret:')]/following-sibling::p")
    private WebElement privateKeyText;


    public DevelopersPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver=driver;
    }


    public void clickOnGetKeysBtn()
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(getKeysPB));
        getKeysPB.click();
    }

    public boolean verifyEmailSentTextVisible()
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(emailSentText));
        return emailSentText.isDisplayed();
    }

    public String getApiKey()
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(apiKeyText));
        return apiKeyText.getText();
    }

    public String getPrivateKey()
    {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(privateKeyText));
        return privateKeyText.getText();
    }
}
