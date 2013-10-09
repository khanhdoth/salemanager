package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_paymentsent", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "paymentSentId")})*/
    
    @OneToMany(mappedBy="contact")
    @XmlTransient
    public Collection<PaymentSent> getListOfPaymentReceived() {
        return listOfPaymentReceived;
    }

    public void setListOfPaymentReceived(
            Collection<PaymentSent> listOfPaymentReceived) {
        this.listOfPaymentReceived = listOfPaymentReceived;
    }

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_paymentreceived", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "paymentReceivedId")})*/
    
    @OneToMany(mappedBy="contact")
    @XmlTransient
    public Collection<PaymentReceived> getListOfPaymentSent() {
        return listOfPaymentSent;
    }

    public void setListOfPaymentSent(Collection<PaymentReceived> listOfPaymentSent) {
        this.listOfPaymentSent = listOfPaymentSent;
    }

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_sale", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "saleId")})*/
    
    @OneToMany(mappedBy="contact")
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

    /*@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contact_buy", joinColumns = {
        @JoinColumn(name = "contactId")}, inverseJoinColumns = {
        @JoinColumn(name = "buyId")})*/
    
    @OneToMany(mappedBy="contact")
    @XmlTransient
    public Collection<Buy> getListOfSale() {
        return listOfSale;
    }

    public void setListOfSale(Collection<Buy> listOfSale) {
        this.listOfSale = listOfSale;
    }

    public double getProductTransactionsAmount(Collection list) {
        double amount = 0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            ProductTransaction pt = (ProductTransaction) iterator.next();
            amount += pt.getAmount();
        }
        return amount;
    }

    public double getBuyAmount() {
        return getProductTransactionsAmount(listOfBuy);
    }

    public double getSaleAmount() {
        return getProductTransactionsAmount(listOfSale);
    }

    public double getPaymentsAmount(Collection list) {
        double amount = 0;
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            PaymentTransaction pmt = (PaymentTransaction) iterator.next();
            amount += pmt.getAmount();
        }
        return amount;
    }

    public double getPaymentReceivedAmount() {
        return getPaymentsAmount(listOfPaymentReceived);
    }

    public double getPaymentSentAmount() {
        return getPaymentsAmount(listOfPaymentSent);
    }

    public double getDebit() {
        return (getSaleAmount() + getPaymentSentAmount());        
    }

    public double getCredit() {
        return (getBuyAmount() + getPaymentReceivedAmount());
    }

    public double getBalance() {
        return (getCredit() - getDebit());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contactId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact)) {
            return false;
        }

        Contact other = (Contact) object;
        return this.contactId == other.contactId
                && this.name.equals(other.name)
                && this.address.equals(other.address)
                && this.district.equals(other.district)
                && this.city.equals(other.city)
                && this.country.equals(other.country)
                && this.postcode.equals(other.postcode)
                && this.moreInfo.equals(other.moreInfo);
    }

    @Override
    public String toString() {
        String myInfo = "";
        myInfo += "============= com.hatde.salemanager.entities.Contact[ id=" + contactId + " ] =============\n";
        myInfo += "name: " + name + "  address: " + address + "  district: " + district + "  city: " + city + "  country: " + country + "  postcode: " + postcode + "  moreInfo: " + moreInfo + "\n";
        
        myInfo += "\n+ listOfBuy has " + listOfBuy.size() +" items:" +"\n";        
        for(Sale sale : listOfBuy){
            myInfo += sale.toString();
        }
        
        myInfo += "\n+ listOfSale has " + listOfSale.size() +" items:" +"\n";        
        for(Buy buy : listOfSale){
            myInfo += buy.toString();
        }

        myInfo += "\n+ listOfPaymentReceived has " + listOfPaymentReceived.size() +" items:" +"\n";        
        for(PaymentSent ps : listOfPaymentReceived){
            myInfo += ps.toString();
        }
        
        myInfo += "\n+ listOfPaymentSent has " + listOfPaymentSent.size() +" items:" +"\n";        
        for(PaymentReceived pr : listOfPaymentSent){
            myInfo += pr.toString();
        }
        
        myInfo += "==========================================================================================\n";
        
        return myInfo;
    }
}