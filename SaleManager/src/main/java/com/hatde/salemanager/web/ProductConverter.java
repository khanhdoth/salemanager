package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Product;
import com.hatde.salemanager.services.ProductFacadeREST;
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
@FacesConverter("productConverter")
public class ProductConverter implements Converter {

    @Inject
    private ProductController bean;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        System.out.println("=========== Product getAsObject==========");
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);                
                List<Product> products = bean.getListOne();    
                for (Product p : products) {
                    if (p.getProductId() == number) {
                        System.out.println("===========getAsObject mached:" + p.getName());
                        return p;
                    }
                }
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid product"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        System.out.println("=========== Product getAsString==========");
        if (value instanceof Product) {
            System.out.println("===========getAsString mached:" + ((Product) value).getName());
            return "" + ((Product) value).getProductId();
        } else {
            return null;
        }
    }    
}
