package com.platform.pages;

import com.platform.base.Base_UI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompanyInformationPage extends Base_UI {

    @FindBy(id = "company_name")
    private WebElement companyNameTB;


    @FindBy(xpath = "//label[text()='No']")
    private WebElement noCB;


    @FindBy(xpath = "//label[text()='Yes']")
    private WebElement yesCB;


    @FindBy(xpath = "//label[text()='Less than 1 Million']")
    private WebElement lessThanCB;


    @FindBy(xpath = "//label[text()='More than 1 Million']")
    private WebElement moreThanCB;

    @FindBy(id = "save_company_info_form")
    private WebElement confifrmButton;



    public CompanyInformationPage() {
        PageFactory.initElements(driver, this);
    }



    public void writeCompanyName(String companyName)
    {
        companyNameTB.sendKeys(companyName);
    }


    public void checkYesbox()
    {
        yesCB.click();
    }

    public void checkNobox()
    {
        noCB.click();
    }

    public void checkMoreThanBox()
    {
        moreThanCB.click();
    }

    public void checkLessThanBox()
    {
        lessThanCB.click();
    }


    public void verifyCompanyNameTB() {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOf(companyNameTB));
    }

    public void clickOnConfirmBtn()
    {
        confifrmButton.click();
    }
}
