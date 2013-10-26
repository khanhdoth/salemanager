package com.hatde.salemanager.reports;

import com.hatde.salemanager.entities.Buy;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.web.BundleBean;
import com.hatde.salemanager.web.StockInController;
import java.io.Serializable;
import java.util.Collection;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Do
 */
@Named(value = "buyReport")
@ViewScoped
public class BuyReport extends StockReport implements Serializable {

    @Inject
    private BundleBean bundleBean;

    @Inject
    private StockInController stockInController;

    @Override
    public void init() {
        PathTemplate = bundleBean.getBundle().getString("PathBuysOrderTemplate");
        TableIndex = bundleBean.getBundle().getString("BuysOrderTableIndex");
        Rows = bundleBean.getBundle().getString("BuysOrderRows");
        DownloadName = bundleBean.getBundle().getString("BuysOrderDownloadName");
    }

    public BuyReport() {        
    }   

    @Override
    public Buy getEntity() {
        id = stockInController.getSelectedT().getProductTransactionId();
        return stockInController.getSelectedT();
    }

    @Override
    public Collection<SaleItem> getDetails() {
        return stockInController.getSelectedT().getListOfSaleItem();
    }
    
}

