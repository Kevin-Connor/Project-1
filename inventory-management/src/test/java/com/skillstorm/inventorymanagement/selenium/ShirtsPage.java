package com.skillstorm.inventorymanagement.selenium;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShirtsPage {

    private WebDriver driver;
    private static final String url = "http://localhost:8080/shirts";

    @FindBy(id = "new-shirt-animal")
    private WebElement animalField;

    @FindBy(id = "new-shirt-size")
    private WebElement sizeField;

    @FindBy(id = "new-warehouse-name")
    private WebElement warehouseField;

    @FindBy(id = "save-button")
    private WebElement saveButton;

    @FindBy(id = "shirt-table-body")
    private WebElement shirtTableBody;

    public ShirtsPage(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.driver.get(url);
    }

    public void setAnimalGraphic(String animal) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        animalField.sendKeys(animal);
    }

    public void setShirtSize(String size) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sizeField.sendKeys(size);
    }

    public void setWarehouseName(String warehouse) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        warehouseField.sendKeys(warehouse);
    }

    public void clickSave() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        saveButton.click();
    }

    public boolean isShirtInTable(String animal, String size, String warehouse) {
        return shirtTableBody.getText().contains(animal) && 
               shirtTableBody.getText().contains(size) && 
               shirtTableBody.getText().contains(warehouse);
    }
}
