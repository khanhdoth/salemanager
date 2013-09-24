package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "Warehouse")
public class Warehouse implements java.io.Serializable {

    private int warehouseId;
    private String name;
    private String address;

    public Warehouse() {
    }

    public Warehouse(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    @Id
    @GeneratedValue
    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String printInfo() {
        String myInfo = "Warehouse information: \n";
        myInfo += "------------------------------------------------\n";
        myInfo += "warehouseId: " + warehouseId + "\n";
        myInfo += name + "\n";
        myInfo += address + "\n\n";

        return myInfo;
    }
}
