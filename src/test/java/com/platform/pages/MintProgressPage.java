package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MintProgressPage extends Base_UI {

    @FindBy(xpath = "//p[@class='text-left font-weight-light font-size-p my-auto progressStep']")
    private WebElement currentStepTxt;

    @FindBy(xpath = "//span[@class='tooltip-text d-inline-block text-center']")
    private WebElement currentPercentage;

    @FindBy(className = "total-token-minted")
    private WebElement totalTokenMintedNum;



    public MintProgressPage() {
        PageFactory.initElements(driver, this);
    }


    public boolean verifyCurrentText(String expectedText)
    {
        if(currentStepTxt.getText().equals(expectedText))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void waitTillStakeAndMintCompleted()
    {
        WebDriverWait wait = new WebDriverWait(driver,1500);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Minting Complete')]")));
    }
    public String getCurrentPercentage()
    {
        return currentPercentage.getText();
    }

    public String getTotalTokenMinted()
    {
        return totalTokenMintedNum.getText();
    }
}
