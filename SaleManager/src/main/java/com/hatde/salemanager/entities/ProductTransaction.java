package com.hatde.salemanager.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

@Entity
@Table(name = "ProductTransaction")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductTransaction implements java.io.Serializable {

    private int productTransactionId;
    private Date date;
    private Collection<SaleItem> listOfSaleItem = new ArrayList<>();
    private PaymentTransaction payment;    
    private float discount;
    
    private float VAT;

    public ProductTransaction() {
    }

    public ProductTransaction(Date date, float discount, float VAT) {
        this.date = date;
        this.discount = discount;
        this.VAT = VAT;
    }

    public ProductTransaction(Date date, float discount, float VAT, Collection listOfSaleItem) {
        this.date = date;
        this.discount = discount;
        this.VAT = VAT;
        this.listOfSaleItem = listOfSaleItem;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public int getProductTransactionId() {
        return productTransactionId;
    }

    public void setProductTransactionId(int productTransactionId) {
        this.productTransactionId = productTransactionId;
    }

    @Column(name = "Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ProductTransaction_saleitem", joinColumns = {
        @JoinColumn(name = "productTransactionId")}, inverseJoinColumns = {
        @JoinColumn(name = "saleItemId")})
    public Collection<SaleItem> getListOfSaleItem() {
        return listOfSaleItem;
    }

    public void setListOfSaleItem(Collection<SaleItem> listOfSaleItem) {
        this.listOfSaleItem = listOfSaleItem;
    }

    @Column(name = "Discount")
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    @Column(name = "VAT")
    public float getVAT() {
        return VAT;
    }

    public void setVAT(float VAT) {
        this.VAT = VAT;
    }

    @OneToOne
    @JoinColumn(name = "paymentTransactionId")
    public PaymentTransaction getPayment() {
        return payment;
    }

    public void setPayment(PaymentTransaction payment) {
        this.payment = payment;
    }

    public String printInfo() {
        String myInfo = "\nSale record information: \n";
        myInfo += "------------------------------------------------\n";
        myInfo += "saleId: " + productTransactionId + "\n";
        myInfo += date.toString() + "\n";
        myInfo += "Discount: " + (int) (discount * 100) + "%   VAT:" + (int) (VAT * 100) + "%   \n\n";
        for (SaleItem saleItem : listOfSaleItem) {
            myInfo += saleItem.printInfo();
        }

        if (payment != null) {
            myInfo += "Payment: " + payment.getAmount() + "\n\n";
        }
        return myInfo;
    }

    public double gAmount() {
        double amount = 0;
        for (SaleItem saleItem : listOfSaleItem) {
            amount += saleItem.gAmount();
        }
        amount = amount * (1 - discount) * (1 - VAT);
        return amount;
    }

    public double gAmountAfterPayment() {
        double amount = gAmount();
        if (payment != null) {
            amount -= payment.getAmount();
        }
        return amount;
    }
}
