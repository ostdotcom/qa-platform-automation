package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TokenDeployPage extends Base_UI {


    @FindBy(xpath = "//p[@class='text-left font-weight-light font-size-p my-auto progressStep']")
    private WebElement currentStepTxt;

    @FindBy(xpath = "//span[@class='tooltip-text d-inline-block text-center']")
    private WebElement currentPercentage;



    @FindBy(xpath = "//a[contains(text(),'Invite Team')]")
    private WebElement inviteTeamPB;


    @FindBy(xpath = "//a[contains(text(),'Get API Keys')]")
    private WebElement getApiKeysPB;


    @FindBy(xpath = "//a[contains(text(),'Ost Guides')]")
    private WebElement ostGuidePB;


    @FindBy(xpath = "//h3[contains(text(),'Setup is Complete')]")
    private WebElement tokenSetupCompleteTxt;

    @FindBy(xpath = "//a[@class='btn btn-primary d-inline']")
    private WebElement mintTokenPB;





    public TokenDeployPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }



    public boolean verifyCurrentText(String expectedText)
    {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(currentStepTxt));

        if(currentStepTxt.getText().equals(expectedText))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public String getCurrentPercentage()
    {
        return currentStepTxt.getText();
    }

    public void clickOnInviteTeam()
    {
        inviteTeamPB.click();
    }

    public void clickOnGetApiKey()
    {
        getApiKeysPB.click();
    }

    public void clickOnOstGuide()
    {
        ostGuidePB.click();
    }

    public void waitTillTokenSetupCompleted()
    {
        WebDriverWait wait = new WebDriverWait(driver,1500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Token Setup Complete')]")));
    }

    public void clickOnMintTokenPB()
    {
        mintTokenPB.click();
    }





}
