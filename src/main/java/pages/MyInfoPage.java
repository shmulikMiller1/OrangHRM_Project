package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyInfoPage {

    @FindBy(linkText = "My Info")
    public WebElement myInfoMenu;

    @FindBy(xpath = "//button[contains(@class, 'oxd-button--text') and contains(., 'Add')]")
    WebElement addButton;

    @FindBy(xpath = "//i[contains(@class, 'oxd-file-input-icon')]")
    WebElement uploadIcon;

    @FindBy(xpath = "//input[@type='file']")
    WebElement fileInputField;

    @FindBy(xpath = "//button[@type='submit' and contains(@class, 'oxd-button oxd-button--medium oxd-button--secondary')]")
    WebElement saveButton;

    private ChromeDriver driver;

    public MyInfoPage(ChromeDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void addFile(String filePath) {
        addButton.click();
        uploadIcon.click();
        fileInputField.sendKeys(filePath);
        saveButton.click();
    }


}
