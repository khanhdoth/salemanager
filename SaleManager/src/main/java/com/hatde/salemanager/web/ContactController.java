package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.AbstractFacade;
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
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Do
 */
@Named(value = "contactController")
@SessionScoped
public class ContactController extends FacadeController<Contact> implements Serializable {
    @EJB
    private ContactFacadeREST bean;

    @Inject
    private BundleBean bundleBean;

    /*
    private List<Contact> list;
    private List<Contact> filterList;
    private Contact selectedContact;
    private Contact newContact;
    private ResourceBundle bundle;
    */
    
    @PostConstruct
    public void init() {
        super.init(Contact.class);
    }

    public ContactController() {        
    }

    @Override
    protected AbstractFacade getBean() {
        return bean;
    }

    @Override
    protected BundleBean getBundleBean() {
        return bundleBean;
    }
    
    /*
    public void initFirstListandNewContact() {
        list = new ArrayList();;
        initNewContact();
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Edit_Success"), "name = " + c.getName()));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Edit_Failed_Message"), "name = " + c.getName()));
            }
        }
    }

    public void delete() {
        System.out.println("============== delete =============");

        try {
            bean.remove(selectedContact);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Delete_Success"), ""));
            initDataList();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Delete_Failed_Message"), "name = " + selectedContact.getName()));
        }
    }

    public void createContact() {
        System.out.println("============== createContact ============= " + newContact.getName());
        try {
            bean.create(newContact);
            initListandNewContact();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("Create_Success"), ""));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("Create_Failed_Message"), ""));
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
    */
    
    
}
