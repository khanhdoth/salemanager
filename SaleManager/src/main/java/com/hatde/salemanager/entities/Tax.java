package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "tax")
public class Tax implements java.io.Serializable {

    private int taxId;
    private float rate;

    public Tax() {
    }

    public Tax(float rate) {
        this.rate = rate;
    }

    @Id
    @GeneratedValue
    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    @Column(name = "rate")
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}