package com.skillstorm.inventorymanagement.selenium;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WarehousePage {

    private WebDriver driver;
    private static final String url = "http://localhost:8080/warehouses";

    @FindBy(id = "new-warehouse-name")
    private WebElement warehouseNameField;

    @FindBy(id = "new-warehouse-location")
    private WebElement warehouseLocationField;

    @FindBy(id = "save-button")
    private WebElement saveButton;

    @FindBy(id = "warehouse-table-body")
    private WebElement warehouseTableBody;

    public WarehousePage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void get() {
        this.driver.get(url);
    }

    public void setWarehouseName(String name) {
        warehouseNameField.sendKeys(name);
    }

    public void setWarehouseLocation(String location) {
        warehouseLocationField.sendKeys(location);
    }

    public void clickSave() {
        saveButton.click();
    }

    public boolean isWarehouseInTable(String name, String location) {
        return warehouseTableBody.getText().contains(name) && 
               warehouseTableBody.getText().contains(location);
    }
}