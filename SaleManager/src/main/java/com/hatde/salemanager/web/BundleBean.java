package com.hatde.salemanager.web;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Khanh
 */
@Named(value = "bundleBean")
@SessionScoped
public class BundleBean implements Serializable {

    private ResourceBundle bundle;

    @PostConstruct
    public void init() {
        bundle = ResourceBundle.getBundle("lang", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void updateClient(final String updateString) {
        RequestContext.getCurrentInstance().update(updateString);
    }
}
