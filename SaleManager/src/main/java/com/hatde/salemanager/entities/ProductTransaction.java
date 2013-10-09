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
    private Contact contact;

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

    @JoinColumn(name = "contactId")
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public double getSubTotal() {
        double subTotal = 0;
        for (SaleItem saleItem : listOfSaleItem) {
            subTotal += saleItem.getAmount();
        }
        return subTotal;
    }

    public double getAmountDiscount() {
        return getSubTotal() * discount/100;
    }

    public double getAmountAfterDiscount() {
        return getSubTotal() * (1-discount/100);
    }
    
    public double getAmountVAT() {
        return getAmountAfterDiscount() * VAT/100;
    }

    public double getAmount() {
        double amount = 0;
        for (SaleItem saleItem : listOfSaleItem) {
            amount += saleItem.getAmount();
        }
        amount = amount * (1 - discount/100) * (1 + VAT/100);
        return amount;
    }

    public double getAmountAfterPayment() {
        double amount = getAmount();
        if (payment != null) {
            amount -= payment.getAmount();
        }
        return amount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productTransactionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductTransaction)) {
            return false;
        }

        ProductTransaction other = (ProductTransaction) object;
        return this.productTransactionId == other.productTransactionId
                && this.date.equals(other.date)
                && this.discount == other.discount;
    }

    @Override
    public String toString() {
        String myInfo = "";
        myInfo += "  == com.hatde.salemanager.entities.ProductTransaction[ id=" + productTransactionId + " ] ==\n";
        myInfo += "     date: " + date.toString() + "  discount: " + discount + "  payment: " + payment.getAmount() + "\n";
        myInfo += "     + listOfSaleItem has " + listOfSaleItem.size() + " items:" + "\n";

        for (SaleItem saleItem : listOfSaleItem) {
            myInfo += saleItem.toString();
        }

        return myInfo;
    }
}
