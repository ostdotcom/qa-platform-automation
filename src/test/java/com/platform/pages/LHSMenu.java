package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LHSMenu extends Base_UI {

    @FindBy(id = "email")
    private WebElement dashboardBtn;

    @FindBy(id = "email")
    private WebElement mintTokenBtn;

    @FindBy(id = "email")
    private WebElement walletBtn;

    @FindBy(id = "email")
    private WebElement developersBtn;

    @FindBy(id = "email")
    private WebElement blockExplorerBtn;

    @FindBy(id = "user-settings")
    private WebElement userSettingLink;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElement logoutLink;



    public LHSMenu(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver=driver;
    }



    public void clickOnDashboardLink()
    {
        dashboardBtn.click();
    }

    public void clickOnMintTokensLink()
    {
        mintTokenBtn.click();
    }

    public void clickOnWalletBtnLink()
    {
        walletBtn.click();
    }

    public void clickOnBlockExplorerLink()
    {
        blockExplorerBtn.click();
    }

    public void clickOnUserSetting()
    {
        userSettingLink.click();
    }

    public void clickOnLogout()
    {
        logoutLink.click();
    }








}
