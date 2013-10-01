package com.hatde.salemanager.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "company")
public class Company implements java.io.Serializable {

    private int companyId;
    private String name;
    private String address;
    private String city;
    private Tax defaultTax;
    private Language language;

    public Company() {
    }

    public Company(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @OneToOne
    @JoinColumn(name = "taxId")
    public Tax getDefaultTax() {
        return defaultTax;
    }

    public void setDefaultTax(Tax defaultTax) {
        this.defaultTax = defaultTax;
    }

    @OneToOne
    @JoinColumn(name = "languageId")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String printInfo() {
        String myInfo = "\n\nCompany informations: \n";
        myInfo += "------------------------------------------------\n";
        myInfo += "companyId: " + companyId + "\n";
        myInfo += name + "\n";
        myInfo += address + "\n";
        myInfo += city + "\n\n";
        myInfo += "default tax: " + (int) defaultTax.getRate() * 100 + "% \n\n";

        /*		myInfo += "We have " + listOfStore.size() + " stores: \n";
		
         for (Iterator iterator = listOfStore.iterator(); iterator.hasNext();){
         Store store = (Store) iterator.next(); 
         myInfo +=  store.getInfo(); 
         }			
		
         myInfo += "We have " + listOfWarehouse.size() + " warehouses: \n";
		
         for (Iterator iterator = listOfWarehouse.iterator(); iterator.hasNext();){
         Warehouse warehouse = (Warehouse) iterator.next(); 
         myInfo +=  warehouse.getInfo(); 
         }
		
         myInfo += "We have " + listOfCustomer.size() + " customers: \n";
		
         for (Iterator iterator = listOfCustomer.iterator(); iterator.hasNext();){
         Contact user = (Contact) iterator.next(); 
         myInfo +=  user.getInfo(); 
         }	
		
         myInfo += "We have " + listOfSupplier.size() + " suppliers: \n";
		
         for (Iterator iterator = listOfSupplier.iterator(); iterator.hasNext();){
         Contact user = (Contact) iterator.next(); 
         myInfo +=  user.getInfo(); 
         }	
		
         myInfo += "We have " + listOfParner.size() + " partners: \n";
		
         for (Iterator iterator = listOfParner.iterator(); iterator.hasNext();){
         Contact user = (Contact) iterator.next(); 
         myInfo +=  user.getInfo(); 
         }	
         */

        return myInfo;
    }
}