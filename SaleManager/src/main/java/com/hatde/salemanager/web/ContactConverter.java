package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
import com.hatde.salemanager.services.ContactFacadeREST;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@FacesConverter("contact")
public class ContactConverter implements Converter {

    @Inject
    private ContactFacadeREST bean;

    /*@Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        //return bean.getList().get(Integer.parseInt(value));
        System.out.println("===========getAsObject==========");
        System.out.println("value=" + value.toString());
        Object o = bean.findAll().get(Integer.parseInt(value));
        System.out.println("===========getAsObject======return: " + o.toString());
        return o;
    }*/
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        System.out.println("===========getAsObject==========");
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);
                List<Contact> contacts = bean.findAll();    
                for (Contact c : contacts) {
                    if (c.getContactId() == number) {
                        return c;
                    }
                }
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        System.out.println("===========getAsString==========" + value.toString());
        if (value instanceof Contact) {
            return "" + ((Contact) value).getContactId();
        } else {
            return null;
        }
    }
    
}
