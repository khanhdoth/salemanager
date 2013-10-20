package com.hatde.salemanager.reports;

import com.hatde.salemanager.entities.PaymentTransaction;
import com.hatde.salemanager.entities.ProductTransaction;
import com.hatde.salemanager.entities.SaleItem;
import java.util.HashMap;

/**
 *
 * @author Do
 */
public abstract class StockReport extends AbstractReport<ProductTransaction, SaleItem>{
    public StockReport() {        
    }

    @Override
    public HashMap<String, String> getVariableMap(ProductTransaction entity) {
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
}
