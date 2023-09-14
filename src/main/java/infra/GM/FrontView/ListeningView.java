package infra.GM.FrontView;

import infra.Common.Common;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ListeningView {
    private Common common = null;

    public ListeningView(Common commonObj) {
        common = commonObj;

    }

    public void verifyListeningView() {

        try {

            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.google.android.googlequicksearchbox:id/googleapp_voice_results_text")));

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }



}
