package pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static java.lang.Thread.sleep;

public class PIMPage {
    @FindBy(linkText = "PIM")
    public WebElement pimMenu;

    @FindBy(linkText = "Employee List")
    public WebElement employeeListButton;

    @FindBy(xpath = "//div[contains(@class,'oxd-autocomplete-wrapper')]//input")
    WebElement employeeNameField;

    @FindBy(xpath = "//button[contains(@class,'oxd-button--secondary') and text()=' Add ']")
    WebElement addButton;

    @FindBy(name = "firstName")
    WebElement firstNameField;

    @FindBy(name = "lastName")
    WebElement lastNameField;

    @FindBy(xpath = "//div[@class='oxd-form-actions']//button[@type='submit']")
    WebElement saveButton;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement searchButton;

    @FindBy(xpath = "//span[text()='No Records Found']")
    WebElement noRecordsMessage;

    @FindBy(xpath = "//span[contains(@class, 'oxd-switch-input')]")
    WebElement createLoginDetailsSwitch;

    @FindBy(xpath = "//input[@class='oxd-input oxd-input--active' and @autocomplete='off']")
    WebElement usernameField;

    @FindBy(xpath = "//input[@type='password' and contains(@class, 'oxd-input') and contains(@class, 'oxd-input--active')]")
    WebElement passwordField;

    @FindBy(xpath = "//div[@class='oxd-input-group oxd-input-field-bottom-space' and .//label[contains(text(), 'Confirm Password')]]//input[@type='password']")
    WebElement confirmPasswordField;

    private ChromeDriver driver;

    public PIMPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addNewEmployee(String firstName, String lastName){
        addButton.click();
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        saveButton.click();
    }

    public void addEmployeeWithLoginDetails(String firstName, String lastName, String username, String password, String confirmPassword) throws InterruptedException {
        addButton.click();
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        createLoginDetailsSwitch.click();
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);
        saveButton.click();
    }

    public void enterEmployeeName(String employeeName) {

        employeeNameField.sendKeys(employeeName);
    }


}


