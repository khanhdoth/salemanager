package com.hatde.salemanager.web;

import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Khanh
 */
@Named(value = "bundleBean")
@SessionScoped
public class BundleBean implements Serializable {
    private ResourceBundle bundle;
    private String updateString=":menuForm, :saleListForm, :buyListForm, :receiptListForm, :productListForm, :paymentListForm, :mainForm, :createSaleForm, :createBuyForm, :createReceiptForm, :createProductForm, :createPaymentForm, :createContactForm, :ContactListForm, :businessListForm, :businessContactForm";

    @PostConstruct
    public void init() {
        bundle = ResourceBundle.getBundle("lang", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getUpdateString() {
        return updateString;
    }

    public void setUpdateString(String updateString) {
        this.updateString = updateString;
    }    
    
    public void updateClient(final String updateString) {
        RequestContext.getCurrentInstance().update(updateString);
    }

    public void changeLanguageVI() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("vi"));
        init();
    }

    public void changeLanguageEN() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("en"));
        init();
    }

}
