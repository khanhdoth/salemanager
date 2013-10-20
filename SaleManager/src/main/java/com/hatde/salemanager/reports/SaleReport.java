/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hatde.salemanager.reports;

import com.hatde.salemanager.entities.PaymentTransaction;
import com.hatde.salemanager.entities.Sale;
import com.hatde.salemanager.entities.SaleItem;
import com.hatde.salemanager.web.BundleBean;
import com.hatde.salemanager.web.ReportBean;
import com.hatde.salemanager.web.StockOutController;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
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
public class SaleReport extends AbstractReport<Sale, SaleItem> implements Serializable {

    @Inject
    private BundleBean bundleBean;

    @Inject
    private StockOutController stockOutController;

    @Override
    public void init() {
        PathSalesOrderTemplate = bundleBean.getBundle().getString("PathSalesOrderTemplate");
        SalesOrderTableIndex = bundleBean.getBundle().getString("SalesOrderTableIndex");
        SalesOrderRows = bundleBean.getBundle().getString("SalesOrderRows");
        SalesOrderDownloadName = bundleBean.getBundle().getString("SalesOrderDownloadName");
        
        try {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(new File(PathSalesOrderTemplate));
            factory = Context.getWmlObjectFactory();
        } catch (Docx4JException ex) {
            Logger.getLogger(ReportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SaleReport() {        
    }

    @Override
    public HashMap<String, String> getVariableMap(Sale entity) {
        HashMap<String, String> variableFill = new HashMap<>();
        variableFill.put("=SID", Integer.toString(entity.getProductTransactionId()));
        variableFill.put("=DATE", customFormatDate(entity.getDate()));
        variableFill.put("=Contact Name", entity.getContact().getName());
        variableFill.put("=Subtotal", customFormatNumber(amountFormat, entity.getSubTotal()));
        variableFill.put("=ADiscountrate", customFormatNumber(percentFormat, entity.getDiscount() / 100));
        variableFill.put("=ADiscount", customFormatNumber(amountFormat, entity.getAmountDiscount()));
        variableFill.put("=TAXRATE", customFormatNumber(percentFormat, entity.getVAT() / 100));
        variableFill.put("=Tax", customFormatNumber(amountFormat, entity.getAmountVAT()));
        variableFill.put("=Total", customFormatNumber(amountFormat, entity.getAmount()));
        variableFill.put("=Paid", (entity.getPayment() instanceof PaymentTransaction) ? ("-" + customFormatNumber(amountFormat, entity.getPayment().getAmount())) : "");
        variableFill.put("=BalanceDue", customFormatNumber(amountFormat, entity.getAmountAfterPayment()));        

        return variableFill;
    }

    @Override
    public HashMap<String, String> getTableMap(SaleItem entityDetail) {
        HashMap<String, String> tableFill = new HashMap<>();
        tableFill.put("Quantity", customFormatNumber(quantityFormat, entityDetail.getQuantity()));
        tableFill.put("ItemName", entityDetail.getProduct().getName());
        tableFill.put("Price", customFormatNumber(amountFormat, entityDetail.getPrice()));
        tableFill.put("Discount", customFormatNumber(percentFormat, entityDetail.getDiscount() / 100));
        tableFill.put("LineTotal", customFormatNumber(amountFormat, entityDetail.getAmount()));

        return tableFill;
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
