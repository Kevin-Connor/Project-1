package com.skillstorm.inventorymanagement.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventorymanagement.models.Shirt;
import com.skillstorm.inventorymanagement.models.Warehouse;

@Repository
public interface ShirtRepository extends JpaRepository<Shirt, Integer> {

    //finding shirts by warehouse
    public List<Shirt> findByWarehouse(Warehouse warehouse);

}
