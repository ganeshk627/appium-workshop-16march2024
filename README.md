# appium-workshop-16march2024
Jumpstart your mobile testing journey with appium

# 00. Pre-requisites
1. [Java JDK](https://www.java.com/en/)
2. [NodeJS LTS](https://nodejs.org/en/download/current)
3. [Appium 2.5](https://appium.io/docs/en/2.5/quickstart/install/)
4. [Appium drivers](https://appium.io/docs/en/2.5/ecosystem/drivers/)
```bash
appium driver list
appium driver install uiautomator2
```
5. [Flutter driver](https://github.com/appium/appium-flutter-driver) - if needed
6. [Appium Inspector](https://github.com/appium/appium-inspector/releases)
7. [Appium Doctor](#) -Health check
8. [Android Studio](https://developer.android.com/studio)
9. [Android Virtual Device](https://developer.android.com/studio/run/managing-avds) or Real Device or Cloud Device
10. [Scrcpy](https://github.com/Genymobile/scrcpy) -Screensharing library for real device

# 01. Setup and start AVD or Real Device

```bash
adb devices
```

# 02. Start Appium Server
```bash
appium
```

```
$ appium
[Appium] Welcome to Appium v2.5.1
[Appium] The autodetected Appium home path: /Users/ganeshk/.appium
[Appium] Attempting to load driver uiautomator2...
[Appium] Attempting to load driver xcuitest...
[Appium] Requiring driver at /Users/ganeshk/.appium/node_modules/appium-xcuitest-driver/build/index.js
[Appium] Requiring driver at /Users/ganeshk/.appium/node_modules/appium-uiautomator2-driver/build/index.js
[Appium] XCUITestDriver has been successfully loaded in 0.840s
[Appium] AndroidUiautomator2Driver has been successfully loaded in 0.840s
[Appium] Appium REST http interface listener started on http://0.0.0.0:4723
[Appium] You can provide the following URLs in your client code to connect to this server:
[Appium]        http://127.0.0.1:4723/ (only accessible from the same host)
[Appium]        http://192.168.1.9:4723/
[Appium] Available drivers:
[Appium]   - uiautomator2@3.0.2 (automationName 'UiAutomator2')
[Appium]   - xcuitest@7.3.1 (automationName 'XCUITest')
[Appium] Available plugins:
[Appium]   - gestures@4.0.1
[Appium]   - element-wait@3.0.2
[Appium] No plugins activated. Use the --use-plugins flag with names of plugins to activate

```


> [!TIP]
> Any above server URLs can be used.

# 03. Connect Appium Inspector through JSON Capabilities
1. Set the ```Desired Capabilities``` in ```Appium Server```
```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:platformVersion": "9.0",
  "appium:deviceName": "Pixel 6 Pro API 28"
}
```
__Remote Host:__ _127.0.0.1_

**Remote Port:** _4723_

**Remote Path:** _/_


2. Click ```Start Session```


# 04. Finding Activity to Launch Already Installed Application

[testsigma Reference](https://support.testsigma.com/support/solutions/articles/32000019977-how-to-find-app-package-and-app-activity-of-your-android-app)


## To ensure device/emulator running

```bash
adb devices
```

## Getting Activity and Package name
### For Mac/Linux:
```bash
adb shell dumpsys window | grep -E 'mCurrentFocus'
```
### For Windows:

```bash
adb shell dumpsys window | find "mCurrentFocus"
```

### Output


>$ adb shell dumpsys window | find "mCurrentFocus"
>
>  mCurrentFocus=Window{c5d6e72 u0 com.android.calculator2/com.android.calculator2.Calculator}
> 
>$

__Package Name__: _com.android.calculator2_

__Activity Name__: *com.android.calculator2.Calculator*


# 05. Locator Strategies

### 5.1. General Locators:
1. Id - fast and not possible to locate all the times
2. Accessibility Id
   1. Android - ```content-desc```
   2. iOS - name
3. Name
4. Xpath - slower
5. Class Name - not recommened if matches multiple elements

### 5.2. Android Specific Locators:
1. UIAutomator - android specific faster and better option than xpath;
   1. [UISelector](https://developer.android.com/reference/androidx/test/uiautomator/UiSelector)
2. Android Data Matcher
3. Android View Matcher
4. Android View Tag

### 5.3. iOS Specific Locators:
1. iOS Class Chain
2. iOS NS Predicate


# 06. Writing the First Script (JAVA)

```java
// FirstTest.java

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FirstTest {
    
    @Test
    public void firstTest() throws URISyntaxException, MalformedURLException, InterruptedException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setPlatformVersion("9.0")
                .setAppPackage("com.vodqareactnative")
                .setAppActivity("com.vodqareactnative.MainActivity")
                .setAutomationName("UiAutomator2")
                .setDeviceName("Pixel 6 Pro API 28");
        
       AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
//        AndroidDriver driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), options);

       // Enter username after clearing
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

```

# 07. Running Tests
1. Right click on IntelliJ workspace --> ```Run 'FirstTest'```


# 08. Inspect BrowserStack Mobile in Local
1. Launch application in [BrowserStack App Live](https://app-live.browserstack.com/)
2. Click ```Inspect``` --> ```Integrate Appium Inspector```
3. Copy the capabilities from BrowserStack

# 09. Mobile Gestures
- Tap
- Double Tap
- Drag/ Scroll
- Flick
- Pinch
- Spread
- Press
- Press and Tap
- Press and Drag
- Rotate - Two Fingers/ Single Finger


# 10. [Appium Pro](https://appiumpro.com/editions)


# 11. [Appium Gestures Plugin](https://github.com/AppiumTestDistribution/appium-gestures-plugin)

[Sai Krishna](https://www.linkedin.com/in/sai-krishna-3755407b/) thoughtworks

__11.1. Install Plugin__


```bash
appium plugin install --source=npm appium-gestures-plugin
```

__11.2. Activate Plugin__

__11.2.1. Activate Plugin by Starting Appium Server through CLI__
```bash
appium --use-plugins=gestures
```
__11.2.2. Activate Plugin by Starting Appium Server through Java Code__
```java
AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
        .usingAnyFreePort()
        .withIPAddress("127.0.0.1")
        .withTimeout(Duration.ofMinutes(5))
        .withArgument(GeneralServerFlag.USE_PLUGINS, "gestures");
```


# 12. [Appium Wait Plugin](https://github.com/AppiumTestDistribution/appium-wait-plugin)

__12.1. Install Plugin__
```bash
appium plugin install --source=npm appium-wait-plugin
```

__12.2. Activate Plugin__

__12.2.1. Activate Plugin by Starting Appium Server through CLI__
```bash
appium --use-plugins=element-wait
```

__12.2.2. Activate Plugin by Starting Appium Server through Java Code__
```java
AppiumServiceBuilder appiumServerArguments = new AppiumServiceBuilder()
        .usingAnyFreePort()
        .withIPAddress("127.0.0.1")
        .withTimeout(Duration.ofMinutes(5))
        .withArgument(GeneralServerFlag.USE_PLUGINS, "gestures,element-wait");
```


# *** OTHER LINKS *** 
[Test Automation of Real-time, Multi-user Games by Anand Bagmar](https://youtu.be/drb6DRG768k?si=RuFSz9VfP_AA0QtE)
