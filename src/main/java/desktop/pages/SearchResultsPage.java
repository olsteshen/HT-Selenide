package desktop.pages;

import abstractclasses.page.AbstractPage;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$;
import static constants.Constants.SEARCH_RESULT_PAGE_URL;
import static java.lang.String.format;

public class SearchResultsPage extends AbstractPage {
    private static final String BOOK_ITEM_TITLE = "//div[@class='book-item'][.//h3/a[contains(text(),'%s')]]//a[contains(@class,'add-to-basket')]";
    private static final By CLICK_BUTTON_CONTINUE = By.xpath("//a[contains(@class,'continue-to-basket')]");
    private static final By REFINE_RESULTS_BUTTON = By.xpath("//button[@class='btn btn-primary'][contains(text(),'Refine results')]");
    private static final By SEARCH_RESULTS = By.xpath("//*[@class='book-item']");
    private static final By PRICE_FILTER = By.xpath("//select[@name='price']");
    private static final By AVAILABILITY_FILTER = By.xpath("//select[@name='availability']");
    private static final By LANGUAGE_FILTER = By.xpath("//select[@name='searchLang']");
    private static final By FORMAT_FILTER = By.xpath("//select[@name='format']");
    private static final By BOOK_TITLE=By.xpath("//h3[@class='title']");

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void isSearchResultsPresent() {
        Assertions.assertFalse($$(SEARCH_RESULTS).isEmpty());
    }

    public void checkSearchPageURL() {
        Assertions.assertTrue(getPageUrl().contains(SEARCH_RESULT_PAGE_URL), "Wrong Search page url");
    }

    public void applyFilters(DataTable filtersData) {
        Map<String, String> data = filtersData.asMap(String.class, String.class);
        selectDropDownValue(PRICE_FILTER,data.get("Price range"));
        selectDropDownValue(AVAILABILITY_FILTER,data.get("Availability"));
        selectDropDownValue(LANGUAGE_FILTER, data.get("Language"));
        selectDropDownValue(FORMAT_FILTER,data.get("Format"));
        clickElement(REFINE_RESULTS_BUTTON);
    }

    public void addProductToBasket(String name) {
        clickElement((By.xpath(format(BOOK_ITEM_TITLE, name))));
    }

    public BasketPage clickButtonContinue() {
        clickElement(CLICK_BUTTON_CONTINUE);
        return new BasketPage(driver);
    }

    public List<String> getBookTitleInResults(){
        return $$(BOOK_TITLE).texts();
    }
}
