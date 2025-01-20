package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class SeleniumBase {

    public static ChromeDriver seleniumInit() {
        System.out.println("[INFO] Initializing ChromeDriver...");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.setAcceptInsecureCerts(true);
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("[INFO] ChromeDriver initialized successfully.");
        return driver;
    }

    public static void navigateToURL(ChromeDriver driver, String url) {
        if (driver != null) {
            System.out.println("[INFO] Navigating to URL: " + url);
            driver.get(url);
        } else {
            System.out.println("[ERROR] Cannot navigate, driver is null.");
        }
    }

    public static void seleniumClose(ChromeDriver driver) {
        if (driver != null) {
            System.out.println("[INFO] Closing ChromeDriver...");
            driver.close();
            System.out.println("[INFO] ChromeDriver closed successfully.");
        } else {
            System.out.println("[WARN] Driver is already null, no action taken.");
        }
    }
}
