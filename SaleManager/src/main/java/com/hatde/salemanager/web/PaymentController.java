package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.PaymentSentFacadeREST;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;

/**
 *
 * @author Khanh
 */
@Named(value = "paymentController")
@ViewScoped
public class PaymentController extends FacadeController<PaymentSent> implements Serializable {

    @EJB
    private PaymentSentFacadeREST bean;
    
    @Inject
    private ContactController contactControllerBean;

    @Inject
    private BundleBean bundleBean;

    @PostConstruct
    public void init() {
        super.init(PaymentSent.class);
    }

    public PaymentController() {
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
    public void setEdit() {
        contactControllerBean.refreshList();
        super.setEdit();
    }

    @Override
    public void initNewT() {
        super.initNewT();
        newT.setDate(new Date());
    }

    @Override
    public String getDialogTitle() {
        return dialogMode == DialogMode.CREATE
                ? bundleBean.getBundle().getString("Payment_NewPayment")
                : bundleBean.getBundle().getString("Payment_EditPayment");

    }
}
