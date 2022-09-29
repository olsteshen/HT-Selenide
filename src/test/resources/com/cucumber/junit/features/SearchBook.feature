@Regression
Feature: As a user I want to get ability to search for a book

  Scenario: Search results contain a book
    Given I open bookdepository site
    When I search for "Thinking"
    Then I am redirected to a "Search page"
    And the search results are displayed
