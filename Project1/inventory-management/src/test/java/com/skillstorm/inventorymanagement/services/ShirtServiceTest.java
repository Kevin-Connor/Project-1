package com.skillstorm.inventorymanagement.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.models.Warehouse;
import com.skillstorm.inventorymanagement.repositories.ShirtRepository;
import com.skillstorm.inventorymanagement.repositories.WarehouseRepository;

public class ShirtServiceTest {

    @InjectMocks
    private ShirtService shirtService;
    private AutoCloseable closeable;

    @Mock
    private ShirtRepository shirtRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private WarehouseService warehouseService;

    private Shirt shirt;
    private Warehouse warehouse;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        warehouse = new Warehouse(1, "Main Warehouse", "New York", 500, new HashSet<>());
        shirt = new Shirt(1, "Tiger", "L", warehouse);
    }

    @AfterMethod
    public void teardown() throws Exception{
        closeable.close();
    }

    @Test
    public void testFindAllShirts() {
        List<Shirt> shirts = new ArrayList<>();
        shirts.add(shirt);

        when(shirtRepository.findAll()).thenReturn(shirts);

        List<Shirt> result = shirtService.findAllShirts();

        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void testSaveShirtWithExistingWarehouse() {
        when(warehouseRepository.findByName(warehouse.getWarehouseName())).thenReturn(warehouse);
        when(shirtRepository.save(shirt)).thenReturn(shirt);

        Shirt result = shirtService.saveShirt(shirt);

        Assert.assertEquals(result.getWarehouse(), warehouse);
    }

    @Test
    public void testSaveShirtWithNewWarehouse() {
        when(warehouseRepository.findByName(warehouse.getWarehouseName())).thenReturn(null);
        when(shirtRepository.save(shirt)).thenReturn(shirt);

        Shirt result = shirtService.saveShirt(shirt);

        Assert.assertEquals(result.getWarehouse(), warehouse);

    }

    @Test
    public void testAddShirtWithExistingWarehouse() {
        when(warehouseRepository.findByName(warehouse.getWarehouseName())).thenReturn(warehouse);

        shirtService.addShirt(shirt);

        Assert.assertEquals(shirt.getWarehouse(), warehouse);
    }

    @Test
    public void testAddShirtWithNewWarehouse() {
        when(warehouseRepository.findByName(warehouse.getWarehouseName())).thenReturn(null);

        shirtService.addShirt(shirt);
    }

    @Test
    public void testDeleteShirt() {
        doNothing().when(shirtRepository).delete(shirt);

        shirtService.deleteShirt(shirt);
    }

    @Test
    public void testUpdateShirtsWarehouse() {
        Warehouse newWarehouse = new Warehouse(2, "Secondary Warehouse", "Los Angeles", 300, new HashSet<>());
        List<Shirt> shirtsToUpdate = new ArrayList<>();
        shirtsToUpdate.add(shirt);

        when(shirtRepository.findByWarehouse(warehouse)).thenReturn(shirtsToUpdate);

        shirtService.updateShirtsWarehouse(warehouse, newWarehouse);

        
        Assert.assertEquals(shirt.getWarehouse(), newWarehouse);
    }
}
