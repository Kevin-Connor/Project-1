@shirts
Feature: Shirt Management

  Scenario: Add a new shirt to the inventory
    Given I am on the shirt management page
    When I enter "Elephant" for the animal graphic, "L" for the size, and "New York City" for the warehouse
    And I click the save button
    Then I should see the shirt with animal graphic "Elephant", size "L", and warehouse "New York City" in the table
