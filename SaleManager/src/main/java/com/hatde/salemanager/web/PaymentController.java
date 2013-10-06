/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ContactFacadeREST;
import com.hatde.salemanager.services.PaymentSentFacadeREST;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Khanh
 */
@Named(value = "paymentController")
@SessionScoped
public class PaymentController extends FacadeContactController<PaymentSent> implements Serializable {

    @EJB
    private PaymentSentFacadeREST bean;

    @EJB
    private ContactFacadeREST contactBean;

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
    protected ContactFacadeREST getContactBean() {
        return contactBean;
    }

    @Override
    public void create() {
        contact = contactBean.find(1);

        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            ((PaymentSent) newT).setDate(df.parse("10.11.2013"));
        } catch (ParseException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        newT.setContact(contact);
        contact.getListOfPaymentReceived().add(newT);
        super.create();
    }
}
