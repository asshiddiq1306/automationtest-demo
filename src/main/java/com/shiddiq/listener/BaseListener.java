package com.shiddiq.listener;

import org.testng.ITestResult;

import com.shiddiq.BaseTest;

public interface BaseListener {
    int getPriority();
    void beforeMethod(BaseTest test);
    void afterMethod(BaseTest test, ITestResult result);
}
