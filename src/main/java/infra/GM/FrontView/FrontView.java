package infra.GM.FrontView;

import infra.Common.Common;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FrontView {

    private Common common = null;

    public FrontView(Common commonObj) {
        common = commonObj;

    }

    public Integer getCurrentTemperature() {

        Integer temperature = 0;

        try {
            String temp = common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(temperature.toString()))).getText();

            temperature = Integer.valueOf(temp);

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

        return temperature;
    }

    public void setTemperatureTextBox(Integer temperature) {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.googlequicksearchbox:id/googleapp_search_box"))).sendKeys(temperature.toString() + " Celsius");

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }
    }

    public void clickTemperatureTextBox() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Search"))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void verifyTemperatureList(String expectedTemperature) {

        try {
            String text = common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.support.v7.widget.RecyclerView[@content-desc=\"Predictions\"]/android.widget.LinearLayout[1]/android.view.ViewGroup/android.widget.LinearLayout/android.widget.TextView"))).getText();

            if(text.contains(expectedTemperature)){
                common.Report("Found text " + text + " contains expected " + expectedTemperature);
            }else{
                common.Report("Found text " + text + " contains expected " + expectedTemperature);
            }
        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void clickSubmit() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Search"))).submit();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void clickCloseIcon() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Clear"))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void clickMicIcon() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Voice Search"))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void verifySearchResult() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.googlequicksearchbox:id/webx_web_view")));


        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }


   
}
