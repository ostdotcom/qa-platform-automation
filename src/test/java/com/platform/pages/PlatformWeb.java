package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PlatformWeb extends Base_UI {



    @FindBy(xpath = "//a[@class='btn btn-primary my-3']")
    private WebElement registerNowPB;

    public PlatformWeb() {
        PageFactory.initElements(driver, this);
    }


    public void clickOnRegisterNow()
    {
        registerNowPB.click();
    }


}
