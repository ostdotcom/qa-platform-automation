package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MintTokensPage extends Base_UI {

    @FindBy(id = "bt_to_mint")
    private WebElement btToMintTB;

    @FindBy(xpath = "//div[div[contains(text(),'VALUE IN USD')]]/div/div[@class='ost-mocker-value']")
    private WebElement tokensValueInUsdNum;

    @FindBy(xpath = "//div[span[contains(text(),'Stake Available')]]/span[2]")
    private WebElement stakeAvailableNum;

    @FindBy(xpath = "//p[small[contains(text(),'Remaining Stake')]]/small/span")
    private WebElement remainingStakeNum;

    @FindBy(xpath = "//p[small[contains(text(),'Stake Amount')]]/small/span")
    private WebElement stakeAmountNum;

    @FindBy(id = "stake-and-mint-btn")
    private WebElement mintTokensPB;

    @FindBy(id = "mint-tokens")
    private WebElement setupMintingPB;


    public MintTokensPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickOnMintTokenBtn()
    {
        mintTokensPB.click();
    }

    public void writeMintedBts(double mintedBts)
    {
        //webInteractionsUtils.movetoElement(driver,btToMintTB);
        btToMintTB.click();
        btToMintTB.sendKeys(String.valueOf(mintedBts));


        btToMintTB.click();
        btToMintTB.sendKeys(Keys.HOME);
        btToMintTB.sendKeys(Keys.ARROW_RIGHT);
        btToMintTB.sendKeys(Keys.BACK_SPACE);
    }

    public void clickOnSetupMinting()
    {
        setupMintingPB.click();
    }

    public String getStakeAvailable()
    {
        return stakeAvailableNum.getText();
    }

    public String getStakeAmount()
    {
        return stakeAmountNum.getText();
    }

    public String getRemainingStake()
    {
        return remainingStakeNum.getText();
    }

    public String getUsdValue()
    {
        return tokensValueInUsdNum.getText();
    }





}
