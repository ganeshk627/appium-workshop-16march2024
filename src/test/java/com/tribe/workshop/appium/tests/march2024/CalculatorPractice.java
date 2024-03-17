package com.tribe.workshop.appium.tests.march2024;

import com.tribe.workshop.appium.tests.dec2023.VodQATests;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class CalculatorPractice {
    private static final Logger logger = LogManager.getLogger(VodQATests.class);

    AppiumDriverLocalService appiumService;
    private AndroidDriver driver;
    private WebDriverWait wait;


    @BeforeSuite
    public void setupAppiumServer() {
        AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withIPAddress("127.0.0.1")
                .withTimeout(Duration.ofMinutes(5));

        appiumService = AppiumDriverLocalService.buildService(appiumServerArguments);

        // start appium server
        appiumService.start();
        logger.info("Appium Server Started At : " + appiumService.getUrl());
    }

//    @BeforeClass
    @BeforeMethod
    public void initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAppPackage("com.android.calculator2")
                .setAppActivity("com.android.calculator2.Calculator")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel 6 Pro API 28");

//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver = new AndroidDriver(appiumService.getUrl(), options);

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @DataProvider(name = "calc-data")
    public Object[][] calcdata() {
        return new Object[][]{
                {"100", "+", "100", "200"},
                {"100", "−", "100", "0"},
                {"100", "÷", "100", "1"},
                {"100", "×", "100", "1000"},
                {"100", "×", "100", "10000"},
        };
    }

    @Test(dataProvider = "calc-data")
    public void calcTests(String a, String op, String b, String output) {
//        calc("14", "+", "1");
        calc(a, op, b);
        assertEquals(getResult(), output);
    }

    private void calc(String a, String op, String b) {
//        findElement(AppiumBy.androidUIAutomator(("new UiSelector().text(\""+a+"\")"))).click();
        clickByNumbers(a);
        waitFor(1);
        findElement(AppiumBy.androidUIAutomator(("new UiSelector().text(\"" + op + "\")"))).click();
        waitFor(1);
//        findElement(AppiumBy.androidUIAutomator(("new UiSelector().text(\""+b+"\")"))).click();
        clickByNumbers(b);
        waitFor(1);
        findElement(AppiumBy.androidUIAutomator(("new UiSelector().text(\"=\")"))).click();
    }

//    @AfterClass
    @AfterMethod
    public void closeSession() {
//        driver.close(); //deprecated
        driver.quit();
    }

    @AfterSuite
    public void stopAppiumServer() {
        logger.info("Stopping Appium Server");
        appiumService.stop();
    }


    private void clickByNumbers(String sequence) {
        for (char number : String.valueOf(sequence).toCharArray()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + number + "\")")))
                    .click();
            waitFor(1);
        }
    }

    private String getResult() {
        return findElement(AppiumBy.id("com.android.calculator2:id/result")).getText();
    }

    private void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private WebElement findElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
