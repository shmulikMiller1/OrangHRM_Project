package base;

import utilities.SeleniumBase;
import utilities.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestBase extends SeleniumBase {

    protected static ExtentReports extent;
    protected static ExtentTest test;

    public static void startReport() {
        extent = ExtentReportManager.getExtentReports();
    }

    public static void endReport() {
        extent.flush();
    }
}
