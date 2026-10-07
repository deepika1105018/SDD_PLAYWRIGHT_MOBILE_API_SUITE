package ae.sharjah.sdd.core.retry;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class RetryAnalyzer implements IRetryAnalyzer {
    private int count=0;
    private static final int MAX=1;
    public boolean retry(ITestResult r) {
        if(count<MAX) {
            count++;
            System.out.println("Retrying: "+r.getName()+" ("+count+"/"+MAX+")");
            return true;
        }
        return false;
    }
}
