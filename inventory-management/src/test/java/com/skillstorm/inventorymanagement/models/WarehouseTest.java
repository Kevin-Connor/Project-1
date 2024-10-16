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

    // Test for default constructor
    @Test
    public void testDefaultConstructor() {
        Warehouse newWarehouse = new Warehouse();
        Assert.assertNotNull(newWarehouse);
        Assert.assertEquals(newWarehouse.getId(), 0);  // Default int value
        Assert.assertNull(newWarehouse.getWarehouseName());
        Assert.assertNull(newWarehouse.getCity());
        Assert.assertEquals(newWarehouse.getCapacity(), 0);  // Default int value
        Assert.assertNull(newWarehouse.getShirts());
    }

    // Test for constructor without shirts
    @Test
    public void testConstructorWithoutShirts() {
        Warehouse newWarehouse = new Warehouse("Test Warehouse", "Test City", 1000);
        Assert.assertEquals(newWarehouse.getWarehouseName(), "Test Warehouse");
        Assert.assertEquals(newWarehouse.getCity(), "Test City");
        Assert.assertEquals(newWarehouse.getCapacity(), 1000);
        Assert.assertNull(newWarehouse.getShirts());  // Default null value
    }
    @Test
    public void testConstructorWithNameCityCapacityAndShirts() {
        Set<Shirt> shirts = new HashSet<>();
        shirts.add(new Shirt(1, "Lion", "M", warehouse));

        Warehouse newWarehouse = new Warehouse("Test Warehouse", "Test City", 1000, shirts);

        Assert.assertEquals(newWarehouse.getWarehouseName(), "Test Warehouse");
        Assert.assertEquals(newWarehouse.getCity(), "Test City");
        Assert.assertEquals(newWarehouse.getCapacity(), 1000);
        Assert.assertEquals(newWarehouse.getShirts(), shirts);
    }


    @Test
    public void testEquals() {
        // Same object
        Assert.assertTrue(warehouse.equals(warehouse));

        // Object with same field values
        Warehouse sameWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, shirts);
        Assert.assertTrue(warehouse.equals(sameWarehouse));

        // Different id
        Warehouse differentIdWarehouse = new Warehouse(2, "Main Warehouse", "New York", 500, shirts);
        Assert.assertFalse(warehouse.equals(differentIdWarehouse));

        // Different warehouseName
        Warehouse differentNameWarehouse = new Warehouse(1, "Secondary Warehouse", "New York", 500, shirts);
        Assert.assertFalse(warehouse.equals(differentNameWarehouse));

        // Different city
        Warehouse differentCityWarehouse = new Warehouse(1, "Main Warehouse", "Los Angeles", 500, shirts);
        Assert.assertFalse(warehouse.equals(differentCityWarehouse));

        // Different capacity
        Warehouse differentCapacityWarehouse = new Warehouse(1, "Main Warehouse", "New York", 1000, shirts);
        Assert.assertFalse(warehouse.equals(differentCapacityWarehouse));

        // Different shirts
        Set<Shirt> newShirts = new HashSet<>();
        newShirts.add(new Shirt(1, "Lion", "M", warehouse));
        Warehouse differentShirtsWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, newShirts);
        Assert.assertFalse(warehouse.equals(differentShirtsWarehouse));

        // Null case
        Assert.assertFalse(warehouse.equals(null));

        // Different class
        Assert.assertFalse(warehouse.equals(new Object()));
    }

    @Test
    public void testEqualsWithNullFields() {
        // Case where warehouseName, city, and shirts are null
        Warehouse warehouseWithNullFields = new Warehouse(1, null, null, 500, null);
        Warehouse warehouseWithNullFieldsSame = new Warehouse(1, null, null, 500, null);

        // Ensure that equals works when some fields are null
        Assert.assertTrue(warehouseWithNullFields.equals(warehouseWithNullFieldsSame));

        // Ensure equality fails when only one of the null fields is different
        Warehouse warehouseWithDifferentNullField = new Warehouse(1, "Main Warehouse", null, 500, null);
        Assert.assertFalse(warehouseWithNullFields.equals(warehouseWithDifferentNullField));
    }

    @Test
    public void testHashCode() {
        // Same object should have the same hashCode
        Warehouse sameWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, shirts);
        Assert.assertEquals(warehouse.hashCode(), sameWarehouse.hashCode());

        // Different id should result in different hashCode
        Warehouse differentIdWarehouse = new Warehouse(2, "Main Warehouse", "New York", 500, shirts);
        Assert.assertNotEquals(warehouse.hashCode(), differentIdWarehouse.hashCode());

        // Different warehouseName should result in different hashCode
        Warehouse differentNameWarehouse = new Warehouse(1, "Secondary Warehouse", "New York", 500, shirts);
        Assert.assertNotEquals(warehouse.hashCode(), differentNameWarehouse.hashCode());

        // Different city should result in different hashCode
        Warehouse differentCityWarehouse = new Warehouse(1, "Main Warehouse", "Los Angeles", 500, shirts);
        Assert.assertNotEquals(warehouse.hashCode(), differentCityWarehouse.hashCode());

        // Different capacity should result in different hashCode
        Warehouse differentCapacityWarehouse = new Warehouse(1, "Main Warehouse", "New York", 1000, shirts);
        Assert.assertNotEquals(warehouse.hashCode(), differentCapacityWarehouse.hashCode());

        // Different shirts should result in different hashCode
        Set<Shirt> newShirts = new HashSet<>();
        newShirts.add(new Shirt(1, "Lion", "M", warehouse));
        Warehouse differentShirtsWarehouse = new Warehouse(1, "Main Warehouse", "New York", 500, newShirts);
        Assert.assertNotEquals(warehouse.hashCode(), differentShirtsWarehouse.hashCode());
    }

    @Test
    public void testHashCodeWithNullValues() {
        // Create a Warehouse with null values
        Warehouse warehouseWithNullValues = new Warehouse(0, null, null, 0, null);

        // Ensure hashCode works even with null fields
        Assert.assertNotNull(warehouseWithNullValues.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Warehouse [id=1, warehouseName=Main Warehouse, city=New York, capacity=500]";
        Assert.assertEquals(warehouse.toString(), expected);
    }
}
