package com.hatde.salemanager.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentSent")
public class PaymentSent extends PaymentTransaction implements java.io.Serializable {

    public PaymentSent() {
        super();
    }

    public PaymentSent(Date date, double amount, String paymentReason) {
        super(date, amount, paymentReason);
    }
}
