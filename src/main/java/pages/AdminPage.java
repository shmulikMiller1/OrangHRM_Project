package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AdminPage {

    @FindBy(linkText = "Admin")
    public WebElement adminMenu;

    @FindBy(css = "div.oxd-input-group input")
    public WebElement usernameField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchButton;

    @FindBy(xpath = "//i[contains(@class, 'oxd-icon-button__icon')]")
    public WebElement sortArrowButton;

    @FindBy(xpath = "//span[text()='Ascending']")
    public WebElement ascendingButton;

    @FindBy(xpath = "//span[text()='Descending']")
    public WebElement descendingButton;


    private ChromeDriver driver;

    public AdminPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

}
