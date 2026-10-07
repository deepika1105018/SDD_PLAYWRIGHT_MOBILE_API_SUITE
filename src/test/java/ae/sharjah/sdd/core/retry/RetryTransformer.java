package ae.sharjah.sdd.core.retry;
import org.testng.*;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.*;
public class RetryTransformer implements IAnnotationTransformer {
    public void transform(ITestAnnotation a, Class c, Constructor con, Method m) {
        if(a.getRetryAnalyzerClass()==null) a.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
