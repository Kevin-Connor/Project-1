package com.skillstorm.inventorymanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.skillstorm.inventorymanagement.models.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    @Query("SELECT w FROM Warehouse w WHERE w.warehouseName = :name")
    Warehouse findByName(@Param("name") String name);
}
