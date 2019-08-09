package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MintTokensPage extends Base_UI {

    @FindBy(id = "bt_to_mint")
    private WebElement btToMintTB;

    @FindBy(xpath = "//div[div[contains(text(),'TOKENS VALUE IN USD')]]/div/div[@class='ost-mocker-value']")
    private WebElement tokensValueInUsdNum;

    @FindBy(xpath = "//div[span[contains(text(),'Total OST tokens')]]/span[2]")
    private WebElement totalOstTokensNum;

    @FindBy(xpath = "//p[small[contains(text(),'OST tokens available after minting')]]/small[2]/span")
    private WebElement totalAvailableTokenNum;

    @FindBy(xpath = "//p[small[contains(text(),'OST tokens used for minting')]]/small[2]/span")
    private WebElement tokenUsedForMintingNum;

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
        btToMintTB.sendKeys(mintedBts+"");
    }

    public void clickOnSetupMinting()
    {
        setupMintingPB.click();
    }


}
