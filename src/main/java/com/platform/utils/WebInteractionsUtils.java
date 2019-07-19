package com.platform.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Iterator;
import java.util.Set;

public class WebInteractionsUtils {

    public void openNewTab(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
    }

    public void switchToNewTab(WebDriver driver) {
        openNewTab(driver);
        String subWindowHandler = null;

        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();
        while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
        }
        driver.switchTo().window(subWindowHandler);
    }

    public void movetoElement(WebDriver driver, WebElement webElement)
    {
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].scrollIntoView()", webElement);
    }




}
