package com.skillstorm.inventorymanagement.cucumber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.skillstorm.inventorymanagement.selenium.ShirtsPage;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class ShirtsSteps {

    private WebDriver driver;
    private ShirtsPage shirtsPage;

    @Before("@shirts")
    public void setUp() {
        // Initialize the ChromeDriver
        driver = new ChromeDriver();
        // Initialize the page objects
        shirtsPage = new ShirtsPage(driver);
    }

    @After("@shirts")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the shirt management page")
    public void iAmOnTheShirtManagementPage() {
        this.shirtsPage.get();
    }

    @When("I enter {string} for the animal graphic, {string} for the size, and {string} for the warehouse")
    public void iEnterShirtDetails(String animal, String size, String warehouse) {
        this.shirtsPage.setAnimalGraphic(animal);
        this.shirtsPage.setShirtSize(size);
        this.shirtsPage.setWarehouseName(warehouse);
    }

    @And("I click the save button")
    public void iClickTheSaveButton() {
        this.shirtsPage.clickSave();
    }

    @Then("I should see the shirt with animal graphic {string}, size {string}, and warehouse {string} in the table")
    public void iShouldSeeTheShirtInTheTable(String animal, String size, String warehouse) {
        Assert.assertTrue(this.shirtsPage.isShirtInTable(animal, size, warehouse));
    }
}
