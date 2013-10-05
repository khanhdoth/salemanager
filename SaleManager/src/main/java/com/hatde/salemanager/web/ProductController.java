package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.entities.Product;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ProductFacadeREST;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Do
 */
@Named(value = "productController")
@SessionScoped
public class ProductController extends FacadeController<Product> implements Serializable {

    @EJB
    private ProductFacadeREST bean;

    @Inject
    private BundleBean bundleBean;

    @PostConstruct
    public void init() {
        super.init(Product.class);
    }

    public ProductController() {
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
