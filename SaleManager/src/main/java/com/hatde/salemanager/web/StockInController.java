package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Buy;
import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.BuyFacadeREST;
import com.hatde.salemanager.services.ContactFacadeREST;
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
    private BuyFacadeREST bean;

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
    public void initNewT() {
        super.initNewT();
        newT.setDate(new Date());
        newT.setPayment(new PaymentSent());
    }
}
