package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ContactFacadeREST;
import com.hatde.salemanager.services.PaymentSentFacadeREST;
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
