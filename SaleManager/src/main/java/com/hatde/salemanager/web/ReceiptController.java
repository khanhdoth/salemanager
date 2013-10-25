/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.PaymentReceivedFacadeREST;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Khanh
 */
@Named(value = "receiptController")
@SessionScoped
public class ReceiptController extends FacadeController<PaymentReceived> implements Serializable {

    @EJB
    private PaymentReceivedFacadeREST bean;
    
    @Inject
    private ContactController contactControllerBean;

    @Inject
    private BundleBean bundleBean;

    @PostConstruct
    public void init() {
        super.init(PaymentReceived.class);
    }

    public ReceiptController() {
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
                ? bundleBean.getBundle().getString("Receipt_NewReceipt")
                : bundleBean.getBundle().getString("Receipt_EditReceipt");

    }

}
