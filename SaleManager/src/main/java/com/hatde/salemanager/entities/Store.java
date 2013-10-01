package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Store")
public class Store implements java.io.Serializable {

    private int storeId;
    private String name;
    private String address;

    public Store() {
    }

    public Store(String name, String address) {
        super();
        this.name = name;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
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
        String myInfo = "Store information: \n";
        myInfo += "------------------------------------------------\n";
        myInfo += "storeId:" + storeId + "\n";
        myInfo += name + "\n";
        myInfo += address + "\n\n";

        return myInfo;
    }
}
