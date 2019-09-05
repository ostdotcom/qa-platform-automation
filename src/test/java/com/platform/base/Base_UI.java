package com.platform.base;

import com.platform.utils.WebInteractionsUtils;
import cucumber.api.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;

public class Base_UI extends Base{

    Date date= new Date();
    public long time = date. getTime();
    public String newEmailId = "qa.automation+"+ time+"@ost.com";

    public WebDriver driver;
    public WebInteractionsUtils webInteractionsUtils = new WebInteractionsUtils();

    public void takeScreenshot()
    {
        new WebDriverWait(driver, 30).until(
                webDriver -> (long)((JavascriptExecutor) webDriver).executeScript("return jQuery.active") == 0);

        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        scenario.embed(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES),"image/png");
    }
}
