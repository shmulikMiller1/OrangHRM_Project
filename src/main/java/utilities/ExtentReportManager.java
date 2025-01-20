package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReportManager {
    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/src/main/resources/ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Automation Test Results");
            reporter.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Your Name");
        }
        return extent;
    }
}

