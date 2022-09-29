package abstractclasses.page;
import driver.SingletonDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static com.codeborne.selenide.Selenide.$;

public abstract class AbstractPage {
    protected WebDriver driver = SingletonDriver.getInstance();
    private String pageUrlPattern;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageUrl() {
        return SingletonDriver.getInstance().getCurrentUrl();
    }

    public void setPageUrl(String pageUrl) {
        SingletonDriver.getInstance().get(pageUrl);
    }

    public String setPageUrlPattern(String pageUrlPattern) {
        return this.pageUrlPattern = pageUrlPattern;
    }

    public String getPageUrlPattern() {
        return pageUrlPattern;
    }

    protected void setValueIntoInputField(By element, String value){
        $(element).setValue(value);
    }

    protected void clickElement(WebElement element){
        $(element).click();
    }
    protected void clickElement(By element){
        $(element).click();
    }

    protected Boolean isElementDisplayed(By element){
        return $(element).isDisplayed();
    }

    protected void selectDropDownValue (By element, String value){
        $(element).selectOption(value);
    }
    protected String getTextFromElement(By element){
        return $(element).text();
    }
}