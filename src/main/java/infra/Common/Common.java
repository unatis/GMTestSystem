package infra.Common;

import com.opencsv.CSVWriter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Common {

    public enum Browser{
        CHROME,
        FIREFOX
    }

    public enum MessageColor{
        RED,
        BLACK,
        GREEN
    }

    public List<String> reportResultRow = new ArrayList<String>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLACK= "\u001B[30m";

    public enum Context{
        NativeAPP,
        WebView
    }
    private WebDriver driver = null;
    private WebDriverWait wait = null;
    private AppiumDriver mDriver = null;

    public String LaunchBrowser(Browser browser){

        String drvUID = "";

        try{

            switch (browser){

                case FIREFOX:

                    System.setProperty("webdriver.gecko.driver", ".//Tools//geckodriver.exe");

                    driver = new FirefoxDriver();

                    break;

                case CHROME:
                    System.setProperty("webdriver.chrome.driver", ".//Tools//chromedriver.exe");

                    driver = new ChromeDriver();
                    break;

                default:
                    driver = new ChromeDriver();
                    break;

            }

            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

            Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
            Report("Browser "+cap.getBrowserName()+ " version " + (String)cap.getCapability("browserVersion") + " running successfully");

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return "";
        }

        return drvUID;
    }

    public void NavigateTo(String URL){

        try{
            driver.get(URL);
        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void ExitEnvironment(){

        try{
            driver.quit();

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void SwitchToWindow(String WindowTitle){

        try{
            ArrayList<String> widows = new ArrayList<String>(driver.getWindowHandles());

            for(String window : widows){

                driver.switchTo().window(window);

                if(driver.getTitle().trim().contains(WindowTitle)){
                    Report("Switch to " + WindowTitle + " succeed");
                    break;
                }

            }

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }
    }

    public void CloseBrowser(){

        try{
            if(driver != null)
            {
                driver.quit();
                Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
                Report("Browser "+cap.getBrowserName()+ " version " + (String)cap.getCapability("browserVersion") + " closed successfully");
            }else{
                Report("Browser driver is null");
            }

        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void Await(int Seconds){

        try{
            Thread.sleep(Seconds * 1000);
        }catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public static void Report(String Message){
        System.out.println(Message);
    }

    public static void Report(String Message, MessageColor Color){

        switch(Color){

            case RED:
                System.out.println(ANSI_RED + Message + ANSI_RESET);
                assertTrue(false, "");
                break;

            case BLACK:
                System.out.println(ANSI_BLACK + Message + ANSI_RESET);
                break;

            case GREEN:
                System.out.println(ANSI_GREEN + Message + ANSI_RESET);
                break;
        }

    }

    public WebDriver getDriver(){

        try{
            return driver;

        } catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return null;
        }

    }

    public WebDriver getMobileDriver(){

        try{
            return mDriver;

        } catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return null;
        }

    }

    public WebDriverWait getDriverWait(){

        try{
            return wait;

        } catch(Exception e){
            Report(e.getMessage(), MessageColor.RED);
            return null;
        }

    }

    public String executeJavaScript(String scriptText) {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String response = (String) jse.executeScript(scriptText);

        return response;
    }

    public boolean isElementPresent(By locatorKey) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locatorKey));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementVisible(String cssLocator) {
        return driver.findElement(By.cssSelector(cssLocator)).isDisplayed();
    }

    public void LaunchEmulator(String DeviceName, String DeviceID) {

        Process process;

        try {

            String emulatorLocation = getAppLocation("emulator");
            //String emulatorLocation = Paths.get("/Library/Android/sdk/tools/emulator").toAbsolutePath().toString();

            //builder = new ProcessBuilder("/bin/sh", "-c", emulatorLocation + " -avd " + DeviceName);
            process = Runtime.getRuntime().exec(emulatorLocation + " -avd " + DeviceName);
            process.waitFor(20, TimeUnit.SECONDS);

            Report("Emulator android running");
        } catch (Exception e) {

            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void enableMicrophone() {

        Process process;

        try {

            process = Runtime.getRuntime().exec("adb emu avd hostmicon");
            process.waitFor(20, TimeUnit.SECONDS);

            Report("Emulator android running");
        } catch (Exception e) {

            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void stopEmulator(String DeviceID) {

        Process process;

        try {

            process = Runtime.getRuntime().exec("adb -s "+DeviceID+" emu kill");
            process.waitFor(20, TimeUnit.SECONDS);

            Report("Emulator android killed");
        } catch (Exception e) {

            Report(e.getMessage(), MessageColor.RED);
        }

    }

    public void LaunchApp(String PackageName, String ActivityName, String DeviceName, String DeviceID, String PlatformVersion) {
        try {
            mDriver = null;

            String ApplicationPath = "";

            String version;

            //ApplicationPath = Paths.get("Tools").toAbsolutePath().toString() + File.separator+ApplicationFileName;

            URL url = new URL("http://127.0.0.1:4723/wd/hub");

            DesiredCapabilities dc = new DesiredCapabilities();
            dc.setCapability(MobileCapabilityType.DEVICE_NAME, DeviceName);
            dc.setCapability(MobileCapabilityType.UDID, DeviceID);
            dc.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            dc.setCapability("platformVersion", PlatformVersion);
            dc.setCapability("ensureWebviewsHavePages", true);
            dc.setCapability("usePrebuiltWDA", true);

            dc.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 180000);
            dc.setCapability(AndroidMobileCapabilityType.ANDROID_DEVICE_READY_TIMEOUT, 180);

            dc.setCapability(MobileCapabilityType.NO_RESET, true);//clears the app data, such as its cache


            dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 10800); //keep appium session alive for 3 hours (in seconds)
            //dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PackageName);
            //dc.setCapability("appActivity", ActivityName);

            mDriver = new AndroidDriver(url, dc);

            mDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            wait = new WebDriverWait(mDriver, Duration.ofSeconds(10));

        } catch (Exception ex) {
            Report(ex.getMessage(), MessageColor.RED);
        }
    }

    public void closeApp() {
        try {
            mDriver.quit();

        } catch (Exception ex) {
            Report(ex.getMessage(), MessageColor.RED);
        }
    }

    public void switchContext(Context context) {
        Set<String> contextNames = ((SupportsContextSwitching) mDriver).getContextHandles();
        if (context.equals(Context.NativeAPP)) {
            for (String contextName : contextNames) {
                if (contextName.contains("NATIVE_APP")) {
                    ((SupportsContextSwitching) mDriver).context(contextName);
                    Report("Current context is " + contextName);
                    break;
                }
            }
        } else if (context.equals(Context.WebView)) {
            for (String contextName : contextNames) {
                if (contextName.contains("WEBVIEW")) {
                    ((SupportsContextSwitching) mDriver).context(contextName);
                    Report("Current context is " + contextName);
                    break;
                }
            }
        } else {
            Report("Context " + context.toString() + " not found");
        }
    }
    public void createCSVReport(String filePath) {

        try {

            Files.createDirectories(Paths.get(filePath.substring(0, filePath.lastIndexOf("//"))));

            File file = new File(filePath);

            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Utterance", "API intent", "Actual intent", "API entity", "Actual entity", "Intent Similarity",  "Entity Similarity" };
            writer.writeNext(header);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void reportCSVReport(String filePath, List<String> data) {

        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);


            //String[] data = { "Suraj", "10", "630" };
            writer.writeNext(data.toArray(new String[0]));

            // closing writer connection
            writer.close();

            reportResultRow.clear();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void await(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
            Report("Waiting for " +seconds+ " seconds");
        } catch (Exception ex) {
            Report(ex.getMessage(), MessageColor.RED);
        }
    }
    private static String getAppLocation(String AppName) {
        String tmp = "";

        try {

            switch (AppName) {
                case "appium":
                    tmp = "/usr/local/bin/appium";
                    break;
                case "emulator":
                    //tmp = System.getProperty("user.home") + "/Library/Android/sdk/tools/emulator";
                      tmp = "C:\\Documents and Settings\\ygpan\\AppData\\Local\\Android\\Sdk\\emulator\\emulator.exe";
                    //tmp = "C:\\emulator\\emulator.exe";
                    break;
                case "adb":
                    tmp = System.getProperty("user.home") + "/Library/Android/sdk/platform-tools/adb";
                    break;
                default:
                    tmp = "";
            }

        } catch (Exception e) {
            Report(e.getMessage(), MessageColor.RED);
        }

        return tmp;
    }

}
