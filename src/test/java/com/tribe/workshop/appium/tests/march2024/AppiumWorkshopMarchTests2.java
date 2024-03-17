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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class AppiumWorkshopMarchTests2 {

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




    @BeforeClass
    public void initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel 6 Pro API 28");

//        driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        driver = new AndroidDriver(appiumService.getUrl(), options);


        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void loginTest() throws InterruptedException {

//        Thread.sleep(5000);

        // Explicit wait for 30 seconds
//        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("username")));

        // Enter username after clearing
//        WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"username\"]"));
//        WebElement username = driver.findElement(AppiumBy.accessibilityId("username"));
        WebElement username = findElement(AppiumBy.accessibilityId("username"));
        username.clear();
        Thread.sleep(2000);
        username.sendKeys("admin");
        Thread.sleep(2000);

        // Enter password after clearing
//        WebElement password = driver.findElement(AppiumBy.accessibilityId("password"));
        WebElement password = findElement(AppiumBy.accessibilityId("password"));
        password.clear();
        Thread.sleep(2000);
        password.sendKeys("admin");
        Thread.sleep(2000);

        // Clicking login button
//        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
        Thread.sleep(5000);

    }

    private WebElement findElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @AfterClass
    public void closeSession() {
//        driver.close(); //deprecated
        driver.quit();
    }

    @AfterSuite
    public void stopAppiumServer() {
        logger.info("Stopping Appium Server");
        appiumService.stop();
    }
}
