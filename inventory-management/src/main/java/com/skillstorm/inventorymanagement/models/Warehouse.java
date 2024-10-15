package com.skillstorm.inventorymanagement.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity                           
@Table(name = "WAREHOUSES") 
public class Warehouse {

    @Id                                                  
    @Column                                                 
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    private int id;

    @Column(name = "warehouse_name")  
    private String warehouseName;

    @Column(name = "city") 
    private String city;

    @Column(name = "capacity") 
    private int capacity;

    @OneToMany(targetEntity = Shirt.class, mappedBy = "warehouse")  
    private Set<Shirt> shirts;

    public Warehouse() {
    }

    public Warehouse(String warehouseName, String city, int capacity) {
        this.warehouseName = warehouseName;
        this.city = city;
        this.capacity = capacity;
    }

    public Warehouse(String warehouseName, String city, int capacity, Set<Shirt> shirts) {
        this.warehouseName = warehouseName;
        this.city = city;
        this.capacity = capacity;
        this.shirts = shirts;
    }

    public Warehouse(int id, String warehouseName, String city, int capacity, Set<Shirt> shirts) {
        this.id = id;
        this.warehouseName = warehouseName;
        this.city = city;
        this.capacity = capacity;
        this.shirts = shirts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Shirt> getShirts() {
        return shirts;
    }

    public void setShirts(Set<Shirt> shirts) {
        this.shirts = shirts;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((warehouseName == null) ? 0 : warehouseName.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + capacity;
        result = prime * result + ((shirts == null) ? 0 : shirts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Warehouse other = (Warehouse) obj;
        if (id != other.id)
            return false;
        if (warehouseName == null) {
            if (other.warehouseName != null)
                return false;
        } else if (!warehouseName.equals(other.warehouseName))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (capacity != other.capacity)
            return false;
        if (shirts == null) {
            if (other.shirts != null)
                return false;
        } else if (!shirts.equals(other.shirts))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Warehouse [id=" + id + ", warehouseName=" + warehouseName + ", city=" + city + ", capacity=" + capacity + "]";
    }

}
