package com.skillstorm.inventorymanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.services.ShirtService;

@RestController
@RequestMapping("/shirts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ShirtController {

    @Autowired
    ShirtService shirtService;

    /**
     * Get all shirts
     */
    @GetMapping
    public ResponseEntity<List<Shirt>> findAllShirts() {
        List<Shirt> shirts = shirtService.findAllShirts();
        return new ResponseEntity<>(shirts, HttpStatus.OK);
    }

    /**
     * Create shirt
     */
    @PostMapping("/shirt")
    public ResponseEntity<Shirt> createShirt(@RequestBody Shirt shirt) {
        Shirt newShirt = shirtService.saveShirt(shirt);
        return new ResponseEntity<>(newShirt, HttpStatus.OK);
    }

    /**
     * Add shirt
     */
    @PostMapping("/add")
    public ResponseEntity<String> addShirt(@RequestBody Shirt shirt) {
        shirtService.addShirt(shirt);
        return ResponseEntity.ok("Shirt added successfully");
    }

    /**
     * Update shirt
     */
    @PutMapping("/shirt")
    public ResponseEntity<Shirt> updateShirt(@RequestBody Shirt shirt) {
        Shirt updatedShirt = shirtService.saveShirt(shirt);
        return new ResponseEntity<>(updatedShirt, HttpStatus.OK);
    }

    /**
     * Delete shirt
     */
    @DeleteMapping("/shirt")
    public ResponseEntity<Void> deleteShirt(@RequestBody Shirt shirt) {
        shirtService.deleteShirt(shirt);
        return ResponseEntity.noContent().build();
    }
}
