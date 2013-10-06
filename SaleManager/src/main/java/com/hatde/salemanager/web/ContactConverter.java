package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@FacesConverter("contact")
public class ContactConverter implements Converter {
    @Inject 
    private ContactFacadeREST bean;
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        //return bean.getList().get(Integer.parseInt(value));
        System.out.println("value=" + value);
        return bean.findAll().get(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
       if(value instanceof Contact) 
            return "" + ((Contact) value).getContactId();
        else
            return null;
    }
}
