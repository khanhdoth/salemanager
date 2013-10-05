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
}
