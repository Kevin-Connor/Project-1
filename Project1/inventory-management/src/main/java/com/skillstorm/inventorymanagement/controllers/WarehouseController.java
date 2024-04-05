package com.skillstorm.inventorymanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventorymanagement.models.Warehouse;
import com.skillstorm.inventorymanagement.services.WarehouseService;

@RestController
@RequestMapping("/warehouses")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class WarehouseController {
    
    @Autowired
    WarehouseService warehouseService;


    /**
     *  Get all warehouses
     */
    @GetMapping
    public ResponseEntity<List<Warehouse>> findAllWarehouses() {
        List<Warehouse> warehouse = warehouseService.findAllWarehouses();

        return new ResponseEntity<List<Warehouse>>(warehouse, HttpStatus.OK);
    }

    /**
     *  Create warehouse
     */
    @PostMapping("/warehouse")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.saveWarehouse(warehouse);
        return new ResponseEntity<Warehouse>(newWarehouse, HttpStatus.OK);        
    }

    /**
     *  Update warehouse
     */
    @PutMapping("/warehouse")
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse newWarehouse = warehouseService.saveWarehouse(warehouse);
        return new ResponseEntity<Warehouse>(newWarehouse, HttpStatus.OK);  
    }

    /**
     *  Delete warehouse
     */
    @DeleteMapping("/warehouse/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable int id) {
        try {
            // Call the service method to delete the warehouse
            warehouseService.deleteWarehouse(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Handle any exceptions and return appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
