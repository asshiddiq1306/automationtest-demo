package com.shiddiq;

import org.openqa.selenium.WebDriver;

public class BaseTemplate {
    protected WebDriver webDriver;

    public BaseTemplate(){
        this.webDriver = GlobalVariable.webDriver;
    }
    
}
