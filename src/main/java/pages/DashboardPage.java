package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage {

    @FindBy(className = "oxd-userdropdown-name")
    WebElement profileMenu;

    @FindBy(xpath = "//a[contains(@class, 'oxd-userdropdown-link') and text()='Logout']")
    WebElement logoutButton;

    private ChromeDriver driver;

    public DashboardPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        profileMenu.click();
        logoutButton.click();
    }
}

