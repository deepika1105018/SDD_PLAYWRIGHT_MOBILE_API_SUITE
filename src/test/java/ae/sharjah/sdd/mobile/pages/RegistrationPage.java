package ae.sharjah.sdd.mobile.pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
public class RegistrationPage {
    private final AndroidDriver d;
    private final WebDriverWait w;
    private final By title=By.xpath("//*[@text='Welcome to register a new User']"), username=By.id("io.selendroid.testapp:id/inputUsername"), email=By.id("io.selendroid.testapp:id/inputEmail"), password=By.id("io.selendroid.testapp:id/inputPassword"), name=By.id("io.selendroid.testapp:id/inputName"), ruby=By.xpath("//*[@text='Ruby']"), adds=By.id("io.selendroid.testapp:id/input_adds"), verify=By.id("io.selendroid.testapp:id/btnRegisterUser"), waiting=By.xpath("//*[contains(@text,'Waiting Dialog')]"), register=By.xpath("//*[@text='Register User']");
    public RegistrationPage(AndroidDriver d) {
        this.d=d;
        w=new WebDriverWait(d,Duration.ofSeconds(30));
    }
    public boolean isRegistrationPageDisplayed() {
        return !d.findElements(title).isEmpty();
    }
    public String getName() {
        return d.findElement(name).getText();
    }
    public String getLanguage() {
        return d.findElements(ruby).isEmpty()?"":"Ruby";
    }
    private void type(By b,String s) {
        d.findElement(b).clear();
        d.findElement(b).sendKeys(s);
    }
    public void enterUsername(String s) {
        type(username,s);
    }
    public void enterEmail(String s) {
        type(email,s);
    }
    public void enterPassword(String s) {
        type(password,s);
    }
    public void enterName(String s) {
        type(name,s);
    }
    public void selectAcceptAdds() {
        if(!d.findElement(adds).isSelected())d.findElement(adds).click();
    }
    public void clickRegisterVerify() {
        try {
            d.hideKeyboard();
        } catch(Exception ignored) {
        }
        d.findElement(verify).click();
    }
    public boolean isValueDisplayed(String s) {
        return !d.findElements(By.xpath("//*[contains(@text,'"+s+"')]")).isEmpty();
    }
    public boolean isAcceptAddsTrue() {
        return !d.findElements(By.xpath("//*[@text='true']")).isEmpty();
    }
    public void clickRegisterUser() {
        d.findElement(register).click();
    }
    public boolean isWaitingDialogDisplayed() {
        return !d.findElements(waiting).isEmpty();
    }
    public void waitForWaitingDialogToDisappear() {
        w.until(ExpectedConditions.invisibilityOfElementLocated(waiting));
    }
}
