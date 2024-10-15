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

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.services.ShirtService;

public class ShirtControllerTest {

    @InjectMocks
    private ShirtController shirtController;

    @Mock
    private ShirtService shirtService;

    private AutoCloseable closeable;
    private Shirt shirt;
    private List<Shirt> shirts;

    @BeforeMethod
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        
        // Initialize Shirt and List of Shirts
        shirt = new Shirt(1, "Tiger", "L", null);
        shirts = new ArrayList<>();
        shirts.add(shirt);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindAllShirts() {
        // Mocking the service method
        when(shirtService.findAllShirts()).thenReturn(shirts);

        // Call the controller method
        ResponseEntity<List<Shirt>> response = shirtController.findAllShirts();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), shirts);
    }

    @Test
    public void testCreateShirt() {
        // Mocking the service method
        when(shirtService.saveShirt(shirt)).thenReturn(shirt);

        // Call the controller method
        ResponseEntity<Shirt> response = shirtController.createShirt(shirt);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), shirt);
    }

    @Test
    public void testAddShirt() {
        // No return value needed, just verify service call
        doNothing().when(shirtService).addShirt(shirt);

        // Call the controller method
        ResponseEntity<String> response = shirtController.addShirt(shirt);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), "Shirt added successfully");
    }

    @Test
    public void testUpdateShirt() {
        // Mocking the service method
        when(shirtService.saveShirt(shirt)).thenReturn(shirt);

        // Call the controller method
        ResponseEntity<Shirt> response = shirtController.updateShirt(shirt);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), shirt);
    }

    @Test
    public void testDeleteShirt() {
        // No return value needed, just verify service call
        doNothing().when(shirtService).deleteShirt(shirt);

        // Call the controller method
        ResponseEntity<Void> response = shirtController.deleteShirt(shirt);

        // Assertions
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}
