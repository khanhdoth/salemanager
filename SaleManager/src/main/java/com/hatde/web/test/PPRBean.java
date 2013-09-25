package com.hatde.web.test;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

@Named(value = "pprBean")
@SessionScoped
public class PPRBean implements Serializable {

    private String firstname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void viewCars() {
        System.out.println("***** viewCard *****");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("isValid", true);
    }

}
