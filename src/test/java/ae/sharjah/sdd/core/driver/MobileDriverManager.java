package ae.sharjah.sdd.core.driver;
import ae.sharjah.sdd.core.config.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;
import java.time.Duration;
public final class MobileDriverManager {
    private static AndroidDriver driver;
    private MobileDriverManager() {
    }
    public static AndroidDriver getDriver() {
        if(driver==null) start();
        return driver;
    }
    private static void start() {
        try {
            UiAutomator2Options o=new UiAutomator2Options();
            o.setPlatformName("Android");
            o.setAutomationName("UiAutomator2");
            o.setDeviceName(Config.get("device.name"));
            o.setUdid(Config.get("udid"));
            o.setAppPackage(Config.get("app.package"));
            o.setAppActivity(Config.get("app.activity"));
            o.setNoReset(true);
            o.setNewCommandTimeout(Duration.ofSeconds(120));
            driver=new AndroidDriver(new URL(Config.get("appium.url")),o);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        } catch(Exception e) {
            throw new RuntimeException("Unable to start Appium",e);
        }
    }
    public static void quitDriver() {
        if(driver!=null) {
            driver.quit();
            driver=null;
        }
    }
}
