Feature: Warehouse Management

  Scenario: Add a new warehouse to the system
    Given I am on the warehouse management page
    When I enter "Main Warehouse" for the warehouse name and "New York" for the location
    And I click the save button on the warehouse page
    Then I should see the warehouse with name "Main Warehouse" and location "New York" in the table
