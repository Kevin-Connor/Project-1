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
public void testEqualsSameObject() {
    Assert.assertTrue(shirt.equals(shirt)); // Same object should be equal
}

@Test
public void testEqualsNullObject() {
    Assert.assertFalse(shirt.equals(null)); // Should not be equal to null
}

@Test
public void testEqualsDifferentClass() {
    Warehouse warehouse = new Warehouse("Different Warehouse", "Test City", 1000, null);
    Assert.assertFalse(shirt.equals(warehouse)); // Should not be equal to an object of a different class
}

@Test
public void testEqualsSameValues() {
    Shirt sameShirt = new Shirt(1, "Tiger", "L", warehouse);
    Assert.assertTrue(shirt.equals(sameShirt)); // Should be equal when all attributes are the same
}

@Test
public void testEqualsDifferentId() {
    Shirt differentShirt = new Shirt(2, "Tiger", "L", warehouse);
    Assert.assertFalse(shirt.equals(differentShirt)); // Should not be equal with a different id
}

@Test
public void testEqualsDifferentAnimal() {
    Shirt differentShirt = new Shirt(1, "Lion", "L", warehouse);
    Assert.assertFalse(shirt.equals(differentShirt)); // Should not be equal with a different animal
}

@Test
public void testEqualsDifferentSize() {
    Shirt differentShirt = new Shirt(1, "Tiger", "M", warehouse);
    Assert.assertFalse(shirt.equals(differentShirt)); // Should not be equal with a different size
}

@Test
public void testEqualsDifferentWarehouse() {
    Warehouse differentWarehouse = new Warehouse(2, "Secondary Warehouse", "Los Angeles", 300, null);
    Shirt differentShirt = new Shirt(1, "Tiger", "L", differentWarehouse);
    Assert.assertFalse(shirt.equals(differentShirt)); // Should not be equal with a different warehouse
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

    @Test
    public void testDefaultConstructor() {
        Shirt newShirt = new Shirt();
        Assert.assertNotNull(newShirt);
        Assert.assertNull(newShirt.getAnimal());
        Assert.assertNull(newShirt.getSize());
        Assert.assertNull(newShirt.getWarehouse());
    }

    @Test
    public void testEqualsWithNullAndDifferentClass() {
        // Check against null
        Assert.assertNotEquals(shirt, null);

        // Check against different class object
        Assert.assertNotEquals(shirt, new Object());
    }

    @Test
    public void testEqualsWithSelf() {
        // Check against itself
        Assert.assertEquals(shirt, shirt);
    }

    @Test
    public void testHashCodeWithNullValues() {
        // Create a Shirt with null values
        Shirt shirtWithNullValues = new Shirt(0, null, null, null);
        
        // Ensure hashCode works even with null fields
        Assert.assertNotNull(shirtWithNullValues.hashCode());
    }
}
