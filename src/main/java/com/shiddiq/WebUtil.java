package com.shiddiq;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebUtil {
   
    private static WebDriver webDriver = GlobalVariable.webDriver;

    public static void navigateToUrl(String url){
        webDriver.navigate().to(url);
    }

    public static void verifyElementVisible(String xpath, int timeout){
        boolean isVisible = checkVisibility(xpath, timeout);
        if (isVisible){
            System.out.println("Element with path " + xpath + " is visible");
        }else{
            throw new TimeoutException("Element with path " + xpath + " is not visible");
        }
    }

    public static void verifyElementVisible(String xpath){
        verifyElementVisible(xpath, GlobalConstants.WAIT_PAGE_FAST);
    }

    public static void verifyElementNotVisible(String xpath, int timeout){
        boolean isVisible = checkVisibility(xpath, timeout);
        if (!isVisible){
            System.out.println("Element with path " + xpath + " is not visible");
        }else{
            throw new TimeoutException("Element with path " + xpath + " is visible!");
        }
    }

    public static void verifyElementNotVisible(String xpath){
        verifyElementNotVisible(xpath, GlobalConstants.WAIT_PAGE_FAST);
    }

    public static String getText(String xpath, int timeout){
        boolean isVisible = checkVisibility(xpath, timeout);
        if(isVisible){
            String text;
            WebElement webElement = webDriver.findElement(By.xpath(xpath));
            if (webElement.getTagName().equals("input")){
                text = webElement.getAttribute("value");
            }else{
                text = webElement.getText();
            }
            System.out.println("Element with path " + xpath + " has text: " + text);
            return text;
        }else{
            throw new NoSuchElementException("Element with path " + xpath + " is not visible");
        }
    }

    public static String getText(String xpath){
        return getText(xpath, GlobalConstants.WAIT_PAGE_FAST);
    }

    public static void verifyTextFieldEmpty(String xpath, int timeout){
        Assert.assertTrue(getText(xpath, timeout).isEmpty());
    }

    public static void verifyTextFieldEmpty(String xpath){
        Assert.assertTrue(getText(xpath, GlobalConstants.WAIT_PAGE_FAST).isEmpty());
    }

    public static void setText(String xpath, String text){
        boolean isVisible = checkVisibility(xpath, GlobalConstants.WAIT_PAGE_FAST);
        if (isVisible){
            clearText(xpath);
            WebElement element = webDriver.findElement(By.xpath(xpath));
            element.sendKeys(text);
            System.out.println("Text in element " + xpath + " inserted");
        }else{
            throw new NoSuchElementException("Element with " + xpath + " is not visible");
        }
    }

    public static void clearText(String xpath){
        boolean isVisible = checkVisibility(xpath, GlobalConstants.WAIT_PAGE_FAST);
        if (isVisible){
            WebElement element = webDriver.findElement(By.xpath(xpath));
            if (System.getProperty("os.name").equals("mac")){
                element.sendKeys(Keys.COMMAND + "a");
                element.sendKeys(Keys.DELETE);
            }else{
                element.sendKeys(Keys.CONTROL + "a");
                element.sendKeys(Keys.DELETE);
            }
            System.out.println("Text in element " + xpath + " is clear");
        }else{
            throw new NoSuchElementException("Element with " + xpath + " is not visible");
        }
    }

    public static void click(String xpath){
        boolean isVisible = checkVisibility(xpath, GlobalConstants.WAIT_PAGE_FAST);
        boolean isClickable = checkClickability(xpath, GlobalConstants.WAIT_PAGE_FAST);
        if (isVisible && isClickable){
            WebElement element = webDriver.findElement(By.xpath(xpath));
            element.click();
            System.out.println("Element with path " + xpath + " is clicked");
        }else{
            if (!isVisible){
                throw new NoSuchElementException("Element with " + xpath + " is not visible");
            }else{
                throw new IllegalStateException("Element with path " + xpath + " is not clickable");
            }
        }
    }

    public static void saveImage(String xpath){
        try {
            BufferedImage image = null;
            boolean isVisible = checkVisibility(xpath, GlobalConstants.WAIT_PAGE_FAST);
            if (isVisible){
                WebElement element = webDriver.findElement(By.xpath(xpath));
                URL imageUrl = new URL(element.getAttribute("src"));
                image = ImageIO.read(imageUrl);
                ImageIO.write(image, "png", new File("qrCode.png"));
                System.out.println("Image saved successfully");
            }else{
                throw new NoSuchElementException("Element with " + xpath + " is not visible");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void uploadFile(String xpath, String fileName){
        boolean isVisible = checkVisibility(xpath, GlobalConstants.WAIT_PAGE_FAST);
        if (isVisible){
            String basePath = System.getProperty("user.dir");
            WebElement element = webDriver.findElement(By.xpath(xpath));
            element.sendKeys(basePath + "\\" + fileName);
            System.out.println("File is inserted into element with path " + xpath);
        }else{
            System.out.println(String.format("Element with path %s not visible in viewport!", xpath));
        }
    }

    public static void verifyFileInserted(String xpath){
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        WebElement element = webDriver.findElement(By.xpath(xpath));
        boolean result = (Boolean) js.executeScript("return arguments[0].files.length == 1", element);
        Assert.assertTrue(result);
    }

    private static boolean checkVisibility(String xpath, int timeout){
        boolean isVisible = false;
        try {
            By by = By.xpath(xpath);
            new WebDriverWait(webDriver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
            isVisible = true;
        } catch (Exception e) {
            System.out.println("[verifyElementVisible_Exception] - " + e.getMessage());
            e.printStackTrace();
        }

       return isVisible;
    }

    private static boolean checkClickability(String xpath, int timeout){
         boolean isClickable = false;
        try {
            By by = By.xpath(xpath);
            new WebDriverWait(webDriver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(by));
            isClickable = true;
        } catch (Exception e) {
            System.out.println("[verifyElementVisible_Exception] - " + e.getMessage());
            e.printStackTrace();
        }

       return isClickable;
    }
}
