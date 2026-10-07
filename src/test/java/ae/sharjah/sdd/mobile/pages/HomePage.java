package ae.sharjah.sdd.mobile.pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
public class HomePage {
    private final AndroidDriver d;
    private final By title=By.id("android:id/title"), en=By.id("io.selendroid.testapp:id/buttonTest"), name=By.id("io.selendroid.testapp:id/my_text_field"), progress=By.id("io.selendroid.testapp:id/waitingButtonTest"), toast=By.id("io.selendroid.testapp:id/showToastButton"), popup=By.id("io.selendroid.testapp:id/showPopupWindowButton"), no=By.xpath("//*[@text='No, no']"), chrome=By.id("io.selendroid.testapp:id/buttonStartWebview"), register=By.id("io.selendroid.testapp:id/startUserRegistration"), exception=By.id("io.selendroid.testapp:id/exceptionTestButton"), exceptionField=By.xpath("//android.widget.EditText"), dismiss=By.xpath("//*[contains(@text,'Dismiss')]");
    public HomePage(AndroidDriver d) {
        this.d=d;
    }
    public String getTitle() {
        return d.findElement(title).getText();
    }
    public boolean isEnButtonVisible() {
        return !d.findElements(en).isEmpty();
    }
    public boolean isNameFieldVisible() {
        return !d.findElements(name).isEmpty();
    }
    public boolean isProgressButtonVisible() {
        return !d.findElements(progress).isEmpty();
    }
    public boolean isToastButtonVisible() {
        return !d.findElements(toast).isEmpty();
    }
    public boolean isPopupButtonVisible() {
        return !d.findElements(popup).isEmpty();
    }
    public boolean isHomePageDisplayed() {
        return isEnButtonVisible();
    }
    public void clickEnButton() {
        d.findElement(en).click();
    }
    public void clickNoButton() {
        d.findElement(no).click();
    }
    public void clickChromeButton() {
        d.findElement(chrome).click();
    }
    public void clickRegisterButton() {
        dismissPopupIfPresent();
        d.findElement(register).click();
    }
    public void clickProgressButton() {
        dismissPopupIfPresent();
        d.findElement(progress).click();
    }
    public void clickToastButton() {
        dismissPopupIfPresent();
        d.findElement(toast).click();
    }
    public void clickPopupButton() {
        d.findElement(popup).click();
    }
    public void clickExceptionButton() {
        dismissPopupIfPresent();
        d.findElement(exception).click();
    }
    public void enterExceptionText(String s) {
        dismissPopupIfPresent();
        d.findElement(exceptionField).clear();
        d.findElement(exceptionField).sendKeys(s);
    }
    public void dismissPopupIfPresent() {
        if(!d.findElements(dismiss).isEmpty())d.findElement(dismiss).click();
    }
}
