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
    private double priceIn;
    private double priceOut;
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

    @Column(name = "PriceIn")
    public double getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(double priceIn) {
        this.priceIn = priceIn;
    }

    @Column(name = "PriceOut")
    public double getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(double priceOut) {
        this.priceOut = priceOut;
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
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }

        Product other = (Product) object;
        return this.productId == other.productId
                && this.name.equals(other.name)
                && this.manufacturer.equals(other.manufacturer)
                && this.unit.equals(other.unit);
    }

    @Override
    public String toString() {
        return "com.hatde.salemanager.entities.Product[ id=" + productId + " ]";
    }
}
