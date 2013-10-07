/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ContactFacadeREST;
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
public class ReceiptController extends FacadeContactController<PaymentReceived> implements Serializable {

    @EJB
    private PaymentReceivedFacadeREST bean;

    @EJB
    private ContactFacadeREST contactBean;

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
    public void doCreate() {
        bean.create(newT, contact);
        tempViewContact();
    }

    @Override
    public void doDelete() {
        super.doDelete();
        tempViewContact();
    }

    public void tempViewContact() {
        Contact contact2 = contactBean.find(contact.getContactId());
        System.out.println(contact2.toString());
    }

    @Override
    public void initNewT() {
        super.initNewT();
        newT.setDate(new Date());
    }
}
