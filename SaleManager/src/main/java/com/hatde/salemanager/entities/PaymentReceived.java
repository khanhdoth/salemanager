package com.hatde.salemanager.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PaymentReceived")
public class PaymentReceived extends PaymentTransaction implements java.io.Serializable {

    public PaymentReceived() {
        super();
    }

    public PaymentReceived(Date date, double amount, String paymentReason) {
        super(date, amount, paymentReason);
    }
}
