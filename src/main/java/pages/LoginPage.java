package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(name = "username")
    WebElement userName;

    @FindBy(name = "password")
    WebElement Password;

    @FindBy(className = "oxd-button")
    WebElement loginButton;

    @FindBy(className = "oxd-alert")
    WebElement errorMessage;


    private ChromeDriver driver;

    public LoginPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        userName.sendKeys(username);
        Password.sendKeys(password);
        loginButton.click();
    }
    public String getErrorMessage(){
        return errorMessage.getText();
    }
    public boolean isErrorMessageDisplayed(WebDriverWait wait, String expectedMessage) {
        try {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//p[contains(@class,'oxd-text') and text()='" + expectedMessage + "']")
            ));
            return errorMessage.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean isCurrentURL(WebDriver driver, String expectedURL) {
        return driver.getCurrentUrl().equals(expectedURL);
    }


}
