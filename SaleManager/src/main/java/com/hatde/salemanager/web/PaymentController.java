/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
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
        newT.setContact(contact);
        contact.getListOfPaymentReceived().add(newT);
        super.create();

        Contact contact2 = contactBean.find(1);
        System.out.println(contact2.toString());
    }

    @Override
    public void delete() {
        contact = ((PaymentSent) selectedT).getContact();
        contact.getListOfPaymentReceived().remove(selectedT);
        super.delete();

        Contact contact2 = contactBean.find(contact.getContactId());
        System.out.println(contact2.toString());
    }
}
