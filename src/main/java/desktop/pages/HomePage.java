package desktop.pages;

import abstractclasses.page.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {
    private static final By SIGN_IN_LINK = By.xpath("//a[@href='/account/login/to/account']");
    private static final By SEARCH_FIELD = By.name("searchTerm");
    private static final By SEARCH_BUTTON = By.className("header-search-btn");

    public HomePage (WebDriver driver){
        super(driver);
    }

    public SearchResultsPage enterSearchTerm(String searchTerm) {
        setValueIntoInputField(SEARCH_FIELD, searchTerm);
        clickElement(SEARCH_BUTTON);
        return new SearchResultsPage(driver);
    }

    public AccountPage clickSignInLink(){
        clickElement(SIGN_IN_LINK);
        return new AccountPage(driver);
    }

}