package com.hatde.salemanager.entities;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Buy")
@XmlRootElement
public class Buy extends ProductTransaction implements java.io.Serializable {

    public Buy() {
        super();
    }

    public Buy(Date date, float discount, float VAT) {
        super(date, discount, VAT);
    }

    public Buy(Date date, float discount, float VAT, Collection listOfSaleItem) {
        super(date, discount, VAT, listOfSaleItem);
    }
}
