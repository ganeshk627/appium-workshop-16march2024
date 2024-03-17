package com.tribe.workshop.appium.tests.march2024;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class Gestures {

    private static final Logger logger = LogManager.getLogger(Gestures.class);
    AppiumDriverLocalService appiumService;
    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeSuite
    public void setupAppiumServer() {
        AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withIPAddress("127.0.0.1")
                .withTimeout(Duration.ofMinutes(5))
                .withArgument(GeneralServerFlag.USE_PLUGINS, "gestures,element-wait");

        appiumService = AppiumDriverLocalService.buildService(appiumServerArguments);

        // start appium server
        appiumService.start();
        logger.info("Appium Server Started At : " + appiumService.getUrl());
    }

    @BeforeMethod
    public void initializeDriver() throws URISyntaxException, MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel 6 Pro API 28");

        driver = new AndroidDriver(appiumService.getUrl(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Test
    public void DragAndDrop() {

        //login
        login();
        // select drag and drop
        selectOption("Drag & Drop");

        // drag and drop
        WebElement dragMeElement = findElement(AppiumBy.accessibilityId("dragMe"));
        WebElement dropMeElement = findElement(AppiumBy.accessibilityId("dropzone"));

        // create first finger event
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

        Sequence dragAndDropSequence = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getCenterOfElement(dragMeElement.getLocation(), dragMeElement.getSize())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(firstFinger.createPointerMove(Duration.ofMillis(900), PointerInput.Origin.viewport(), getCenterOfElement(dropMeElement.getLocation(), dropMeElement.getSize())))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));


        // execute actions

        driver.perform(Collections.singleton(dragAndDropSequence));

    }

    @Test
    public void DragAndDropUsingPlugin() {

        //login
        login();
        // select drag and drop
        selectOption("Drag & Drop");

        // drag and drop using plugin
        RemoteWebElement source = (RemoteWebElement) wait.until(elementToBeClickable(AppiumBy.accessibilityId("dragMe")));
        RemoteWebElement destination = (RemoteWebElement) wait.until(elementToBeClickable(AppiumBy.accessibilityId("dropzone")));

        driver.executeScript("gesture: dragAndDrop", Map.of("sourceId", source.getId(), "destinationId", destination.getId()));
    }

    @Test
    public void Slider() {

        //login
        login();
        // select drag and drop
        selectOption("Slider");

        // drag and drop - slider
        WebElement slider1 = findElement(AppiumBy.accessibilityId("slider"));
        WebElement slider2 = findElement(AppiumBy.accessibilityId("slider1"));

        // create first finger event
        PointerInput firstFinger = new PointerInput(PointerInput.Kind.TOUCH, "finger1");


        Sequence slider1Sequence50 = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getLeftOfElement(slider1.getLocation())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(firstFinger.createPointerMove(Duration.ofMillis(900), PointerInput.Origin.viewport(), getCenterOfElement(slider1.getLocation(), slider1.getSize())))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        Sequence slider2Sequence100 = new Sequence(firstFinger, 1)
                .addAction(firstFinger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), getLeftOfElement(slider2.getLocation())))
                .addAction(firstFinger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(firstFinger.createPointerMove(Duration.ofMillis(900), PointerInput.Origin.viewport(), getRightOfElement(slider2.getLocation(), slider2.getSize())))
                .addAction(firstFinger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // execute actions
        driver.perform(Collections.singleton(slider1Sequence50));
        driver.perform(Collections.singleton(slider2Sequence100));




    }

    @Test
    public void SliderUsingPlugin() {

        //login
        login();
        // select drag and drop
        selectOption("Slider");

        // slider using plugin


    }

    @AfterMethod
    public void closeSession() {
//        driver.close(); //deprecated
        waitFor(5);
        driver.quit();
    }

    @AfterSuite
    public void stopAppiumServer() {
        logger.info("Stopping Appium Server");
        appiumService.stop();
    }

    private WebElement findElement(By by) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    private void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public Point getCenterOfElement(Point elementLocation, Dimension elementSize) {
        return new Point(elementLocation.getX() + (elementSize.getWidth() / 2),
                elementLocation.getY() + (elementSize.getHeight() / 2));
    }

    public Point getLeftOfElement(Point elementLocation) {
        return new Point(elementLocation.getX(), elementLocation.getY());
    }

    public Point getRightOfElement(Point elementLocation, Dimension elementSize) {
        return new Point(elementLocation.getX() + elementSize.getWidth(),
                elementLocation.getY() + elementSize.getHeight());
    }
    private void login() {
        WebElement username = findElement(AppiumBy.accessibilityId("username"));
        username.clear();
//        waitFor(1);
        username.sendKeys("admin");
//        waitFor(1);
        WebElement password = findElement(AppiumBy.accessibilityId("password"));
        password.clear();
//        waitFor(1);
        password.sendKeys("admin");
//        waitFor(1);
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"LOG IN\")")).click();
//        waitFor(1);
    }

    public void selectOption(String option) {
        findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"" + option + "\")")).click();
    }


}
