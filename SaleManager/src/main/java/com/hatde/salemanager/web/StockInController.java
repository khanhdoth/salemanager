package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.Buy;
import com.hatde.salemanager.entities.PaymentSent;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.BuyFacadeREST;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Khanh
 */
@Named(value = "stockInController")
@ViewScoped
public class StockInController extends FacadeController<Buy> implements Serializable {
    private SaleItem selectedSI;        

    @EJB
    private BuyFacadeREST bean;

    @Inject
    private ContactController contactControllerBean;

    @Inject
    private ProductController productControllerBean;

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
    public void initNewT() {
        super.initNewT();
        newT.setDate(new Date());
        newT.setPayment(new PaymentSent());
        for (int i = 0; i < 3; i++) {
            addSaleItem();
        }
    }

    public void onSICellEdit(CellEditEvent event) {
        
    }

    public void setEdit() {
        refresh();
        super.setEdit();
    }

    public void deleteSI() {
        newT.getListOfSaleItem().remove(selectedSI);
    }

    public SaleItem getSelectedSI() {
        return selectedSI;
    }

    public void setSelectedSI(SaleItem selectedSI) {
        this.selectedSI = selectedSI;
    }
    
    public void refresh() {
        contactControllerBean.refreshList();
        productControllerBean.refreshList();
    }

    public void addSaleItem() {
        newT.getListOfSaleItem().add(new SaleItem());
    }

    public void processPrice() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        int rowIndex = Integer.parseInt(externalContext.getRequestParameterMap().get("rowIndex"));        
        SaleItem si = (SaleItem)((List) newT.getListOfSaleItem()).get(rowIndex);
        si.setPrice((float) si.getProduct().getPriceIn());
    }

    @Override
    public String getDialogTitle() {
        return dialogMode == DialogMode.CREATE
                ? bundleBean.getBundle().getString("StockIn_NewStockIn")
                : bundleBean.getBundle().getString("StockIn_EditStockIn");

    }
}
