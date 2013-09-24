package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "contact")
@XmlRootElement
public class Contact implements java.io.Serializable {

    private int contactId;
    private String name;
    private String address;
    private String district;
    private String city;
    private String country;
    private String postcode;
    private String moreInfo;
    private Collection<PaymentSent> listOfPaymentReceived = new ArrayList<>();
    private Collection<PaymentReceived> listOfPaymentSent = new ArrayList<>();
    private Collection<Buy> listOfSale = new ArrayList<>();
    private Collection<Sale> listOfBuy = new ArrayList<>();

    public Contact() {
    }

    public Contact(String name, String address, String district, String city, String country,
            String postcode, String moreInfo) {
        this.name = name;
        this.address = address;
        this.district = district;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
        this.moreInfo = moreInfo;
    }

    @Id
    @GeneratedValue
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "City")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "MoreInfo")
    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_paymentsent", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "paymentSentId")})
    @XmlTransient
    public Collection<PaymentSent> getListOfPaymentReceived() {
        return listOfPaymentReceived;
    }

    public void setListOfPaymentReceived(
            Collection<PaymentSent> listOfPaymentReceived) {
        this.listOfPaymentReceived = listOfPaymentReceived;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_paymentreceived", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "paymentReceivedId")})
    @XmlTransient
    public Collection<PaymentReceived> getListOfPaymentSent() {
        return listOfPaymentSent;
    }

    public void setListOfPaymentSent(Collection<PaymentReceived> listOfPaymentSent) {
        this.listOfPaymentSent = listOfPaymentSent;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_sale", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "saleId")})
    @XmlTransient
    public Collection<Sale> getListOfBuy() {
        return listOfBuy;
    }

    public void setListOfBuy(Collection<Sale> listOfBuy) {
        this.listOfBuy = listOfBuy;
    }

    @Column(name = "District")
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "Postcode")
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_buy", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "buyId")})
    @XmlTransient
    public Collection<Buy> getListOfSale() {
        return listOfSale;
    }

    public void setListOfSale(Collection<Buy> listOfSale) {
        this.listOfSale = listOfSale;
    }

    public String printInfo() {
        String myInfo = "Contact info------------------------";
        myInfo += name + " : " + address + " " + district + " " + postcode + " " + city + " " + country + "\n\n";

//        try {
//            myInfo += "This contact has " + listOfSale.size() + " sale records: \n";
//            for (Iterator iterator = listOfSale.iterator(); iterator.hasNext();) {
//                Buy buy = (Buy) iterator.next();
//                myInfo += buy.printInfo();
//            }
//
//            myInfo += "This contact has " + listOfBuy.size() + " buy records: \n";
//            for (Sale sale : listOfBuy) {
//                myInfo += sale.printInfo();
//            }
//
//            myInfo += "This contact received " + listOfPaymentReceived.size() + " payments: \n";
//            for (PaymentSent paymentSent : listOfPaymentReceived) {
//                myInfo += paymentSent.printInfo();
//            }
//
//            myInfo += "This contact sent " + listOfPaymentSent.size() + " payments: \n";
//            for (PaymentReceived paymentReceived : listOfPaymentSent) {
//                myInfo += paymentReceived.printInfo();
//            }
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            throw e;
//        }
        return myInfo;
    }

    public double gProductTransactionsAmount(Collection list) {
        double amount = 0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ProductTransaction pt = (ProductTransaction) iterator.next();
            amount += pt.gAmount();
        }
        return amount;
    }

    public double gBuyAmount() {
        return gProductTransactionsAmount(listOfBuy);
    }

    public double gSaleAmount() {
        return gProductTransactionsAmount(listOfSale);
    }

    public double gPaymentsAmount(Collection list) {
        double amount = 0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            PaymentTransaction pmt = (PaymentTransaction) iterator.next();
            amount += pmt.getAmount();
        }
        return amount;
    }

    public double gPaymentReceivedAmount() {
        return gPaymentsAmount(listOfPaymentReceived);
    }

    public double gPaymentSentAmount() {
        return gPaymentsAmount(listOfPaymentSent);
    }

    public double gDebit() {
        return (gBuyAmount() + gPaymentReceivedAmount());
    }

    public double gCredit() {
        return (gSaleAmount() + gPaymentSentAmount());
    }

    public double gBalance() {
        return (gCredit() - gDebit());
    }
}
