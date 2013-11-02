package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.SaleFacadeREST;
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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Khanh
 */
@Named(value = "stockOutController")
@ViewScoped
public class StockOutController extends FacadeController<Sale> implements Serializable {

    private SaleItem selectedSI;

    @EJB
    private SaleFacadeREST bean;

    @Inject
    private ContactController contactControllerBean;

    @Inject
    private ProductController productControllerBean;

    @Inject
    private BundleBean bundleBean;

    @PostConstruct
    public void init() {
        super.init(Sale.class);
    }

    public StockOutController() {
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
        newT.setPayment(new PaymentReceived());
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
        si.setPrice((float) si.getProduct().getPriceOut());
    }

    @Override
    public String getDialogTitle() {
        return dialogMode == FacadeController.DialogMode.CREATE
                ? bundleBean.getBundle().getString("StockOut_NewStockOut")
                : bundleBean.getBundle().getString("StockOut_EditStockOut");

    }
}
