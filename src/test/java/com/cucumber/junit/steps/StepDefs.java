package com.cucumber.junit.steps;

import com.codeborne.selenide.Selenide;
import desktop.pages.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

import static constants.Constants.INITIAL_HOME_PAGE;

public class StepDefs {
    WebDriver driver;
    HomePage homePageObject = new HomePage(driver);
    SearchResultsPage searchResultsPageObject;
    AccountPage accountPageObject;
    BasketPage basketPageObject;
    CheckoutPage checkoutPageObject;

    @Given("^I open bookdepository site$")
    public void openSite() {
        homePageObject.setPageUrl(INITIAL_HOME_PAGE);
    }

    @Given("I open the {string}")
    public void openPage(String pageName) {
        if(pageName.equals("Initial home page")){
            homePageObject.setPageUrl(INITIAL_HOME_PAGE);
        }
    }

    @When("I search for {string}")
    public void searchForTerm(String searchTerm) {
         searchResultsPageObject = homePageObject.enterSearchTerm(searchTerm);
    }

    @Then("^the search results are displayed$")
    public void pageWithSearchResultsIsPresent() {
        searchResultsPageObject.isSearchResultsPresent();
    }

    @When("^click on the Sign (?:in|out) link on navigation bar$")
    public void clickSigninButton() {
        accountPageObject = homePageObject.clickSignInLink();
    }

    @When("^I fill in the login and password$")
    public void fillInCredentials() {
        accountPageObject.fillInSignInFields("olsteshen@example.com", "Temp12345");
    }

    @Then("^I am logged in$")
    public void advancedSearchPageIsDisplayed() {
        accountPageObject.checkAccountPageURL();
    }

    @Given("I am an anonymous customer with clear cookies")
    public void setAnonymousCustomer() {
        Selenide.clearBrowserCookies();
    }

    @And("Search results contain the following products")
    public void checkSearchResultsContainsProducts(List<String> expectedBookNames) {
        Assertions.assertThat(searchResultsPageObject.getBookTitleInResults())
                .extracting(String::toString)
                .as("Some of the books are not shown")
                .containsAll(expectedBookNames);
    }

    @And("I apply the following search filters")
    public void applySearchFilters(DataTable filtersData) {
    searchResultsPageObject.applyFilters(filtersData);
    }

    @Then("Search results contain only the following products")
    public void checkSearchResultsContainOnlyProducts(List<String> expectedOnlyBookNames) {
        Assertions.assertThat(searchResultsPageObject.getBookTitleInResults())
                .extracting(String::toString)
                .as("Search results are not as expected")
                .containsExactlyElementsOf(expectedOnlyBookNames);
    }

    @Then("I am redirected to a {string}")
    public void checkPageURL(String pageName) {
        switch (pageName) {
            case "Basket page" -> basketPageObject.checkBasketPageURL();
            case "Checkout page" -> checkoutPageObject.checkCheckoutPageURL();
            case "Search page" -> searchResultsPageObject.checkSearchPageURL();
        }
    }

    @When("I click 'Add to basket' button for product with name {string}")
    public void clickATBButton(String productName) {
        searchResultsPageObject.addProductToBasket(productName);
    }

    @When("I click 'Checkout' button on 'Basket' page")
    public void clickCheckoutOnBasket() {
        checkoutPageObject = basketPageObject.buttonCheckoutOnBasket();
    }

    @When("I click 'Buy now' button")
    public void clickBuyButton() {
        checkoutPageObject.buyNowButtonclick();
    }

    @Then("the following validation error messages are displayed on 'Delivery Address' form:")
    public void checkValidationErrorMessage(List<Map<String, String>> expectedErrors){
        checkoutPageObject.checkErrorMessage(expectedErrors);
    }

    @And("Checkout order summary is as following:")
    public void checkOrderSummary(@Transpose Map<String, String> orderDetails) {
        checkoutPageObject.checkOrderSummary(orderDetails);
    }

    @And("I checkout as a new customer with email {string}")
    public void fillUserDetails(String email) {
        checkoutPageObject.fillInUserEmail(email);
    }

    @When("I fill delivery address information manually:")
    public void fillDeliveryAddressFields(@Transpose Map<String, String> deliveryAddress) {
        checkoutPageObject.fillAddressFields(deliveryAddress);
    }

    @Then("the following validation error messages are displayed on 'Payment' form:")
    public void checkValidationErrorMessage(String expectedError) {
        checkoutPageObject.checkErrorMessageOnPayment(expectedError);
    }

    @When("I enter my card details")
    public void fillCardDetails(Map<String, String> cardDetails) {
        checkoutPageObject.enterCardDetails(cardDetails);
    }

    @And("Basket order summary is as following:")
    public void checkBasketSummary(DataTable basketSummary) {
        basketPageObject.checkBasketOrderSummary(basketSummary);
    }

    @And("I select 'Basket Checkout' in basket pop-up")
    public void clickButtonContinue() {
        basketPageObject = searchResultsPageObject.clickButtonContinue();
    }

    @Then("there is no validation error messages displayed on 'Delivery Address' form")
    public void checkNoErrorInAddressForm() {
        checkoutPageObject.checkNoErrorMessagesOnDelivery();
    }
}
