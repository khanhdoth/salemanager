package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "product")
@XmlRootElement
public class Product implements java.io.Serializable {

    private int productId;
    private String name;
    private String manufacturer;
    private String unit;
    /*private Collection<ProductTag> listOfProductTag = new ArrayList<>();
    private Collection<Unit> listOfUnit = new ArrayList<>();*/

    public Product() {
    }

    public Product(String name, String manufacturer, String unit) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.unit = unit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Column(name = "Unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /*@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_productTag", joinColumns = {
        @JoinColumn(name = "productId")}, inverseJoinColumns = {
        @JoinColumn(name = "productTagId")})
    public Collection<ProductTag> getListOfProductTag() {
        return listOfProductTag;
    }

    public void setListOfProductTag(Collection<ProductTag> listOfProductTag) {
        this.listOfProductTag = listOfProductTag;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_unit", joinColumns = {
        @JoinColumn(name = "productId")}, inverseJoinColumns = {
        @JoinColumn(name = "unitId")})
    public Collection<Unit> getListOfUnit() {
        return listOfUnit;
    }

    public void setListOfUnit(Collection<Unit> listOfUnit) {
        this.listOfUnit = listOfUnit;
    }*/
}
