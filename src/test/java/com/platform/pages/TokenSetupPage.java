package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TokenSetupPage extends Base_UI {

    @FindBy(id = "name")
    private WebElement tokenNameTB;

    @FindBy(id = "symbol")
    private WebElement tokenSymbolTB;

    @FindBy(id = "setup-token-btn")
    private WebElement proceedBtn;

    @FindBy(xpath = "//span[@class='edit-collapse ml-1']")
    private WebElement editBtn;

    @FindBy(id = "sc_to_bt_id")
    private WebElement btTokensTB;

    @FindBy(xpath = "//span[contains(text(),'USE USDC')]")
    private WebElement useUSDCPB;


    @FindBy(xpath = "//span[contains(text(),'USE OST')]")
    private WebElement useOSTPB;


    @FindBy(id = "jTokenSetupOstBtn")
    private WebElement ostManagedBtn;

    @FindBy(xpath = "//span[@class='ost-mocker-value conversion-rate j-bt-to-fiat-val text-truncate']")
    private WebElement usdAtCurrentConversionTB;




    public TokenSetupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void numberOfBTs(double bts)
    {
        webInteractionsUtils.movetoElement(driver,btTokensTB);
        btTokensTB.clear();
        btTokensTB.sendKeys(String.valueOf(bts));
    }


    public void clickOnProceed()
    {
        proceedBtn.click();
    }

    public void writeTokenName(String tokenName)
    {
        tokenNameTB.sendKeys(tokenName);
    }


    public void writeTokenSymbol(String tokenSymbol)
    {
        tokenSymbolTB.sendKeys(tokenSymbol);
    }


    public void clickOnUseUSDC()
    {
        webInteractionsUtils.movetoElement(driver,useUSDCPB);
        useUSDCPB.click();
    }

    public void clickOnUseOST()
    {
        useOSTPB.click();
    }

    public void clickOnEditBtn()
    {
        webInteractionsUtils.movetoElement(driver,editBtn);
        editBtn.click();
    }

    public void clickOnProceedWithOst()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webInteractionsUtils.movetoElement(driver,ostManagedBtn);
        ostManagedBtn.click();
    }
}
