/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Khanh
 */
public abstract class FacadeContactController<T> extends FacadeController<T> {

    protected abstract ContactFacadeREST getContactBean();
    protected Contact contact;

    public FacadeContactController() {
    }

    public void create() {
        System.out.println("----create----" + this.toString());
        try {
            getContactBean().edit(contact);
            initListandNewT();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Create_Success"), ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Create_Failed_Message"), ""));
        }
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
