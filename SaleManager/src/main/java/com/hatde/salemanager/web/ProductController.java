package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Product;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.ProductFacadeREST;
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
    
    @Override
    public String getDialogTitle() {
        return dialogMode == DialogMode.CREATE
                ? bundleBean.getBundle().getString("Product_NewProduct")
                : bundleBean.getBundle().getString("Product_EditProduct");

    }
}
