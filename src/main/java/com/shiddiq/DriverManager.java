package com.shiddiq;

import org.openqa.selenium.WebDriver;

public interface DriverManager {
    WebDriver getDriver();
    void destroyDriver(WebDriver driver);
}
