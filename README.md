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

# 03. Connect Appium Inspector through JSON Capabilities

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

```bash
  mCurrentFocus=Window{c5d6e72 u0 com.android.calculator2/com.android.calculator2.Calculator}
```
__Package Name__: _com.android.calculator2_

__Activity Name__: *com.android.calculator2.Calculator*


# 05. Locator Strategies

1. Id
2. Accessibility Id
3. Name
4. Xpath - slower
5. UIAutomator - faster and better option than xpath;
- [UISelector](https://developer.android.com/reference/androidx/test/uiautomator/UiSelector)
6. Class Name

# 06. Writing the First Script


# 07. 
