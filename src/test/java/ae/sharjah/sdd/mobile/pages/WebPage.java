package ae.sharjah.sdd.mobile.pages;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
public class WebPage {
    private final AndroidDriver d;
    private final WebDriverWait w;
    private final By send=By.xpath("//*[@text='Send me your name!']"), name=By.className("android.widget.EditText"), mercedes=By.xpath("//*[@text='Mercedes']"), result=By.xpath("//*[@text='This is my way of saying hello']"), here=By.xpath("//*[@text='here']");
    public WebPage(AndroidDriver d) {
        this.d=d;
        w=new WebDriverWait(d,Duration.ofSeconds(10));
    }
    public boolean isFormDisplayed() {
        try {
            w.until(x->!x.findElements(send).isEmpty());
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    public void enterName(String s) {
        d.findElement(name).clear();
        d.findElement(name).sendKeys(s);
        try {
            d.hideKeyboard();
        } catch(Exception ignored) {
        }
    }
    public void selectCar(String s) {
        d.findElements(By.className("android.widget.Spinner")).get(1).click();
        if(s.equalsIgnoreCase("Mercedes"))d.findElement(mercedes).click();
    }
    public String getSelectedCar() {
        return d.findElements(By.className("android.widget.Spinner")).get(1).getText();
    }
    public void clickSend() {
        d.findElement(send).click();
    }
    public boolean isResultDisplayed() {
        return !d.findElements(result).isEmpty();
    }
    public boolean isNameDisplayed(String s) {
        return !d.findElements(By.xpath("//*[contains(@text,'"+s+"')]")).isEmpty();
    }
    public void clickHere() {
        d.findElement(here).click();
    }
    public boolean isDefaultCarVolvo() {
        return !d.findElements(By.xpath("//*[@text='Volvo']")).isEmpty();
    }
}
