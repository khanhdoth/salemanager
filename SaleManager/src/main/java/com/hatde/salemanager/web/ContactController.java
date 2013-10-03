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
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

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
    private ResourceBundle bundle;
    
    @PostConstruct
    public void init() {
        initListandNewContact();
        bundle = ResourceBundle.getBundle("lang", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public void initListandNewContact() {
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

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            Contact c = (Contact) ((DataTable) event.getSource()).getRowData();
            try {
                bean.edit(c);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer edited", "name = " + c.getName()));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot edit this customer", "name = " + c.getName()));
            }
        }
    }

    public void delete() {
        System.out.println("============== delete =============");
        
        try {
            bean.remove(selectedContact);
            //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted successfully", "");            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Delete_Success"), ""));
            initDataList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete this contact!", "name = " + selectedContact.getName()));
        }
    }

    public void createContact() {
        System.out.println("============== createContact ============= " + newContact.getName());
        try {
            bean.create(newContact);
            initListandNewContact();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Contact is created successfully", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot create this contact", "");
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

    public void refreshList() {
        initDataList();
    }
}
