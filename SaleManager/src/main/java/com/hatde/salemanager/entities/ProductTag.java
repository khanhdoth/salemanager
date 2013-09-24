package com.hatde.salemanager.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "ProductTag")
public class ProductTag implements java.io.Serializable {

    private int productTagId;
    private String name;
    private Collection<Unit> listOfUnit = new ArrayList<>();

    public ProductTag() {
    }

    @Id
    @GeneratedValue
    public int getProductTagId() {
        return productTagId;
    }

    public void setProductTagId(int productTagId) {
        this.productTagId = productTagId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "producttag_unit", joinColumns = {
        @JoinColumn(name = "productTagId")}, inverseJoinColumns = {
        @JoinColumn(name = "unitId")})
    public Collection<Unit> getListOfUnit() {
        return listOfUnit;
    }

    public void setListOfUnit(Collection<Unit> listOfUnit) {
        this.listOfUnit = listOfUnit;
    }
}