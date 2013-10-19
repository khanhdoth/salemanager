package com.hatde.salemanager.web;

import com.hatde.salemanager.entities.PaymentReceived;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.services.AbstractFacade;
import com.hatde.salemanager.services.SaleFacadeREST;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Khanh
 */

@Named(value = "stockOutController")
@SessionScoped
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

    @Inject
    private ReportBean reportBean;
    
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
        System.out.println("Stock-out onSICellEdit");
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        SaleItem c = (SaleItem) ((DataTable) event.getSource()).getRowData();

        System.out.println(c.getProduct().getName() + " " + c.getQuantity()
                + " X " + c.getPrice() + " _ " + c.getDiscount()
                + "% = " + c.getAmount());
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

    public void print() {
        reportBean.createStockOutForm(selectedT);
    }

    @Override
    public String getDialogTitle() {
        return dialogMode == FacadeController.DialogMode.CREATE
                ? bundleBean.getBundle().getString("StockOut_NewStockOut")
                : bundleBean.getBundle().getString("StockOut_EditStockOut");

    }
}
