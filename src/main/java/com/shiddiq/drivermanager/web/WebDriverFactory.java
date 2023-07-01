package com.shiddiq.drivermanager.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
    
    public WebDriver createBrowserSession(){
        WebDriver driver = null;
        try {
            WebDriverManager webDriverManager = WebDriverManager.chromedriver();
            System.out.println("Initializing chrome browser session...");
            webDriverManager.setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--remote-allow-origins=*", "--start-maximized");
            driver = new ChromeDriver(chromeOptions);
        } catch (Exception e) {
            System.out.println("[createBrowserSession_Exception] - " + e.getMessage());
            e.printStackTrace();
        }
        return driver;
    }
}
