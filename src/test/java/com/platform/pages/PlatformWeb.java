package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlatformWeb extends Base_UI  {

    @FindBy(xpath = "//a[contains(text(),'Register Now')]")
    private WebElement registerNowPB;

    @FindBy(xpath = "//a[contains(text(),'Log In')]")
    private WebElement loginPB;

    public PlatformWeb(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    public void clickOnRegisterNow()
    {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(registerNowPB)).click();
    }

    public void clickOnLogIn()
    {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.elementToBeClickable(loginPB)).click();
        //loginPB.click();
    }


}
