package desktop.pages;

import abstractclasses.page.AbstractPage;
import driver.SingletonDriver;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$$;
import static constants.Constants.CHECKOUT_PAGE_URL;

public class CheckoutPage extends AbstractPage {
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit']");
    private static final By EMAIL_FIELD = By.xpath("//input[@name='emailAddress']");
    private static final By FULL_NAME = By.xpath("//input[@name='delivery-fullName']");
    private static final By ADDRESS_LINE_1 = By.xpath("//input[@name='delivery-addressLine1']");
    private static final By ADDRESS_LINE_2 = By.xpath("//input[@name='delivery-addressLine2']");
    private static final By DELIVERY_CITY = By.xpath("//input[@name='delivery-city']");
    private static final By DELIVERY_STATE = By.xpath("//input[@name='delivery-county']");
    private static final By POSTCODE = By.name("delivery-postCode");
    private static final By COUNTRY = By.xpath("//select[@id='delivery-CountryDropdown']");
    private static final By CC_NUMBER = By.xpath("//input[@id='credit-card-number']");
    private static final By EXPIRY_DATE= By.xpath("//input[@id='expiration']");
    private static final By CVV = By.xpath("//input[@id='cvv']");
    private static final By SUB_TOTAL = By.xpath("//div[@class='wrapper']/div[2]/dl/dd");
    private static final By DELIVERY_COST = By.xpath("//div[@class='wrapper']/div[3]/dl/dd");
    private static final By VAT = By.xpath("//dd[@class='text-right total-tax']");
    private static final By TOTAL = By.xpath("//dd[@class='text-right total-price']");
    private static final By EMAIL_ERROR =  By.xpath("//div[@id='emailAddress']//div[@class='error-block']");
    private static final By FULL_NAME_ERROR =  By.xpath("//div[@id='delivery-fullName-errors']");
    private static final By ADDR1_ERROR =  By.xpath("//div[@id='delivery-addressLine1-errors']");
    private static final By CITY_ERROR =  By.xpath("//div[@id='delivery-city-errors']");
    private static final By POSTCODE_ERROR = By.xpath("//div[@id='delivery-postCode-errors']");
    private static final By PAYMENT_ERROR = By.xpath("//div[@class='buynow-error-msg']");
    private static final By DELIVERY_ERROR= By.xpath("//div[@id='deliveryAddress']//div[@class='error-block']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void checkCheckoutPageURL(){
        Assertions.assertEquals(CHECKOUT_PAGE_URL, getPageUrl(), "Wrong Checkout page url" );
    }

    public void buyNowButtonclick(){
        clickElement(SUBMIT_BUTTON);
    }

    public void fillInUserEmail(String email){
        setValueIntoInputField(EMAIL_FIELD, email);
    }

    public void fillAddressFields(Map<String, String> deliveryAddress){
        setValueIntoInputField(FULL_NAME, deliveryAddress.get("Full name"));
        WebElement countryDD = SingletonDriver.getInstance().findElement(By.xpath("//span[@name='deliveryCountry']"));
 //       WebElement countryList = SingletonDriver.getInstance().findElement((By.xpath("//ul[contains(@class,'dropdown-list')]/li")));
        Actions builder = new Actions(SingletonDriver.getInstance());
                builder.moveToElement(countryDD).click().perform();
   // builder.moveToElement(countryList).click().perform();
        selectDropDownValue(COUNTRY,deliveryAddress.get("Delivery country"));
        setValueIntoInputField(ADDRESS_LINE_1,deliveryAddress.get("Address line 1"));
        setValueIntoInputField(ADDRESS_LINE_2, deliveryAddress.get("Address line 2"));
        setValueIntoInputField(DELIVERY_CITY, deliveryAddress.get("Town/City"));
        setValueIntoInputField(DELIVERY_STATE, deliveryAddress.get("County/State"));
        setValueIntoInputField(POSTCODE, deliveryAddress.get("Postcode"));
    }

    public void enterCardDetails(Map<String, String> cardDetails){
        SingletonDriver.getInstance().switchTo().frame(1);
        setValueIntoInputField(CC_NUMBER,cardDetails.get("cardNumber"));
        SingletonDriver.getInstance().switchTo().defaultContent();
        SingletonDriver.getInstance().switchTo().frame(2);
        setValueIntoInputField(EXPIRY_DATE,cardDetails.get("Expiry date"));
        SingletonDriver.getInstance().switchTo().defaultContent();
        SingletonDriver.getInstance().switchTo().frame(3);
        setValueIntoInputField(CVV, cardDetails.get("Cvv"));
    }

    public void checkOrderSummary(Map<String, String> orderDetails){
        Assertions.assertAll("Error on checkout",
                () -> Assertions.assertEquals(orderDetails.get("Sub-total"), getTextFromElement(SUB_TOTAL)),
                () -> Assertions.assertEquals(orderDetails.get("Delivery"), getTextFromElement(DELIVERY_COST)),
                () -> Assertions.assertEquals(orderDetails.get("VAT"), getTextFromElement(VAT)),
                () -> Assertions.assertEquals(orderDetails.get("Total"), getTextFromElement(TOTAL)));
    }

    public void checkErrorMessage(List<Map<String, String>> expectedErrors){
        Assertions.assertAll("Error on checkout",
                () -> Assertions.assertEquals(expectedErrors.get(0).get("validaton error message"), getTextFromElement(EMAIL_ERROR)),
                () -> Assertions.assertEquals(expectedErrors.get(1).get("validaton error message"), getTextFromElement(FULL_NAME_ERROR)),
                () -> Assertions.assertEquals(expectedErrors.get(2).get("validaton error message"), getTextFromElement(ADDR1_ERROR)),
                () -> Assertions.assertEquals(expectedErrors.get(3).get("validaton error message"), getTextFromElement(CITY_ERROR)),
                () -> Assertions.assertEquals(expectedErrors.get(4).get("validaton error message"), getTextFromElement(POSTCODE_ERROR))
        );
    }

    public void checkErrorMessageOnPayment(String expectedError){
        String errors = getTextFromElement(PAYMENT_ERROR).replace("\n", ", ").replace("\r", ", ");
        Assertions.assertEquals(expectedError, errors);
    }

    public void checkNoErrorMessagesOnDelivery(){
        Assertions.assertTrue($$(DELIVERY_ERROR).isEmpty());
    }
}
