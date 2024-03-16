package com.tribe.workshop.appium.tests.march2024;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class AppiumWorkshopMarchTests {
    @Test
    public void firstTest() throws URISyntaxException, MalformedURLException, InterruptedException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel 6 Pro API 28");

        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);
//        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);

        Thread.sleep(5000);

        // Enter username after clearing
//        WebElement username = driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"username\"]"));
        WebElement username = driver.findElement(AppiumBy.accessibilityId("username"));
        username.clear();
        Thread.sleep(2000);
        username.sendKeys("admin");
        Thread.sleep(2000);

        // Enter password after clearing
        WebElement password = driver.findElement(AppiumBy.accessibilityId("password"));
        password.clear();
        Thread.sleep(2000);
        password.sendKeys("admin");
        Thread.sleep(2000);

        // Clicking login button
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
        Thread.sleep(5000);

//        driver.close(); //deprecated
        driver.quit();

    }
}
