package ae.sharjah.sdd.core.config;
import java.io.*;
import java.util.Properties;
public final class Config {
    private static final Properties P=new Properties();
    static {
        try(InputStream in=new FileInputStream("config/config.properties")) {
            P.load(in);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Config() {
    }
    public static String get(String k) {
        return System.getProperty(k,P.getProperty(k));
    }
    public static boolean bool(String k) {
        return Boolean.parseBoolean(get(k));
    }
}
