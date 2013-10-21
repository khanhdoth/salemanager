/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.reports;

import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.web.BundleBean;
import com.hatde.salemanager.web.StockOutController;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 *
 * @author Do
 */
@Named(value = "saleReport")
@SessionScoped
public class SaleReport extends StockReport implements Serializable {

    @Inject
    private BundleBean bundleBean;

    @Inject
    private StockOutController stockOutController;   

    @Override
    public void init() {
        PathTemplate = bundleBean.getBundle().getString("PathSalesOrderTemplate");
        TableIndex = bundleBean.getBundle().getString("SalesOrderTableIndex");
        Rows = bundleBean.getBundle().getString("SalesOrderRows");
        DownloadName = bundleBean.getBundle().getString("SalesOrderDownloadName");
        
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(PathTemplate);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
            factory = Context.getWmlObjectFactory();
        } catch (Docx4JException ex) {
            Logger.getLogger(SaleReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SaleReport() {
    }

    @Override
    public Sale getEntity() {
        id = stockOutController.getSelectedT().getProductTransactionId();
        return stockOutController.getSelectedT();
    }

    @Override
    public Collection<SaleItem> getDetails() {
        return stockOutController.getSelectedT().getListOfSaleItem();
    }

}
