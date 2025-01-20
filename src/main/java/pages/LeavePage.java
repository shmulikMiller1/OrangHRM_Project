package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class LeavePage {

    @FindBy(xpath = "//span[contains(@class, 'oxd-main-menu-item') and text()='Leave']")
    WebElement leaveMenu;

    @FindBy(linkText = "Apply")
    WebElement applyButton;

    @FindBy(css = "div.oxd-select-text-input")
    WebElement leaveTypeDropdown;

    @FindBy(css = "div[role='option']")
    List<WebElement> dropdownOptions;

    @FindBy(className = "oxd-input")
    List<WebElement> dateFields;

    @FindBy(className = "oxd-button")
    WebElement submitApplyButton;

    private WebDriver driver;

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectLeaveType(int index) {
        leaveTypeDropdown.click();
        dropdownOptions.get(index).click();
    }

    public void enterLeaveDates(String fromDate, String toDate) {
        dateFields.get(0).sendKeys(fromDate);
        dateFields.get(1).sendKeys(toDate);
    }

}