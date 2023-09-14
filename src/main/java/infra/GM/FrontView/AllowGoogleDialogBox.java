package infra.GM.FrontView;

import infra.Common.Common;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AllowGoogleDialogBox {

    private Common common = null;

    public AllowGoogleDialogBox(Common commonObj) {
        common = commonObj;

    }

    public void verifyAllowDialog() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.android.permissioncontroller:id/grant_dialog")));

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }

    public void clickAllowAllTheTimeLink() {

        try {
            common.getDriverWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_always_button"))).click();

        } catch (Exception e) {
            common.Report(e.getMessage(), Common.MessageColor.RED);
        }

    }
}
