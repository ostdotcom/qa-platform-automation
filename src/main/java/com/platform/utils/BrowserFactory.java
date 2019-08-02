package com.platform.utils;

import com.platform.constants.Constant;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

public class BrowserFactory{

    static DesiredCapabilities capabilities;


    public static WebDriver initChromeBrowser()
    {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(0);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);

        // put our custom header to each request
        proxy.addRequestFilter((request, contents, messageInfo)->{
            //request.headers().add("Host", "stagingost.com");
            //request.headers().add("Authorization", " Basic b3N0OkEkRl4mbiFAJGdoZiU3");
            System.out.println(request.headers().entries().toString());
            return null;
        });

        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKEYCHROME,Constant.BROWSER_SPECIFICATION.CHROMEDRIVERMAC);
            ChromeOptions options = new ChromeOptions();

            // options.addArguments("--incognito");
            String proxyOption = "--proxy-server=" + seleniumProxy.getHttpProxy();
            //options.addArguments(proxyOption);
             //options.addArguments("--headless");
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {
            System.out.println("In init chrome with Linux OS");
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKEYCHROME,Constant.BROWSER_SPECIFICATION.CHROMEDRIVERLINUX);
            ChromeOptions options = new ChromeOptions();
            //
            // options.addArguments("--incognito");
            options.addArguments("--headless");
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKEYCHROME,Constant.BROWSER_SPECIFICATION.CHROMEDRIVERWINDOWS);
            ChromeOptions options = new ChromeOptions();
            //
            // options.addArguments("--incognito");
            options.addArguments("--headless");
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        }
        else{
            throw new IllegalArgumentException("This OS is not supported as of now");
        }

        return new ChromeDriver(capabilities);
    }



    public static WebDriver initFirefoxBrowser()
    {
        if(Constant.PROJECTOS.toLowerCase().contains("mac"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKRYFIREFOX,Constant.BROWSER_SPECIFICATION.GECKODRIVERMAC);
        }
        else if(Constant.PROJECTOS.toLowerCase().contains("linux"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKRYFIREFOX,Constant.BROWSER_SPECIFICATION.GECKODRIVERLINUX);
        }
        else if(Constant.PROJECTOS.toLowerCase().contains("windows"))
        {
            System.setProperty(Constant.BROWSER_SPECIFICATION.PROPERTYKRYFIREFOX,Constant.BROWSER_SPECIFICATION.GECKODRIVERWINDOWS);
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
