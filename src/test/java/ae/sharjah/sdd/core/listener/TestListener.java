package ae.sharjah.sdd.core.listener;
import ae.sharjah.sdd.core.driver.MobileDriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.testng.*;
import java.io.ByteArrayInputStream;
public class TestListener extends TestListenerAdapter {
    @Override public void onTestFailure(ITestResult r) {
        try {
            byte[] b=MobileDriverManager.getDriver().getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Mobile failure screenshot","image/png",new ByteArrayInputStream(b),"png");
        } catch(Exception ignored) {
        }
    }
}
