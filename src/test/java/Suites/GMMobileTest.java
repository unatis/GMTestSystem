package Suites;

import infra.Common.Common;
import infra.Common.Config;
import infra.GM.API.API;
import infra.GM.FrontView.AllowGoogleDialogBox;
import infra.GM.FrontView.FrontView;
import infra.GM.FrontView.ListeningView;
import infra.GM.Helper.Helper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GMMobileTest {
    private static String deviceName = "";
    private static String deviceID = "";
    private static String packageName = "";
    private static String activityName = "";
    private static String platformVersion = "";
    private static Common common = null;

    @BeforeAll
    public static void setup(){

        deviceName = Config.getProperty("mobile.deviceName");
        deviceID = Config.getProperty("mobile.deviceID");
        packageName = Config.getProperty("mobile.app.packageName");
        activityName = Config.getProperty("mobile.app.activityName");
        platformVersion = Config.getProperty("mobile.app.platformVersion");

        common = new Common();

        common.LaunchEmulator(deviceName, deviceID);//cmd adb devices
        common.Await(80);
        common.enableMicrophone();
    }

    @BeforeEach
    public void init(){
        common.LaunchApp(packageName, activityName, deviceName, deviceID, platformVersion);
        common.Await(25);
    }

    @Test
    void GMChangeTemperature(){

        FrontView frontView = new FrontView(common);

        frontView.clickTemperatureTextBox();

        frontView.setTemperatureTextBox(10);

        frontView.verifyTemperatureList("10 degrees Celsius = 50 degrees Fahrenheit");

        frontView.clickCloseIcon();

        common.await(2);

        frontView.clickMicIcon();

        AllowGoogleDialogBox allowGoogleDialogBox = new AllowGoogleDialogBox(common);

        allowGoogleDialogBox.verifyAllowDialog();

        allowGoogleDialogBox.clickAllowAllTheTimeLink();

        ListeningView listeningView = new ListeningView(common);

        listeningView.verifyListeningView();

        common.await(3);

        new Thread(() -> {

            Helper helper = new Helper(common);

            helper.playSound(".//Tools//ttsmaker-file-2023-9-14-12-16-1.wav");


        }).start();

        common.await(10);

        frontView.verifySearchResult();

        common.await(20);

    }

    @AfterEach
    public void teardown() {
        common.closeApp();
    }

    @AfterAll
    public static void cleanup() {
        common.stopEmulator(deviceID);
    }
}
