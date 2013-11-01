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
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Khanh
 */
@Named(value = "stockInController")
@ViewScoped
public class StockInController extends FacadeController<Buy> implements Serializable {
    private SaleItem selectedSI;    
    private String testString = "";

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

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
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
        String param = externalContext.getRequestParameterMap().get("testParam");
        int rowIndex = Integer.parseInt(externalContext.getRequestParameterMap().get("rowIndex"));
        
        //SaleItem si = (SaleItem) event.getComponent().getAttributes().get("si");
        System.out.println("New product selected: " + (selectedSI == null ? "null" : selectedSI.getProduct().getName()));
        System.out.println("******* Test param: " + param);
        System.out.println("******* rowIndex: " + rowIndex);
        
        SaleItem si = (SaleItem)((List) newT.getListOfSaleItem()).get(rowIndex);
        si.setPrice((float) si.getProduct().getPriceIn());
        
        //System.out.println("New product selected2: " + si.getProduct().getName());
        //si.setPrice((float) si.getProduct().getPriceIn());
        
        /*
        for (SaleItem si : newT.getListOfSaleItem()) {
            si.setPrice((float) (si.getProduct() == null ? 0 : si.getProduct().getPriceIn()));
        }*/
    }

    @Override
    public String getDialogTitle() {
        return dialogMode == DialogMode.CREATE
                ? bundleBean.getBundle().getString("StockIn_NewStockIn")
                : bundleBean.getBundle().getString("StockIn_EditStockIn");

    }
}
