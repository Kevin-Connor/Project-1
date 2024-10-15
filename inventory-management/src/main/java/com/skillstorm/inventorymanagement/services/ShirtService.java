package com.skillstorm.inventorymanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.models.Warehouse;
import com.skillstorm.inventorymanagement.repositories.ShirtRepository;
import com.skillstorm.inventorymanagement.repositories.WarehouseRepository;

@Service
public class ShirtService {

    @Autowired
    ShirtRepository shirtRepository;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    // finding all shirts
    public List<Shirt> findAllShirts() {
        return shirtRepository.findAll();    
    }

    // saving shirt
    // Saving shirt
    public Shirt saveShirt(Shirt shirt) {
        Warehouse existingWarehouse = warehouseRepository.findByName(shirt.getWarehouse().getWarehouseName());
        if (existingWarehouse != null) {
            shirt.setWarehouse(existingWarehouse); // Associate the shirt with the existing warehouse
        }
        return shirtRepository.save(shirt);
    }

    // Adding shirt (ensures no duplicate warehouse creation)
    public void addShirt(Shirt shirt) {
        String warehouseName = shirt.getWarehouse().getWarehouseName();
        Warehouse existingWarehouse = warehouseRepository.findByName(warehouseName);
        if (existingWarehouse != null) {
            shirt.setWarehouse(existingWarehouse); // Associate the shirt with the existing warehouse
        } else {
            // If the warehouse does not exist, create a new one
            Warehouse newWarehouse = new Warehouse();
            newWarehouse.setWarehouseName(warehouseName);
            shirt.setWarehouse(newWarehouse); // Associate the shirt with the new warehouse
        }
        shirtRepository.save(shirt);
    }

    // deleting shirt
    public void deleteShirt(Shirt shirt) {
        shirtRepository.delete(shirt);
    }

    // updates shirts to a warehouse being deleted
    public void updateShirtsWarehouse(Warehouse currentWarehouse, Warehouse newWarehouse) {
        List<Shirt> shirtsToUpdate = shirtRepository.findByWarehouse(currentWarehouse);
        
        for (Shirt shirt : shirtsToUpdate) {
            shirt.setWarehouse(newWarehouse);
        }
        
        shirtRepository.saveAll(shirtsToUpdate);
    }

}
