package com.skillstorm.inventorymanagement.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

public class WarehouseServiceTest {

    @InjectMocks
    private WarehouseService warehouseService;

    private AutoCloseable closeable;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private ShirtRepository shirtRepository;

    private Warehouse warehouse;
    private Shirt shirt;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        warehouse = new Warehouse(1, "Main Warehouse", "New York", 500, new HashSet<>());
        shirt = new Shirt(1, "Tiger", "L", warehouse);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        warehouses.add(warehouse);

        when(warehouseRepository.findAll()).thenReturn(warehouses);

        List<Warehouse> result = warehouseService.findAllWarehouses();

        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void testSaveWarehouse() {
        when(warehouseRepository.save(warehouse)).thenReturn(warehouse);

        Warehouse result = warehouseService.saveWarehouse(warehouse);

        Assert.assertEquals(result, warehouse);

    }

    @Test
    public void testDeleteWarehouse() {
        int warehouseId = warehouse.getId();
        doNothing().when(warehouseRepository).deleteById(warehouseId);

        warehouseService.deleteWarehouse(warehouseId);

    }

    @Test
    public void testDeleteWarehouseAndAssociatedShirts() {
        int warehouseId = warehouse.getId();
        List<Shirt> shirts = new ArrayList<>();
        shirts.add(shirt);
        
        when(warehouseRepository.findById(warehouseId)).thenReturn(Optional.of(warehouse));
        when(shirtRepository.findByWarehouse(warehouse)).thenReturn(shirts);

        warehouseService.deleteWarehouseAndAssociatedShirts(warehouseId);

    }
}
