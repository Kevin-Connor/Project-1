package com.skillstorm.inventorymanagement.controllers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.skillstorm.inventorymanagement.models.Warehouse;
import com.skillstorm.inventorymanagement.services.WarehouseService;

public class WarehouseControllerTest {

    @InjectMocks
    private WarehouseController warehouseController;

    @Mock
    private WarehouseService warehouseService;

    private AutoCloseable closeable;
    private Warehouse warehouse;
    private List<Warehouse> warehouses;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        // Initialize Warehouse and List of Warehouses
        warehouse = new Warehouse(1, "Main Warehouse", "New York", 500, null);
        warehouses = new ArrayList<>();
        warehouses.add(warehouse);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindAllWarehouses() {
        // Mocking the service method
        when(warehouseService.findAllWarehouses()).thenReturn(warehouses);

        // Call the controller method
        ResponseEntity<List<Warehouse>> response = warehouseController.findAllWarehouses();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), warehouses);
    }

    @Test
    public void testCreateWarehouse() {
        // Mocking the service method
        when(warehouseService.saveWarehouse(warehouse)).thenReturn(warehouse);

        // Call the controller method
        ResponseEntity<Warehouse> response = warehouseController.createWarehouse(warehouse);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), warehouse);
    }

    @Test
    public void testUpdateWarehouse() {
        // Mocking the service method
        when(warehouseService.saveWarehouse(warehouse)).thenReturn(warehouse);

        // Call the controller method
        ResponseEntity<Warehouse> response = warehouseController.updateWarehouse(warehouse);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), warehouse);
    }

    @Test
    public void testDeleteWarehouseSuccess() {
        // No return value needed, just verify service call
        doNothing().when(warehouseService).deleteWarehouse(1);

        // Call the controller method
        ResponseEntity<Void> response = warehouseController.deleteWarehouse(1);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

    }

    @Test
    public void testDeleteWarehouseFailure() {
        // Simulating a failure scenario
        doThrow(new IllegalArgumentException("Warehouse not found")).when(warehouseService).deleteWarehouse(1);

        // Call the controller method and expect an internal server error
        ResponseEntity<Void> response = warehouseController.deleteWarehouse(1);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
