package desktop.pages;

import abstractclasses.page.AbstractPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.switchTo;
import static constants.Constants.SIGN_IN_PAGE_URL;

public class AccountPage extends AbstractPage {
    private static final By EMAIL_FIELD = By.xpath("//input[@name='email']");
    private static final By PASS_FIELD =  By.xpath("//input[@type='password']");
    private static final By SUBMIT_BUTTON = By.xpath("//input[@id='signInSubmit']");
    private static final By LOGIN_TITLE =  By.xpath("//h1[contains(@class,'a-spacing-top-small')]");

    public AccountPage (WebDriver driver){
            super(driver);
    }

    public void checkAccountPageURL() {
        Assertions.assertEquals(SIGN_IN_PAGE_URL, getPageUrl(), "Wrong page url" );
    }

    public void enterEmail(String email) {
        switchTo().frame(0);
        setValueIntoInputField(EMAIL_FIELD, email);
    }

    public void enterPassword(String password) {
        setValueIntoInputField(PASS_FIELD, password);
    }

    public void clickSignInButton() {
        clickElement(SUBMIT_BUTTON);
    }

    public void isLoginTitleDisplayed() {
        isElementDisplayed(LOGIN_TITLE);
    }

    public void fillInSignInFields(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
    }
}