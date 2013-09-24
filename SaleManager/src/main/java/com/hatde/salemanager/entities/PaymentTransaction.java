package com.hatde.salemanager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "PaymentTransaction")
@Inheritance(strategy = InheritanceType.JOINED)
public class PaymentTransaction implements java.io.Serializable {

    private int paymentTransactionId;
    private Date date;
    private double amount;
    private String paymentReason;

    public PaymentTransaction() {
    }

    public PaymentTransaction(Date date, double amount, String paymentReason) {
        this.date = date;
        this.amount = amount;
        this.paymentReason = paymentReason;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public int getPaymentTransactionId() {
        return paymentTransactionId;
    }

    public void setPaymentTransactionId(int paymentTransactionId) {
        this.paymentTransactionId = paymentTransactionId;
    }

    @Column(name = "Date")
    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "Amount")
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Column(name = "PaymentReason")
    public String getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(String paymentReason) {
        this.paymentReason = paymentReason;
    }

    public String printInfo() {
        String myInfo = "\nPaymentSent information: \n";
        myInfo += "------------------------------------------------\n";
        myInfo += "paymentTransactionId: " + paymentTransactionId + "\n";
        myInfo += date.toString() + "    Amount:" + amount + "\n";
        myInfo += paymentReason + "\n\n";

        return myInfo;
    }
}
