package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ContactFacadeREST;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Do
 */
@Named(value = "contactController")
@SessionScoped
public class ContactController extends FacadeController<Contact> implements Serializable {
    private Contact viewT = new Contact();
    private final String updateReportString = ":businessContactForm:iPGBusinessContactList, :businessContactForm:apContactDetail:iStockOutTable, :businessContactForm:apContactDetail:iStockInTable, :businessContactForm:apContactDetail:iPaymentTable, :businessContactForm:apContactDetail:iReceiptTable";

    @EJB
    private ContactFacadeREST bean;

    @Inject
    private BundleBean bundleBean;

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

    @Override
    public String getDialogTitle() {
        return dialogMode == DialogMode.CREATE
                ? bundleBean.getBundle().getString("Contact_NewContact")
                : bundleBean.getBundle().getString("Contact_EditContact");

    }

    public String getUpdateReportString() {
        return updateReportString;
    }
    
    public double getBusinessStockInAmount() {
        if (list == null) {
            return 0;
        }
        double amount = 0;
        for (Contact c : getList()) {
            amount += c.getSaleAmount();
        }
        return amount;
    }

    public double getBusinessStockOutAmount() {
        if (list == null) {
            return 0;
        }
        double amount = 0;

        for (Contact c : getList()) {
            amount += c.getBuyAmount();
        }
        return amount;
    }

    public double getBusinessPaymentAmount() {
        if (list == null) {
            return 0;
        }
        double amount = 0;
        for (Contact c : getList()) {
            amount += c.getPaymentReceivedAmount();
        }
        return amount;
    }

    public double getBusinessReceiptAmount() {
        if (list == null) {
            return 0;
        }
        double amount = 0;
        for (Contact c : getList()) {
            amount += c.getPaymentSentAmount();
        }
        return amount;
    }

    public double getBusinessDebit() {
        if (list == null) {
            return 0;
        }
        double amount = 0;
        for (Contact c : getList()) {
            amount += c.getCredit();
        }
        return amount;
    }

    public double getBusinessCredit() {
        if (list == null) {
            return 0;
        }
        double amount = 0;
        for (Contact c : getList()) {
            amount += c.getDebit();
        }
        return amount;
    }

    public double getBusinessBalance() {
        return (getBusinessCredit() - getBusinessDebit());
    }

    public Contact getViewT() {
        return viewT;
    }

    public void setViewT(Contact viewT) {
        this.viewT = viewT;
    }
    
    public void refreshViewT() {
        if(viewT instanceof Contact){
            viewT = bean.find(viewT.getContactId());
        }
    }
    
    public void hello() {
        System.out.println("+++++++++++++++++++++++++++++++ Hello! ++++++++++++++++++++++++++++++++++++++++++");
    }
    
    
    
}
