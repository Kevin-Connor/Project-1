package com.skillstorm.inventorymanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.models.Warehouse;
import com.skillstorm.inventorymanagement.repositories.ShirtRepository;
import com.skillstorm.inventorymanagement.repositories.WarehouseRepository;


@Service
public class WarehouseService {
    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    ShirtRepository shirtRepository;

    // finding all warehouses
    public List<Warehouse> findAllWarehouses() {
        return warehouseRepository.findAll();   
    }

    // saving warehouse
    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    
    // deleting warehouse
    public void deleteWarehouse(int warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }

    // deleting warehouse and shirts in it
    public void deleteWarehouseAndAssociatedShirts(int warehouseId) {
        // Retrieve the warehouse entity by its ID
        Warehouse warehouse = warehouseRepository.findById(warehouseId)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found with id: " + warehouseId));
        
        // Retrieve the associated shirts of the warehouse
        List<Shirt> shirts = shirtRepository.findByWarehouse(warehouse);
        
        // Delete the associated shirts
        shirtRepository.deleteAll(shirts);
        
        // Delete the warehouse
        warehouseRepository.delete(warehouse);
    }
    
}
