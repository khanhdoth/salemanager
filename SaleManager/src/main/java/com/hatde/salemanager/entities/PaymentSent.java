package com.hatde.salemanager.entities;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PaymentSent")
@XmlRootElement
public class PaymentSent extends PaymentTransaction implements java.io.Serializable {

    public PaymentSent() {
        super();
    }

    public PaymentSent(Date date, double amount, String paymentReason) {
        super(date, amount, paymentReason);
    }
}
