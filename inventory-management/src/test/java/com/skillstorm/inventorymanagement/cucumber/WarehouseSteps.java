package com.skillstorm.inventorymanagement.cucumber;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.skillstorm.inventorymanagement.selenium.WarehousePage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class WarehouseSteps {

    private WebDriver driver;
    private WarehousePage warehousePage;

    @Before("@warehouses")
    public void before() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.warehousePage = new WarehousePage(driver);
    }

    @After("@warehouses")
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the warehouse management page")
    public void iAmOnTheWarehouseManagementPage() {
        this.warehousePage.get();
    }

    @When("I enter {string} for the warehouse name and {string} for the location")
    public void iEnterWarehouseDetails(String name, String location) {
        this.warehousePage.setWarehouseName(name);
        this.warehousePage.setWarehouseLocation(location);
    }

    @And("I click the save button on the warehouse page")
    public void iClickTheSaveButton() {
        this.warehousePage.clickSave();
    }

    @Then("I should see the warehouse with name {string} and location {string} in the table")
    public void iShouldSeeTheWarehouseInTheTable(String name, String location) {
        assertTrue(this.warehousePage.isWarehouseInTable(name, location));
    }
}
