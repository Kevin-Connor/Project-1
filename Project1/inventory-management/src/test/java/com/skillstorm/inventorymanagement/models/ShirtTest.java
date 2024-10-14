package com.skillstorm.inventorymanagement.models;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ShirtTest {

    private Shirt shirt;
    private Warehouse warehouse;

    @BeforeMethod
    public void setUp() {
        warehouse = new Warehouse(1, "Main Warehouse", "New York", 500, null);
        shirt = new Shirt(1, "Tiger", "L", warehouse);
    }

    @Test
    public void testId() {
        // Getter
        Assert.assertEquals(shirt.getId(), 1);
        // Setter
        shirt.setId(2);
        Assert.assertEquals(shirt.getId(), 2);
    }

    @Test
    public void testAnimal() {
        // Getter
        Assert.assertEquals(shirt.getAnimal(), "Tiger");
        // Setter
        shirt.setAnimal("Lion");
        Assert.assertEquals(shirt.getAnimal(), "Lion");
    }

    @Test
    public void testSize() {
        // Getter
        Assert.assertEquals(shirt.getSize(), "L");
        // Setter
        shirt.setSize("M");
        Assert.assertEquals(shirt.getSize(), "M");
    }

    @Test
    public void testWarehouse() {
        // Getter
        Assert.assertEquals(shirt.getWarehouse(), warehouse);
        // Setter
        Warehouse newWarehouse = new Warehouse(2, "Secondary Warehouse", "Los Angeles", 300, null);
        shirt.setWarehouse(newWarehouse);
        Assert.assertEquals(shirt.getWarehouse(), newWarehouse);
    }

    @Test
    public void testEquals() {
        Shirt sameShirt = new Shirt(1, "Tiger", "L", warehouse);
        Assert.assertEquals(shirt, sameShirt);

        Shirt differentShirt = new Shirt(2, "Lion", "M", warehouse);
        Assert.assertNotEquals(shirt, differentShirt);
    }

    @Test
    public void testHashCode() {
        Shirt sameShirt = new Shirt(1, "Tiger", "L", warehouse);
        Assert.assertEquals(shirt.hashCode(), sameShirt.hashCode());

        Shirt differentShirt = new Shirt(2, "Lion", "M", warehouse);
        Assert.assertNotEquals(shirt.hashCode(), differentShirt.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Shirt [id=1, animal=Tiger, size=L, warehouse=" + warehouse + "]";
        Assert.assertEquals(shirt.toString(), expected);
    }
}
