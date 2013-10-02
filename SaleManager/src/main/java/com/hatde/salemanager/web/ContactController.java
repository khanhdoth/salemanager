//KHANH DO
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Do
 */
//@Named(value = "contactController")
//@SessionScoped
@Named(value = "contactController")
@SessionScoped
public class ContactController implements Serializable {

    @EJB
    private ContactFacadeREST bean;

    private List<Contact> list;
    private List<Contact> filterList;
    private Contact selectedContact;
    private Contact newContact;

    @PostConstruct
    public void init() {
        initDataList();
        initNewContact();
    }

    public void initDataList() {
        list = new ArrayList();
        list = bean.findAll();
    }

    public void initNewContact() {
        newContact = new Contact();
    }

    public ContactController() {
    }

    public List<Contact> getList() {
        System.out.println("----ContactController.getList----");
        return list;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    public List<Contact> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Contact> filterList) {
        this.filterList = filterList;
    }

    public void onEdit(RowEditEvent event) {
        Contact c = (Contact) event.getObject();
        try {            
            bean.edit(c);
            FacesMessage msg = new FacesMessage("Customer edited", "name = " + c.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Cannot edit this customer", "name = " + c.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onCancel(RowEditEvent event) {
        Contact c = (Contact) event.getObject();
        FacesMessage msg = new FacesMessage("Customer Cancelled", "name = " + c.getName());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void delete() {
        try {
            bean.remove(selectedContact);
            FacesMessage msg = new FacesMessage("Deleted successfully", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            initDataList();

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Cannot delete this contact!", "name = " + selectedContact.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void createContact() {
        System.out.println("============== createContact =============");
        try {
            bean.create(newContact);
            FacesMessage msg = new FacesMessage("Contact is created successfully", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            init();

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Cannot create this contact", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void setSelectedContact(Contact selectedContact) {
        this.selectedContact = selectedContact;
    }

    public Contact getNewContact() {
        return newContact;
    }

    public void setNewContact(Contact newContact) {
        this.newContact = newContact;
    }

}
