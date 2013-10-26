package com.hatde.salemanager.web;

import com.hatde.salemanager.reports.BuyReport;
import com.hatde.salemanager.reports.SaleReport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Khanh
 */
@Named(value = "bundleBean")
@ViewScoped
public class BundleBean implements Serializable {

    @Inject
    SaleReport saleReport;
    @Inject
    BuyReport buyReport;

    private ResourceBundle bundle;
    private List updateStrings = Arrays.asList("menuForm", "saleListForm", "buyListForm", "receiptListForm", "productListForm", "paymentListForm", "mainForm", "createSaleForm", "createBuyForm", "createReceiptForm", "createProductForm", "createPaymentForm", "createContactForm", "ContactListForm", "businessListForm", "businessContactForm");

    @PostConstruct
    public void init() {
        bundle = ResourceBundle.getBundle("lang", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        saleReport.init();
        buyReport.init();
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void changeLanguageVI() {
        setLanguage("vi");
    }

    public void changeLanguageEN() {
        setLanguage("en");
    }

    public void setLanguage(String language) {
        String currentLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
        if (!currentLanguage.equals(language)) {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
            init();
            RequestContext.getCurrentInstance().update(updateStrings);
        }
    }
}
