package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "SaleItem")
public class SaleItem implements java.io.Serializable {

    private int saleItemId;
    private Product product;
    private float quantity;
    private float price;
    private float discount;

    public SaleItem() {
    }

    public SaleItem(Product product, float quantity, float discount) {
        this.product = product;
        this.quantity = quantity;
        this.discount = discount;
    }

    public SaleItem(Product product, float quantity, float price, float discount) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getSaleItemId() {
        return saleItemId;
    }

    public void setSaleItemId(int saleItemId) {
        this.saleItemId = saleItemId;
    }

    @OneToOne
    @JoinColumn(name = "productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "Quatity")
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Column(name = "Discount")
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String printInfo() {
        String myInfo = "";
        myInfo += "     " + product.getName() + "   " + quantity + " " + product.getUnit() + "\n";
        return myInfo;
    }

    public double gAmount() {
        return quantity * price * (1 - discount);
    }

    @Column(name = "Price")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) saleItemId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaleItem)) {
            return false;
        }

        SaleItem other = (SaleItem) object;
        return this.saleItemId == other.saleItemId
                && this.quantity == (other.quantity)
                && this.price == (other.price)
                && this.discount == (other.discount);

    }

    @Override
    public String toString() {
        String myInfo = "";
        myInfo += "        = com.hatde.salemanager.entities.SaleItem[ id=" + saleItemId + " ] =\n";
        myInfo += "          product: " + product.getName() + "  quantity: " + quantity + "  price: " + price + "  discount: " + discount + "\n";

        return myInfo;
    }
}
