package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlatformWeb extends Base_UI {



    @FindBy(xpath = "//a[@class='btn btn-primary my-3']")
    private WebElement registerNowPB;

    @FindBy(xpath = "//a[@class='btn btn-secondary my-2 ml-md-3']")
    private WebElement loginPB;

    public PlatformWeb() {
        PageFactory.initElements(driver, this);
    }


    public void clickOnRegisterNow()
    {
        registerNowPB.click();
    }

    public void clickOnLogIn()
    {
        loginPB.click();
    }


}
