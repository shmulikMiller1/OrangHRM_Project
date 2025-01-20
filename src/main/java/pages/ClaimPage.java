package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ClaimPage {
    private WebDriver driver;

    public ClaimPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Claim")
    public WebElement claimMenu;

    @FindBy(xpath = "//button[contains(@class, 'oxd-button') and contains(@class, 'oxd-button--secondary') and text()=' Submit Claim ']")
    public WebElement submitClaimButton;

    @FindBy(xpath = "//div[contains(@class, 'oxd-select-text') and contains(@class, 'oxd-select-text--active')]")
    WebElement eventDropdown;

    @FindBy(xpath = "(//div[contains(@class, 'oxd-select-wrapper')])[2]")
    WebElement currencyDropdown;

    @FindBy(xpath = "//button[contains(@class, 'oxd-button') and contains(@class, 'oxd-button--secondary') and text()=' Create ']")
    public WebElement createButton;

    public void selectEventAndCurrency(int indexEvent, int indexCurrency) throws Exception {
        eventDropdown.click();
        Thread.sleep(2000);
        List<WebElement> eventOptions = driver.findElements(By.xpath("//div[contains(@class, 'oxd-select-option')]"));
        if (eventOptions.isEmpty()) {
            throw new Exception("No options found in the Event dropdown");
        }
        System.out.println("Number of event options found: " + eventOptions.size());
        eventOptions.get(indexEvent).click();
        currencyDropdown.click();
        Thread.sleep(2000);
        List<WebElement> currencyOptions = driver.findElements(By.xpath("//div[contains(@class, 'oxd-select-option')]"));
        if (currencyOptions.isEmpty()) {
            throw new Exception("No options found in the Currency dropdown");
        }
        System.out.println("Number of currency options found: " + currencyOptions.size());
        currencyOptions.get(indexCurrency).click();
    }

}

