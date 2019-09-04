package com.platform.base;

import com.platform.utils.WebInteractionsUtils;
import cucumber.api.Scenario;
import org.openqa.selenium.WebDriver;

import java.util.Date;

public class Base_UI extends Base{

    Date date= new Date();
    public long time = date. getTime();
    public String newEmailId = "qa.automation+"+ time+"@ost.com";

    public WebDriver driver;
    public WebInteractionsUtils webInteractionsUtils = new WebInteractionsUtils();
}
