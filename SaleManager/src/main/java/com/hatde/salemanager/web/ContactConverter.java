package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Contact;
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
@FacesConverter("contactConverter")
public class ContactConverter implements Converter {

    @Inject    
    private ContactController bean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        System.out.println("===========getAsObject==========");
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);
                List<Contact> contacts = bean.getListOne();    
                for (Contact c : contacts) {
                    if (c.getContactId() == number) {
                        System.out.println("===========getAsObject mached:" + c.getName());
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
        System.out.println("===========getAsString==========");
        if (value instanceof Contact) {
            System.out.println("===========getAsString mached:" + ((Contact) value).getName());
            return "" + ((Contact) value).getContactId();
        } else {
            return null;
        }
    }
    
}
