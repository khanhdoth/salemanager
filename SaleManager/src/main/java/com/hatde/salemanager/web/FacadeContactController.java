/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;

/**
 *
 * @author Khanh
 */
public abstract class FacadeContactController<T> extends FacadeController<T> {
    protected Contact contact;
    
    protected abstract ContactFacadeREST getContactBean();
    
    public FacadeContactController() {
    }
    
    @Override
    public void doCreate() {
        getBean().create(newT, contact);
        tempViewContact();
    }

    @Override
    public void doDelete() {
        super.doDelete();
        tempViewContact();
    }

    public void tempViewContact() {
        Contact contact2 = getContactBean().find(contact.getContactId());
        System.out.println(contact2.toString());
    }
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    
}
