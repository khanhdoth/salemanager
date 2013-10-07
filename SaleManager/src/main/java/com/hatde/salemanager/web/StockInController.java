package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Buy;
import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.entities.PaymentTransaction;
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
@Named(value = "stockInController")
@SessionScoped
public class StockInController extends FacadeContactController<Buy> implements Serializable {

    @EJB
    private PaymentSentFacadeREST bean;

    @EJB
    private ContactFacadeREST contactBean;

    @Inject
    private BundleBean bundleBean;

    @PostConstruct
    public void init() {
        super.init(Buy.class);
    }

    public StockInController() {
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
        System.out.println("---create Sale---- " + contact.getName());
        
        PaymentSent ps = (PaymentSent) newT.getPayment();
        if(ps.getAmount()>0){
            ps.setDate(newT.getDate());
            ps.setContact(contact);
            contact.getListOfPaymentReceived().add(ps);
        } else {
            ps = null;
        }
        
        newT.setContact(contact);
        contact.getListOfSale().add(newT);
        super.create();

        Contact contact2 = contactBean.find(contact.getContactId());
        System.out.println(contact2.toString());
    }

    @Override
    public void delete() {
        contact = ((Buy) selectedT).getContact();
        contact.getListOfSale().remove(selectedT);
        super.delete();

        Contact contact2 = contactBean.find(contact.getContactId());
        System.out.println(contact2.toString());
    }
    
    @Override
    public void initNewT() {
        super.initNewT();
        newT.setDate(new Date());
        newT.setPayment(new PaymentSent());
    }
}