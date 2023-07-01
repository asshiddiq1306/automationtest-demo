package com.shiddiq.listener;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.shiddiq.BaseTest;
import com.shiddiq.GlobalVariable;

public class Automation implements BaseListener {
    private boolean isInitiated;

    public Automation(){
        this.isInitiated = false;
    }

    @Override
    public void beforeMethod(BaseTest test) {
        System.out.println("----- Running Automation Before Test -----");
        WebDriver webDriver;
        try {
            if (test.isIndividualRun()){
                if (this.isInitiated){
                    webDriver = test.getDriverManager().getDriver();
                    test.setDriver(webDriver);
                }else{
                    webDriver = test.getDriver();
                    this.isInitiated = true;
                }
                GlobalVariable.webDriver = webDriver;
            }               
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void afterMethod(BaseTest test, ITestResult result) {
        System.out.println("----- Running Automation After Test -----");
        if (result.getStatus() == ITestResult.FAILURE){
            test.setIndividualRun(true);
            if (test.isCloseAfterFinish()){
                test.getDriverManager().destroyDriver(test.getDriver());
            }
        }else{
            test.setIndividualRun(false);
        }
    }

    @Override
    public int getPriority() {
        return -100;
    }    
}
