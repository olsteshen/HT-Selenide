@Regression
Feature: As a user I want to get ability to login to account

  Scenario: Successful login to account
    Given I open bookdepository site
    And click on the Sign in link on navigation bar
    When I fill in the login and password
    Then I am logged in