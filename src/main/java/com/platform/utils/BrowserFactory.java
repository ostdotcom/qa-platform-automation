package com.platform.utils;

import com.platform.constants.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory{


    public static WebDriver initChromeBrowser()
    {
        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKEYCHROME,Constant.BROWSER_SPECIFICATION.CHROMEDRIVERMAC);
        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {

        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {

        }
        else{
            throw new IllegalArgumentException("This OS is not supported as of now");
        }

        return new ChromeDriver();
    }



    public static WebDriver initFirefoxBrowser()
    {
        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKRYFIREFOX,Constant.BROWSER_SPECIFICATION.GECKODRIVERMAC);
        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {

        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {

        }
        else{
            throw new IllegalArgumentException("This OS is not supported as of now");
        }


        return new FirefoxDriver();
    }



    public static WebDriver initIEBrowser()
    {
        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {


        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {

        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {

        }
        else{
            throw new IllegalArgumentException("This OS is not supported as of now");
        }


        return new ChromeDriver();
    }

    public static WebDriver initSafariBrowser() {
        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {


        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {

        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {

        }
        else{
            throw new IllegalArgumentException("This OS is not supported as of now");
        }

        return new SafariDriver();
    }
}
