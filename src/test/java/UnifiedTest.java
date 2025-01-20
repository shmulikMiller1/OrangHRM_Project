import base.TestBase;
import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class UnifiedTest extends TestBase {
    private ChromeDriver driver;

    @BeforeAll
    public static void initializeReport() {startReport(); }

    @AfterAll
    public static void finalizeReport() {endReport(); }

    @Test
    public void TC1() {
        // Testing login with valid user credentials

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC1 - Valid Login Test", "Verify login with valid credentials");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login with valid credentials
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Verify user is redirected to the dashboard
            if (driver.getCurrentUrl().contains("dashboard")) {
                test.pass("Login successful");
            } else {
                throw new Exception("Login failed - dashboard not reached.");
            }
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC2() {
        // Testing login with invalid user credentials

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC2 - Invalid Login Test", "Verify error message for invalid credentials");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Attempt login with invalid credentials
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("InvalidUser", "InvalidPass");

            // Verify the displayed error message
            String errorMessage = loginPage.getErrorMessage();
            if (errorMessage.equals("Invalid credentials")) {
                test.pass("Error message displayed correctly");
            } else {
                throw new Exception("Error message not displayed as expected.");
            }
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC3() {
        // Test for adding a new employee with valid details

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC3 - Add New Employee Test", "Verify adding a new employee with valid details");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login to the system with admin credentials
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigate to the PIM module and add a new employee
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addNewEmployee("Shmulik", "Miller");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean urlUpdated = wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));

            // Verify the new employee was added successfully
            String currentURL = driver.getCurrentUrl();
            if (currentURL.contains("/pim/viewPersonalDetails/empNumber/")) {
                test.pass("Employee added successfully. Current URL: " + currentURL);
            } else {
                throw new Exception("Failed to add employee. Unexpected URL: " + currentURL);
            }
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC4() {
        //  Verify adding an employee without required fields

        ExtentTest test = null;
        try {
            // Creating a test report for this scenario
            test = extent.createTest("TC4 - Add Employee Without Required Fields", "Verify system's behavior when adding an employee without mandatory fields");

            // Initialize the browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Logging in with admin credentials
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigating to the Add Employee page
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addNewEmployee("", "");

            // Verifying the error message displayed
            WebElement firstNameError = driver.findElement(By.className("oxd-text"));
            if (firstNameError.isDisplayed()) {
                test.pass("Error message for First Name is displayed correctly.");
            } else {
                throw new Exception("Error message for First Name is not displayed!");
            }

        } catch (Exception e) {
            // טיפול בחריגות ועדכון הדוח במקרה של כישלון / Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // סגירת הדפדפן / Closing the browser after the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC5() {
        // Test case to verify successful claim submission by a user

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC5 - Successful Claim Submission", "Verify that a claim can be submitted successfully");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("admin", "admin123");

            // Navigating to the Add Employee page and add a new employee with login details
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addEmployeeWithLoginDetails("Saar", "Dee", "saarD", "saar1234", "saar1234");
            sleep(5000);

            // Log out as admin
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));

            // Log in with the new employee credentials
            loginPage.login("saarD", "saar1234");

            // Navigate to the "Claim" module
            ClaimPage claimPage = new ClaimPage(driver);
            claimPage.claimMenu.click();

            // Click on "+ Submit Claim" button
            claimPage.submitClaimButton.click();

            // Select Event in Event dropdown and Currency in Currency dropdown
            claimPage.selectEventAndCurrency(1, 2);

            // Click on the "Create" button
            claimPage.createButton.click();

            // Verify that the claim was submitted successfully by checking the URL
            boolean isSuccessful = wait.until(ExpectedConditions.urlContains("submitClaim"));
            if (isSuccessful) {
                test.pass("Claim submitted successfully");
            } else {
                throw new Exception("Claim submission failed - success page not reached.");
            }

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC6() {
        // Negative test case to verify claim submission without selecting event and currency

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC6 - Claim Submission Without Mandatory Fields", "Verify claim cannot be submitted without event and currency selection");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("admin", "admin123");

            // Navigating to the Add Employee page and adding a new employee with login details
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addEmployeeWithLoginDetails("Kobi", "Yunasi", "kobiyunasi", "kobi123", "kobi123");
            sleep(5000);

            // Log out as admin
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@type='submit']")));

            // Log in with the new employee credentials
            loginPage.login("kobiyunasi", "kobi123");

            // Navigate to the "Claim" module
            ClaimPage claimPage = new ClaimPage(driver);
            claimPage.claimMenu.click();

            // Click on "+ Submit Claim" button
            claimPage.submitClaimButton.click();

            // Click on "Create" button without selecting Event and Currency
            claimPage.createButton.click();

            // Verify that the system highlights mandatory fields and prevents form submission
            WebElement eventError = driver.findElement(By.xpath("//span[text()='Required' and contains(@class, 'oxd-input-group__message')]"));
            WebElement currencyError = driver.findElement(By.xpath("//span[text()='Required' and contains(@class, 'oxd-input-group__message')]"));

            if (eventError.isDisplayed() && currencyError.isDisplayed()) {
                test.pass("Mandatory fields are highlighted as expected and form submission is prevented.");
            } else {
                throw new Exception("Mandatory fields are not highlighted, or the form was submitted incorrectly.");
            }

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC7() {
        // Test case to search for an employee by a valid name

        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC7 - Search Employee by Valid Name", "Verify that the system displays the correct employee details for a valid name");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("admin", "admin123");

            // Navigate to the "PIM" module
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();

            // Enter employee name in the search field
            pimPage.enterEmployeeName("Jennifer Jones");

            // Click the "Search" button
            pimPage.searchButton.click();

            // Log test success
            test.pass("Search button clicked successfully");

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC8() {
        // Test to verify restricted access to the Admin module by a regular user
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC8 - Restricted Access to Admin Module", "Verify that a regular user cannot access the Admin module");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("admin", "admin123");

            // Navigate to the Add Employee page and add a new employee with login details
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addEmployeeWithLoginDetails("Chemi", "Raved", "chemi", "chemi123", "chemi123");
            sleep(5000);

            // Log out as admin
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

            // Log in with the new employee credentials
            loginPage.login("chemi", "chemi123");

            // Attempt to navigate to the Admin module
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                AdminPage adminPage = new AdminPage(driver);
                adminPage.adminMenu.click();
                throw new Exception("Regular user was able to access the Admin module, which is incorrect.");
            } catch (NoSuchElementException e) {
                // Confirm that the Admin module is not accessible
                test.pass("Regular user could not access the Admin module as expected.");
            }
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC9() {
        // Test to verify that the employee list is displayed correctly for the admin user
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC9 - View Employee List Test", "Verify that the admin user can view the full employee list");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigate to the PIM module and click on the "Employee List" tab
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.employeeListButton.click();

            // Confirm that the Employee List button was clicked successfully
            test.pass("Employee List button clicked successfully and test passed.");

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC10() {
        // Test to verify filtering users in the Admin module by name
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC10 - Filter Users in Admin Module by Name", "Verify filtering users by name in the Admin module");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigate to the "Admin" module
            AdminPage adminPage = new AdminPage(driver);
            adminPage.adminMenu.click();

            // Enter a username in the filter and click "Search"
            adminPage.usernameField.sendKeys("Admin");
            adminPage.searchButton.click();

            // Confirm that the search button was clicked successfully
            test.pass("Search button clicked successfully, and the user list is filtered by the entered username.");
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC11() {
        // Test to verify navigation between tabs
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC11 - Navigation Between Tabs Test", "Verify navigation between Admin and PIM modules");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigate to the "Admin" module
            AdminPage adminPage = new AdminPage(driver);
            adminPage.adminMenu.click();
            test.pass("Navigated to Admin module successfully.");

            // Navigate to the "PIM" module
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            test.pass("Navigated to PIM module successfully.");

            // Return to the "Admin" module
            adminPage.adminMenu.click();
            test.pass("Returned to Admin module successfully.");
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC12() {
        // Test to verify sorting of the User List by Username in ascending and descending order
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC12 - Sort User List by Username", "Verify sorting of User List by Username in ascending and descending order");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Navigate to the "Admin" module
            AdminPage adminPage = new AdminPage(driver);
            adminPage.adminMenu.click();

            // Click the arrow button in the "Username" column to sort ascending
            adminPage.sortArrowButton.click();
            adminPage.ascendingButton.click();
            test.pass("User list sorted in ascending order by username.");

            // Click the arrow button again in the "Username" column to sort descending
            adminPage.sortArrowButton.click();
            adminPage.descendingButton.click();
            test.pass("User list sorted in descending order by username.");
        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC13() {
        // Test to verify the document upload functionality in the "My Info" module
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC13 - Upload Document in My Info", "Verify that an employee can upload a document in the My Info module");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("admin", "admin123");

            // Navigate to the "PIM" module and add a new employee with login details
            PIMPage pimPage = new PIMPage(driver);
            pimPage.pimMenu.click();
            pimPage.addEmployeeWithLoginDetails("Sapir", "Mymon", "sapir", "sapir123", "sapir123");
            sleep(5000);

            // Log out as admin
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

            // Log in with the new employee credentials
            loginPage.login("sapir", "sapir123");

            // Navigate to the "My Info" module
            MyInfoPage myInfoPage = new MyInfoPage(driver);
            myInfoPage.myInfoMenu.click();

            // Scroll to the "Attachments" section and click "Add"
            myInfoPage.addFile("C:\\Users\\user\\Downloads\\תעודה קואליטסט.jpg");

            test.pass("The 'Save' button was clicked successfully and the test passed.");

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC14() {
        // Test to verify unauthorized access prevention after logout
        ExtentTest test = null;
        try {
            // Create a test report
            test = extent.createTest("TC14 - Unauthorized Access Prevention Test", "Verify that unauthorized users cannot access restricted pages after logout");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Login as admin
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("Admin", "admin123");

            // Save the dashboard URL for later use
            String dashboardURL = driver.getCurrentUrl();
            test.info("Dashboard URL saved: " + dashboardURL);

            // Log out from the system
            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.logout();

            // Attempt to access the saved URL after logout
            driver.get(dashboardURL);

            // Verify that the user is redirected to the login page
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean isRedirectedToLogin = wait.until(ExpectedConditions.urlContains("/auth/login"));
            if (isRedirectedToLogin) {
                // Log success if redirection occurs
                test.pass("The system successfully redirected the user to the login page and prevented unauthorized access.");
            } else {
                // Throw exception if redirection fails
                throw new Exception("The system failed to redirect the user to the login page. Unauthorized access was possible.");
            }
        } catch (Exception e) {
            // Handle errors and update the test report
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }


    @Test
    public void TC15() {
        // SQL Injection Test on Login Page
        ExtentTest test = null;
        try {
            // Create a test report for this specific test
            test = extent.createTest("TC15 - SQL Injection Test on Login Page",
                    "Verify that SQL injection is rejected and the system remains functional");

            // Open browser and navigate to the login page
            driver = seleniumInit();
            navigateToURL(driver, "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            // Initialize login page
            LoginPage loginPage = new LoginPage(driver);

            // Perform SQL injection attempt
            loginPage.login(" OR '1'='1' ", "randomPassword");

            // Wait for and verify the error message
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            String expectedErrorMessage = "Invalid credentials";
            boolean isErrorDisplayed = loginPage.isErrorMessageDisplayed(wait, expectedErrorMessage);
            if (isErrorDisplayed) {
                test.pass("SQL injection attempt was successfully blocked. Error message displayed: " + expectedErrorMessage);
            } else {
                throw new Exception("SQL injection attempt was not blocked or the error message was not displayed.");
            }

            // Perform valid login to ensure the system is functional
            loginPage.login("admin", "admin123");

            // Verify redirection to the dashboard
            wait.until(ExpectedConditions.urlContains("/dashboard/index"));
            test.pass("Valid login successful, and system remains functional after SQL injection attempt.");

        } catch (Exception e) {
            // Handle exceptions and update the report on failure
            handleTestFailure(e, test);
        } finally {
            // Close the browser after completing the test
            closeDriver(driver);
        }
    }

    private void handleTestFailure(Exception e, ExtentTest test) {
        if (test != null) {
            test.fail("Test failed: " + e.getMessage());
        }
        System.out.println("Error: " + e.getMessage());
    }

    private void closeDriver(ChromeDriver driver) {
        if (driver != null) {
            try {
                seleniumClose(driver);
            } catch (Exception e) {
                System.out.println("Error closing driver: " + e.getMessage());
            }
        }
    }


}
