package com.skillstorm.inventorymanagement.models;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class WarehouseTest {

    private Warehouse warehouse;
    private Set<Shirt> shirts;

    @BeforeMethod
    public void setUp() {
        shirts = new HashSet<>();
        shirts.add(new Shirt("Tiger", "L", null));
        shirts.add(new Shirt("Lion", "M", null));
        warehouse = new Warehouse(1, "Main Warehouse", "New York", 500, shirts);
    }

    @Test
    public void testId() {
        // Getter
        Assert.assertEquals(warehouse.getId(), 1);
        // Setter
        warehouse.setId(2);
        Assert.assertEquals(warehouse.getId(), 2);
    }

    @Test
    public void testWarehouseName() {
        // Getter
        Assert.assertEquals(warehouse.getWarehouseName(), "Main Warehouse");
        // Setter
        warehouse.setWarehouseName("Updated Warehouse");
        Assert.assertEquals(warehouse.getWarehouseName(), "Updated Warehouse");
    }

    @Test
    public void testCity() {
        // Getter
        Assert.assertEquals(warehouse.getCity(), "New York");
        // Setter
        warehouse.setCity("Los Angeles");
        Assert.assertEquals(warehouse.getCity(), "Los Angeles");
    }

    @Test
    public void testCapacity() {
        // Getter
        Assert.assertEquals(warehouse.getCapacity(), 500);
        // Setter
        warehouse.setCapacity(1000);
        Assert.assertEquals(warehouse.getCapacity(), 1000);
    }

    @Test
    public void testShirts() {
        // Getter
        Assert.assertEquals(warehouse.getShirts().size(), 2);
        // Setter
        Set<Shirt> newShirts = new HashSet<>();
        newShirts.add(new Shirt("Elephant", "S", null));
        warehouse.setShirts(newShirts);
        Assert.assertEquals(warehouse.getShirts().size(), 1);
        Assert.assertEquals(warehouse.getShirts().iterator().next().getAnimal(), "Elephant");
    }

    @Test
    public void testEquals() {
        Warehouse sameWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, shirts);
        Assert.assertEquals(warehouse, sameWarehouse);

        Warehouse differentWarehouse = new Warehouse(2, "Other Warehouse", "Boston", 300, new HashSet<>());
        Assert.assertNotEquals(warehouse, differentWarehouse);
    }

    @Test
    public void testHashCode() {
        Warehouse sameWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, shirts);
        Assert.assertEquals(warehouse.hashCode(), sameWarehouse.hashCode());

        Warehouse differentWarehouse = new Warehouse(2, "Other Warehouse", "Boston", 300, new HashSet<>());
        Assert.assertNotEquals(warehouse.hashCode(), differentWarehouse.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Warehouse [id=1, warehouseName=Main Warehouse, city=New York, capacity=500]";
        Assert.assertEquals(warehouse.toString(), expected);
    }
}
