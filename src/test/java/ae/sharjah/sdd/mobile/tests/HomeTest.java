package ae.sharjah.sdd.mobile.tests;
import ae.sharjah.sdd.core.driver.MobileDriverManager;
import ae.sharjah.sdd.mobile.pages.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;


public class HomeTest {
    @BeforeMethod public void setUp() {
        MobileDriverManager.quitDriver();
        AndroidDriver d=MobileDriverManager.getDriver();
        d.activateApp("io.selendroid.testapp");
        for(int i=0;i<5;i++) {
            By x=By.xpath("//*[contains(@text,'Dismiss')]");
            if(!d.findElements(x).isEmpty())d.findElement(x).click();
            if(!d.findElements(By.id("io.selendroid.testapp:id/buttonTest")).isEmpty())return;
            d.navigate().back();
        }
    }
    @Test(groups={"smoke","mobile","regression"}) public void verifyHomePage() {
        HomePage p=new HomePage(MobileDriverManager.getDriver());
        Assert.assertEquals(p.getTitle(),"selendroid-test-app");
        Assert.assertTrue(p.isEnButtonVisible()&&p.isProgressButtonVisible()&&p.isToastButtonVisible());
    }
    @Test(groups={"sanity","mobile","regression"}) public void verifyEnButton() {
        HomePage p=new HomePage(MobileDriverManager.getDriver());
        p.clickEnButton();
        p.clickNoButton();
        Assert.assertTrue(p.isHomePageDisplayed());
    }
    @Test(groups={"smoke","mobile","regression"}) public void verifyWebPage() {
        AndroidDriver d=MobileDriverManager.getDriver();
        HomePage h=new HomePage(d);
        WebPage w=new WebPage(d);
        h.clickChromeButton();
        Assert.assertTrue(w.isFormDisplayed());
        w.enterName("Deepika");
        w.selectCar("Mercedes");
        Assert.assertEquals(w.getSelectedCar(),"Mercedes");
        w.clickSend();
        Assert.assertTrue(w.isResultDisplayed()&&w.isNameDisplayed("Deepika"));
        w.clickHere();
        Assert.assertTrue(w.isDefaultCarVolvo());
    }
    @Test(groups={"sanity","mobile","regression"}) public void verifyUserRegistration() {
        AndroidDriver d=MobileDriverManager.getDriver();
        HomePage h=new HomePage(d);
        RegistrationPage r=new RegistrationPage(d);
        h.clickRegisterButton();
        Assert.assertTrue(r.isRegistrationPageDisplayed());
        Assert.assertEquals(r.getName(),"Mr. Burns");
        Assert.assertEquals(r.getLanguage(),"Ruby");
        r.enterUsername("Deepika");
        r.enterEmail("deepika@gmail.com");
        r.enterPassword("12345678");
        r.enterName("Deepika");
        r.selectAcceptAdds();
        r.clickRegisterVerify();
        Assert.assertTrue(r.isValueDisplayed("Deepika")&&r.isValueDisplayed("deepika@gmail.com")&&r.isValueDisplayed("Ruby")&&r.isAcceptAddsTrue());
        r.clickRegisterUser();
        Assert.assertTrue(h.isHomePageDisplayed());
    }
    @Test(groups={"sanity","mobile","regression"}) public void verifyProgressBar() {
        AndroidDriver d=MobileDriverManager.getDriver();
        HomePage h=new HomePage(d);
        RegistrationPage r=new RegistrationPage(d);
        h.clickProgressButton();
        Assert.assertTrue(r.isWaitingDialogDisplayed());
        r.waitForWaitingDialogToDisappear();
        Assert.assertTrue(r.isRegistrationPageDisplayed());
        Assert.assertEquals(r.getLanguage(),"Ruby");
    }
    @Test(groups={"smoke","mobile","regression"}) public void verifyToastMessage() {
        AndroidDriver d=MobileDriverManager.getDriver();
        HomePage h=new HomePage(d);
        h.clickToastButton();
        By t=By.xpath("//android.widget.Toast");
        new WebDriverWait(d,Duration.ofSeconds(5)).until(x->!x.findElements(t).isEmpty());
        Assert.assertEquals(d.findElement(t).getText(),"Hello selendroid toast!");
    }
    @Test(groups={"sanity","mobile","regression"},enabled=false,description="Enable after confirming popup accessibility locator with uiautomator dump") public void verifyPopupWindow() {
        AndroidDriver d=MobileDriverManager.getDriver();
        new HomePage(d).clickPopupButton();
        By x=By.xpath("//*[contains(@text,'Dismiss')]");
        new WebDriverWait(d,Duration.ofSeconds(5)).until(v->!v.findElements(x).isEmpty());
        d.findElement(x).click();
    }
    @Test(groups={"negative","mobile","regression"}) public void verifyUnhandledExceptionButton() {
        AndroidDriver d=MobileDriverManager.getDriver();
        new HomePage(d).clickExceptionButton();
        Assert.assertFalse(d.findElements(By.id("android:id/title")).isEmpty());
    }
    @Test(groups={"negative","mobile","regression"}) public void verifyUnhandledExceptionText() {
        AndroidDriver d=MobileDriverManager.getDriver();
        new HomePage(d).enterExceptionText("test");
        Assert.assertFalse(d.findElements(By.id("android:id/title")).isEmpty());
    }
    @AfterClass public void close() {
        MobileDriverManager.quitDriver();
    }
}
